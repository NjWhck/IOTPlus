package com.whck.mina.message;

import java.io.IOException;

public class BinDeviceParamsRequestMessage extends AbstractMessage{
	public static final byte[] COMMAND={(byte)0xE0,0,4};
	@Override
	public Object convert() throws IOException {
		return null;
	}
}
