package com.whck.serviceImpl.base;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.whck.dao.base.SinDeviceDao;
import com.whck.domain.base.SinDevice;
import com.whck.service.base.SinDeviceService;

@Service
public class SinDeviceServiceImpl implements  SinDeviceService{
	@Autowired
	private SinDeviceDao deviceDao;
	@Override
	public List<SinDevice> getAll() {
		return deviceDao.findAll();
	}
	
	@Override
	public SinDevice addOrUpdate(SinDevice device) {
		SinDevice dev=deviceDao.findByZoneNameAndName(device.getZoneName(),device.getName());
		if(dev==null)
			return deviceDao.save(device);
		dev.setIp(device.getIp());
		dev.setOnline(device.getOnline());
		dev.setState(device.getState());
		dev.setType(device.getType());
		return deviceDao.save(dev);
	}

	@Override
	public SinDevice removeDevice(SinDevice sinDevice) {
		return deviceDao.deleteByZoneNameAndName(sinDevice.getZoneName(),sinDevice.getName());
	}

	@Override
	public SinDevice getDevice(SinDevice sinDevice) {
		return deviceDao.findByZoneNameAndName(sinDevice.getZoneName(),sinDevice.getName());
	}

	@Override
	public List<SinDevice> getAllByZoneName(String zoneName) {
		return deviceDao.findByZoneName(zoneName);
	}

}
