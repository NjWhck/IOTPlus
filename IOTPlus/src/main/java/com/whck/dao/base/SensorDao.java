package com.whck.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whck.domain.base.Sensor;


public interface SensorDao extends JpaRepository<Sensor, Integer>{
	Sensor findByZoneNameAndName(String zoneName,String sensorName);
	Sensor deleteByZoneNameAndName(String zoneName,String name);
	List<Sensor> findByZoneName(String zoneName);
}
