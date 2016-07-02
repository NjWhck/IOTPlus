package com.whck.domain.base;

import java.io.IOException;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import com.whck.mina.constants.Constants;
import com.whck.mina.helper.Converter;
import com.whck.mina.message.DeviceStateMessage;

@Entity
@Table(name="tb_device")
public class BinDevice {
	@Id
	@GeneratedValue
	private int id;
	
	@Column(name="zone_name",length=11)
	private String zoneName;
	
	@Column(nullable=false,unique=true)
	private String name;
	
	@Column(length=15)
	private String ip;
	
	private int type;
	
	@Column(name="ctrl_mode")
	@Max(value=1)
	@Min(value=0)
	private int ctrlMode;
	
	@Min(value=0)
	@Max(value=1)
	private int online;
	
	@Min(value=0)
	@Max(value=1)
	private int state;
	@OneToOne(optional=false,fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="bdpid")//TODO: 表中添加此外键
	private BinDeviceParams binDeviceParams;
	
	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.PERSIST)
	@JoinTable(name="tb_bindevice_sensor",
			joinColumns=@JoinColumn(name="bdid"),
			inverseJoinColumns=@JoinColumn(name="sid"))
	private List<Sensor> sensors;
	
	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public List<Sensor> getSensors() {
		return sensors;
	}

	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public BinDeviceParams getBinDeviceParams() {
		return binDeviceParams;
	}

	public void setBinDeviceParams(BinDeviceParams binDeviceParams) {
		this.binDeviceParams = binDeviceParams;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCtrlMode() {
		return ctrlMode;
	}

	public void setCtrlMode(int ctrlMode) {
		this.ctrlMode = ctrlMode;
	}

	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	public DeviceStateMessage convert() throws IOException{
		DeviceStateMessage m=new DeviceStateMessage();
		byte[] id=zoneName.getBytes("ISO-8859-1");
		m.setId(id);
		m.setLongitude(new byte[]{0,0,0});
		m.setLatitude(new byte[]{0,0,0});
		byte[] devName=new byte[20];
		byte[] validBytes=name.getBytes("GBK");
		System.arraycopy(validBytes, 0, devName, 0, validBytes.length);
		byte[] ctrlMod= (ctrlMode==0? new byte[]{0}:new byte[]{1});
		byte[] devType= new byte[]{2};
		byte[] status;
		if(0==state)
			status=new byte[]{0};
		else if(1==state)
			status=new byte[]{1};
		else
			status=new byte[]{2};
		byte[] data=Converter.byteArrsMerger(Converter.byteArrsMerger(
				Converter.byteArrsMerger(devName, ctrlMod), devType), status);
		m.setData(data);
		m.setDataLen(new byte[]{0,(byte) (data.length+Constants.CRC_LEN+Constants.ENDER_LEN)});
		return m;
	}

	@Override
	public String toString() {
		return "<电机设备>[id=" + id + ", zoneName=" + zoneName + ", name=" + name + ", ip=" + ip + ", type=" + type
				+ ", ctrlMode=" + ctrlMode + ", online=" + online + ", state=" + state + ", binDeviceParams="
				+ binDeviceParams + ", sensors=" + sensors + "]";
	}
	
	
	
}
