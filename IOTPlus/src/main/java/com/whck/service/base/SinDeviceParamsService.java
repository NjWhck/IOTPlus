package com.whck.service.base;

import org.springframework.stereotype.Service;
import com.whck.domain.base.SinDeviceParams;

@Service
public interface SinDeviceParamsService {
	SinDeviceParams getSinDeviceParams(String zoneName,String deviceName);
	SinDeviceParams addOrUpdate(SinDeviceParams dp);
}
