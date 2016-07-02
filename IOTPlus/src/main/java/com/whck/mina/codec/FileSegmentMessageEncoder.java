package com.whck.mina.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import com.whck.mina.message.FileSegmentMessage;

public class FileSegmentMessageEncoder extends AbstractEncoder<FileSegmentMessage>{

	@Override
	protected void encodeBody(IoSession session, FileSegmentMessage message, IoBuffer out) {
		out.put(message.appendCrcAndEnder());
	}
}
