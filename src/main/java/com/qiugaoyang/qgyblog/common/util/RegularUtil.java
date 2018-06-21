package com.qiugaoyang.qgyblog.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularUtil {
	
	public static boolean checkPhone(String inputPhone){
		boolean check = false;
		Pattern p = Pattern.compile("^[1][3-8]+\\d{9}");
		Matcher m = p.matcher(inputPhone);
		if(m.matches()){
			check = true;
		}else{
			check = false;
		}
		return check;
	}
	
	public static boolean checkMail(String inputMail){
		boolean check = false;
		Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*.\\w+([-.]\\w+)*");
		Matcher m = p.matcher(inputMail);
		if(m.matches()){
			check = true;
		}else{
			check = false;
		}
		return check;
	}
	
	public static boolean checkPwd(String inputPwd){
		boolean check = false;
		Pattern p = Pattern.compile("^[a-zA-Z]/w{5,17}$");
		Matcher m = p.matcher(inputPwd);
		if(m.matches()){
			check = true;
		}else{
			check = false;
		}
		return check;
	}
	
	
}
