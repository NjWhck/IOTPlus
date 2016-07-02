package com.whck.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import com.whck.domain.base.BinDeviceParams;

public interface BinDeviceParamsDao extends JpaRepository<BinDeviceParams, Integer>{
	BinDeviceParams findByZoneNameAndDeviceName(String zoneName,String deviceName);
}
