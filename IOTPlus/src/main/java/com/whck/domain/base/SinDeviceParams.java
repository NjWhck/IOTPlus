package com.whck.domain.base;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.apache.mina.core.buffer.IoBuffer;
import com.whck.mina.constants.Constants;
import com.whck.mina.helper.Converter;
import com.whck.mina.message.SinDeviceParamsMessage;


@Entity
@Table(name = "tb_sin_device_params")
public class SinDeviceParams {
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(name="zone_name",length=11)
	private String zoneName;
	
	@Column(name="device_name",nullable=false,unique=true)
	private String deviceName;
	@Column(name="work_days")
	private int workDays;
	@Temporal(TemporalType.TIME)
	private Date time_1_start;
	@Temporal(TemporalType.TIME)
	private Date time_1_end;
	
	@Max(value=43200)
	private Integer run_time_1;
	@Max(value=43200)
	private Integer idle_time_1;
	
	@Temporal(TemporalType.TIME)
	private Date time_2_start;
	@Temporal(TemporalType.TIME)
	private Date time_2_end;
	@Max(value=43200)
	private Integer run_time_2;
	@Max(value=43200)
	private Integer idle_time_2;
	
	@Temporal(TemporalType.TIME)
	private Date time_3_start;
	@Temporal(TemporalType.TIME)
	private Date time_3_end;
	@Max(value=43200)
	private Integer run_time_3;
	@Max(value=43200)
	private Integer idle_time_3;
	
	@Temporal(TemporalType.TIME)
	private Date time_4_start;
	@Temporal(TemporalType.TIME)
	private Date time_4_end;
	@Max(value=43200)
	private Integer run_time_4;
	@Max(value=43200)
	private Integer idle_time_4;
	
	@Max(value=1)
	@Min(value=0)
	private int sensor_1_enable;
	private String sensor_1_name;
	
	@Max(value=1)
	@Min(value=0)
	@Column(name="upvalue_action_1")
	private int upValueAction_1;
	@Column(name="upvalue_1",precision=12,scale=2)
	private double upValue_1;
	@Max(value=1)
	@Min(value=0)
	@Column(name="midvalue_action_1")
	private int midValueAction_1;
	@Column(name="downvalue_1",precision=12,scale=2)
	private double downValue_1;
	@Max(value=1)
	@Min(value=0)
	@Column(name="downvalue_action_1")
	private int downValueAction_1;
	
	@Max(value=1)
	@Min(value=0)
	private int sensor_2_enable;
	private String sensor_2_name;
	@Max(value=1)
	@Min(value=0)
	@Column(name="upvalue_action_2")
	private int upValueAction_2;
	@Column(name="upvalue_2",precision=12,scale=2)
	private double upValue_2;
	@Max(value=1)
	@Min(value=0)
	@Column(name="midvalue_action_2")
	private int midValueAction_2;
	@Column(name="downvalue_2",precision=12,scale=2)
	private double downValue_2;
	@Max(value=1)
	@Min(value=0)
	@Column(name="downvalue_action_2")
	private int downValueAction_2;
	
	@Max(value=1)
	@Min(value=0)
	private int sensor_3_enable;
	private String sensor_3_name;
	@Max(value=1)
	@Min(value=0)
	@Column(name="upvalue_action_3")
	private int upValueAction_3;
	@Column(name="upvalue_3",precision=12,scale=2)
	private double upValue_3;
	@Max(value=1)
	@Min(value=0)
	@Column(name="dmidvalue_action_3")
	private int midValueAction_3;
	@Column(name="downvalue_3",precision=12,scale=2)
	private double downValue_3;
	@Max(value=1)
	@Min(value=0)
	@Column(name="downvalue_action_3")
	private int downValueAction_3;
	
	@Max(value=1)
	@Min(value=0)
	private int sensor_4_enable;
	private String sensor_4_name;
	@Max(value=1)
	@Min(value=0)
	@Column(name="upvalue_action_4")
	private int upValueAction_4;
	@Column(name="upvalue_4")
	private double upValue_4;
	@Max(value=1)
	@Min(value=0)
	@Column(name="midvalue_action_4")
	private int midValueAction_4;
	@Column(name="downvalue_4")
	private double downValue_4;
	@Max(value=1)
	@Min(value=0)
	@Column(name="downvalue_action_4")
	private int downValueAction_4;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
	public int getWorkDays() {
		return workDays;
	}
	public void setWorkDays(int workDays) {
		this.workDays = workDays;
	}
	public Date getTime_1_start() {
		return time_1_start;
	}
	public void setTime_1_start(Date time_1_start) {
		this.time_1_start = time_1_start;
	}
	public Date getTime_1_end() {
		return time_1_end;
	}
	public void setTime_1_end(Date time_1_end) {
		this.time_1_end = time_1_end;
	}
	public Integer getRun_time_1() {
		return run_time_1;
	}
	public void setRun_time_1(Integer run_time_1) {
		this.run_time_1 = run_time_1;
	}
	public Integer getIdle_time_1() {
		return idle_time_1;
	}
	public void setIdle_time_1(Integer idle_time_1) {
		this.idle_time_1 = idle_time_1;
	}
	public Date getTime_2_start() {
		return time_2_start;
	}
	public void setTime_2_start(Date time_2_start) {
		this.time_2_start = time_2_start;
	}
	public Date getTime_2_end() {
		return time_2_end;
	}
	public void setTime_2_end(Date time_2_end) {
		this.time_2_end = time_2_end;
	}
	public Integer getRun_time_2() {
		return run_time_2;
	}
	public void setRun_time_2(Integer run_time_2) {
		this.run_time_2 = run_time_2;
	}
	public Integer getIdle_time_2() {
		return idle_time_2;
	}
	public void setIdle_time_2(Integer idle_time_2) {
		this.idle_time_2 = idle_time_2;
	}
	public Date getTime_3_start() {
		return time_3_start;
	}
	public void setTime_3_start(Date time_3_start) {
		this.time_3_start = time_3_start;
	}
	public Date getTime_3_end() {
		return time_3_end;
	}
	public void setTime_3_end(Date time_3_end) {
		this.time_3_end = time_3_end;
	}
	public Integer getRun_time_3() {
		return run_time_3;
	}
	public void setRun_time_3(Integer run_time_3) {
		this.run_time_3 = run_time_3;
	}
	public Integer getIdle_time_3() {
		return idle_time_3;
	}
	public void setIdle_time_3(Integer idle_time_3) {
		this.idle_time_3 = idle_time_3;
	}
	public Date getTime_4_start() {
		return time_4_start;
	}
	public void setTime_4_start(Date time_4_start) {
		this.time_4_start = time_4_start;
	}
	public Date getTime_4_end() {
		return time_4_end;
	}
	public void setTime_4_end(Date time_4_end) {
		this.time_4_end = time_4_end;
	}
	public Integer getRun_time_4() {
		return run_time_4;
	}
	public void setRun_time_4(Integer run_time_4) {
		this.run_time_4 = run_time_4;
	}
	public Integer getIdle_time_4() {
		return idle_time_4;
	}
	public void setIdle_time_4(Integer idle_time_4) {
		this.idle_time_4 = idle_time_4;
	}
	public int getSensor_1_enable() {
		return sensor_1_enable;
	}
	public void setSensor_1_enable(int sensor_1_enable) {
		this.sensor_1_enable = sensor_1_enable;
	}
	public String getSensor_1_name() {
		return sensor_1_name;
	}
	public void setSensor_1_name(String sensor_1_name) {
		this.sensor_1_name = sensor_1_name;
	}
	public int getUpValueAction_1() {
		return upValueAction_1;
	}
	public void setUpValueAction_1(int upValueAction_1) {
		this.upValueAction_1 = upValueAction_1;
	}
	public double getUpValue_1() {
		return upValue_1;
	}
	public void setUpValue_1(double upValue_1) {
		this.upValue_1 = upValue_1;
	}
	public int getDownValueAction_1() {
		return downValueAction_1;
	}
	public void setDownValueAction_1(int downValueAction_1) {
		this.downValueAction_1 = downValueAction_1;
	}
	public double getDownValue_1() {
		return downValue_1;
	}
	public void setDownValue_1(double downValue_1) {
		this.downValue_1 = downValue_1;
	}
	public int getSensor_2_enable() {
		return sensor_2_enable;
	}
	public void setSensor_2_enable(int sensor_2_enable) {
		this.sensor_2_enable = sensor_2_enable;
	}
	public String getSensor_2_name() {
		return sensor_2_name;
	}
	public void setSensor_2_name(String sensor_2_name) {
		this.sensor_2_name = sensor_2_name;
	}
	public int getUpValueAction_2() {
		return upValueAction_2;
	}
	public void setUpValueAction_2(int upValueAction_2) {
		this.upValueAction_2 = upValueAction_2;
	}
	public double getUpValue_2() {
		return upValue_2;
	}
	public void setUpValue_2(double upValue_2) {
		this.upValue_2 = upValue_2;
	}
	public int getDownValueAction_2() {
		return downValueAction_2;
	}
	public void setDownValueAction_2(int downValueAction_2) {
		this.downValueAction_2 = downValueAction_2;
	}
	public double getDownValue_2() {
		return downValue_2;
	}
	public void setDownValue_2(double downValue_2) {
		this.downValue_2 = downValue_2;
	}
	public int getSensor_3_enable() {
		return sensor_3_enable;
	}
	public void setSensor_3_enable(int sensor_3_enable) {
		this.sensor_3_enable = sensor_3_enable;
	}
	public String getSensor_3_name() {
		return sensor_3_name;
	}
	public void setSensor_3_name(String sensor_3_name) {
		this.sensor_3_name = sensor_3_name;
	}
	public int getUpValueAction_3() {
		return upValueAction_3;
	}
	public void setUpValueAction_3(int upValueAction_3) {
		this.upValueAction_3 = upValueAction_3;
	}
	public double getUpValue_3() {
		return upValue_3;
	}
	public void setUpValue_3(double upValue_3) {
		this.upValue_3 = upValue_3;
	}
	public int getDownValueAction_3() {
		return downValueAction_3;
	}
	public void setDownValueAction_3(int downValueAction_3) {
		this.downValueAction_3 = downValueAction_3;
	}
	public double getDownValue_3() {
		return downValue_3;
	}
	public void setDownValue_3(double downValue_3) {
		this.downValue_3 = downValue_3;
	}
	public int getSensor_4_enable() {
		return sensor_4_enable;
	}
	public void setSensor_4_enable(int sensor_4_enable) {
		this.sensor_4_enable = sensor_4_enable;
	}
	public String getSensor_4_name() {
		return sensor_4_name;
	}
	public void setSensor_4_name(String sensor_4_name) {
		this.sensor_4_name = sensor_4_name;
	}
	public int getUpValueAction_4() {
		return upValueAction_4;
	}
	public void setUpValueAction_4(int upValueAction_4) {
		this.upValueAction_4 = upValueAction_4;
	}
	public double getUpValue_4() {
		return upValue_4;
	}
	public void setUpValue_4(double upValue_4) {
		this.upValue_4 = upValue_4;
	}
	public int getDownValueAction_4() {
		return downValueAction_4;
	}
	public void setDownValueAction_4(int downValueAction_4) {
		this.downValueAction_4 = downValueAction_4;
	}
	public double getDownValue_4() {
		return downValue_4;
	}
	public void setDownValue_4(double downValue_4) {
		this.downValue_4 = downValue_4;
	}
	public int getMidValueAction_1() {
		return midValueAction_1;
	}
	public void setMidValueAction_1(int midValueAction_1) {
		this.midValueAction_1 = midValueAction_1;
	}
	public int getMidValueAction_2() {
		return midValueAction_2;
	}
	public void setMidValueAction_2(int midValueAction_2) {
		this.midValueAction_2 = midValueAction_2;
	}
	public int getMidValueAction_3() {
		return midValueAction_3;
	}
	public void setMidValueAction_3(int midValueAction_3) {
		this.midValueAction_3 = midValueAction_3;
	}
	public int getMidValueAction_4() {
		return midValueAction_4;
	}
	public void setMidValueAction_4(int midValueAction_4) {
		this.midValueAction_4 = midValueAction_4;
	}
	
	public SinDeviceParamsMessage convert() throws IOException{
		SinDeviceParamsMessage sdpm=new SinDeviceParamsMessage();
		sdpm.setId(zoneName.getBytes("ISO-8859-1"));
		sdpm.setLongitude(new byte[]{0,0,0});
		sdpm.setLatitude(new byte[]{0,0,0});
		byte[] devNameBytes=new byte[20];
		byte[] validDevNameBytes=this.deviceName.getBytes("GBK");
		System.arraycopy(validDevNameBytes, 0, devNameBytes, 0, validDevNameBytes.length);
		byte[] workDaysByte=new byte[]{(byte) workDays};
		byte[] preData=Converter.byteArrsMerger(devNameBytes, workDaysByte);
		
		Calendar c1Start=getCalendar(time_1_start);
		Calendar c1End=getCalendar(time_1_end);
		byte[] time1Bytes=new byte[]{(byte) c1Start.get(Calendar.HOUR),
										(byte) c1Start.get(Calendar.MINUTE),
											(byte) c1Start.get(Calendar.SECOND),
												(byte) c1End.get(Calendar.HOUR),
													(byte) c1End.get(Calendar.MINUTE),
														(byte) c1End.get(Calendar.SECOND)};
		byte[] run1Bytes=new byte[]{(byte) (run_time_1/256),(byte) (run_time_1%256)};
		byte[] idle1Bytes=new byte[]{(byte) (idle_time_1/256),(byte) (idle_time_1%256)};
		byte[] time1Data=Converter.byteArrsMerger(Converter.byteArrsMerger(time1Bytes, run1Bytes), idle1Bytes);
		
		Calendar c2Start=getCalendar(time_2_start);
		Calendar c2End=getCalendar(time_2_end);
		byte[] time2Bytes=new byte[]{(byte) c2Start.get(Calendar.HOUR),
										(byte) c2Start.get(Calendar.MINUTE),
											(byte) c2Start.get(Calendar.SECOND),
												(byte) c2End.get(Calendar.HOUR),
													(byte) c2End.get(Calendar.MINUTE),
														(byte) c2End.get(Calendar.SECOND)};
		byte[] run2Bytes=new byte[]{(byte) (run_time_2/256),(byte) (run_time_2%256)};
		byte[] idle2Bytes=new byte[]{(byte) (idle_time_2/256),(byte) (idle_time_2%256)};
		byte[] time2Data=Converter.byteArrsMerger(Converter.byteArrsMerger(time2Bytes, run2Bytes), idle2Bytes);
		
		Calendar c3Start=getCalendar(time_3_start);
		Calendar c3End=getCalendar(time_3_end);
		byte[] time3Bytes=new byte[]{(byte) c3Start.get(Calendar.HOUR),
										(byte) c3Start.get(Calendar.MINUTE),
											(byte) c3Start.get(Calendar.SECOND),
												(byte) c3End.get(Calendar.HOUR),
													(byte) c3End.get(Calendar.MINUTE),
														(byte) c3End.get(Calendar.SECOND)};
		byte[] run3Bytes=new byte[]{(byte) (run_time_3/256),(byte) (run_time_3%256)};
		byte[] idle3Bytes=new byte[]{(byte) (idle_time_3/256),(byte) (idle_time_3%256)};
		byte[] time3Data=Converter.byteArrsMerger(Converter.byteArrsMerger(time3Bytes, run3Bytes), idle3Bytes);
		
		Calendar c4Start=getCalendar(time_4_start);
		Calendar c4End=getCalendar(time_4_end);
		byte[] time4Bytes=new byte[]{(byte) c4Start.get(Calendar.HOUR),
										(byte) c4Start.get(Calendar.MINUTE),
											(byte) c4Start.get(Calendar.SECOND),
												(byte) c4End.get(Calendar.HOUR),
													(byte) c4End.get(Calendar.MINUTE),
														(byte) c4End.get(Calendar.SECOND)};
		byte[] run4Bytes=new byte[]{(byte) (run_time_4/256),(byte) (run_time_4%256)};
		byte[] idle4Bytes=new byte[]{(byte) (idle_time_4/256),(byte) (idle_time_4%256)};
		byte[] time4Data=Converter.byteArrsMerger(Converter.byteArrsMerger(time4Bytes, run4Bytes), idle4Bytes);
		byte[] timeData=Converter.byteArrsMerger(Converter.byteArrsMerger(Converter.byteArrsMerger(time1Data, time2Data), time3Data), time4Data);
		
		byte[] enable1Bytes=new byte[]{(byte) sensor_1_enable};
		byte[] sensor1NameBytes=new byte[20];
		byte[] sensor1NameValid=sensor_1_name.getBytes("GBK");
		System.arraycopy(sensor1NameValid, 0, sensor1NameBytes, 0, sensor1NameValid.length);
		IoBuffer buf=IoBuffer.allocate(8);
		buf.putDouble(upValue_1);
		byte[] upvalue1Bytes=buf.array();
		buf.clear();
		buf.putDouble(downValue_1);
		byte[] downvalue1Bytes=buf.array();
		byte upaction1Bytes=(byte)upValueAction_1;
		byte midaction1Bytes=(byte) midValueAction_1;
		byte downaction1Bytes=(byte) downValueAction_1;
		byte[] sensor1Data=Converter.byteArrsMerger(
								Converter.byteArrsMerger(
									Converter.byteArrsMerger(
										Converter.byteArrsMerger(enable1Bytes, sensor1NameBytes), 
										upvalue1Bytes), downvalue1Bytes), new byte[]{upaction1Bytes,midaction1Bytes,downaction1Bytes});
		
		byte[] enable2Bytes=new byte[]{(byte) sensor_2_enable};
		byte[] sensor2NameBytes=new byte[20];
		byte[] sensor2NameValid=sensor_2_name.getBytes("GBK");
		System.arraycopy(sensor2NameValid, 0, sensor2NameBytes, 0, sensor2NameValid.length);
		buf.clear();
		buf.putDouble(upValue_2);
		byte[] upvalue2Bytes=buf.array();
		buf.clear();
		buf.putDouble(downValue_2);
		byte[] downvalue2Bytes=buf.array();
		byte upaction2Bytes=(byte) upValueAction_2;
		byte midaction2Bytes=(byte) midValueAction_2;
		byte downaction2Bytes=(byte) downValueAction_2;
		byte[] sensor2Data=Converter.byteArrsMerger(
				Converter.byteArrsMerger(
					Converter.byteArrsMerger(
						Converter.byteArrsMerger(enable2Bytes, sensor2NameBytes), 
						upvalue2Bytes), downvalue2Bytes), new byte[]{upaction2Bytes,midaction2Bytes,downaction2Bytes});

		byte[] enable3Bytes=new byte[]{(byte) sensor_3_enable};
		byte[] sensor3NameBytes=new byte[20];
		byte[] sensor3NameValid=sensor_3_name.getBytes("GBK");
		System.arraycopy(sensor3NameValid, 0, sensor3NameBytes, 0, sensor3NameValid.length);
		buf.clear();
		buf.putDouble(upValue_3);
		byte[] upvalue3Bytes=buf.array();
		buf.clear();
		buf.putDouble(downValue_3);
		byte[] downvalue3Bytes=buf.array();
		byte upaction3Bytes=(byte) upValueAction_3;
		byte midaction3Bytes=(byte) midValueAction_3;
		byte downaction3Bytes=(byte) downValueAction_3;
		byte[] sensor3Data=Converter.byteArrsMerger(
				Converter.byteArrsMerger(
					Converter.byteArrsMerger(
						Converter.byteArrsMerger(enable3Bytes, sensor3NameBytes), 
						upvalue3Bytes), downvalue3Bytes), new byte[]{upaction3Bytes,midaction3Bytes,downaction3Bytes});

		byte[] enable4Bytes=new byte[]{(byte) sensor_4_enable};
		byte[] sensor4NameBytes=new byte[20];
		byte[] sensor4NameValid=sensor_4_name.getBytes("GBK");
		System.arraycopy(sensor4NameValid, 0, sensor4NameBytes, 0, sensor4NameValid.length);
		buf.clear();
		buf.putDouble(upValue_4);
		byte[] upvalue4Bytes=buf.array();
		buf.clear();
		buf.putDouble(downValue_4);
		byte[] downvalue4Bytes=buf.array();
		byte upaction4Bytes=(byte) upValueAction_4;
		byte midaction4Bytes=(byte) midValueAction_4;
		byte downaction4Bytes=(byte) downValueAction_4;
		byte[] sensor4Data=Converter.byteArrsMerger(
				Converter.byteArrsMerger(
					Converter.byteArrsMerger(
						Converter.byteArrsMerger(enable4Bytes, sensor4NameBytes), 
						upvalue4Bytes), downvalue4Bytes), new byte[]{upaction4Bytes,midaction4Bytes,downaction4Bytes});
		byte[] sensorData=Converter.byteArrsMerger(Converter.byteArrsMerger(Converter.byteArrsMerger(sensor1Data, sensor2Data), sensor3Data), sensor4Data);
		byte[] data=Converter.byteArrsMerger(Converter.byteArrsMerger(preData, timeData), sensorData);
		sdpm.setData(data);
		int dataLen=data.length+Constants.CRC_LEN+Constants.ENDER_LEN;
		sdpm.setDataLen(new byte[]{(byte) (dataLen/256),(byte) (dataLen%256)});
		return sdpm;
	}
	
	protected Calendar getCalendar(Date date){
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		return c;
	}
	@Override
	public String toString() {
		return "<单点设备参数>[id=" + id + ", zoneName=" + zoneName + ", deviceName=" + deviceName + ", workDays="
				+ workDays + ", time_1_start=" + time_1_start + ", time_1_end=" + time_1_end + ", run_time_1="
				+ run_time_1 + ", idle_time_1=" + idle_time_1 + ", time_2_start=" + time_2_start + ", time_2_end="
				+ time_2_end + ", run_time_2=" + run_time_2 + ", idle_time_2=" + idle_time_2 + ", time_3_start="
				+ time_3_start + ", time_3_end=" + time_3_end + ", run_time_3=" + run_time_3 + ", idle_time_3="
				+ idle_time_3 + ", time_4_start=" + time_4_start + ", time_4_end=" + time_4_end + ", run_time_4="
				+ run_time_4 + ", idle_time_4=" + idle_time_4 + ", sensor_1_enable=" + sensor_1_enable
				+ ", sensor_1_name=" + sensor_1_name + ", upValueAction_1=" + upValueAction_1 + ", upValue_1="
				+ upValue_1 + ", midValueAction_1=" + midValueAction_1 + ", downValue_1=" + downValue_1
				+ ", downValueAction_1=" + downValueAction_1 + ", sensor_2_enable=" + sensor_2_enable
				+ ", sensor_2_name=" + sensor_2_name + ", upValueAction_2=" + upValueAction_2 + ", upValue_2="
				+ upValue_2 + ", midValueAction_2=" + midValueAction_2 + ", downValue_2=" + downValue_2
				+ ", downValueAction_2=" + downValueAction_2 + ", sensor_3_enable=" + sensor_3_enable
				+ ", sensor_3_name=" + sensor_3_name + ", upValueAction_3=" + upValueAction_3 + ", upValue_3="
				+ upValue_3 + ", midValueAction_3=" + midValueAction_3 + ", downValue_3=" + downValue_3
				+ ", downValueAction_3=" + downValueAction_3 + ", sensor_4_enable=" + sensor_4_enable
				+ ", sensor_4_name=" + sensor_4_name + ", upValueAction_4=" + upValueAction_4 + ", upValue_4="
				+ upValue_4 + ", midValueAction_4=" + midValueAction_4 + ", downValue_4=" + downValue_4
				+ ", downValueAction_4=" + downValueAction_4 + "]";
	}
	
}
