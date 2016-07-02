package com.whck.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.whck.domain.base.BinDevice;

public interface BinDeviceDao extends JpaRepository<BinDevice, Integer>{
	BinDevice findByZoneNameAndName(String zoneName,String devName);
	BinDevice deleteByZoneNameAndName(String zoneName,String devName);
	List<BinDevice> findByZoneName(String zoneName);
}
