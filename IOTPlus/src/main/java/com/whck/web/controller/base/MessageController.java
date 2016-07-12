package com.whck.web.controller.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import com.whck.domain.base.BinDevice;
import com.whck.domain.base.Message;
import com.whck.domain.base.Sensor;
import com.whck.domain.base.SinDevice;
import com.whck.mina.constants.Constants;
import com.whck.mina.message.BinDeviceParamsRequestMessage;
import com.whck.mina.message.SinDeviceParamsRequestMessage;
import com.whck.mina.server.Broadcast;
import com.whck.service.base.BinDeviceService;
import com.whck.service.base.SinDeviceService;
import net.minidev.json.JSONArray;

@Controller
public class MessageController {
	@Autowired
	private SimpMessagingTemplate template;
	@Autowired
	private SinDeviceService sds;
	@Autowired
	private BinDeviceService bds;
	
	@MessageMapping("/sensormsg")
	public void sensorInit(Message msg){
		String zoneName=msg.getZoneName();
		List<Sensor> sensors=new ArrayList<>();
		Sensor sensor1=new Sensor();
		sensor1.setZoneName(zoneName);
		sensor1.setName("温度传感器");
		sensor1.setOnline(0);
		sensor1.setValue(20);
		Sensor sensor2=new Sensor();
		sensor2.setZoneName(zoneName);
		sensor2.setName("湿度传感器");
		sensor2.setOnline(0);
		sensor2.setValue(17);
		
		while(true){
//			List<Sensor> sensors=ss.getAllByZoneName(zoneName);
			sensors.clear();
			sensor1.setValue(900);
			sensor2.setValue(90);
			sensors.add(sensor1);
			sensors.add(sensor2);
			template.convertAndSend("/sensor/update/"+zoneName,JSONArray.toJSONString(sensors));
			sensors.clear();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sensor1.setValue(90);
			sensor2.setValue(60);
			sensors.add(sensor1);
			sensors.add(sensor2);
			
			template.convertAndSend("/sensor/update/"+zoneName,JSONArray.toJSONString(sensors));
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
		
		List<SinDevice> sinDevs=new ArrayList<>();
		
		SinDevice sinDevice1=new SinDevice();
		sinDevice1.setZoneName(zoneName);
		sinDevice1.setName("降雨器");
		sinDevice1.setOnline(0);
		sinDevice1.setCtrlMode(1);
		
		SinDevice sinDevice2=new SinDevice();
		sinDevice2.setZoneName(zoneName);
		sinDevice2.setName("生长灯");
		sinDevice2.setOnline(0);
		sinDevice2.setCtrlMode(0);
		
		while(true){
		//	List<SinDevice> sinDevs=sds.getAllByZoneName(zoneName);
			sinDevs.clear();
			sinDevs.add(sinDevice1);
			sinDevs.add(sinDevice2);
			template.convertAndSend("/sindevice/update/"+zoneName,JSONArray.toJSONString(sinDevs));
			sinDevs.clear();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			sinDevs.add(sinDevice1);
			sinDevs.add(sinDevice2);
			template.convertAndSend("/sindevice/update/"+zoneName,JSONArray.toJSONString(sinDevs));
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
		List<BinDevice> binDevs=new ArrayList<>();
		BinDevice binDevice1=new BinDevice();
		binDevice1.setZoneName(zoneName);
		binDevice1.setName("风机");
		binDevice1.setOnline(0);
		binDevice1.setCtrlMode(1);
		BinDevice binDevice2=new BinDevice();
		binDevice2.setZoneName(zoneName);
		binDevice2.setName("窗帘");
		binDevice2.setOnline(0);
		binDevice2.setCtrlMode(0);
		while(true){
	//		List<BinDevice> binDevs=bds.getAllByZoneName(zoneName);
			binDevs.clear();
			binDevs.add(binDevice1);
			binDevs.add(binDevice2);
			template.convertAndSend("/bindevice/update/"+zoneName,JSONArray.toJSONString(binDevs));
			binDevs.clear();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			binDevs.add(binDevice1);
			binDevs.add(binDevice2);
			template.convertAndSend("/bindevice/update/"+zoneName,JSONArray.toJSONString(binDevs));
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@MessageMapping("/bindevparammsg")
	public void binDevParamUpdate(Message msg){
		//TODO:broadcast
		String zoneName=msg.getZoneName();
		List<BinDevice> binDevices=bds.getAllByZoneName(zoneName);
		
		new Thread(){
			public void run() {
					for(Iterator<BinDevice> it=binDevices.iterator();it.hasNext();){
						BinDevice binDev=it.next();
						String DevName=binDev.getName();
						try {
							byte[] zoneNameBytes=zoneName.getBytes("GBK");
							byte[] devNameBytes=DevName.getBytes("GBK");
							BinDeviceParamsRequestMessage request=new BinDeviceParamsRequestMessage();
							int dataLen=devNameBytes.length+Constants.CRC_LEN+Constants.ENDER_LEN;
							request.setId(zoneNameBytes);
							request.setData(devNameBytes);
							request.setDataLen(new byte[]{(byte) (dataLen/256),(byte) (dataLen%256)});
							Broadcast.broadcast(request);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
			};
		}.start();
	}
	
	@MessageMapping("/sindevparammsg")
	public void sinDevParamUpdate(Message msg){
		String zoneName=msg.getZoneName();
		List<SinDevice> sinDevices=sds.getAllByZoneName(zoneName);
		new Thread(){
			public void run() {
					for(Iterator<SinDevice> it=sinDevices.iterator();it.hasNext();){
						SinDevice sinDev=it.next();
						String DevName=sinDev.getName();
						try {
							byte[] zoneNameBytes=zoneName.getBytes("GBK");
							byte[] devNameBytes=DevName.getBytes("GBK");
							SinDeviceParamsRequestMessage request=new SinDeviceParamsRequestMessage();
							int dataLen=devNameBytes.length+Constants.CRC_LEN+Constants.ENDER_LEN;
							request.setId(zoneNameBytes);
							request.setData(devNameBytes);
							request.setDataLen(new byte[]{(byte) (dataLen/256),(byte) (dataLen%256)});
							Broadcast.broadcast(request);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
			};
		}.start();
	}
}
