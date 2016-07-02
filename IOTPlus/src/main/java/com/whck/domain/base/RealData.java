package com.whck.domain.base;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name="tb_real_data")
public class RealData {
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(name="zone_name",length=11)
	private String zoneName;
	private String name;
	@Column(name="date_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime;
	@Column(precision=16,scale=3)
	private double value;
	@Column(length=12)
	private String unit;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public String toString() {
		return "<实时数据>[id=" + id + ", zoneName=" + zoneName + ", name=" + name + ", dateTime=" + dateTime
				+ ", value=" + value + ", unit=" + unit + "]";
	}
	
}
