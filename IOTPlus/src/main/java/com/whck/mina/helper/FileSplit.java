package com.whck.mina.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import com.whck.mina.handler.ProtocolHandler;

public class FileSplit {
	private static String fileVersion;
	private static String filePath;
	static {
		Properties pro = new Properties();
		try {
			pro.load(ProtocolHandler.class.getResourceAsStream("/fileinfo.properties"));
		} catch (IOException e) {
			System.out.println("读取fileinfo.properties文件失败");
			e.printStackTrace();
		}
		fileVersion = pro.getProperty("fileversion");
		filePath = pro.getProperty("filepath");
	}
	/**
	 * @param filePath 	文件路径
	 * @param order   	片段序号(从1开始)
	 */
	public static byte[] pieceCreate(int order) throws IOException {
		byte[] data=new byte[1024];
		@SuppressWarnings("resource")
		FileInputStream in = new FileInputStream(filePath+"/"+fileVersion+".bin");
		int length=in.read(data, (order-1)*1024, 1024);
		//最后一包不够1024
		if(length<1024){
			byte[] eData=new byte[length];
			System.arraycopy(data, 0, eData, 0, length);
			return eData;
		}
		return data;
	}
	public static String getFileVersion(){
		return fileVersion;
	}
	public static byte[] getFileSize() throws IOException{
		@SuppressWarnings("resource")
		FileInputStream in = new FileInputStream(filePath+"/"+fileVersion+".bin");
		int fileSize=in.available();
		int intRslt=0;
		if(0==fileSize%1024){
			intRslt=fileSize/1024;
		}else{
			intRslt=fileSize/1024+1;
		}
		return new byte[]{(byte) (intRslt/256),(byte) (intRslt%256)};
	}
}
