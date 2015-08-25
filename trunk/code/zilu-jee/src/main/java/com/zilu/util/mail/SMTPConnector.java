package com.zilu.util.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.zilu.util.Strings;


public class SMTPConnector {

	private MailServer server;

	private MailUser user;

	private Session session;

	private Transport transport;

	public SMTPConnector(MailServer server, MailUser user) {
		this.server = server;
		this.user = user;

		boolean ssl = user.isSsl();
		
//		初始化连接属性
		String hostProperty = this.hostProperty(ssl);
		String portProperty = this.portProperty(ssl);
//		是否鉴权
		String authProperty = this.authProperty(ssl);

		Properties mailProps = new Properties();
		mailProps.put(hostProperty, server.getSmtpHost());
		mailProps.put(portProperty, server.getSmtpPort());

		mailProps.put("mail.mime.address.strict", "false");
		mailProps.put("mail.mime.charset", server.getCharset());
		mailProps.put(authProperty, String.valueOf(user.isValidate()));

		this.session = Session.getInstance(mailProps);
	}

	/**
	 * 连接到服务器（仅仅对于需要鉴权的）
	 * @return
	 */
	public boolean connect() {
		try {
			if (!Strings.isEmpty(user.getUserName())
					&& !Strings.isEmpty(user.getPassword())) {
				boolean ssl = user.isSsl();

				transport = this.session.getTransport(ssl ? "smtps" : "smtp");

				String host = server.getSmtpHost();
				transport.connect(host, user.getUserName(), user.getPassword());

				if (transport.isConnected()) {
					return true;
				} else {
					return false;
				}

			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		transport = null;
		return false;
	}

	/**
	 * 发送邮件
	 * @param mailInfo
	 */
	public void sendMessage(MailInfo mailInfo) {
		try {
			if (mailInfo.getToMails() == null) {
				return;
			}
			if (user.isValidate()) {
				boolean close = false;
				try {
					if (transport == null) {
						close = true;
						if (!connect()) {
							throw new MailException(
									"can't not conect to the sever");
						}
					}
					if (transport.isConnected()) {
						Message message = prepareMessage(mailInfo);
						
						transport.sendMessage(message, message.getAllRecipients());
					}
				} finally {
					if (close&& transport != null) {
						transport.close();
						transport = null;
					}
				}
			} else {
				Message message = prepareMessage(mailInfo);
				Transport.send(message, message.getAllRecipients());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new MailException(e);
		}
	}

	/**
	 * 配置邮件信息
	 * @param mailInfo
	 * @return
	 * @throws MailException
	 */
	protected Message prepareMessage(MailInfo mailInfo) throws MailException {
		MimeMessage message = new MimeMessage(session);

		try {
			message.setSentDate(new Date());
			message.setFrom(new InternetAddress(user.getMailAddress()));
			message.setSubject(mailInfo.getTitle(), server.getCharset());
			
			if (mailInfo.getInReplyTo() != null) {
				message.addHeader("In-Reply-To", mailInfo.getInReplyTo());
			}

			int messageFormat = mailInfo.getMessageFormat();
			if (messageFormat == MailInfo.MESSAGE_HTML) {
				message.setContent(mailInfo.getContent().replaceAll("\n",
						"<br />"), "text/html; charset=" + server.getCharset());
			} else {
				message.setText(mailInfo.getContent());
			}
			
			Address[] address = new Address[mailInfo.getToMails().size()];
			for (int i = 0; i < mailInfo.getToMails().size(); i++) {
				address[i] = new InternetAddress(mailInfo.getToMails().get(i));
				
				message.addRecipient(Message.RecipientType.TO,
						address[i]);
			}
			
			
			if (mailInfo.getCopyMails() != null) {
				Address[] caddress = new Address[mailInfo.getCopyMails().size()];
				for (int i=0; i < mailInfo.getCopyMails().size(); i++ ) {
					caddress[i] = new InternetAddress(mailInfo.getCopyMails().get(i));
					
					message.addRecipient(Message.RecipientType.CC,
							caddress[i]);
				}
			}
			
			if (mailInfo.getBcopyMails() != null) {
				Address[] bcaddress = new Address[mailInfo.getBcopyMails().size()];
				for (int i=0; i < mailInfo.getBcopyMails().size(); i++ ) {
					bcaddress[i] = new InternetAddress(mailInfo.getBcopyMails().get(i));
					
					message.addRecipient(Message.RecipientType.BCC,
							bcaddress[i]);
				}
			}
			
			return message;
		} catch (Exception e) {
			throw new MailException(e);
		}
	}

	private String authProperty(boolean ssl) {
		return ssl ? MailConstants.MAIL_SMTP_SSL_AUTH
				: MailConstants.MAIL_SMTP_AUTH;
	}

	private String portProperty(boolean ssl) {
		return ssl ? MailConstants.MAIL_SMTP_SSL_PORT
				: MailConstants.MAIL_SMTP_PORT;
	}

	private String hostProperty(boolean ssl) {
		return ssl ? MailConstants.MAIL_SMTP_SSL_HOST
				: MailConstants.MAIL_SMTP_HOST;
	}
}
