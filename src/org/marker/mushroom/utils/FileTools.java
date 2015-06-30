package org.marker.mushroom.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * 文件工具类
 * @author marker
 * @date 2012-12-03
 * */
public class FileTools {
	
	/** 文本文件UTF-8编码 */
	public static final String FILE_CHARACTER_UTF8 = "UTF-8";
	/** 文本文件GBK编码 */
	public static final String FILE_CHARACTER_GBK  = "GBK";
 

	
	/**
	 * 获取文本文件内容
	 * @param filePath 文件路径
	 * @param character 字符编码
	 * @return String 文件文本内容
	 * @throws IOException 
	 * */
	public static final String getFileContet(File filePath,String character) throws IOException{
		return FileTools.getContent(filePath, character);
	}
	
	
	/**
	 * 写入文本文件内容
	 * @param filePath 文件路径
	 * @param character 字符编码 
	 * @throws IOException 
	 * */
	public static final void setFileContet(File filePath, String content, String character) throws IOException{
		FileTools.setContent(filePath, content, character);
	}	
	
	
	//内部处理文件方法
	private static String getContent(File filePath,String character) throws IOException{
		FileInputStream   __fis = new FileInputStream(filePath);//文件字节流
		InputStreamReader __isr = new InputStreamReader(__fis, character);//字节流和字符流的桥梁，可以指定指定字符格式
		BufferedReader    __br  = new BufferedReader(__isr);
		StringBuffer sb = new StringBuffer();
		String temp = null;
		while ((temp = __br.readLine()) != null) { sb.append(temp+"\n"); }
		__br.close();__isr.close(); __fis.close();
		return sb.toString();//返回文件内容
	}
	
	//内部处理文件保存
	private static void setContent(File filePath, String content, String character) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter __pw = new PrintWriter(filePath, character);
		__pw.write(content); __pw.close();
	}
}
