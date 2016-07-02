package com.whck.mina.message;

import java.io.IOException;
import java.sql.Time;
import java.util.Calendar;
import org.apache.mina.core.buffer.IoBuffer;
import com.whck.domain.base.SinDeviceParams;
import com.whck.mina.helper.Converter;

public class SinDeviceParamsMessage extends AbstractMessage{
	public static final byte[] COMMAND = {(byte) 0xE0,0,3};
	private static int position=0;
	private static int byte_1=1;
	private static int byte_2=2;
	private static int byte_3=3;
	private static int byte_8=8;
	private static int byte_11=11;
	private static int byte_20=20;
	@Override
	public SinDeviceParams convert() throws IOException {
		SinDeviceParams sdp = new SinDeviceParams();
		
		byte[] data = getData();
		byte[] zoneNameBytes=new byte[byte_11];
		byte[] devNameBytes = new byte[byte_20];

		System.arraycopy(data, position, zoneNameBytes, 0, byte_11);
		String zoneName = new String(zoneNameBytes, "ISO-8859-1");
		sdp.setZoneName(zoneName);
		// 设置控制器名称0
		System.arraycopy(data, position+=byte_11, devNameBytes, 0, byte_20);
		String ctrllerName = new String(devNameBytes, "GBK");
		sdp.setDeviceName(ctrllerName.trim());

		byte[] workDays=new byte[byte_1];
		byte[] timeBytes = new byte[byte_3];
		byte[] timeDurBytes = new byte[byte_2];
		
		System.arraycopy(data, position+=byte_20, workDays, 0, byte_1);
		sdp.setWorkDays(Converter.byteArr2Int(workDays));
		// time1
		System.arraycopy(data, position+=byte_1, timeBytes, 0, byte_3);
		sdp.setTime_1_start(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_3, timeBytes, 0, byte_3);
		sdp.setTime_1_end(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_3, timeDurBytes, 0, byte_2);
		sdp.setRun_time_1(Converter.byteArr2Int(timeDurBytes));
		
		System.arraycopy(data, position+=byte_2, timeDurBytes, 0, byte_2);
		sdp.setIdle_time_1(Converter.byteArr2Int(timeDurBytes));
		
		// time2
		System.arraycopy(data, position+=byte_2, timeBytes, 0, byte_3);
		sdp.setTime_2_start(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_3, timeBytes, 0, byte_3);
		sdp.setTime_2_end(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_3, timeDurBytes, 0, byte_2);
		sdp.setRun_time_2(Converter.byteArr2Int(timeDurBytes));
		
		System.arraycopy(data, position+=byte_2, timeDurBytes, 0, byte_2);
		sdp.setIdle_time_2(Converter.byteArr2Int(timeDurBytes));
		

		// time3
		System.arraycopy(data, position+=byte_2, timeBytes, 0, byte_3);
		sdp.setTime_3_start(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_3, timeBytes, 0, byte_3);
		sdp.setTime_3_end(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_3, timeDurBytes, 0, byte_2);
		sdp.setRun_time_3(Converter.byteArr2Int(timeDurBytes));
		
		System.arraycopy(data, position+=byte_2, timeDurBytes, 0, byte_2);
		sdp.setIdle_time_3(Converter.byteArr2Int(timeDurBytes));
		
		// time4
		System.arraycopy(data, position+=byte_2, timeBytes, 0, byte_3);
		sdp.setTime_4_start(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_3, timeBytes, 0, byte_3);
		sdp.setTime_4_end(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_3, timeDurBytes, 0, byte_2);
		sdp.setRun_time_4(Converter.byteArr2Int(timeDurBytes));
		
		System.arraycopy(data, position+=byte_2, timeDurBytes, 0, byte_2);
		sdp.setIdle_time_4(Converter.byteArr2Int(timeDurBytes));
		

		byte[] enable = new byte[byte_1];
		byte[] sensorName = new byte[byte_20];
		byte[] action=new byte[byte_1];
		byte[] valueLimit = new byte[byte_8];
		// sensor1
		System.arraycopy(data, position+=byte_2, enable, 0, byte_1);
		sdp.setSensor_1_enable(Converter.byteArr2Int(enable));
		
		System.arraycopy(data, position+=byte_1, sensorName, 0, byte_20);
		sdp.setSensor_1_name(new String(sensorName, "GBK").trim());
		
		System.arraycopy(data, position+=byte_20, action, 0, byte_1);
		IoBuffer ioBuffer=IoBuffer.wrap(action);
		sdp.setUpValueAction_1(ioBuffer.getInt());
		
		System.arraycopy(data, position+=byte_1, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		sdp.setUpValue_1(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		sdp.setMidValueAction_1(ioBuffer.getInt());
		
		System.arraycopy(data, position+=byte_1, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		sdp.setDownValue_1(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		sdp.setDownValueAction_1(ioBuffer.getInt());
		

		// sensor2
		System.arraycopy(data, position+=byte_1, enable, 0, byte_1);
		sdp.setSensor_2_enable(Converter.byteArr2Int(enable));
		
		System.arraycopy(data, position+=byte_1, sensorName, 0, byte_20);
		sdp.setSensor_2_name(new String(sensorName, "GBK").trim());
		
		System.arraycopy(data, position+=byte_20, action, 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		sdp.setUpValueAction_2(ioBuffer.getInt());
		
		System.arraycopy(data, position+=byte_1, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		sdp.setUpValue_2(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		sdp.setMidValueAction_2(ioBuffer.getInt());
		
		System.arraycopy(data, position+=byte_1, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		sdp.setDownValue_2(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		sdp.setDownValueAction_2(ioBuffer.getInt());

		// sensor3
		System.arraycopy(data, position+=byte_1, enable, 0, byte_1);
		sdp.setSensor_3_enable(Converter.byteArr2Int(enable));
		
		System.arraycopy(data, position+=byte_1, sensorName, 0, byte_20);
		sdp.setSensor_3_name(new String(sensorName, "GBK").trim());
		
		System.arraycopy(data, position+=byte_20, action, 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		sdp.setUpValueAction_3(ioBuffer.getInt());
		
		System.arraycopy(data, position+=byte_1, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		sdp.setUpValue_3(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		sdp.setMidValueAction_3(ioBuffer.getInt());
		
		System.arraycopy(data, position+=byte_1, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		sdp.setDownValue_3(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		sdp.setDownValueAction_3(ioBuffer.getInt());

		// sensor4
		System.arraycopy(data, position+=byte_1, enable, 0, byte_1);
		sdp.setSensor_4_enable(Converter.byteArr2Int(enable));
		
		System.arraycopy(data, position+=byte_1, sensorName, 0, byte_20);
		sdp.setSensor_4_name(new String(sensorName, "GBK").trim());
		
		System.arraycopy(data, position+=byte_20, action, 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		sdp.setUpValueAction_4(ioBuffer.getInt());
		
		System.arraycopy(data, position+=byte_1, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		sdp.setUpValue_4(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		sdp.setMidValueAction_4(ioBuffer.getInt());
		
		System.arraycopy(data, position+=byte_1, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		sdp.setDownValue_4(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		sdp.setDownValueAction_4(ioBuffer.getInt());

		return sdp;
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
