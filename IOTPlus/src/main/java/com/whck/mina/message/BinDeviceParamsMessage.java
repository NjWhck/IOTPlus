package com.whck.mina.message;

import java.io.IOException;
import java.sql.Time;
import java.util.Calendar;
import org.apache.mina.core.buffer.IoBuffer;
import com.whck.domain.base.BinDeviceParams;
import com.whck.mina.helper.Converter;

public class BinDeviceParamsMessage extends AbstractMessage{
	public static final byte[] COMMAND={(byte)0xE0,0,4};
	private static int position=0;
	private static int byte_1=1;
	private static int byte_2=2;
	private static int byte_3=3;
	private static int byte_8=8;
	private static int byte_11=11;
	private static int byte_20=20;

	@Override
	public BinDeviceParams convert() throws IOException {
		BinDeviceParams bdp=new BinDeviceParams();
		byte[] data = getData();
		byte[] zoneNameBytes=new byte[byte_11];
		byte[] devNameBytes = new byte[byte_20];

		System.arraycopy(data, position, zoneNameBytes, 0, byte_11);
		String zoneName = new String(zoneNameBytes, "ISO-8859-1");
		bdp.setZoneName(zoneName);
		// 设置控制器名称0
		System.arraycopy(data, position+=byte_11, devNameBytes, 0, byte_20);
		String ctrllerName = new String(devNameBytes, "GBK");
		bdp.setDeviceName(ctrllerName.trim());

		byte[] workDays = new byte[byte_1];
		byte[] timeBytes = new byte[byte_3];
		byte[] openingBytes = new byte[byte_1];
		
		System.arraycopy(data, position+=byte_20, workDays, 0, byte_1);
		bdp.setWorkDays(Converter.byteArr2Int(workDays));
		
		// time1
		System.arraycopy(data, position+=byte_1, timeBytes, 0, byte_3);
		bdp.setTime_1_start(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_3, timeBytes, 0, byte_3);
		bdp.setTime_1_end(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_3, openingBytes, 0, byte_1);
		bdp.setOpening_1(Converter.byteArr2Int(openingBytes));
		
		
		// time2
		System.arraycopy(data, position+=byte_1, timeBytes, 0, byte_3);
		bdp.setTime_2_start(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_3, timeBytes, 0, byte_3);
		bdp.setTime_2_end(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_3, openingBytes, 0, byte_1);
		bdp.setOpening_2(Converter.byteArr2Int(openingBytes));
		
		// time3
		System.arraycopy(data, position+=byte_1, timeBytes, 0, byte_3);
		bdp.setTime_3_start(bytes2Time(timeBytes));
		
		
		System.arraycopy(data, position+=byte_3, timeBytes, 0, byte_3);
		bdp.setTime_3_end(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_3, openingBytes, 0, byte_1);
		bdp.setOpening_3(Converter.byteArr2Int(openingBytes));
		
		
		// time4
		System.arraycopy(data, position+=byte_1, timeBytes, 0, byte_3);
		bdp.setTime_4_start(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_3, timeBytes, 0, byte_3);
		bdp.setTime_4_end(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_3, openingBytes, 0, byte_1);
		bdp.setOpening_4(Converter.byteArr2Int(openingBytes));
		
		

		byte[] enable = new byte[byte_1];
		byte[] sensorName = new byte[byte_20];
		byte[] action=new byte[byte_1];
		byte[] valueLimit = new byte[byte_8];
		// sensor1
		System.arraycopy(data, position+=byte_2, enable, 0, byte_1);
		bdp.setSensor_1_enable(Converter.byteArr2Int(enable));
		
		System.arraycopy(data, position+=byte_1, sensorName, 0, byte_20);
		bdp.setSensor_1_name(new String(sensorName, "GBK").trim());
		
		System.arraycopy(data, position+=byte_20, action, 0, byte_1);
		IoBuffer ioBuffer=IoBuffer.wrap(action);
		bdp.setUpValueAction_1(ioBuffer.getInt());
		
		System.arraycopy(data, position+=byte_1, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		bdp.setUpValue_1(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		bdp.setMidValueAction_1(ioBuffer.getInt());
		
		System.arraycopy(data, position+=byte_1, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		bdp.setDownValue_1(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		bdp.setDownValueAction_1(ioBuffer.getInt());
		

		// sensor2
		System.arraycopy(data, position+=byte_1, enable, 0, byte_1);
		bdp.setSensor_2_enable(Converter.byteArr2Int(enable));
		
		System.arraycopy(data, position+=byte_1, sensorName, 0, byte_20);
		bdp.setSensor_2_name(new String(sensorName, "GBK").trim());
		
		System.arraycopy(data, position+=byte_20, action, 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		bdp.setUpValueAction_2(ioBuffer.getInt());
		
		System.arraycopy(data, position+=byte_1, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		bdp.setUpValue_2(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		bdp.setMidValueAction_2(ioBuffer.getInt());
		
		System.arraycopy(data, position+=byte_1, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		bdp.setDownValue_2(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		bdp.setDownValueAction_2(ioBuffer.getInt());

		// sensor3
		System.arraycopy(data, position+=byte_1, enable, 0, byte_1);
		bdp.setSensor_3_enable(Converter.byteArr2Int(enable));
		
		System.arraycopy(data, position+=byte_1, sensorName, 0, byte_20);
		bdp.setSensor_3_name(new String(sensorName, "GBK").trim());
		
		System.arraycopy(data, position+=byte_20, action, 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		bdp.setUpValueAction_3(ioBuffer.getInt());
		
		System.arraycopy(data, position+=byte_1, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		bdp.setUpValue_3(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		bdp.setMidValueAction_3(ioBuffer.getInt());
		
		System.arraycopy(data, position+=byte_1, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		bdp.setDownValue_3(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		bdp.setDownValueAction_3(ioBuffer.getInt());

		// sensor4
		System.arraycopy(data, position+=byte_1, enable, 0, byte_1);
		bdp.setSensor_4_enable(Converter.byteArr2Int(enable));
		
		System.arraycopy(data, position+=byte_1, sensorName, 0, byte_20);
		bdp.setSensor_4_name(new String(sensorName, "GBK").trim());
		
		System.arraycopy(data, position+=byte_20, action, 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		bdp.setUpValueAction_4(ioBuffer.getInt());
		
		System.arraycopy(data, position+=byte_1, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		bdp.setUpValue_4(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		bdp.setMidValueAction_4(ioBuffer.getInt());
		
		System.arraycopy(data, position+=byte_1, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		bdp.setDownValue_4(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		bdp.setDownValueAction_4(ioBuffer.getInt());
		return bdp;
	}
	protected Time bytes2Time(byte[] tb) {
		int hour = Byte.toUnsignedInt(tb[0]);
		int minute = Byte.toUnsignedInt(tb[1]);
		int second = Byte.toUnsignedInt(tb[2]);
		Calendar cld = Calendar.getInstance();
		cld.set(Calendar.HOUR_OF_DAY, hour);
		cld.set(Calendar.MINUTE, minute);
		cld.set(Calendar.SECOND, second);
		Time time = new Time(cld.getTimeInMillis());
		return time;
	}
}
