package com.whck.web.controller.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import com.whck.domain.base.BinDevice;
import com.whck.domain.base.Message;
import com.whck.domain.base.Sensor;
import com.whck.domain.base.SinDevice;
import com.whck.service.base.BinDeviceService;
import com.whck.service.base.SensorService;
import com.whck.service.base.SinDeviceService;

@Controller
public class MessageController {
	@Autowired
	private SimpMessagingTemplate template;
	@Autowired
	private SensorService ss;
	@Autowired
	private SinDeviceService sds;
	@Autowired
	private BinDeviceService bds;
	
	@MessageMapping("/sensormsg")
	public void sensorInit(Message msg){
		String zoneName=msg.getZoneName();
		while(true){
			List<Sensor> sensors=ss.getAllByZoneName(zoneName);
			template.convertAndSend("/sensor/update/"+zoneName,sensors);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@MessageMapping("/sindevmsg")
	public void sinDevInit(Message msg){
		String zoneName=msg.getZoneName();
		while(true){
			List<SinDevice> sinDevs=sds.getAllByZoneName(zoneName);
			template.convertAndSend("/sindevice/update/"+zoneName,sinDevs);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@MessageMapping("/bindevmsg")
	public void binDevInit(Message msg){
		String zoneName=msg.getZoneName();
		while(true){
			List<BinDevice> binDevs=bds.getAllByZoneName(zoneName);
			template.convertAndSend("/bindevice/update/"+zoneName,binDevs);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
