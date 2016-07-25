package com.whck.mina.message;

import java.io.IOException;
import java.sql.Time;
import org.apache.mina.core.buffer.IoBuffer;
import com.whck.domain.base.SinDeviceParams;
import com.whck.mina.helper.Converter;

public class SinDeviceParamsMessage extends AbstractMessage{
	public static final byte[] COMMAND = {(byte) 0xE0,0,3};
	private static int position=0;
	private static int byte_1=1;
	private static int byte_2=2;
	private static int byte_4=4;
	private static int byte_8=8;
	private static int byte_20=20;
	@Override
	public SinDeviceParams convert() throws IOException {
		SinDeviceParams sdp = new SinDeviceParams();
		String zoneName=new String(getId(),"GBK");
		sdp.setZoneName(zoneName);
		
		byte[] data = getData();
		byte[] devNameBytes = new byte[byte_20];
		// 设置控制器名称0
		System.arraycopy(data, 0, devNameBytes, 0, byte_20);
		String ctrllerName = new String(devNameBytes, "GBK");
		sdp.setDeviceName(ctrllerName.trim());

		byte[] timeBytes = new byte[byte_4];
		byte[] timeDurBytes = new byte[byte_2];
		
		
		// time1
		System.arraycopy(data, position+=byte_4, timeBytes, 0, byte_4);
		
		sdp.setTime_1_start(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_4, timeBytes, 0, byte_4);
		sdp.setTime_1_end(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_4, timeDurBytes, 0, byte_2);
		IoBuffer ioBuffer=IoBuffer.wrap(timeDurBytes);
		sdp.setRun_time_1(ioBuffer.getUnsignedShort());
		
		System.arraycopy(data, position+=byte_2, timeDurBytes, 0, byte_2);
		ioBuffer=IoBuffer.wrap(timeDurBytes);
		sdp.setIdle_time_1(ioBuffer.getUnsignedShort());
		
		// time2
		System.arraycopy(data, position+=byte_2, timeBytes, 0, byte_4);
		sdp.setTime_2_start(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_4, timeBytes, 0, byte_4);
		sdp.setTime_2_end(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_4, timeDurBytes, 0, byte_2);
		ioBuffer=IoBuffer.wrap(timeDurBytes);
		sdp.setRun_time_2(ioBuffer.getUnsignedShort());
		
		System.arraycopy(data, position+=byte_2, timeDurBytes, 0, byte_2);
		ioBuffer=IoBuffer.wrap(timeDurBytes);
		sdp.setIdle_time_2(ioBuffer.getUnsignedShort());
		

		// time3
		System.arraycopy(data, position+=byte_2, timeBytes, 0, byte_4);
		sdp.setTime_3_start(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_4, timeBytes, 0, byte_4);
		sdp.setTime_3_end(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_4, timeDurBytes, 0, byte_2);
		ioBuffer=IoBuffer.wrap(timeDurBytes);
		sdp.setRun_time_3(ioBuffer.getUnsignedShort());
		
		System.arraycopy(data, position+=byte_2, timeDurBytes, 0, byte_2);
		ioBuffer=IoBuffer.wrap(timeDurBytes);
		sdp.setIdle_time_3(ioBuffer.getUnsignedShort());
		
		// time4
		System.arraycopy(data, position+=byte_2, timeBytes, 0, byte_4);
		sdp.setTime_4_start(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_4, timeBytes, 0, byte_4);
		sdp.setTime_4_end(bytes2Time(timeBytes));
		
		System.arraycopy(data, position+=byte_4, timeDurBytes, 0, byte_2);
		ioBuffer=IoBuffer.wrap(timeDurBytes);
		sdp.setRun_time_4(ioBuffer.getUnsignedShort());
		
		System.arraycopy(data, position+=byte_2, timeDurBytes, 0, byte_2);
		ioBuffer=IoBuffer.wrap(timeDurBytes);
		sdp.setIdle_time_4(ioBuffer.getUnsignedShort());
		
		
		byte[] enable = new byte[byte_1];
		byte[] sensorName = new byte[byte_20];
		byte[] action=new byte[byte_1];
		byte[] valueLimit = new byte[byte_8];
		// sensor1
		System.arraycopy(data, position+=byte_2, enable, 0, byte_1);
		sdp.setSensor_1_enable(Converter.byteArr2Int(enable));
		
		System.arraycopy(data, position+=byte_1, sensorName, 0, byte_20);
		sdp.setSensor_1_name(new String(sensorName, "GBK").trim());
		
		
		System.arraycopy(data, position+=byte_20, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		sdp.setUpValue_1(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		sdp.setDownValue_1(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, action, 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		sdp.setUpValueAction_1(ioBuffer.get());
		
		System.arraycopy(data, position+=byte_1, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		sdp.setMidValueAction_1(ioBuffer.get());
		
		System.arraycopy(data, position+=byte_1, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		sdp.setDownValueAction_1(ioBuffer.get());
		
		// sensor2
		System.arraycopy(data, position+=byte_1, enable, 0, byte_1);
		sdp.setSensor_2_enable(Converter.byteArr2Int(enable));
		
		System.arraycopy(data, position+=byte_1, sensorName, 0, byte_20);
		sdp.setSensor_2_name(new String(sensorName, "GBK").trim());
		
		System.arraycopy(data, position+=byte_20, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		sdp.setUpValue_2(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		sdp.setDownValue_2(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, action, 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		sdp.setUpValueAction_2(ioBuffer.getInt());
		
		System.arraycopy(data, position+=byte_1, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		sdp.setMidValueAction_2(ioBuffer.getInt());
		
		System.arraycopy(data, position+=byte_1, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		sdp.setDownValueAction_2(ioBuffer.getInt());

		// sensor3
		System.arraycopy(data, position+=byte_1, enable, 0, byte_1);
		sdp.setSensor_3_enable(Converter.byteArr2Int(enable));
		
		System.arraycopy(data, position+=byte_1, sensorName, 0, byte_20);
		sdp.setSensor_3_name(new String(sensorName, "GBK").trim());
		
		System.arraycopy(data, position+=byte_20, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		sdp.setUpValue_3(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		sdp.setDownValue_3(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, action, 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		sdp.setUpValueAction_3(ioBuffer.get());
		
		System.arraycopy(data, position+=byte_1, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		sdp.setMidValueAction_3(ioBuffer.get());
		
		System.arraycopy(data, position+=byte_1, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		sdp.setDownValueAction_3(ioBuffer.get());

		// sensor4
		System.arraycopy(data, position+=byte_1, enable, 0, byte_1);
		sdp.setSensor_4_enable(Converter.byteArr2Int(enable));
		
		System.arraycopy(data, position+=byte_1, sensorName, 0, byte_20);
		sdp.setSensor_4_name(new String(sensorName, "GBK").trim());
		
		System.arraycopy(data, position+=byte_20, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		sdp.setUpValue_4(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, valueLimit, 0, byte_8);
		ioBuffer=IoBuffer.wrap(valueLimit);
		sdp.setDownValue_4(ioBuffer.getDouble());
		
		System.arraycopy(data, position+=byte_8, action, 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		sdp.setUpValueAction_4(ioBuffer.get());
		
		System.arraycopy(data, position+=byte_1, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		sdp.setMidValueAction_4(ioBuffer.get());
		
		System.arraycopy(data, position+=byte_1, action , 0, byte_1);
		ioBuffer=IoBuffer.wrap(action);
		sdp.setDownValueAction_4(ioBuffer.get());
		
		byte[] workDays=new byte[byte_1];
		System.arraycopy(data, position+=byte_1, workDays, 0, byte_1);
		ioBuffer=IoBuffer.wrap(workDays);
		sdp.setWorkDays(ioBuffer.get());
		return sdp;
	}

	protected Time bytes2Time(byte[] tb) {
		IoBuffer buf=IoBuffer.wrap(tb);
		Time time = new Time(buf.getUnsignedInt());
		return time;
	}
}
