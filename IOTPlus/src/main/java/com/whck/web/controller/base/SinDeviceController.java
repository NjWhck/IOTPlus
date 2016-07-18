package com.whck.web.controller.base;

import java.util.List;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.whck.mina.server.Broadcast;
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
	public void updateDeviceState(@RequestParam String zoneName, @RequestParam String devName,@PathVariable String cmd){
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
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/params_update",method = {RequestMethod.POST})
	public String updateDeviceParams(SinDeviceParams dp){
		try {
			SinDeviceParamsMessage	m = dp.convert();
			if(!Broadcast.broadcast(m))
				return "disconnected";
			sdps.addOrUpdate(dp);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}
	}
	
	@RequestMapping(value="/params_load",method = {RequestMethod.POST})
	public SinDeviceParams loadDeviceParams(@RequestParam String zoneName,@RequestParam String deviceName){
		
		return sdps.getSinDeviceParams(zoneName,deviceName);
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
