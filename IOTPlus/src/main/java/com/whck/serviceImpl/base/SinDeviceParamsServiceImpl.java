package com.whck.serviceImpl.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whck.dao.base.SinDeviceParamsDao;
import com.whck.domain.base.SinDeviceParams;
import com.whck.service.base.SinDeviceParamsService;

@Service
public class SinDeviceParamsServiceImpl implements SinDeviceParamsService{

	@Autowired
	private SinDeviceParamsDao deviceParamsDao;
	@Override
	public SinDeviceParams getSinDeviceParams(String zoneName,String deviceName) {
		return deviceParamsDao.findByZoneNameAndDeviceName(zoneName,deviceName);
	}
	@Override
	public SinDeviceParams addOrUpdate(SinDeviceParams dp) {
		SinDeviceParams deviceParams=deviceParamsDao.findByZoneNameAndDeviceName(dp.getZoneName(),dp.getDeviceName());
		if(deviceParams==null)
			return deviceParamsDao.save(dp);

		deviceParams.setTime_1_start(dp.getTime_1_start());
		deviceParams.setTime_2_start(dp.getTime_2_start());
		deviceParams.setTime_3_start(dp.getTime_3_start());
		deviceParams.setTime_4_start(dp.getTime_4_start());
		
		deviceParams.setTime_1_end(dp.getTime_1_end());
		deviceParams.setTime_2_end(dp.getTime_2_end());
		deviceParams.setTime_3_end(dp.getTime_3_end());
		deviceParams.setTime_4_end(dp.getTime_4_end());
		
		deviceParams.setRun_time_1(dp.getRun_time_1());
		deviceParams.setRun_time_2(dp.getRun_time_2());
		deviceParams.setRun_time_3(dp.getRun_time_3());
		deviceParams.setRun_time_4(dp.getRun_time_4());
		
		deviceParams.setIdle_time_1(dp.getIdle_time_1());
		deviceParams.setIdle_time_2(dp.getIdle_time_2());
		deviceParams.setIdle_time_3(dp.getIdle_time_3());
		deviceParams.setIdle_time_4(dp.getIdle_time_4());
		
		deviceParams.setSensor_1_name(dp.getSensor_1_name());
		deviceParams.setSensor_2_name(dp.getSensor_2_name());
		deviceParams.setSensor_3_name(dp.getSensor_3_name());
		deviceParams.setSensor_4_name(dp.getSensor_4_name());
		
		deviceParams.setUpValueAction_1(dp.getUpValueAction_1());
		deviceParams.setUpValueAction_2(dp.getUpValueAction_2());
		deviceParams.setUpValueAction_3(dp.getUpValueAction_3());
		deviceParams.setUpValueAction_4(dp.getUpValueAction_4());
		
		deviceParams.setUpValue_1(dp.getUpValue_1());
		deviceParams.setUpValue_2(dp.getUpValue_2());
		deviceParams.setUpValue_3(dp.getUpValue_3());
		deviceParams.setUpValue_4(dp.getUpValue_4());
		
		deviceParams.setMidValueAction_1(dp.getMidValueAction_1());
		deviceParams.setMidValueAction_2(dp.getMidValueAction_2());
		deviceParams.setMidValueAction_3(dp.getMidValueAction_3());
		deviceParams.setMidValueAction_4(dp.getMidValueAction_4());
		
		deviceParams.setDownValue_1(dp.getDownValue_1());
		deviceParams.setDownValue_2(dp.getDownValue_2());
		deviceParams.setDownValue_3(dp.getDownValue_3());
		deviceParams.setDownValue_4(dp.getDownValue_4());
		
		deviceParams.setDownValueAction_1(dp.getDownValueAction_1());
		deviceParams.setDownValueAction_2(dp.getDownValueAction_2());
		deviceParams.setDownValueAction_3(dp.getDownValueAction_3());
		deviceParams.setDownValueAction_4(dp.getDownValueAction_4());
		
		return deviceParamsDao.save(deviceParams);
	}
}
