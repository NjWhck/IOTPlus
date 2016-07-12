package com.whck.mina.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import com.whck.mina.message.SinDeviceParamsRequestMessage;

public class SinDeviceParamsRequestMessageEncoder extends AbstractEncoder<SinDeviceParamsRequestMessage>{

	@Override
	protected void encodeBody(IoSession session, SinDeviceParamsRequestMessage message, IoBuffer out) {
		out.put(message.appendCrcAndEnder());
	}
}
