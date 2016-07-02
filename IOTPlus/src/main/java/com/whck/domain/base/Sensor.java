package com.whck.domain.base;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
@Entity
@Table(name="tb_sensor")
public class Sensor {
	@Id
	@GeneratedValue
	private Integer id;
	@Column(name="zone_name")
	private String zoneName;
	@Column(length=15)
	private String ip;
	@Column(length=24,nullable=false,unique=true)
	private String name;
	@Column(length=20)
	private String unit;
	@Column(name="up_value",precision=12,scale=3)
	private double upValue;
	@Column(name="down_value",precision=12,scale=3)
	private double downValue;
	@Column(precision=12,scale=3)
	private double value;
	@Min(value=0)
	@Max(value=1)
	private Integer online;
	
	@Min(value=0)
	@Max(value=1)
	private Integer state;
	
	@ManyToMany(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.MERGE},mappedBy="sensors")
	private List<SinDevice> sinDevice;
	@ManyToMany(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.MERGE},mappedBy="sensors")
	private List<BinDevice> binDevice;
	
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getUpValue() {
		return upValue;
	}

	public void setUpValue(double upValue) {
		this.upValue = upValue;
	}

	public double getDownValue() {
		return downValue;
	}

	public void setDownValue(double downValue) {
		this.downValue = downValue;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public List<SinDevice> getSinDevice() {
		return sinDevice;
	}

	public void setSinDevice(List<SinDevice> sinDevice) {
		this.sinDevice = sinDevice;
	}

	public List<BinDevice> getBinDevice() {
		return binDevice;
	}

	public void setBinDevice(List<BinDevice> binDevice) {
		this.binDevice = binDevice;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	@Override
	public String toString() {
		return "<传感器>[id=" + id + ", zoneName=" + zoneName + ", ip=" + ip + ", name=" + name + ", unit=" + unit
				+ ", upValue=" + upValue + ", downValue=" + downValue + ", online=" + online + ", sinDevice="
				+ sinDevice + ", binDevice=" + binDevice + "]";
	}

}
