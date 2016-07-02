package com.whck.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whck.domain.base.RealData;

public interface RealDataDao extends JpaRepository<RealData, Integer>{
	//TODO:average value per minute,hour,day...
	RealData findTopByZoneNameAndNameOrderByDateTimeDesc(String zoneName,String name);
}
