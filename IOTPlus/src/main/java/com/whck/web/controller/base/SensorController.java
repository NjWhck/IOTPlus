package com.whck.web.controller.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.whck.domain.base.Sensor;
import com.whck.service.base.SensorService;

@Controller
@RequestMapping(value="/sensor")
public class SensorController {
	@Autowired
	private SensorService ss;
	
	@RequestMapping(value="/add",method={RequestMethod.POST})
	@ResponseBody
	public Sensor addSensor(@RequestBody Sensor sensor){
		return ss.addOrUpdateSensor(sensor);
	}
	@RequestMapping(value="/update",method={RequestMethod.POST})
	@ResponseBody
	public Sensor updateSensor(@RequestBody Sensor sensor){
		return ss.addOrUpdateSensor(sensor);
	}
	@RequestMapping(value="/delete/{zone_name}/{sensor_name}",method={RequestMethod.DELETE})
	@ResponseBody
	public Sensor deleteSensor(@PathVariable("zone_name")String zoneName,@PathVariable("sensor_name") String sensorName){
		return ss.removeSensor(zoneName, sensorName);
	}
}
