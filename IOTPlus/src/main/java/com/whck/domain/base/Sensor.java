package com.whck.domain.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
@Entity
@Table(name="tb_sensor")
public class Sensor {
	@Id
	@GeneratedValue
	private int id;
	@Column(name="zone_name")
	private String zoneName;
	@Column(name="dev_name")
	private String devName;
	@Column(length=15)
	private String ip;
	@Column(length=24,nullable=false)
	private String name;
	@Column(length=20)
	private String unit;
	@Column(name="up_value")
	private double upValue;
	@Column(name="down_value")
	private double downValue;
	private double value;
	@Min(value=0)
	@Max(value=1)
	private int online;
	
	@Min(value=0)
	@Max(value=1)
	private int state;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getDevName() {
		return devName;
	}

	public void setDevName(String devName) {
		this.devName = devName;
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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	@Override
	public String toString() {
		return "<传感器>[zoneName=" + zoneName + ", ip=" + ip + ", name=" + name + ", unit=" + unit
				+ ", upValue=" + upValue + ", downValue=" + downValue + ", online=" + online +  "]";
	}

}
