/**
 * 
 */
package org.finance.mybtc.core.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.configuration.AbstractFileConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.nutz.lang.Files;

/**
 * @author zongtao liu
 *
 */
public class MailFactory {

	private List<Email> myEmailList;

	private static volatile MailFactory mailFactory;
	private static volatile int curIndex = 0;
	
	private static String emailName = "数字货币交易";
	private static HashSet<String> emailAddrSet = new HashSet<String>();

	private MailFactory() {
		File file = Files.findFile("app.properties");
		if(!file.exists()){
			file = Files.findFile("configs/app.properties");
		}
		if(file.exists()){
			try {
				AbstractFileConfiguration config = new PropertiesConfiguration(file);
				if(config.containsKey("emailAcc") && config.containsKey("emailKey")){
					String emailAcc = config.getString("emailAcc");
					String emailKey = config.getString("emailKey");
					Email email = new Email(emailAcc, emailKey, emailName);
					myEmailList.add(email);
					emailAddrSet.add(emailAcc);
				}
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
		}
	}

	public static MailFactory getInstance() {
		if (mailFactory == null) {
			synchronized (MailFactory.class) {
				if (mailFactory == null) {
					mailFactory = new MailFactory();
				}
			}
		}
		return mailFactory;
	}

	/**
	 * 发送邮件
	 * 
	 * @param emailAddrSet
	 * @param teacherName
	 * @param mailContent
	 */
	public synchronized boolean sendEmail(Set<String> emailAddrSet, String info, String mailContent) {
		boolean result = false;
		if (myEmailList != null && myEmailList.size() > 0 && emailAddrSet.size() > 0) {
			List<String> emailAddrList = new ArrayList<String>(emailAddrSet);
			int limitSize = 20;
			int tmpIndex = 0;
			Set<String> tmpEmailAddrSet = getLimit(emailAddrList, tmpIndex, limitSize);
			while (tmpEmailAddrSet != null && tmpEmailAddrSet.size() > 0) {
				Email email = myEmailList.get(curIndex);
				curIndex = (curIndex + 1) % myEmailList.size();
				String mailSubject = emailName + ":" + info;
				result = email.sendBccMessage(tmpEmailAddrSet, mailSubject, mailContent);
				if (!result) {
					// 如果失败，再次发送一遍
					email.sendBccMessage(tmpEmailAddrSet, mailSubject, mailContent);
				}
				tmpIndex += tmpEmailAddrSet.size();
				tmpEmailAddrSet = getLimit(emailAddrList, tmpIndex, limitSize);
			}
		}
		return result;
	}
	
	public synchronized boolean sendEmail(String info, String mailContent) {
		return sendEmail(emailAddrSet, info, mailContent);
	}

	/**
	 * 获取部分信息
	 * 
	 * @param list
	 * @param curIndex
	 * @param limit
	 * @return
	 */
	private Set<String> getLimit(List<String> list, int curIndex, int limit) {
		if (list == null || list.size() == 0) {
			return null;
		}
		int size = list.size();
		if (curIndex >= size) {
			return null;
		}
		int endIndex = curIndex + limit;
		if (endIndex > size) {
			endIndex = size;
		}
		return new HashSet<String>(list.subList(curIndex, endIndex));
	}
}
