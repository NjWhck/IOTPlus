package com.whck.mina.codec;

import java.util.Arrays;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

import com.whck.mina.constants.Constants;
import com.whck.mina.message.AbstractMessage;

public abstract class AbstractDecoder implements MessageDecoder{

	private final byte[] COMMAND;
	private boolean readHeader;
	protected AbstractDecoder(byte[] cmd){
		this.COMMAND=cmd;
	}
	@Override
	public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
//		System.out.println("In come:"+in);
		in.setAutoExpand(true);
		if(in.remaining()<Constants.BASE_PROTOL_LENGTH)
			return MessageDecoderResult.NEED_DATA;
		
		byte[] header=new byte[Constants.HEADER_LEN];
		in.get(header);
//		System.out.println("header:"+header);
		if(!Arrays.equals(header, Constants.REQ_HEADER))
			return MessageDecoderResult.NOT_OK;
		
		byte[] cmd=new byte[COMMAND.length];
		in.get(cmd);
//		System.out.println("cmd:"+cmd);
		if(!Arrays.equals(COMMAND, cmd))
			return MessageDecoderResult.NOT_OK;
		return MessageDecoderResult.OK;
	}

	@Override
	public MessageDecoderResult decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		//Skip Header if not Read
		
		if(!readHeader){
			in.get(new byte[Constants.HEADER_LEN]);
			in.get(new byte[COMMAND.length]);
		}
		AbstractMessage m=decodeBody(session,in);
		if(m==null)
			return MessageDecoderResult.NEED_DATA;
		else
			readHeader=false;
		m.setCmd(COMMAND);
		out.write(m);
		return MessageDecoderResult.OK;
	}
	
	@Override
	public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {}
	
	protected abstract AbstractMessage decodeBody(IoSession session,IoBuffer in);
}
