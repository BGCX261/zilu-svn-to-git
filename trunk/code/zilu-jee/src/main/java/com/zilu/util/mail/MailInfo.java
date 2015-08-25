package com.zilu.util.mail;

import java.util.ArrayList;
import java.util.List;

public class MailInfo {
	
	public static final int MESSAGE_HTML = 0;
	public static final int MESSAGE_TEXT = 1;
	
//	信息格式
	private int messageFormat = MESSAGE_TEXT;
	
//	退信地址
	private String inReplyTo;
	
//	标题
	private String title;
	
//	内容
	private String content;
	
//	目标地址
	private List<String> toMails;
	
//	抄送
	private List<String> copyMails;
	
//	暗送
	private List<String> bcopyMails;

	public int getMessageFormat() {
		return messageFormat;
	}

	public void setMessageFormat(int messageFormat) {
		this.messageFormat = messageFormat;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public String getInReplyTo() {
		return inReplyTo;
	}

	public void setInReplyTo(String inReplyTo) {
		this.inReplyTo = inReplyTo;
	}

	public List<String> getToMails() {
		return toMails;
	}

	public void setToMails(List<String> toMails) {
		this.toMails = toMails;
	}

	public List<String> getCopyMails() {
		return copyMails;
	}

	public void setCopyMails(List<String> copyMails) {
		this.copyMails = copyMails;
	}

	public List<String> getBcopyMails() {
		return bcopyMails;
	}

	public void setBcopyMails(List<String> bcopyMails) {
		this.bcopyMails = bcopyMails;
	}
	
	public void addToMail(String mail) {
		if (toMails == null) {
			toMails = new ArrayList<String>();
		}
		toMails.add(mail);
	}
	
	public void addCopyMail(String mail) {
		if (copyMails == null) {
			copyMails = new ArrayList<String>();
		}
		copyMails.add(mail);
	}
	
	public void addBcopyMail(String mail) {
		if (bcopyMails == null) {
			bcopyMails = new ArrayList<String>();
		}
		bcopyMails.add(mail);
	}
	
}
