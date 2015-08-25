package com.zilu.util.mail;

public class MailUser
{
//	是否验证
	private boolean validate = true;
	
//	是否使用ssl
	private boolean ssl = false;
	
//	用户名
	private String userName;
	
//	密码
	private String password;
	
//	邮箱地址
	private String mailAddress;
	
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public boolean isValidate() {
		return validate;
	}
	public void setValidate(boolean validate) {
		this.validate = validate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String passWord) {
		this.password = passWord;
	}
	public boolean isSsl() {
		return ssl;
	}
	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}



}
