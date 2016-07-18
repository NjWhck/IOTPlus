package com.whck.service.base;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whck.domain.base.Sensor;

@Service
@Transactional
public interface SensorService {
	Sensor addOrUpdate(Sensor sensor);
	Sensor removeSensor(Sensor sensor);
	Sensor getSensor(Sensor sensor);
	Sensor getSensor(String zoneName,String sensorName);
	List<Sensor> getAllByZoneName(String zoneName);
}
