package com.whck.web.controller.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	public String addSensor(Sensor sensor){
		try{
			Sensor sens=ss.getSensor(sensor);
			if(sens!=null)
				return "exist";
			ss.addOrUpdate(sensor);
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			return "failed";
		}
	}
	@RequestMapping(value="/update",method={RequestMethod.POST})
	@ResponseBody
	public String updateSensor(Sensor sensor){
		try{
			ss.addOrUpdate(sensor);
			return  "success";
		}catch(Exception e){
			e.printStackTrace();
			return "false";
		}
	}
	@RequestMapping(value="/delete",method={RequestMethod.DELETE})
	@ResponseBody
	public String deleteSensor(Sensor sensor){
		try{
			ss.removeSensor(sensor);
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			return "failed";
		}
	}
}
