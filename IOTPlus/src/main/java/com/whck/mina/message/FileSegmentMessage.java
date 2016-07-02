package com.whck.mina.message;

import java.io.IOException;

import org.apache.mina.core.buffer.IoBuffer;

import com.whck.mina.constants.Constants;
import com.whck.mina.helper.Converter;
import com.whck.mina.helper.FileSplit;

public class FileSegmentMessage extends AbstractMessage{
	
	public final static byte[] COMMAND=new byte[]{(byte)0xC0};
	private byte[] sequence;
	
	public byte[] getSequence() {
		return sequence;
	}

	public void setSequence(byte[] sequence) {
		this.sequence = sequence;
	}

	@Override
	public FileSegmentMessage convert() throws IOException {
		FileSegmentMessage m=new FileSegmentMessage();
		m.setCmd(new byte[]{COMMAND[0],sequence[0],sequence[1]}); //合并cmd,sequence
		m.setId(getId());
		m.setLongitude(getLongitude());
		m.setLatitude(getLatitude());
		IoBuffer buf=IoBuffer.wrap(sequence);
		short order=buf.getUnsigned();
		byte[] data=FileSplit.pieceCreate(order);
		m.setData(data);
		int len=data.length+Constants.CRC_LEN+Constants.ENDER_LEN;
		byte[] lens=Converter.int2ByteArr(len);
		m.setDataLen(new byte[]{lens[0],lens[1]});
		return m;
	}

}
