package com.zilu.util.mail;

import java.util.ArrayList;
import java.util.List;

public class MailServerFactory {
	
	static List<MailServer> servers = new ArrayList<MailServer>();
	
	private static MailServer sinaServer;
	private static MailServer c163Server;
	private static MailServer tomServer;
	private static MailServer qqServer;
	private static MailServer c126Server;
	private static MailServer n263Server;
	private static MailServer sohuServer;
	private static MailServer gmailServer;
	private static MailServer fstiServer;
	
	static {
		sinaServer = new MailServer();
		sinaServer.setPopHost("pop3.sina.com.cn");
		sinaServer.setSmtpHost("smtp.sina.com.cn");
		sinaServer.define(".+@sina.com.cn");
		servers.add(sinaServer);
		
		fstiServer = new MailServer();
		fstiServer.setPopHost("pop3.fsti.com.cn");
		fstiServer.setSmtpHost("smtp.fsti.com.cn");
		fstiServer.define(".+@fsti.com.cn");
		servers.add(fstiServer);
		
		c163Server = new MailServer();
		c163Server.setPopHost("pop.163.com");
		c163Server.setSmtpHost("smtp.163.com");
		c163Server.define(".+@163.com");
		servers.add(c163Server);
		
		tomServer = new MailServer();
		tomServer.setPopHost("pop.tom.com");
		tomServer.setSmtpHost("smtp.tom.com");
		tomServer.define(".+@tom.com");
		servers.add(tomServer);
		
		qqServer = new MailServer();
		qqServer.setPopHost("pop.qq.com");
		qqServer.setSmtpHost("smtp.qq.com");
		qqServer.define(".+@qq.com");
		servers.add(qqServer);
		
		c126Server = new MailServer();
		c126Server.setPopHost("pop3.126.com");
		c126Server.setSmtpHost("smtp.126.com");
		c126Server.define(".+@126.com");
		servers.add(c126Server);
		
		n263Server = new MailServer();
		n263Server.setPopHost("263.net");
		n263Server.setSmtpHost("smtp.263.net");
		n263Server.define(".+@263.net");
		servers.add(n263Server);
		
		sohuServer = new MailServer();
		sohuServer.setPopHost("pop3.sohu.com");
		sohuServer.setSmtpHost("smtp.sohu.com");
		sohuServer.define(".+@sohu.com");
		servers.add(sohuServer);
		
		gmailServer = new MailServer();
		gmailServer.setPopHost("pop.gmail.com");
		gmailServer.setSmtpHost("smtp.gmail.com");
		gmailServer.define(".+@gmail.com");
		servers.add(gmailServer);
	}
	
	public static MailServer findServer(String mailAddress) {
		for (MailServer server: servers) {
			if (server.matchs(mailAddress)) {
				return server;
			}
		}
		return null;
	}

	public static MailServer getSinaServer() {
		return sinaServer;
	}

	public static void setSinaServer(MailServer sinaServer) {
		MailServerFactory.sinaServer = sinaServer;
	}

	public static MailServer getC163Server() {
		return c163Server;
	}

	public static void setC163Server(MailServer server) {
		c163Server = server;
	}

	public static MailServer getTomServer() {
		return tomServer;
	}

	public static void setTomServer(MailServer tomServer) {
		MailServerFactory.tomServer = tomServer;
	}

	public static MailServer getQqServer() {
		return qqServer;
	}

	public static void setQqServer(MailServer qqServer) {
		MailServerFactory.qqServer = qqServer;
	}

	public static MailServer getC126Server() {
		return c126Server;
	}

	public static void setC126Server(MailServer server) {
		c126Server = server;
	}

	public static MailServer getN263Server() {
		return n263Server;
	}

	public static void setN263Server(MailServer server) {
		n263Server = server;
	}

	public static MailServer getSohuServer() {
		return sohuServer;
	}

	public static void setSohuServer(MailServer sohuServer) {
		MailServerFactory.sohuServer = sohuServer;
	}

	public static MailServer getGmailServer() {
		return gmailServer;
	}

	public static void setGmailServer(MailServer gmailServer) {
		MailServerFactory.gmailServer = gmailServer;
	}
	
}
