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
	public BinDevice removeDevice(BinDevice binDevice) {
		return deviceDao.deleteByZoneNameAndName(binDevice.getZoneName(),binDevice.getName());
	}

	@Override
	public BinDevice getDevice(BinDevice binDevice) {
		return deviceDao.findByZoneNameAndName(binDevice.getZoneName(),binDevice.getName());
	}

	@Override
	public List<BinDevice> getAllByZoneName(String zoneName) {
		return deviceDao.findByZoneName(zoneName);
	}
}
