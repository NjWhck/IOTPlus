package com.whck.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.whck.domain.base.SinDevice;

public interface SinDeviceDao extends JpaRepository<SinDevice, Integer>{
	List<SinDevice> findByZoneName(String zoneName);
	SinDevice findByZoneNameAndName(String zoneName,String devName);
	SinDevice deleteByZoneNameAndName(String zoneName,String devName);
}
