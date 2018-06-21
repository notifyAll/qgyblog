package com.qiugaoyang.qgyblog.common.tools;



import com.qiugaoyang.qgyblog.common.util.DataUtil;
import com.qiugaoyang.qgyblog.common.util.TimeUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.ParseException;

public class FileTool {

	public static void Copy(File oldfile, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldfile);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
//					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("File Copy Error");
			e.printStackTrace();
		}
	}
	
	public static String setFile(File file, String key, String picName) {
		String dir = null;
		try {
			dir = DataUtil.GOODSPICDIR + key+"_"+ TimeUtil.getTimestamp()+"_"+picName;
			Copy(file,dir);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dir;
	}
	
	public static void main(String[] args) {
		String dir = "E:\\pic\\temp\\file2.txt";
		File oldfile = new File(dir);
		
		setFile(oldfile, "1001", "pic1.png");
	}
}
