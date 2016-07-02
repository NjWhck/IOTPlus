package com.whck.mina.message;

import java.io.IOException;
import com.whck.mina.constants.Constants;
import com.whck.mina.helper.Converter;
import com.whck.mina.helper.FileSplit;
import com.whck.mina.message.AbstractMessage;

public class FileRequestMessage extends AbstractMessage{
	public final static byte[] COMMAND={(byte) 0xD0,0,1};

	public FileResponseMessage convert() throws IOException {
		FileResponseMessage message=new FileResponseMessage();
		String reqFileVersion=new String(getData(),"ISO-8859-1").trim();
		String curFileVersion=FileSplit.getFileVersion();
		if(reqFileVersion.equals(curFileVersion)){
			message.setCmd(FileResponseMessage.NONEED_TO_UPDATE_COMMAND);
			message.setData(new byte[0]);
			message.setId(getId());
			message.setLatitude(getLatitude());
			message.setLongitude(getLongitude());
			message.setDataLen(new byte[]{0,Constants.CRC_LEN+Constants.ENDER_LEN});
		}else{
			message.setCmd(FileResponseMessage.NEED_TO_UPDATE_COMMAND);
			message.setId(getId());
			message.setLatitude(getLatitude());
			message.setLongitude(getLongitude());
			byte[] data=FileSplit.getFileSize();
			message.setData(data);
			byte[] length=Converter.int2ByteArr(data.length+getCrc().length+Constants.RESP_ENDER.length);
			//TODO:Check out here
			message.setDataLen(new byte[]{length[0],length[1]});
		}
		return message;
	}
}
