package com.whck.service.base;

import java.util.List;

import org.springframework.stereotype.Service;

import com.whck.domain.base.Sensor;

@Service
public interface SensorService {
	Sensor addOrUpdateSensor(Sensor sensor);
	Sensor removeSensor(String zoneName,String sensorName);
	Sensor getSensor(String zoneName,String sensorName);
	List<Sensor> getAllByZoneName(String zoneName);
}
