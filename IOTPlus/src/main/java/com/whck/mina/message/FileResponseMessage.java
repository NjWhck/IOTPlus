package com.whck.mina.message;

public class FileResponseMessage extends AbstractMessage {
	public final static byte[] NEED_TO_UPDATE_COMMAND={(byte) 0xD0,0,1};
	public final static byte[] NONEED_TO_UPDATE_COMMAND={(byte) 0xD0,0,2};
	
	@Override
	public Object convert(){
		return null;
	}
}
