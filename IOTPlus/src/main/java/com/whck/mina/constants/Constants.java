package com.whck.mina.constants;

public class Constants {
	public final static String BIN_FILE_PATH="D:/blvl2.bin"; 
	
	
	public final static byte[] REQ_HEADER={(byte)0xE8,(byte)0xE8,
												(byte)0xE8,(byte)0xE8,
												(byte)0xE8,(byte)0xE8,
												(byte)0xE8,(byte)0xE8};
	
	public final static byte[] RESP_HEADER={(byte)0xAB,(byte)0xAB,
												(byte)0xAB,(byte)0xAB,
												(byte)0xAB,(byte)0xAB,
												(byte)0xAB,(byte)0xAB};
	public final static byte[] REQ_ENDER={(byte)0x8E,(byte)0x8E,
												(byte)0x8E,(byte)0x8E,
												(byte)0x8E,(byte)0x8E,
												(byte)0x8E,(byte)0x8E};

	public final static byte[] RESP_ENDER={(byte)0xBA,(byte)0xBA,
												(byte)0xBA,(byte)0xBA,
												(byte)0xBA,(byte)0xBA,
												(byte)0xBA,(byte)0xBA};
	public final static int HEADER_LEN=8;
	public final static int CMD_LEN=3;
	public final static int ID_LEN=11;
	public final static int LONGITUDE_LEN=3;
	public final static int LATITUDE_LEN=3;
	public final static int LENGTH_LEN=2;
	public final static int CRC_LEN=2;
	public final static int ENDER_LEN=8;
	public final static int BASE_PROTOL_LENGTH=HEADER_LEN+CMD_LEN+ID_LEN+LONGITUDE_LEN+LATITUDE_LEN+LENGTH_LEN+CRC_LEN+ENDER_LEN;
	
	public final static byte[] REQ_DOWNLOAD_BIN={(byte) 0xD0,0,1};
	public final static byte[] RESP_NEED_TO_UPDATE={(byte) 0xD0,0,1};
	public final static byte[] RESP_NONEED_TO_UPDATE={(byte) 0xD0,0,2};
	public static final byte REQ_SPLITED_FILE_FIRST_BYTE=(byte)0xC0;
	
	public final static byte[] CMD_OF_SENSOR_REAL_DATA={(byte)0xE0,0,1};
	public final static byte[] CMD_OF_CONTROLLER_REAL_DATA={(byte)0xE0,0,2};
	public final static byte[] CMD_OF_SINGLE_CONTROLLER_PARAMS={(byte)0xE0,0,3};
	public final static byte[] CMD_OF_DOUBLE_CONTROLLER_PARAMS={(byte)0xE0,0,4};
	
	public final static int ONLINE=1;
	public final static int OFFLINE=0;
	
}
