package com.whck.service.base;

import java.util.List;
import org.springframework.stereotype.Service;
import com.whck.domain.base.BinDevice;

@Service
public interface BinDeviceService {
	List<BinDevice> getAll();
	BinDevice getDevice(String zoneName,String devName);
	BinDevice addOrUpdate(BinDevice device);
	BinDevice removeDevice(String zoneName,String devName);
	List<BinDevice> getAllByZoneName(String zoneName);
}
