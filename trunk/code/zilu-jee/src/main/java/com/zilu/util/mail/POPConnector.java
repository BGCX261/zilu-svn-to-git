package com.zilu.util.mail;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Flags.Flag;

/**
 * pop邮件接收器
 * @author 陈华敏
 * @Time 
 * @Description
 */
public class POPConnector
{
	private Store store;
	private Folder folder;
	private MailServer server;
	private MailUser user;
	private Message[] messages;
	
	public POPConnector(MailServer server, MailUser user) {
		this.server = server;
		this.user = user;
	}
	
//	接收信息
	public Message[] listMessages()
	{
		try {
			this.messages = this.folder.getMessages();
			return this.messages;
		}
		catch (Exception e) {
			throw new MailException(e);
		}
	}
	
	/**
	 * 开启连接
	 */
	public void openConnection()
	{
		try {
			Session session = Session.getDefaultInstance(new Properties());
			
			this.store = session.getStore(this.user.isSsl() ? "pop3s" : "pop3");

			this.store.connect(this.server.getPopHost(), 
					this.server.getPopPort(), 
					this.user.getUserName(),
					this.user.getPassword());
			
			this.folder = this.store.getFolder("INBOX");
			
			if (folder == null) {
				throw new Exception("No Inbox");
			}
			
			this.folder.open(Folder.READ_WRITE);
		}
		catch (Exception e) {
			throw new MailException(e);
		}
	}
	
	public void closeConnection()
	{
		boolean deleteMessages = true;
		this.closeConnection(deleteMessages);
	}
	
	public void closeConnection(boolean deleteAll)
	{
		if (deleteAll) {
			this.markAllMessagesAsDeleted();
		}
		
		if (this.folder != null) try { this.folder.close(false); } catch (Exception e) {}
		if (this.store != null) try { this.store.close(); } catch (Exception e) {}
	}
	
	/**
	 * 删除邮件（标示未删除）
	 */
	private void markAllMessagesAsDeleted()
	{
		try {
			if (this.messages != null) {
				for (int i = 0; i < this.messages.length; i++) {
					this.messages[i].setFlag(Flag.DELETED, true);
				}
			}
		}
		catch (Exception e) {
			throw new MailException(e);
		}
	}
}
