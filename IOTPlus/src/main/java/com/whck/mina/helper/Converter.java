package com.whck.mina.helper;
/**
 * 
 * @author JSexy
 * @date 2016/3/29
 *
 */
public class Converter {
	public static int byteArr2Int(byte[] b){
		//底层语言为无符号
		int rslt=b[0]&0xff;
		for(int i=0;i<b.length;i++){
			int tmp=b[i]&0xff;
			for(int j=0;j<i;j++){
				rslt+=tmp*256;
			}
		}
		return rslt;
	}
	public static byte[] int2ByteArr(int n){
		byte[] b = new byte[4];  
        b[3] = (byte) (n & 0xff);  
        b[2] = (byte) (n >> 8 & 0xff);  
        b[1] = (byte) (n >> 16 & 0xff);  
        b[0] = (byte) (n >> 24 & 0xff);  
        return b;  
	}
	public static byte[] byteArrsMerger(byte[] byte_1, byte[] byte_2){  
        byte[] byte_3 = new byte[byte_1.length+byte_2.length];  
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);  
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);  
        return byte_3;  
    }
}
