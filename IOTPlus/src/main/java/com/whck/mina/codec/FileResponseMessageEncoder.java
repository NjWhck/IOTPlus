package com.whck.mina.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import com.whck.mina.message.FileResponseMessage;

public class FileResponseMessageEncoder extends AbstractEncoder<FileResponseMessage>{

	@Override
	protected void encodeBody(IoSession session, FileResponseMessage message, IoBuffer out) {
		out.put(message.appendCrcAndEnder());
	}
}
