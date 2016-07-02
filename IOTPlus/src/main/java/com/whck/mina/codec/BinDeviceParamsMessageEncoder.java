package com.whck.mina.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import com.whck.mina.message.BinDeviceParamsMessage;

public class BinDeviceParamsMessageEncoder extends AbstractEncoder<BinDeviceParamsMessage>{

	@Override
	protected void encodeBody(IoSession session, BinDeviceParamsMessage message, IoBuffer out) {
		out.put(message.appendCrcAndEnder());
	}
}
