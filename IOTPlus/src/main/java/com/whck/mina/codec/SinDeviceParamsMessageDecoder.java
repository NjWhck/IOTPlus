package com.whck.mina.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import com.whck.mina.constants.Constants;
import com.whck.mina.message.AbstractMessage;
import com.whck.mina.message.SinDeviceParamsMessage;

public class SinDeviceParamsMessageDecoder extends AbstractDecoder{

	public SinDeviceParamsMessageDecoder(byte[] cmd) {
		super(cmd);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected AbstractMessage decodeBody(IoSession session, IoBuffer in) {
			in.mark();
			SinDeviceParamsMessage m=new SinDeviceParamsMessage();
			byte[] id=new byte[Constants.ID_LEN];
			in.get(id);
			m.setId(id);
			
			byte[] longitude=new byte[Constants.LONGITUDE_LEN];
			in.get(longitude);
			m.setLongitude(longitude);
			
			byte[] latitude=new byte[Constants.LATITUDE_LEN];
			in.get(latitude);
			m.setLatitude(latitude);
			
			byte[] dataLenCache=new byte[Constants.LENGTH_LEN];
			in.get(dataLenCache);
			m.setDataLen(dataLenCache);
			
			IoBuffer buf=IoBuffer.wrap(dataLenCache);
			int dataLen=Short.toUnsignedInt(buf.getShort());
			if(in.remaining()>=dataLen){
				byte[] data=new byte[dataLen-Constants.CRC_LEN-Constants.ENDER_LEN];
				in.get(data);
				m.setData(data);
				
				byte[] crc=new byte[Constants.CRC_LEN];
				in.get(crc);
				m.setCrc(crc);
				
				in.get(new byte[Constants.ENDER_LEN]);
				return m;
			}
			in.reset();
			return null;
	}
}
