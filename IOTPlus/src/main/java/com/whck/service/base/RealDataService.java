package com.whck.service.base;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

import com.whck.domain.base.RealData;

@Service
public interface RealDataService {
	RealData addOne(RealData realData);
	List<RealData> getByName(String zoneName,String name);
	RealData getLatest(String zoneName,String name);
	List<RealData> getAvg(String zoneName,String name,Date from,Date to,TimeSpan timeType,Integer limit,Integer fromIndex);
}
