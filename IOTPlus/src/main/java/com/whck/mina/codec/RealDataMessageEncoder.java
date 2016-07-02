package com.whck.mina.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import com.whck.mina.message.RealDataMessage;

public class RealDataMessageEncoder extends AbstractEncoder<RealDataMessage>{

	@Override
	protected void encodeBody(IoSession session, RealDataMessage message, IoBuffer out) {
		out.put(message.appendCrcAndEnder());
	}

}
