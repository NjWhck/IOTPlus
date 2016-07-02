package com.whck.web.controller.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whck.domain.base.RealData;
import com.whck.service.base.RealDataService;

@RestController
@RequestMapping(value="/realdata")
public class RealDataController {
	
	@Autowired
	private RealDataService realDataService;
	
	@RequestMapping("/latest/{zone_name}/{name}")
	public RealData getLatest(@PathVariable("zone_name")String zoneName,@PathVariable("name")String name){
		return realDataService.getLatest(zoneName, name);
	}
}
