package com.whck.service.base;

import java.util.List;
import org.springframework.stereotype.Service;
import com.whck.domain.base.SinDevice;

@Service
public interface SinDeviceService {
	List<SinDevice> getAll();
	List<SinDevice> getAllByZoneName(String zoneName);
	SinDevice getDevice(SinDevice sinDevice);
	SinDevice addOrUpdate(SinDevice device);
	SinDevice removeDevice(SinDevice sinDevice);
}
