package com.whck.mina.message;

import java.io.IOException;

public class SinDeviceParamsRequestMessage extends AbstractMessage{
	public static final byte[] COMMAND={(byte)0xE0,0,3};
	@Override
	public Object convert() throws IOException {
		return null;
	}
}
