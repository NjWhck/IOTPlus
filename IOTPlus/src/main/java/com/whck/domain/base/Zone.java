package com.whck.domain.base;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="tb_zone")
public class Zone {
	@Id
	@Column(nullable=false,unique=true)
	private Integer id;

	@Column(unique=true,nullable=false)
	private String name;
	
	private String alias;
	
	@Column(length=15)
	private String ip;
	
	@Column(name="zip_code",length=6)
	private String zipCode;
	
	private String address;
	
	@Column(precision=15,scale=3)
	private BigDecimal area;
	
	private String longitude;
	
	private String latitude;
	
	private String administrator;
	
	@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name="sd_id")//TODO:sindevice表中添加外键
	private List<SinDevice> sinDevices;
	@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name="bd_id")//TODO:bindevice表中添加外键
	private List<BinDevice> binDevices;
	
	@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.PERSIST)
	@JoinColumn(name="zid",referencedColumnName="id")
	private List<Sensor> sensors;
	private String remark;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Sensor> getSensors() {
		return sensors;
	}

	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getAdministrator() {
		return administrator;
	}

	public void setAdministrator(String administrator) {
		this.administrator = administrator;
	}

	public List<SinDevice> getSinDevices() {
		return sinDevices;
	}

	public void setSinDevices(List<SinDevice> sinDevices) {
		this.sinDevices = sinDevices;
	}

	public List<BinDevice> getBinDevices() {
		return binDevices;
	}

	public void setBinDevices(List<BinDevice> binDevices) {
		this.binDevices = binDevices;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
