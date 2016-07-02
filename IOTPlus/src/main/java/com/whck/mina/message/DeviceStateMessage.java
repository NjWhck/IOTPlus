package com.whck.mina.message;

import java.io.IOException;
import java.util.Arrays;

import com.whck.domain.base.BinDevice;
import com.whck.domain.base.SinDevice;

public class DeviceStateMessage extends AbstractMessage{
	public final static byte[] COMMAND={(byte) 0xE0,0,2};

	/**
	 * @throws IOException 
	 */
	@Override
	public Object convert() throws IOException {
		byte[] data=getData();
		String zoneName=new String(getId(),"ISO-8859-1");

		byte[] devNameBytes=new byte[20];
		System.arraycopy(data, 0, devNameBytes, 0, 20);
		String name=new String(devNameBytes,"GBK");
		
		byte[] ctrlModeBytes=new byte[1];
		System.arraycopy(data, 20, ctrlModeBytes, 0, 1);
		
		byte[] devTypeBytes=new byte[1];
		System.arraycopy(data, 21, devTypeBytes, 0, 1);
		
		byte[] stateBytes=new byte[1];
		System.arraycopy(data, 22, stateBytes, 0, 1);
		
		if(Arrays.equals(devTypeBytes, new byte[]{1})){
			SinDevice sd=new SinDevice();
			sd.setZoneName(zoneName);
			sd.setName(name.trim());
			sd.setCtrlMode(ctrlModeBytes[0]);
			sd.setType(devTypeBytes[0]);
			sd.setOnline(1);
			sd.setState(stateBytes[0]);
			return sd;
		}else if(Arrays.equals(devTypeBytes, new byte[]{2})){
			BinDevice bd=new BinDevice();
			bd.setZoneName(zoneName);
			bd.setName(name.trim());
			bd.setCtrlMode(ctrlModeBytes[0]);
			bd.setType(devTypeBytes[0]);
			bd.setOnline(1);
			bd.setState(stateBytes[0]);
			return bd;
		}else{
			return null;
		}
	}
}
