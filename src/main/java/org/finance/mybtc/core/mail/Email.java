/**
 * 
 */
package org.finance.mybtc.core.mail;

import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.repo.Base64;

/**
 * @author zongtao liu
 *
 */
public class Email {

	private static Log log = Logs.get();

	private static final String SMPT_HOST_QQ = "smtp.qq.com";
	private static final String SMPT_HOST_163 = "smtp.163.com";

	private volatile long lastSendTime = 0;

	/***
	 * 账号
	 */
	private String account = "";

	/***
	 * 密码
	 */
	private String password = "";

	/***
	 * 账号名
	 */
	private String accountName = "";

	/***
	 * smtp地址
	 */
	private String smtpHost = "";

	/***
	 * 附件地址
	 */
	private String affix = "";
	/***
	 * 附件名称
	 */
	private String affixName = "";

	private Session session;

	public Email(String account, String password, String accountName, String smtpHost) {
		init(account, password, null, smtpHost);
	}

	public Email(String account, String password) {
		init(account, password, null, null);
	}

	public Email(String account, String password, String accountName) {
		init(account, password, accountName, null);
	}

	private void init(String account, String password, String accountName, String smtpHost) {
		if (Strings.isNotBlank(account)) {
			String[] accountInfoArr = account.split("@");
			int infoSize = accountInfoArr.length;
			if (infoSize == 2) {
				String mailAddr = accountInfoArr[1];
				if (mailAddr.equals("163.com")) {
					this.smtpHost = SMPT_HOST_163;
				} else if (mailAddr.equals("qq.com")) {
					this.smtpHost = SMPT_HOST_QQ;
				} else {
					this.smtpHost = smtpHost;
				}
				this.account = account;
				this.password = password;
				if (accountName != null) {
					this.accountName = accountName;
				}
			}
		}

		// 1. 创建参数配置, 用于连接邮件服务器的参数配置
		Properties props = new Properties(); // 参数配置
		props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
		props.setProperty("mail.smtp.host", this.smtpHost); // 发件人的邮箱的 SMTP
															// 服务器地址
		props.setProperty("mail.smtp.auth", "true"); // 需要请求认证

		// PS: 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证 (为了提高安全性, 邮箱支持SSL连接, 也可以自己开启),
		// 如果无法连接邮件服务器, 仔细查看控制台打印的 log, 如果有有类似 “连接失败, 要求 SSL 安全连接” 等错误,
		// 打开下面 /* ... */ 之间的注释代码, 开启 SSL 安全连接。
		/***
		 * SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接, 需要改为对应邮箱的 SMTP
		 * 服务器的端口, 具体可查看对应邮箱服务的帮助, QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
		 ***/
		final String smtpPort = "465";
		props.setProperty("mail.smtp.port", smtpPort);
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.socketFactory.port", smtpPort);

		// 2. 根据配置创建会话对象, 用于和邮件服务器交互
		this.session = Session.getDefaultInstance(props);
		// session.setDebug(true);
	}

	/***
	 * 设置附件
	 * 
	 * @param affix
	 * @param affixName
	 */
	public void setAffix(String affix, String affixName) {
		this.affix = affix;
		this.affixName = affixName;
	}

	/***
	 * 发送邮件
	 * 
	 * @param receiveAccount
	 * @param receiveAccountName
	 * @param mailSubject
	 * @param mailContent
	 * @param bccAccountList
	 * @return
	 */
	public boolean sendMessage(String receiveAccount, String receiveAccountName, String mailSubject, String mailContent,
			Set<String> bccAccountList) {
		long now = System.currentTimeMillis();
		long intervalTime = 3 * 1000;
		if (now - lastSendTime < intervalTime) {
			try {
				Thread.sleep(lastSendTime + intervalTime - now);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		intervalTime = now;
		boolean result = false;
		Transport transport = null;
		// 1.1. 创建一封邮件
		MimeMessage message = null;
		try {
			// 1.1. 创建一封邮件
			message = new MimeMessage(session);
			// 1.2. From: 发件人
			if (Strings.isBlank(accountName)) {
				message.setFrom(new InternetAddress(account));
			} else {
				message.setFrom(new InternetAddress(account, accountName, "UTF-8"));
			}
			// 1.3. To: 收件人（可以增加多个收件人、抄送、密送）
			if (Strings.isBlank(receiveAccountName)) {
				message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveAccount));
			} else {
				message.setRecipient(MimeMessage.RecipientType.TO,
						new InternetAddress(receiveAccount, receiveAccountName, "UTF-8"));
			}
			// To: 收件人（可以增加多个密送）
			if (bccAccountList != null && bccAccountList.size() > 0) {
				for (String bccAccount : bccAccountList) {
					message.addRecipient(MimeMessage.RecipientType.BCC, new InternetAddress(bccAccount));
				}
			}

			// 1.4. Subject: 邮件主题
			message.setSubject(mailSubject, "UTF-8");
			// 1.5. Content: 邮件正文（可以使用html标签）
			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			Multipart multipart = new MimeMultipart();
			// 设置邮件的文本内容
			if (Strings.isNotBlank(mailContent)) {
				BodyPart contentPart = new MimeBodyPart();
				contentPart.setContent(mailContent, "text/html;charset=UTF-8");
				multipart.addBodyPart(contentPart);
			}

			// 添加附件
			if (Strings.isNotBlank(affix)) {
				BodyPart messageBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(affix);
				// 添加附件的内容
				messageBodyPart.setDataHandler(new DataHandler(source));
				// 添加附件的标题
				// 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
				messageBodyPart.setFileName("=?GBK?B?" + Base64.encodeToString(affixName.getBytes(), true) + "?=");
				multipart.addBodyPart(messageBodyPart);
			}
			// 将multipart对象放到message中
			message.setContent(multipart);

			// 1.6. 设置发件时间
			message.setSentDate(new Date());
			// 1.7. 保存设置
			message.saveChanges();

			// 2. 根据 Session 获取邮件传输对象
			transport = session.getTransport();

			// 3. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
			//
			// PS_01: 成败的判断关键在此一句, 如果连接服务器失败, 都会在控制台输出相应失败原因的 log,
			// 仔细查看失败原因, 有些邮箱服务器会返回错误码或查看错误类型的链接, 根据给出的错误
			// 类型到对应邮件服务器的帮助网站上查看具体失败原因。
			//
			// PS_02: 连接失败的原因通常为以下几点, 仔细检查代码:
			// (1) 邮箱没有开启 SMTP 服务;
			// (2) 邮箱密码错误, 例如某些邮箱开启了独立密码;
			// (3) 邮箱服务器要求必须要使用 SSL 安全连接;
			// (4) 请求过于频繁或其他原因, 被邮件服务器拒绝服务;
			// (5) 如果以上几点都确定无误, 到邮件服务器网站查找帮助。
			//
			// PS_03: 仔细看log, 认真看log, 看懂log, 错误原因都在log已说明。
			transport.connect(account, password);

			// 4. 发送邮件, 发到所有的收件地址, message.getAllRecipients()
			// 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
			transport.sendMessage(message, message.getAllRecipients());

			result = true;
		} catch (SendFailedException e) {
			log.error(e.getMessage());
			Address[] invalidAddresses = e.getInvalidAddresses();
			log.error("invalidAddresses is " + Json.toJson(invalidAddresses, JsonFormat.compact()));
			Address[] validUnsentAddresses = e.getValidUnsentAddresses();
			Set<String> unsentSet = new HashSet<String>();
			for (Address validUnsentAddress : validUnsentAddresses) {
				unsentSet.add(validUnsentAddress.toString());
			}
			log.error("unsentSet is " + Json.toJson(unsentSet, JsonFormat.compact()));
			if (invalidAddresses != null) {// 当invalidAddresses为空时，说明邮箱系统出了问题，不在进行发送邮件
				result = sendMessage(receiveAccount, receiveAccountName, mailSubject, mailContent, unsentSet);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (transport != null) {
				// 5. 关闭连接
				try {
					transport.close();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public boolean sendMessage(String receiveAccount, String mailSubject, String mailContent) {
		return sendMessage(receiveAccount, null, mailSubject, mailContent, null);
	}

	/***
	 * 发送邮件
	 * 
	 * @param receiveAccount
	 * @param mailSubject
	 * @param mailContent
	 * @param bccAccountList
	 * @return
	 */
	public boolean sendMessage(String receiveAccount, String mailSubject, String mailContent,
			Set<String> bccAccountList) {
		return sendMessage(receiveAccount, null, mailSubject, mailContent, bccAccountList);
	}

	/***
	 * 发送给自己，密送给其他人
	 * 
	 * @param bccAccountList
	 * @param mailSubject
	 * @param mailContent
	 * @return
	 */
	public boolean sendBccMessage(Set<String> bccAccountList, String mailSubject, String mailContent) {
		return sendMessage(this.account, null, mailSubject, mailContent, bccAccountList);
	}

}
