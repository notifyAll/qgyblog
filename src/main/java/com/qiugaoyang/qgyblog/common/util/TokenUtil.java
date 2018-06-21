package com.qiugaoyang.qgyblog.common.util;



import java.text.ParseException;
import java.util.*;

public class TokenUtil {
	private final int RANDOM_MAX_NUM = 999999999;


	public String createToken(String ip) {
		String result = "NULLTOKEN";
		try {
			String time = TimeUtil.getTimestamp();
			String IP = ip;
			if (null == ip || ip.equals("")) {
				IP = "1.1.1.1";
			}
			Random random = new Random();
			result = MD5Util.GetMD5Code2(IP + "," + Integer.toString(random.nextInt(RANDOM_MAX_NUM)) + ";" + time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}


}
