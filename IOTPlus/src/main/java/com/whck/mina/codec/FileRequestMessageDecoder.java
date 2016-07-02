package com.whck.mina.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import com.whck.mina.constants.Constants;
import com.whck.mina.message.AbstractMessage;
import com.whck.mina.message.FileRequestMessage;

public class FileRequestMessageDecoder extends AbstractDecoder{

	public FileRequestMessageDecoder(byte[] cmd) {
		super(cmd);
	}

	@Override
	protected AbstractMessage decodeBody(IoSession session, IoBuffer in) {
		System.out.println("detail:"+in);
		if(in.remaining()>=Constants.BASE_PROTOL_LENGTH-Constants.HEADER_LEN-Constants.CMD_LEN){
			FileRequestMessage m=new FileRequestMessage();
			byte[] id=new byte[Constants.ID_LEN];
			
			in.get(id);
			m.setId(id);
			
			byte[] longitude=new byte[Constants.LONGITUDE_LEN];
			in.get(longitude);
			m.setLongitude(longitude);
			
			byte[] latitude=new byte[Constants.LATITUDE_LEN];
			in.get(latitude);
			m.setLatitude(latitude);
			
			byte[] dataLen=new byte[Constants.LENGTH_LEN];
			in.get(dataLen);
			m.setDataLen(dataLen);
			
			IoBuffer buf=IoBuffer.wrap(dataLen);
			byte[] data=new byte[Short.toUnsignedInt(buf.getShort())-Constants.CRC_LEN-Constants.ENDER_LEN];
			in.get(data);
			m.setData(data);
			
			byte[] crc=new byte[Constants.CRC_LEN];
			in.get(crc);
			m.setCrc(crc);
			
			in.get(new byte[Constants.ENDER_LEN]);
			return m;
		}
		
		return null;
	}
}
