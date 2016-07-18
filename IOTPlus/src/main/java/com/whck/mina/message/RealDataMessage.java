package com.whck.mina.message;

import java.io.IOException;
import java.util.Date;
import org.apache.mina.core.buffer.IoBuffer;
import com.whck.domain.base.RealData;

public class RealDataMessage extends AbstractMessage{
	public static final byte[] COMMAND={(byte)0xE0,0,1};
	@Override
	public RealData convert() throws IOException {
		RealData rData=new RealData();
		rData.setZoneName(new String(getId(),"GBK"));
		
		byte[] data=getData();
		byte[] sensorNameBytes=new byte[20];
		System.arraycopy(data, 0, sensorNameBytes, 0, 20);
		String name=new String(sensorNameBytes,"GBK").trim();
		rData.setName(name);

		byte[] valueBytes=new byte[8];
		System.arraycopy(data, 20, valueBytes, 0, 8);
		byte[] rslt=new byte[valueBytes.length];
		for(int i=0;i<valueBytes.length;i++){
			rslt[i]=valueBytes[valueBytes.length-i-1];
		}
		IoBuffer buf=IoBuffer.wrap(rslt);
		rData.setValue(buf.getDouble());
		
		byte[] unitBytes=new byte[12];
		System.arraycopy(data, 28, unitBytes, 0, 12);
		rData.setUnit(new String(unitBytes,"GBK").trim());
		
		rData.setDateTime(new Date());
		return rData;
	}
}
