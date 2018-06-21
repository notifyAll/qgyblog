package com.qiugaoyang.qgyblog.common.tools;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class IPCheck {

	public static String getIP(HttpServletRequest request) {
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
		return ip;
	}

	public static boolean checkIP(HttpServletRequest request) {
		boolean result = false;
		String ip = getIP(request);
		if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
			result = true;
		} else if ("192.168.1.146".equals(ip)) {
			result = true;
		}
		return result;
	}

	public static String getMACAddress(String ip) {
		String str = "";
		String macAddress = "";
		try {
			Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (int i = 1; i < 100; i++) {
				str = input.readLine();
				if (str != null) {
					if (str.indexOf("MAC Address") > 1) {
						macAddress = str.substring(str.indexOf("MAC Address") + 14, str.length());
						break;
					}
					if (str.indexOf("MAC Address") > 1) {
						macAddress = str.substring(str.indexOf("MAC 地址") + 14, str.length());
						break;
					}
					// 以上有个判断，不同系统cmd命令执行的返回结果展示方式不一样，我测试的win7是MAC 地址
					// 所以又第二个if判断 你可先在你机器上cmd测试下nbtstat -A 命令 当然得有一个你可以ping通的
					// 网络ip地址,然后根据你得到的结果中mac地址显示方式来确定这个循环取值

				}
			}
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		return macAddress;
	}
}
