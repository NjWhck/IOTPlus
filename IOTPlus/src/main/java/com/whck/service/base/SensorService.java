package com.whck.service.base;

import java.util.List;

import org.springframework.stereotype.Service;

import com.whck.domain.base.Sensor;

@Service
public interface SensorService {
	Sensor addOrUpdate(Sensor sensor);
	Sensor removeSensor(Sensor sensor);
	Sensor getSensor(Sensor sensor);
	List<Sensor> getAllByZoneName(String zoneName);
}
