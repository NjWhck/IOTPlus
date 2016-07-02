package com.whck.mina.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import com.whck.mina.message.DeviceStateMessage;

public class DeviceStateMessageEncoder extends AbstractEncoder<DeviceStateMessage>{

	@Override
	protected void encodeBody(IoSession session, DeviceStateMessage message, IoBuffer out) {
		out.put(message.appendCrcAndEnder());
	}
}
