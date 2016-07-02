package com.whck.serviceImpl.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whck.dao.base.SensorDao;
import com.whck.domain.base.Sensor;
import com.whck.service.base.SensorService;

@Service
public class SensorServiceImpl implements SensorService{

	@Autowired
	private SensorDao sensorDao;
	@Override
	public Sensor addOrUpdateSensor(Sensor sensor) {
		Sensor ss=sensorDao.findByZoneNameAndName(sensor.getZoneName(), sensor.getName());
		if(ss!=null){
			ss.setIp(sensor.getIp());
			ss.setName(sensor.getName());
			ss.setZoneName(sensor.getZoneName());
			ss.setUnit(sensor.getUnit());
			ss.setUpValue(sensor.getUpValue());
			ss.setDownValue(sensor.getDownValue());
			ss.setOnline(sensor.getOnline());
			
			return sensorDao.save(ss);
		}
		return sensorDao.save(ss);
	}

	@Override
	public Sensor removeSensor(String zoneName, String sensorName) {
		return sensorDao.deleteByZoneNameAndName(zoneName, sensorName);
	}

	@Override
	public Sensor getSensor(String zoneName, String sensorName) {
		return sensorDao.findByZoneNameAndName(zoneName, sensorName);
	}

	@Override
	public List<Sensor> getAllByZoneName(String zoneName) {
		return sensorDao.findByZoneName(zoneName);
	}

}
