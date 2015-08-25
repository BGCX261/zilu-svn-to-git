package com.zilu.util.mail;


public class MailFacade {
	
	/**
	 * 简单的发送邮件接口
	 * @param fromEmail
	 * @param toEmail
	 * @param userName
	 * @param psw
	 * @param title
	 * @param content
	 */
	public static void sendSimpleMail(String fromEmail, String toEmail,
			String userName, String psw, String title, String content) {
		MailServer server = MailServerFactory.findServer(fromEmail);
		if (server == null) {
			throw new MailException("server no find for mail address:" + fromEmail);
		}
		MailUser user = new MailUser();
		user.setUserName(userName);
		user.setPassword(psw);
		user.setMailAddress(fromEmail);
		MailInfo mail = new MailInfo();
		mail.setTitle(title);
		mail.setContent(content);
		mail.setMessageFormat(MailInfo.MESSAGE_HTML);
		mail.addToMail(toEmail);
		SMTPConnector connector = new SMTPConnector(server, user);
		connector.sendMessage(mail);
	}
	
	public static void main(String[] args) {
		String src = "http://m94.mail.qq.com/zh_CN/htmledition/images/logo/logo_min_0.gif";
		String html = "<html><head></head><body><h2 style=\"color:red\">测试邮件,这是一封测试邮件</h2><img src='"+ src + "'></img></body></html>";
		sendSimpleMail("system@fsti.com.cn", "33705271@qq.com", "system@fsti.com.cn", "123456", "测试", html);
	}
}
