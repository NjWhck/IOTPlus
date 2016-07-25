package com.whck.mina.message;

import java.io.IOException;
import java.sql.Time;
import org.apache.mina.core.buffer.IoBuffer;
import com.whck.domain.base.BinDeviceParams;

public class BinDeviceParamsMessage extends AbstractMessage{
	public static final byte[] COMMAND={(byte)0xE0,0,4};
	private static int position=0;
	private static int byte_1=1;
	private static int byte_4=4;
	private static int byte_8=8;
	private static int byte_20=20;

	@Override
	public BinDeviceParams convert() throws IOException {
		BinDeviceParams bdp=new BinDeviceParams();
		String zoneName=new String(getId(),"GBK");
		bdp.setZoneName(zoneName);
		
		byte[] data = getData();
		byte[] devNameBytes = new byte[byte_20];

		// 设置控制器名称0
		System.arraycopy(data, 0, devNameBytes, 0, byte_20);
		String ctrllerName = new String(devNameBytes, "GBK");
		bdp.setDeviceName(ctrllerName.trim());

		
		byte[] timeBytes = new byte[byte_4];
		byte[] openingBytes = new byte[byte_1];
		
		
		// time1
		System.arraycopy(data, position+=byte_1, timeBytes, 0, byte_4);
		bdp.setTime_1_start(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_4, timeBytes, 0, byte_4);
		bdp.setTime_1_end(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_4, openingBytes, 0, byte_1);
		IoBuffer ioBuffer=IoBuffer.wrap(openingBytes);
		bdp.setOpening_1(ioBuffer.get());
		
		
		// time2
		System.arraycopy(data, position+=byte_1, timeBytes, 0, byte_4);
		bdp.setTime_2_start(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_4, timeBytes, 0, byte_4);
		bdp.setTime_2_end(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_4, openingBytes, 0, byte_1);
	    ioBuffer=IoBuffer.wrap(openingBytes);
		bdp.setOpening_2(ioBuffer.get());
		
		// time3
		System.arraycopy(data, position+=byte_1, timeBytes, 0, byte_4);
		bdp.setTime_3_start(bytes2Time(timeBytes));
		
		
		System.arraycopy(data, position+=byte_4, timeBytes, 0, byte_4);
		bdp.setTime_3_end(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_4, openingBytes, 0, byte_1);
		ioBuffer=IoBuffer.wrap(openingBytes);
		bdp.setOpening_3(ioBuffer.get());
		
		
		// time4
		System.arraycopy(data, position+=byte_1, timeBytes, 0, byte_4);
		bdp.setTime_4_start(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_4, timeBytes, 0, byte_4);
		bdp.setTime_4_end(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_4, openingBytes, 0, byte_1);
		ioBuffer=IoBuffer.wrap(openingBytes);
		bdp.setOpening_4(ioBuffer.get());
		

		byte[] enable = new byte[byte_1];
		byte[] sensorName = new byte[byte_20];
		byte[] action=new byte[byte_1];
		byte[] valueLimit = new byte[byte_8];
		// sensor1
		System.arraycopy(data, position+=byte_1, enable, 0, byte_1);
		ioBuffer=IoBuffer.wrap(enable);
		bdp.setSensor_1_enable(ioBuffer.get());
		
		System.arraycopy(data, position+=byte_1, sensorName, 0, byte_20);
		bdp.setSensor_1_name(new String(sensorName, "GBK").trim());
		
		System.arraycopy(data, position+=byte_20, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		bdp.setUpValue_1(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		bdp.setDownValue_1(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, action, 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		bdp.setUpValueAction_1(ioBuffer.get());
		
		System.arraycopy(data, position+=byte_1, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		bdp.setMidValueAction_1(ioBuffer.get());
		
		System.arraycopy(data, position+=byte_1, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		bdp.setDownValueAction_1(ioBuffer.get());
		
		// sensor2
		System.arraycopy(data, position+=byte_1, enable, 0, byte_1);
		ioBuffer=IoBuffer.wrap(enable);
		bdp.setSensor_2_enable(ioBuffer.get());
		
		System.arraycopy(data, position+=byte_1, sensorName, 0, byte_20);
		bdp.setSensor_2_name(new String(sensorName, "GBK").trim());

		System.arraycopy(data, position+=byte_20, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		bdp.setUpValue_2(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		bdp.setDownValue_2(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, action, 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		bdp.setUpValueAction_2(ioBuffer.get());
		
		System.arraycopy(data, position+=byte_1, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		bdp.setMidValueAction_2(ioBuffer.get());
		
		System.arraycopy(data, position+=byte_1, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		bdp.setDownValueAction_2(ioBuffer.get());

		// sensor3
		System.arraycopy(data, position+=byte_1, enable, 0, byte_1);
		ioBuffer=IoBuffer.wrap(enable);
		bdp.setSensor_3_enable(ioBuffer.get());
		
		System.arraycopy(data, position+=byte_1, sensorName, 0, byte_20);
		bdp.setSensor_3_name(new String(sensorName, "GBK").trim());
		
		System.arraycopy(data, position+=byte_20, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		bdp.setUpValue_3(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		bdp.setDownValue_3(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, action, 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		bdp.setUpValueAction_3(ioBuffer.get());
		
		System.arraycopy(data, position+=byte_1, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		bdp.setMidValueAction_3(ioBuffer.get());
		
		System.arraycopy(data, position+=byte_1, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		bdp.setDownValueAction_3(ioBuffer.get());

		// sensor4
		System.arraycopy(data, position+=byte_1, enable, 0, byte_1);
		ioBuffer=IoBuffer.wrap(enable);
		bdp.setSensor_4_enable(ioBuffer.get());
		
		System.arraycopy(data, position+=byte_1, sensorName, 0, byte_20);
		bdp.setSensor_4_name(new String(sensorName, "GBK").trim());
		
		System.arraycopy(data, position+=byte_20, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		bdp.setUpValue_4(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		bdp.setDownValue_4(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, action, 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		bdp.setUpValueAction_4(ioBuffer.get());
		
		System.arraycopy(data, position+=byte_1, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		bdp.setMidValueAction_4(ioBuffer.get());
		
		System.arraycopy(data, position+=byte_1, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		bdp.setDownValueAction_4(ioBuffer.get());
		
		byte[] workDays = new byte[byte_1];
		System.arraycopy(data, position+=byte_1, workDays, 0, byte_1);
		ioBuffer=IoBuffer.wrap(workDays);
		bdp.setWorkDays(ioBuffer.get());
		return bdp;
	}
	protected Time bytes2Time(byte[] tb) {
		IoBuffer ioBuffer=IoBuffer.wrap(tb);
		Time time = new Time(ioBuffer.getUnsignedInt());
		return time;
	}
}
