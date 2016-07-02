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
		rData.setZoneName(new String(getId(),"ISO-8859-1"));
		
		byte[] data=getData();
		byte[] sensorNameBytes=new byte[20];
		System.arraycopy(data, 0, sensorNameBytes, 0, 20);
		rData.setName(new String(sensorNameBytes,"GBK").trim());

		byte[] valueBytes=new byte[8];
		System.arraycopy(data, 20, valueBytes, 0, 8);
		IoBuffer buf=IoBuffer.wrap(valueBytes);
		rData.setValue(buf.getDouble());
		
		byte[] unitBytes=new byte[12];
		System.arraycopy(data, 28, unitBytes, 0, 12);
		rData.setUnit(new String(unitBytes,"GBK").trim());
		
		rData.setDateTime(new Date());
		return rData;
	}
}
