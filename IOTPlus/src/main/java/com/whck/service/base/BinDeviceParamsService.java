package com.whck.service.base;

import org.springframework.stereotype.Service;
import com.whck.domain.base.BinDeviceParams;

@Service
public interface BinDeviceParamsService {
	BinDeviceParams getBinDeviceParams(String zoneName,String deviceName);
	BinDeviceParams addOrUpdate(BinDeviceParams dp);
}
