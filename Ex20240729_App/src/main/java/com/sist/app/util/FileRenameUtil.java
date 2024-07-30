package com.sist.app.util;

import java.io.File;

public class FileRenameUtil {
	public static String checkSameFileName(String fileName, String path) {
		// 인자인 fileName에서 확장자를 뺀 파일명 가려내자!
		// 우선 "."의 위치를 알아내야한다.
		int period = fileName.lastIndexOf(".");
		String f_name = fileName.substring(0, period);
		String suffix = fileName.substring(period);
		
		// 전체경로 (절대경로) + 파일명
		String saveFilePath = path+System.getProperty("file.separator")+fileName;
		
		// 위의 전체경로를 가지고 파일객체 생성
		File f = new File(saveFilePath);
		
		// 파일이 이미 있다면 파일명 뒤에 숫자를 붙이기 위해 변수를 하나 준비하자
		int idx = 1;
		
		while(f != null && f.exists()) {
			StringBuffer sb = new StringBuffer();
			sb.append(f_name);
			sb.append("(");
			sb.append(idx++);
			sb.append(")");
			sb.append(suffix);
			
			fileName = sb.toString();
			
			saveFilePath = path+System.getProperty("file.separator"+fileName);
			
			f= new File(saveFilePath);
		}
		
		
		return fileName;
		
	}
	
}
