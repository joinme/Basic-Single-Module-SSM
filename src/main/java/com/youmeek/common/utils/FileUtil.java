//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.youmeek.common.utils;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {
	public FileUtil() {
	}
	
	public static int autoDectect(String savePath, String date) throws Exception {
		File file = new File(savePath + "/" + date);
		if(!file.exists() && !file.mkdirs()) {
			throw new Exception("失败：无法创建以下目录:[" + savePath + "/" + date + "]");
		} else {
			int totalFiles = 0;
			File[] subFiles = file.listFiles();
			if(subFiles != null && subFiles.length > 0) {
				String patternStr = "_" + date + "_";
				
				for(int i = 0; i < subFiles.length; ++i) {
					String fileName = subFiles[i].getName();
					if(fileName != null && fileName.indexOf(patternStr) != -1) {
						++totalFiles;
					}
				}
			}
			
			return totalFiles;
		}
	}
	
	public static void zipFile(String filePath, String fileName) throws Exception {
		String[] filenames = new String[]{fileName};
		byte[] buf = new byte[1024];
		String outFilename = fileName + ".zip";
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(filePath + "/" + outFilename));
		
		for(int i = 0; i < filenames.length; ++i) {
			FileInputStream in = new FileInputStream(filePath + "/" + filenames[i]);
			out.putNextEntry(new ZipEntry(filenames[i]));
			boolean len = false;
			
			int var9;
			while((var9 = in.read(buf)) > 0) {
				out.write(buf, 0, var9);
			}
			
			out.closeEntry();
			in.close();
		}
		
		out.close();
	}
	
	public static void mkDir(String path) {
		File file = new File(path);
		if(!file.exists()) {
			file.mkdirs();
		}
		
	}
	
	public static String writeFile(String path, String fileName, String content) throws IOException {
		return writeFile(path, fileName, content, "UTF-8");
	}
	
	public static String writeFile(String path, String fileName, String content, String charset) throws IOException {
		mkDir(path);
		String filePath = path + File.separator + fileName;
		File file = new File(filePath);
		if(file.exists()) {
			file.delete();
		}
		
		file.createNewFile();
		FileOutputStream fops = new FileOutputStream(file);
		fops.write(content.getBytes(charset));
		fops.flush();
		fops.close();
		return filePath;
	}
	
	public static String readFile(String filePath) throws IOException {
		File file = new File(filePath);
		FileInputStream fin = new FileInputStream(file);
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		
		while(true) {
			int content = fin.read();
			if(content == -1) {
				String content1 = new String(bs.toByteArray(), "UTF-8");
				fin.close();
				bs.close();
				return content1;
			}
			
			bs.write(content);
		}
	}
	
	public static byte[] getFileBytes(String filePath) throws IOException {
		File file = new File(filePath);
		FileInputStream fin = new FileInputStream(file);
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		
		while(true) {
			int bytesArr = fin.read();
			if(bytesArr == -1) {
				byte[] bytesArr1 = bs.toByteArray();
				fin.close();
				bs.close();
				return bytesArr1;
			}
			
			bs.write(bytesArr);
		}
	}
	
	public static byte[] createFileBytesByFileContent(String path, String fileName, String fileContent) {
		return createFileBytesByFileContent(path, fileName, fileContent, "ISO8859-1");
	}
	
	public static byte[] createFileBytesByFileContent(String path, String fileName, String fileContent, String charset) {
		String filePath = null;
		
		try {
			filePath = writeFile(path, fileName, fileContent, charset);
		} catch (IOException var8) {
			var8.printStackTrace();
		}
		
		byte[] retunBytes = (byte[])null;
		
		try {
			retunBytes = getFileBytes(filePath);
		} catch (IOException var7) {
			var7.printStackTrace();
		}
		
		return retunBytes;
	}
	
	public static void main(String[] args) {
		try {
			String content = readFile("D://bankCenter//allinpay//temp_file//testText.txt");
			System.out.println(content);
		} catch (IOException var3) {
			var3.printStackTrace();
		}
		
	}
	
	public static String readSrcFile(String fileName, String encoding) throws IOException {
		if(fileName != null && encoding != null) {
			InputStream in = FileUtil.class.getResourceAsStream("/" + fileName);
			return IOUtils.toString(in, encoding);
		} else {
			return null;
		}
	}
}
