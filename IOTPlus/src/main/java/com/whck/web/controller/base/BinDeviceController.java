package com.whck.web.controller.base;

import java.io.IOException;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.whck.domain.base.BinDevice;
import com.whck.domain.base.BinDeviceParams;
import com.whck.domain.base.Sensor;
import com.whck.mina.handler.ProtocolHandler;
import com.whck.mina.message.BinDeviceParamsMessage;
import com.whck.mina.message.DeviceStateMessage;
import com.whck.mina.server.Broadcast;
import com.whck.service.base.BinDeviceParamsService;
import com.whck.service.base.BinDeviceService;

@RestController
@RequestMapping(value="/bindevice")
public class BinDeviceController {
	@Autowired
	private BinDeviceParamsService bdps;
	@Autowired
	private BinDeviceService bds;
	@RequestMapping(value="/add",method = {RequestMethod.POST})
	@ResponseBody
	public String addDevice(@RequestBody BinDevice device){
		BinDevice dev=bds.getDevice(device.getZoneName(),device.getName());
		if(dev!=null)
			return "exist";
		bds.addOrUpdate(dev);
		return "success";
	}
	@RequestMapping(value="/delete/{zone_name}/{device_name}")
	@ResponseBody
	public String deleteDevice(@PathVariable("zone_name") String zoneName,@PathVariable("device_name") String devName){
		bds.removeDevice(zoneName,devName);
		return "success";
	}
	@RequestMapping(value="/update/{zone_name}/{device_name}/{cmd}",method = {RequestMethod.POST})
	@ResponseBody
	public String updateDeviceState(@RequestBody BinDevice device){
		DeviceStateMessage dsm=null;
		try {
			dsm = device.convert();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!Broadcast.broadcast(dsm))
			return "disconnect";
		bds.addOrUpdate(device);
		return "success";
	}
	
	@RequestMapping(value="/params_update",method = {RequestMethod.POST})
	@ResponseBody
	public String updateDeviceParams(@RequestBody BinDeviceParams dp){
		IoSession session=ProtocolHandler.sessions.get(bds.getDevice(dp.getZoneName(),dp.getDeviceName()).getIp());
		if((session==null)||(!session.isConnected())){
			return "error";
		}
		BinDeviceParamsMessage msg=null;
		try {
			msg = dp.convert();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.write(msg);
		bdps.addOrUpdate(dp);
		return "success";
	}
	
	@RequestMapping(value="/params_load/{zone_name}/{device_name}",method = {RequestMethod.GET})
	@ResponseBody
	public BinDeviceParams loadDeviceParams(@PathVariable("zone_name") String zoneName,@PathVariable("device_name") String deviceName){
		
		return bdps.getBinDeviceParams(zoneName,deviceName);
	}
	
	@RequestMapping(value="/sensor_bind/{zone_name}/{device_name}",method={RequestMethod.POST})
	@ResponseBody
	public String bindSensor(@RequestBody Sensor sensor,
									@PathVariable("zone_name") String zoneName,
											@PathVariable("device_name") String devName){
		BinDevice device=bds.getDevice(zoneName, devName);
		device.getSensors().add(sensor);
		return "success";
	}
	
	@RequestMapping(value="/sensor_unbind/{zone_name}/{device_name}")
	@ResponseBody
	public String unbindSensor(@RequestBody Sensor sensor,@PathVariable("zone_name")String zoneName,
									@PathVariable("device_name")String devName){
		BinDevice device=bds.getDevice(zoneName, devName);
		device.getSensors().remove(sensor);
		return  "success";
	}
}
