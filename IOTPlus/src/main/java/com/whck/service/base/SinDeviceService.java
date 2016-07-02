package com.whck.service.base;

import java.util.List;
import org.springframework.stereotype.Service;
import com.whck.domain.base.SinDevice;

@Service
public interface SinDeviceService {
	List<SinDevice> getAll();
	List<SinDevice> getAllByZoneName(String zoneName);
	SinDevice getDevice(String zoneName,String devName);
	SinDevice addOrUpdate(SinDevice device);
	SinDevice removeDevice(String zoneName,String devName);
}
