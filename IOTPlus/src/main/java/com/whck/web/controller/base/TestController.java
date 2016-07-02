package com.whck.web.controller.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whck.domain.base.BinDevice;
import com.whck.domain.base.Sensor;
import com.whck.domain.base.SinDevice;
import com.whck.domain.base.Zone;

@Controller
@RequestMapping("/test")
public class TestController {
	@RequestMapping("/refresh")
	public String refresh(HttpSession session){
		long time=new Date().getTime();
		System.out.println("=======================timestamp:"+time);
		session.setAttribute("time", time);
		return "test";
	}
	@RequestMapping("/login")
	public String login(){
		return "login";
	}
	@RequestMapping("/socket")
	public String video(){
		return "socket";
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
		
		BinDevice binDev=new BinDevice();
		binDev.setName("风机");
		binDev.setOnline(0);
		binDev.setCtrlMode(1);
		SinDevice sinDev=new SinDevice();
		sinDev.setName("降雨器");
		sinDev.setOnline(1);
		sinDev.setCtrlMode(0);
		Sensor sensor=new Sensor();
		sensor.setName("温度传感器");
		sensor.setDownValue(0);
		sensor.setUpValue(999);
		sensor.setUnit("℃");
		sensor.setOnline(0);
		sensor.setValue(20.8);
		binDev.setSensors(sensors);
		
		binDevs.add(binDev);
		sinDevs.add(sinDev);
		sensors.add(sensor);
		
		zone.setBinDevices(binDevs);
		zone.setSinDevices(sinDevs);
		zone.setSensors(sensors);
		zones.add(zone);
		model.addAttribute("zones", zones);
		return "main";
	}
}
