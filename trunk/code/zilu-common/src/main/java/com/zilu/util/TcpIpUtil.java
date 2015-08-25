package com.zilu.util;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

public class TcpIpUtil {
	
	/**
	 * 获取IP地址
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if(ip.length()>16){
			ip = ip.substring(0, 16);
		}
		return ip;
	}
	
	/**
	 * IP地址转化成Long型
	 * @return
	 */
	public static long getIpAddressToLong(String ipAddress){
		if (Strings.isEmpty(ipAddress)){
			return -1l;
		}
		String[] ipAddressArray = ipAddress.split(".");
		if (ipAddressArray.length!=4){
			return -1l;
		}
		long lIpAddress = -1l;
		try {
			lIpAddress = 	Integer.parseInt(ipAddressArray[0])*256*256*256 + 
							Integer.parseInt(ipAddressArray[1])*256*256 + 
							Integer.parseInt(ipAddressArray[2])*256 + 
							Integer.parseInt(ipAddressArray[3]) - 1;
		} catch (Exception e) {
			return -1l;
		}
		return lIpAddress;
	}
	
	/**
	 * 获取MAC地址
	 * @return
	 * @throws IOException
	 */
	private final static String getMacAddress() throws IOException {	
		String os = getOSName();
		try {
			if (os.startsWith("windows")) {
				return windowsParseMacAddress(windowsRunIpConfigCommand());
			} else if (os.startsWith("linux")) {
				return linuxParseMacAddress(linuxRunIfConfigCommand());
			} else {
				throw new IOException("unknown operating system: " + os);
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
			throw new IOException(ex.getMessage());
		}
	}
	
    /**
     * Java SE 6.0新特性
     * 获取MAC地址的方法
     * @param ia
     * @return
     * @throws Exception
     
    private static String getMACAddress(InetAddress ia)throws Exception{
        //获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        
        //下面代码是把mac地址拼装成String
        StringBuffer sb = new StringBuffer();
        
        for(int i=0;i<mac.length;i++){
            if(i!=0){
                sb.append("-");
            }
            //mac[i] & 0xFF 是为了把byte转化为正整数
            String s = Integer.toHexString(mac[i] & 0xFF);
            sb.append(s.length()==1?0+s:s);
        }
        
        //把字符串所有小写字母改为大写成为正规的mac地址并返回
        return sb.toString().toUpperCase();
    }
    */ 
	
    /**  
     * 获取当前操作系统名称.  
     * return 操作系统名称 例如:windows xp,linux 等.  
     */   
    public static String getOSName() {    
        return System.getProperty("os.name")==null?"":System.getProperty("os.name").toLowerCase();
    }


	/**
	 * Linux stuff
	 * @param ipConfigResponse
	 * @return
	 * @throws ParseException
	 */
	private final static String linuxParseMacAddress(String ipConfigResponse)
		throws ParseException {
		String localHost = null;
		try {
			localHost = InetAddress.getLocalHost().getHostAddress();
		} catch (java.net.UnknownHostException ex) {
			ex.printStackTrace();
			throw new ParseException(ex.getMessage(), 0);
		}

		StringTokenizer tokenizer = new StringTokenizer(ipConfigResponse, "\n");
		
		String lastMacAddress = null;

		while (tokenizer.hasMoreTokens()) {
			String line = tokenizer.nextToken().trim();
			boolean containsLocalHost = line.indexOf(localHost) >= 0;

			// see if line contains IP address
			if (containsLocalHost && lastMacAddress != null) {
				return lastMacAddress;
			}

			// see if line contains MAC address
			int macAddressPosition = line.indexOf("HWaddr");
			if (macAddressPosition <= 0)
				continue;

			String macAddressCandidate = line.substring(macAddressPosition + 6).trim();
			if (linuxIsMacAddress(macAddressCandidate)) {
				lastMacAddress = macAddressCandidate;
				continue;
			}
		}

		ParseException ex = new ParseException("cannot read MAC address for " + localHost + " from [" + ipConfigResponse + "]", 0);
		ex.printStackTrace();
		throw ex;
	}

	/**
	 * 检查MAC地址长度是否合法
	 * @param macAddressCandidate
	 * @return
	 */
	private final static boolean linuxIsMacAddress(String macAddressCandidate) {
		// TODO: use a smart regular expression
		if (macAddressCandidate.length() != 17)
			return false;
		return true;
	}


	private final static String linuxRunIfConfigCommand() throws IOException {
		Process p = Runtime.getRuntime().exec("ifconfig");
		InputStream stdoutStream = new BufferedInputStream(p.getInputStream());
	
		StringBuffer buffer = new StringBuffer();
		for (;;) {
			int c = stdoutStream.read();
			if (c == -1)
				break;
			buffer.append((char) c);
		}
		String outputText = buffer.toString();

		stdoutStream.close();

		return outputText;
	}

	/**
	 * Windows stuff
	 * @param ipConfigResponse
	 * @return
	 * @throws ParseException
	 */
	private final static String windowsParseMacAddress(String ipConfigResponse)
	throws ParseException {
		String localHost = null;
		try {
			localHost = InetAddress.getLocalHost().getHostAddress();
		} catch (java.net.UnknownHostException ex) {
			ex.printStackTrace();
			throw new ParseException(ex.getMessage(), 0);
		}


		StringTokenizer tokenizer = new StringTokenizer(ipConfigResponse, "\n");
		String lastMacAddress = null;
	
	
		while (tokenizer.hasMoreTokens()) {
			String line = tokenizer.nextToken().trim();
	
			// see if line contains IP address
			if (line.endsWith(localHost) && lastMacAddress != null) {
				return lastMacAddress;
			}
	
			// see if line contains MAC address
			int macAddressPosition = line.indexOf(":");
			if (macAddressPosition <= 0)
				continue;
	
	
			String macAddressCandidate = line.substring(macAddressPosition + 1).trim();
			if (windowsIsMacAddress(macAddressCandidate)) {
				lastMacAddress = macAddressCandidate;
				continue;
			}
		}


		ParseException ex = new ParseException("cannot read MAC address from [" + ipConfigResponse + "]", 0);
		ex.printStackTrace();
		throw ex;
	}

	/**
	 * 检查MAC地址长度是否合法
	 * @param macAddressCandidate
	 * @return
	 */
	private final static boolean windowsIsMacAddress(String macAddressCandidate) {
		// TODO: use a smart regular expression
		if (macAddressCandidate.length() != 17)
			return false;
		return true;
	}

	private final static String windowsRunIpConfigCommand() throws IOException {
		Process p = Runtime.getRuntime().exec("ipconfig /all");
		InputStream stdoutStream = new BufferedInputStream(p.getInputStream());

		StringBuffer buffer = new StringBuffer();
		for (;;) {
			int c = stdoutStream.read();
			if (c == -1)
				break;
			buffer.append((char) c);
		}
		String outputText = buffer.toString();

		stdoutStream.close();

		return outputText;
	}

	/*
	* Main
	*/
	public final static void main(String[] args) {
		try {
			System.out.println(System.getProperty("java.version"));

			System.out.println("Network infos");
			System.out.println(" Operating System: " + System.getProperty("os.name"));
			System.out.println(" IP/Localhost: " + InetAddress.getLocalHost().getHostAddress());
			System.out.println(" MAC Address: " + getMacAddress());
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
