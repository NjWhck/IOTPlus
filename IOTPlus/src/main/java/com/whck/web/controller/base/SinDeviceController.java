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
@RequestMapping(value="/sindevice")
public class SinDeviceController {
	@Autowired
	private SinDeviceParamsService sdps;
	@Autowired
	private SinDeviceService sds;
	@RequestMapping(value="/add",method = {RequestMethod.POST})
	@ResponseBody
	public String addDevice(@RequestBody SinDevice device){
		SinDevice dev=sds.getDevice(device.getZoneName(),device.getName());
		if(dev!=null)
			return "exist";
		sds.addOrUpdate(dev);
		return "success";
	}
	@RequestMapping(value="/delete/{zone_name}/{device_name}")
	@ResponseBody
	public String deleteDevice(@PathVariable("zone_name") String zoneName,@PathVariable("device_name") String devName){
		sds.removeDevice(zoneName,devName);
		return "success";
	}
	@RequestMapping(value="/update",method = {RequestMethod.POST})
	@ResponseBody
	public String updateDeviceState(@RequestBody SinDevice device){
		DeviceStateMessage m=null;
		try {
			m = device.convert();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!Broadcast.broadcast(m))
			return "disconnect";
		sds.addOrUpdate(device);
		return "success";
	}
	
	@RequestMapping(value="/params_update",method = {RequestMethod.POST})
	@ResponseBody
	public String updateDeviceParams(@RequestBody SinDeviceParams dp){
		IoSession session=ProtocolHandler.sessions.get(sds.getDevice(dp.getZoneName(),dp.getDeviceName()).getIp());
		if((session==null)||(!session.isConnected())){
			return "error";
		}
		SinDeviceParamsMessage sdp=null;
		try {
			sdp = dp.convert();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.write(sdp);
		sdps.addOrUpdate(dp);
		return "success";
	}
	
	@RequestMapping(value="/params_load/{zone_name}/{device_name}",method = {RequestMethod.GET})
	@ResponseBody
	public SinDeviceParams loadDeviceParams(@PathVariable("zone_name") String zoneName,@PathVariable("device_name") String deviceName){
		
		return sdps.getSinDeviceParams(zoneName,deviceName);
	}
	
	@RequestMapping(value="/sensor_bind/{zone_name}/{device_name}",method={RequestMethod.POST})
	@ResponseBody
	public String bindSensor(@RequestBody Sensor sensor,
									@PathVariable("zone_name") String zoneName,
											@PathVariable("device_name") String devName){
		SinDevice device=sds.getDevice(zoneName, devName);
		device.getSensors().add(sensor);
		return "success";
	}
	
	@RequestMapping(value="/sensor_unbind/{zone_name}/{device_name}")
	@ResponseBody
	public String unbindSensor(@RequestBody Sensor sensor,@PathVariable("zone_name")String zoneName,
									@PathVariable("device_name")String devName){
		SinDevice device=sds.getDevice(zoneName, devName);
		device.getSensors().remove(sensor);
		return  "success";
	}
}
