package com.whck.serviceImpl.base;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.whck.dao.base.BinDeviceDao;
import com.whck.domain.base.BinDevice;
import com.whck.service.base.BinDeviceService;

@Service
public class BinDeviceServiceImpl implements  BinDeviceService{
	@Autowired
	private BinDeviceDao deviceDao;
	@Override
	public List<BinDevice> getAll() {
		return deviceDao.findAll();
	}
	
	@Override
	public BinDevice addOrUpdate(BinDevice device) {
		BinDevice dev=deviceDao.findByZoneNameAndName(device.getZoneName(),device.getName());
		if(dev==null)
			return deviceDao.save(device);
		dev.setIp(device.getIp());
		dev.setOnline(device.getOnline());
		dev.setState(device.getState());
		dev.setType(device.getType());
		return deviceDao.save(dev);
	}

	@Override
	public BinDevice removeDevice(String zoneName, String devName) {
		return deviceDao.deleteByZoneNameAndName(zoneName, devName);
	}

	@Override
	public BinDevice getDevice(String zoneName, String devName) {
		return deviceDao.findByZoneNameAndName(zoneName, devName);
	}

	@Override
	public List<BinDevice> getAllByZoneName(String zoneName) {
		return deviceDao.findByZoneName(zoneName);
	}
}
