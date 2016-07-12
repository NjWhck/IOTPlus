package com.whck.mina.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import com.whck.mina.message.BinDeviceParamsRequestMessage;

public class BinDeviceParamsRequestMessageEncoder extends AbstractEncoder<BinDeviceParamsRequestMessage>{

	@Override
	protected void encodeBody(IoSession session, BinDeviceParamsRequestMessage message, IoBuffer out) {
		out.put(message.appendCrcAndEnder());
	}
}
