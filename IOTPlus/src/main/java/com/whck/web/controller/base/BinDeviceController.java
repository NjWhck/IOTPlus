package com.whck.web.controller.base;

import java.io.IOException;
import java.util.List;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.whck.domain.base.BinDevice;
import com.whck.domain.base.BinDeviceParams;
import com.whck.domain.base.Sensor;
import com.whck.mina.handler.ProtocolHandler;
import com.whck.mina.message.BinDeviceParamsMessage;
import com.whck.mina.message.DeviceStateMessage;
import com.whck.service.base.BinDeviceParamsService;
import com.whck.service.base.BinDeviceService;

@RestController
@RequestMapping(value="/bindevctrl")
public class BinDeviceController {
	@Autowired
	private BinDeviceParamsService bdps;
	@Autowired
	private BinDeviceService bds;
	@RequestMapping(value="/add",method = {RequestMethod.POST})
	public String addDevice(BinDevice device){
		try{
			BinDevice dev=bds.getDevice(device);
			if(dev!=null)
				return "exist";
			bds.addOrUpdate(dev);
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			return "failed";
		}
	}
	@RequestMapping(value="/delete",method = {RequestMethod.POST})
	public String deleteDevice(BinDevice binDevice){
		try{
			bds.removeDevice(binDevice);
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			return "failed";
		}
	}
	@RequestMapping(value="/operation/{cmd}",method = {RequestMethod.POST})
	public String  updateDeviceState(@RequestParam String zoneName,@RequestParam String devName,@PathVariable String cmd){
		try {
			BinDevice binDev=new BinDevice();
			binDev.setZoneName(zoneName);
			binDev.setName(devName);
			BinDevice device=bds.getDevice(binDev);
			if(cmd.equals("manual")){
				device.setCtrlMode(1);
			}else if(cmd.equals("auto")){
				device.setCtrlMode(0);
			}else if(cmd.equals("stop")){
				device.setState(0);
			}else if(cmd.equals("forward")){
				device.setState(1);
			}else if(cmd.equals("backward")){
				device.setState(2);
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
	public String updateDeviceParams(BinDeviceParams dp){
			try {
				BinDeviceParamsMessage msg = dp.convert();
				IoSession session=ProtocolHandler.sessions.get(dp.getZoneName());
				if(session!=null&&session.isConnected()){
					session.write(msg);
					bdps.addOrUpdate(dp);
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
	public BinDeviceParams loadDeviceParams( String zoneName, String devName){
		try{
			BinDeviceParams bdp=bdps.getBinDeviceParams(zoneName,devName);
			return bdp;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value="/sensor_bind",method={RequestMethod.POST})
	public String bindSensor(List<Sensor> sensors,@RequestParam String zoneName, @RequestParam String devName){
		//TODO:Exception Handling
		BinDevice dev=new BinDevice();
		dev.setZoneName(zoneName);
		dev.setName(devName);
		BinDevice device=bds.getDevice(dev);
		device.setSensors(sensors);
		return "success";
	}
}
