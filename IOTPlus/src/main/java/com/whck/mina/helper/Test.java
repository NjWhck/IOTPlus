package com.whck.mina.helper;

import java.io.IOException;

import org.apache.mina.core.buffer.IoBuffer;

public class Test {

	public static void main(String[] args) throws IOException {
		IoBuffer buf=IoBuffer.allocate(8);
		buf.putDouble(1);
		byte[] upvalue1Bytes=buf.array();
		for(int i=0;i<upvalue1Bytes.length;i++){
			System.out.println(upvalue1Bytes[i]);	
		}
	System.out.println("==================");
		buf.clear();
		buf.putDouble(16);
		byte[] downvalue1Bytes=buf.array();
		for(int i=0;i<downvalue1Bytes.length;i++){
			System.out.println(downvalue1Bytes[i]);	
		}
	}
}
