package com.whck.mina.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import com.whck.mina.message.SinDeviceParamsMessage;

public class SinDeviceParamsMessageEncoder extends AbstractEncoder<SinDeviceParamsMessage>{

	@Override
	protected void encodeBody(IoSession session, SinDeviceParamsMessage message, IoBuffer out) {
		out.put(message.appendCrcAndEnder());
	}
}
