package com.whck.web.controller.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.whck.domain.base.BinDevice;
import com.whck.domain.base.Sensor;
import com.whck.domain.base.SinDevice;
import com.whck.domain.base.User;
import com.whck.domain.base.Zone;
import com.whck.service.base.UserService;

@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	private UserService us;
	@RequestMapping("/refresh")
	public String refresh(HttpSession session){
		long time=new Date().getTime();
		System.out.println("=======================timestamp:"+time);
		session.setAttribute("time", time);
		return "test";
	}
	@RequestMapping("/login")
	public String login(User user,Model model){
		try{
			User usr=us.getUser(user);
			if(usr==null){
				model.addAttribute("error", true);
				return "login";
			}
			return "main";
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}
	@RequestMapping("/socket")
	public String video(){
		return "socket";
	}
	@RequestMapping(value="/sensor/add", method = {RequestMethod.POST})
	@ResponseBody
	public String addSensor(Sensor sensor){
		System.out.println("Sensor:"+sensor);
		return "false";
	}
	@RequestMapping("/main")
	public String main(Model model){
		List<Zone> zones=new ArrayList<>();
		Zone zone =new Zone();
		zone.setName("15850506481");
		zone.setAlias("东区");
		List<BinDevice> binDevs=new ArrayList<>();
		List<SinDevice> sinDevs=new ArrayList<>();
		List<Sensor> sensors=new ArrayList<>();
		
		BinDevice binDev1=new BinDevice();
		binDev1.setZoneName("15850506481");
		binDev1.setName("风机");
		binDev1.setOnline(1);
		binDev1.setCtrlMode(1);
		
		BinDevice binDev2=new BinDevice();
		binDev2.setZoneName("15850506481");
		binDev2.setName("窗帘");
		binDev2.setOnline(0);
		binDev2.setCtrlMode(1);
		
		SinDevice sinDev1=new SinDevice();
		sinDev1.setZoneName("15850506481");
		sinDev1.setName("降雨器");
		sinDev1.setOnline(1);
		sinDev1.setCtrlMode(0);
		
		SinDevice sinDev2=new SinDevice();
		sinDev2.setZoneName("15850506481");
		sinDev2.setName("生长灯");
		sinDev2.setOnline(1);
		sinDev2.setCtrlMode(1);
		
		Sensor sensor1=new Sensor();
		sensor1.setName("温度传感器");
		sensor1.setDownValue(0);
		sensor1.setUpValue(999);
		sensor1.setUnit("℃");
		sensor1.setOnline(0);
		sensor1.setValue(20.8);
		
		Sensor sensor2=new Sensor();
		sensor2.setName("湿度传感器");
		sensor2.setDownValue(0);
		sensor2.setUpValue(100);
		sensor2.setUnit("%");
		sensor2.setOnline(1);
		sensor2.setValue(23);
		
		binDevs.add(binDev1);
		binDevs.add(binDev2);
		
		sinDevs.add(sinDev1);
		sinDevs.add(sinDev2);
		
		sensors.add(sensor1);
		sensors.add(sensor2);
		
		binDev1.setSensors(sensors);
		binDev2.setSensors(sensors);
		sinDev1.setSensors(sensors);
		sinDev2.setSensors(sensors);
		
		zone.setBinDevices(binDevs);
		zone.setSinDevices(sinDevs);
		zone.setSensors(sensors);
		zones.add(zone);
		model.addAttribute("zones", zones);
		return "main";
	}
}
