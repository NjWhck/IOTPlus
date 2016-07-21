package com.whck.web.controller.base;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.whck.domain.base.Sensor;
import com.whck.domain.base.SinDevice;
import com.whck.domain.base.SinDeviceParams;
import com.whck.mina.handler.ProtocolHandler;
import com.whck.mina.message.DeviceStateMessage;
import com.whck.mina.message.SinDeviceParamsMessage;
import com.whck.service.base.SinDeviceParamsService;
import com.whck.service.base.SinDeviceService;

@RestController
@RequestMapping(value="/sindevctrl")
public class SinDeviceController {
	@Autowired
	private SinDeviceParamsService sdps;
	@Autowired
	private SinDeviceService sds;
	
	@RequestMapping(value="/add",method = {RequestMethod.POST})
	public String addDevice(SinDevice device){
		try{
			SinDevice dev=sds.getDevice(device);
			if(dev!=null)
				return "exist";
			sds.addOrUpdate(dev);
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			return "failed";
		}
	}
	@RequestMapping(value="/delete")
	public String deleteDevice(SinDevice device){
		try{
			sds.removeDevice(device);
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			return "failed";
		}
	}
	@RequestMapping(value="/operation/{cmd}",method = {RequestMethod.POST})
	public String updateDeviceState(@RequestParam String zoneName, @RequestParam String devName,@PathVariable String cmd){
		try {
			SinDevice sinDev=new SinDevice();
			sinDev.setZoneName(zoneName);
			sinDev.setName(devName);
			SinDevice device=sds.getDevice(sinDev);
			if(cmd.equals("manual")){
				device.setCtrlMode(1);
			}else if(cmd.equals("auto")){
				device.setCtrlMode(0);
			}else if(cmd.equals("start")){
				device.setState(1);
			}else if(cmd.equals("stop")){
				device.setState(0);
			}
			DeviceStateMessage	m = device.convert();
			IoSession session=ProtocolHandler.sessions.get(zoneName);
			if(session!=null&&session.isConnected()){
				session.write(m);
				return "devOffline";
			}
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(value="/params_update",method = {RequestMethod.POST})
	public String updateDeviceParams(SinDeviceParams sp){
		try {
			SinDeviceParamsMessage msg = sp.convert();
			IoSession session=ProtocolHandler.sessions.get(sp.getZoneName());
			if(session!=null&&session.isConnected()){
				session.write(msg);
				sdps.addOrUpdate(sp);
				return "success";
			}else{
				return "disconnected";
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "failed";
		}
	}
	
	@RequestMapping(value="/params_load",method = {RequestMethod.POST})
	public SinDeviceParams loadDeviceParams(@RequestParam String zoneName,@RequestParam String devName){
		SinDeviceParams sdp=new SinDeviceParams();
		sdp.setZoneName("31716071202");
		sdp.setDeviceName("灯光");
		sdp.setWorkDays(1);
		sdp.setDownValue_1(0);
		sdp.setDownValue_2(0);
		sdp.setDownValue_3(0);
		sdp.setDownValue_4(0);
		sdp.setDownValueAction_1(0);
		sdp.setDownValueAction_1(0);
		sdp.setDownValueAction_1(0);
		sdp.setDownValueAction_1(0);
		sdp.setUpValue_1(9);
		sdp.setUpValue_2(9);
		sdp.setUpValue_3(9);
		sdp.setUpValue_4(9);
		sdp.setUpValueAction_1(1);
		sdp.setUpValueAction_2(1);
		sdp.setUpValueAction_3(1);
		sdp.setUpValueAction_4(1);
		sdp.setIdle_time_1(1);
		sdp.setIdle_time_2(1);
		sdp.setIdle_time_3(1);
		sdp.setIdle_time_4(1);
		sdp.setMidValueAction_1(1);
		sdp.setMidValueAction_2(1);
		sdp.setMidValueAction_3(1);
		sdp.setMidValueAction_4(1);
		sdp.setRun_time_1(1);
		sdp.setRun_time_2(1);
		sdp.setRun_time_3(1);
		sdp.setRun_time_4(1);
		sdp.setTime_1_end(new Date());
		sdp.setTime_2_end(new Date());
		sdp.setTime_3_end(new Date());
		sdp.setTime_4_end(new Date());
		sdp.setTime_1_start(new Date());
		sdp.setTime_2_start(new Date());
		sdp.setTime_3_start(new Date());
		sdp.setTime_4_start(new Date());
		sdp.setSensor_1_enable(1);
		sdp.setSensor_2_enable(0);
		sdp.setSensor_3_enable(1);
		sdp.setSensor_4_enable(0);
		sdp.setSensor_1_name("温度");
		sdp.setSensor_2_name("湿度");
		sdp.setSensor_3_name("红外");
		sdp.setSensor_4_name("光照");
		
//		boolean[] sensorEnable={false,false,true,true};
//		boolean[] weekDays={false,false,true,true,true,false,false};
//		model.addAttribute("sinDevParams", sdp);
//		model.addAttribute("sensorEnable", sensorEnable);
//		model.addAttribute("weekDays", weekDays);
		return sdp;
//		return sdps.getSinDeviceParams(zoneName,devName);
	}
	
	@RequestMapping(value="/sensor_bind",method={RequestMethod.POST})
	public String bindSensor(List<Sensor> sensors, @RequestParam String zoneName,@RequestParam String devName){
		SinDevice dev=new SinDevice();
		dev.setZoneName(zoneName);
		dev.setName(devName);
		SinDevice device=sds.getDevice(dev);
		device.setSensors(sensors);
		return "success";
	}
}
