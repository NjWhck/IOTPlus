package com.whck.mina.message;

import java.io.IOException;

import com.whck.mina.constants.Constants;
import com.whck.mina.helper.CRC16M;
import com.whck.mina.helper.Converter;

public abstract class AbstractMessage {
	/** 命令符*/
	private byte[] cmd;
	
	/** 数据中心ID (11字节)*/
	private byte[] id;
	
	/** 经度 (3字节)*/
	private byte[] longitude;
	
	/** 纬度 (3字节)*/
	private byte[] latitude;
	
	/** 数据长度 (2字节)*/
	private byte[] dataLen;
	
	/** 数据 (由数据长度决定)*/
	private byte[] data;
	
	/** Crc16校验结果*/
	private byte[] crc;
	
	public byte[] getId() {
		return id;
	}
	public void setId(byte[] id) {
		this.id = id;
	}
	
	public byte[] getCmd() {
		return cmd;
	}
	public void setCmd(byte[] cmd) {
		this.cmd = cmd;
	}
	public byte[] getLongitude() {
		return longitude;
	}
	public void setLongitude(byte[] longitude) {
		this.longitude = longitude;
	}
	public byte[] getLatitude() {
		return latitude;
	}
	public void setLatitude(byte[] latitude) {
		this.latitude = latitude;
	}
	public byte[] getDataLen() {
		return dataLen;
	}
	public void setDataLen(byte[] dataLen) {
		this.dataLen = dataLen;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	
	public byte[] getCrc() {
		return crc;
	}
	public void setCrc(byte[] crc) {
		this.crc = crc;
	}
	
	public  boolean checkCrc(){
		byte[] data=Converter.byteArrsMerger(
				Converter.byteArrsMerger(
					Converter.byteArrsMerger(
						Converter.byteArrsMerger(
							Converter.byteArrsMerger(
								Converter.byteArrsMerger(
									Converter.byteArrsMerger(
										Constants.REQ_HEADER,getCmd()),
										getId()), getLongitude()),
										getLatitude()), getDataLen()),
										getData()),getCrc());
		return CRC16M.checkBuf(data);
	}
	
	public byte[] appendCrcAndEnder(){
		byte[] data=Converter.byteArrsMerger(
				Converter.byteArrsMerger(
					Converter.byteArrsMerger(
						Converter.byteArrsMerger(
							Converter.byteArrsMerger(
								Converter.byteArrsMerger(Constants.RESP_HEADER,getCmd()),
									getId()), getLongitude()),
										getLatitude()), getDataLen()),
											getData());
		byte[] appendCrc=CRC16M.getSendBuf(CRC16M.getBufHexStr(data));
		byte[] appendEnder=Converter.byteArrsMerger(appendCrc,Constants.RESP_ENDER);
		return appendEnder;
	}
	public abstract Object convert() throws IOException;
}
