package com.whck.web.controller.base;

import java.util.Iterator;
import java.util.List;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import com.whck.domain.base.BinDevice;
import com.whck.domain.base.Message;
import com.whck.domain.base.Sensor;
import com.whck.domain.base.SinDevice;
import com.whck.domain.base.Zone;
import com.whck.mina.constants.Constants;
import com.whck.mina.handler.ProtocolHandler;
import com.whck.mina.message.BinDeviceParamsRequestMessage;
import com.whck.mina.message.SinDeviceParamsRequestMessage;
import com.whck.service.base.BinDeviceService;
import com.whck.service.base.SinDeviceService;
import com.whck.service.base.ZoneService;

import net.minidev.json.JSONArray;

@Controller
public class MessageController {
	@Autowired
	private SimpMessagingTemplate template;
	@Autowired
	private ZoneService zs;
	@Autowired
	private SinDeviceService sds;
	@Autowired
	private BinDeviceService bds;
	
	@MessageMapping("/sensormsg")
	public void sensorInit(Message msg){
		String zoneName=msg.getZoneName();
		Zone zone=zs.findByName(zoneName);
		List<Sensor> sensors=zone.getSensors();
		template.convertAndSend("/sensor/update/"+zoneName,JSONArray.toJSONString(sensors));
	}
	
	@MessageMapping("/sindevmsg")
	public void sinDevInit(Message msg){
		String zoneName=msg.getZoneName();
		Zone zone=zs.findByName(zoneName);
		List<SinDevice> sinDevs=zone.getSinDevices();
		template.convertAndSend("/sindevice/update/"+zoneName,JSONArray.toJSONString(sinDevs));
	}
	
	@MessageMapping("/bindevmsg")
	public void binDevInit(Message msg){
		String zoneName=msg.getZoneName();
		Zone zone=zs.findByName(zoneName);
		List<BinDevice> binDevs=zone.getBinDevices();
		template.convertAndSend("/bindevice/update/"+zoneName,JSONArray.toJSONString(binDevs));
	}
	
	@MessageMapping("/bindevparammsg")
	public void binDevParamUpdate(Message msg){
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
							request.setCmd(BinDeviceParamsRequestMessage.COMMAND);
							request.setData(devNameBytes);
							request.setLatitude(new byte[]{0,0,0});
							request.setLongitude(new byte[]{0,0,0});
							request.setDataLen(new byte[]{(byte) (dataLen/256),(byte) (dataLen%256)});
							IoSession session=ProtocolHandler.sessions.get(zoneName);
							if(session!=null&&session.isConnected()){
								session.write(request);
							}
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
							request.setCmd(SinDeviceParamsRequestMessage.COMMAND);
							request.setLatitude(new byte[]{0,0,0});
							request.setLongitude(new byte[]{0,0,0});
							request.setData(devNameBytes);
							request.setDataLen(new byte[]{(byte) (dataLen/256),(byte) (dataLen%256)});
							IoSession session=ProtocolHandler.sessions.get(zoneName);
							if(session!=null&&session.isConnected()){
								session.write(request);
							}
						//	Broadcast.broadcast(request);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
			};
		}.start();
	}
}
