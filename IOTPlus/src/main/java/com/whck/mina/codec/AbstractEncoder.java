package com.whck.mina.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

import com.whck.mina.constants.Constants;
import com.whck.mina.message.AbstractMessage;

public abstract class AbstractEncoder<T extends AbstractMessage> implements MessageEncoder<T> {
	@Override
	public void encode(IoSession session, T message, ProtocolEncoderOutput out) throws Exception {
		IoBuffer ioBuffer=IoBuffer.allocate(Constants.BASE_PROTOL_LENGTH);
		ioBuffer.setAutoExpand(true);
		encodeBody(session,message,ioBuffer);
		ioBuffer.flip();
		out.write(ioBuffer);
	}
	protected abstract void encodeBody(IoSession session,T message,IoBuffer out);
}
