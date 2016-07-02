package com.whck.web.controller.base;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.whck.domain.base.Zone;
import com.whck.service.base.ZoneService;

@RestController
@RequestMapping(value="/zone")
public class ZoneController {
	@Autowired
	private ZoneService zoneService;
	@RequestMapping(value="/add",method={RequestMethod.POST})
	@ResponseBody
	public Zone addZone(@RequestBody Zone zone){
		System.out.println("add zone triggered");
		return zoneService.addOrUpdate(zone);
	}
	@RequestMapping(value="/update",method={RequestMethod.POST})
	@ResponseBody
	public Zone updateZone(@RequestBody Zone zone){
		return zoneService.addOrUpdate(zone);
	}
	@RequestMapping(value="/add/{zone_name}",method={RequestMethod.DELETE})
	@ResponseBody
	public Zone deleteZone(@PathVariable("zone_name")String zoneName){
		return zoneService.deleteByName(zoneName);
	}
	@RequestMapping(value="/getAll",method={RequestMethod.GET})
	@ResponseBody
	public List<Zone> getAll(){
		return zoneService.findAll();
	}
}
