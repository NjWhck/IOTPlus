package com.whck.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import com.whck.domain.base.SinDeviceParams;

public interface SinDeviceParamsDao extends JpaRepository<SinDeviceParams, Integer>{
	SinDeviceParams findByZoneNameAndDeviceName(String zoneName,String deviceName);
}
