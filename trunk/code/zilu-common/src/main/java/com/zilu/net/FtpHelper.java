package com.zilu.net;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.zilu.util.Strings;

public class FtpHelper {

	private String server;

	private String username;

	private String password;
	
	private boolean binaryTransfer = false;
	
	private FTPClient ftp;
	
	private int port = 21;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public FtpHelper() {
		ftp = new FTPClient();
	}

	public void connect() throws IOException {
		if (Strings.isEmpty(server)) {
			throw new IOException("server is not all null");
		}
		if (Strings.isEmpty(username)) {
			throw new IOException("username is not all null");
		}
		if (Strings.isEmpty(password)) {
			throw new IOException("password is not all null");
		}
		try {
			ftp.connect(server, port);
			int reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				throw new IOException("FTP server refused connection.");
			}
		} catch (Exception e) {
			if (ftp.isConnected()) {
				ftp.disconnect();
				throw new IOException("FTP server refused connection.");
			}
		}
		if (!ftp.login(username, password)) {
			ftp.logout();
			throw new IOException("FTP server refused login");
		}
	}
	
	public void read(String remote, String local) throws IOException {
		if (binaryTransfer) {
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
		}
		ftp.enterLocalPassiveMode();
		OutputStream output;

		output = new FileOutputStream(local);

		ftp.retrieveFile(remote, output);

        output.close();
	}
	
	public void disconnet() throws IOException {
		ftp.disconnect();
	}

	public boolean isBinaryTransfer() {
		return binaryTransfer;
	}

	public void setBinaryTransfer(boolean binaryTransfer) {
		this.binaryTransfer = binaryTransfer;
	}

	public String getUrl() {
		return server;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}
	
	public static void main(String args[]) {
		FtpHelper helper = new FtpHelper();
		helper.setServer("218.85.135.153");
//		helper.setPort(8888);
		helper.setUsername("becp");
		helper.setPassword("becpbecp");
		try {
			helper.connect();
			helper.ftp.setControlEncoding("GBK");
//			helper.ftp.changeWorkingDirectory("spcheck");
			FTPFile[] files = helper.ftp.listFiles();
			for (FTPFile file: files) {
				System.out.println(file.getName());
			}
			helper.disconnet();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
