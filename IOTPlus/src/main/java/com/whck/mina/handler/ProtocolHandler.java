package com.whck.mina.handler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.whck.domain.base.BinDevice;
import com.whck.domain.base.BinDeviceParams;
import com.whck.domain.base.RealData;
import com.whck.domain.base.Sensor;
import com.whck.domain.base.SinDevice;
import com.whck.domain.base.SinDeviceParams;
import com.whck.domain.base.Zone;
import com.whck.mina.message.BinDeviceParamsMessage;
import com.whck.mina.message.DeviceStateMessage;
import com.whck.mina.message.FileRequestMessage;
import com.whck.mina.message.FileSegmentMessage;
import com.whck.mina.message.RealDataMessage;
import com.whck.mina.message.SinDeviceParamsMessage;
import com.whck.service.base.BinDeviceParamsService;
import com.whck.service.base.BinDeviceService;
import com.whck.service.base.RealDataService;
import com.whck.service.base.SensorService;
import com.whck.service.base.SinDeviceParamsService;
import com.whck.service.base.SinDeviceService;
import com.whck.service.base.ZoneService;

@Component
public class ProtocolHandler implements IoHandler{
	private final static Logger log = LoggerFactory.getLogger(ProtocolHandler.class); 
	public static Map<String,IoSession> sessions=new HashMap<>();
	
	@Autowired
	private ZoneService zoneService;
	@Autowired
	private SinDeviceService sinDeviceService;
	@Autowired
	private SinDeviceParamsService sinDeviceParamsService;
	@Autowired
	private BinDeviceService binDeviceService;
	@Autowired
	private BinDeviceParamsService binDeviceParamsService;
	@Autowired
	private RealDataService realDataService;
	@Autowired
	private SensorService ss;
	
	@Override
	public void exceptionCaught(IoSession session, Throwable e) throws Exception {
		String InetSocketAddress= ((InetSocketAddress) session.getRemoteAddress()).toString().substring(1);
		Zone zone=zoneService.findByIp(InetSocketAddress);
		if((zone!=null)&&sessions.containsKey(InetSocketAddress)){
			sessions.remove(zone.getName());
		}
		e.printStackTrace();
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		String InetSocketAddress= ((InetSocketAddress) session.getRemoteAddress()).toString().substring(1);
		messageHandler(InetSocketAddress,session,message);
	}
	private void messageHandler(String InetSocketAddress,IoSession session, Object message) throws IOException {
		String zoneName=null;
		if(message instanceof RealDataMessage){
			RealDataMessage m=(RealDataMessage)message;
			if(!m.checkCrc()){
				System.out.println("<Error:>RealDataMessage CRC unCurrect");
				return;
			}
			RealData rData=(RealData)m.convert();
//			System.out.println("<Messsage:>"+rData);
			rData.setDateTime(new Date());
			realDataService.addOne(rData);
			String zName=rData.getZoneName();
			String name=rData.getName();
			Sensor sensor=ss.getSensor(zName,name);
			sensor.setOnline(1);
			sensor.setValue(rData.getValue());
			ss.addOrUpdate(sensor);
			zoneName=zName;
		}else if(message instanceof DeviceStateMessage){
			DeviceStateMessage m=(DeviceStateMessage)message;
			if(!m.checkCrc()){
				System.out.println("<Error:>DeviceStateMessage CRC unCurrect");
				return;
			}
			Object msg=m.convert();
			if(msg instanceof BinDevice){
				BinDevice binDevice=(BinDevice)msg;
//				System.out.println("<Message:>"+binDevice);
				binDeviceService.addOrUpdate(binDevice);
				zoneName=binDevice.getZoneName();
			}else if(msg instanceof SinDevice){
				SinDevice sinDevice=(SinDevice)msg;
//				System.out.println("<Message:>"+sinDevice);
				sinDeviceService.addOrUpdate(sinDevice);
				zoneName=sinDevice.getZoneName();
			} 
		}else if(message instanceof SinDeviceParamsMessage){
			SinDeviceParamsMessage m=(SinDeviceParamsMessage)message;
			if(!m.checkCrc()){
				System.out.println("<Error:>SinDeviceParamsMessage CRC unCurrect");
				return;
			}
			SinDeviceParams sdp=m.convert();
//			System.out.println("<Message:>"+sdp);
			sinDeviceParamsService.addOrUpdate(sdp);
			zoneName=sdp.getZoneName();
		}else if(message instanceof BinDeviceParamsMessage){
			BinDeviceParamsMessage m=(BinDeviceParamsMessage)message;
			if(!m.checkCrc()){
				System.out.println("<Error:>BinDeviceParamsMessage CRC unCurrect");
				return;
			}
			BinDeviceParams bdp=m.convert();
//			System.out.println("<Message:>"+bdp);
			binDeviceParamsService.addOrUpdate(bdp);
			zoneName=bdp.getZoneName();
		}else if(message instanceof FileRequestMessage){
			FileRequestMessage m=(FileRequestMessage)message;
			if(!m.checkCrc()){
				System.out.println("<Error:>FileRequestMessage CRC unCurrect");
				return;
			}
//			session.write(m.convert());
		}else if(message instanceof FileSegmentMessage){
			FileSegmentMessage m=(FileSegmentMessage)message;
			if(!m.checkCrc()){
				System.out.println("<Error:>FileSegmentMessage CRC unCurrect");
				return;
			}
			session.write(m.convert());
		}else{
			log.warn("<Message Type unFound>");
		}
		if((zoneName!=null)&&(!sessions.containsKey(zoneName))){
			sessions.put(zoneName, session);
			Zone zone=zoneService.findByName(zoneName);
			if(zone!=null){
				zone.setIp(InetSocketAddress);
				zoneService.addOrUpdate(zone);
			}
		}
	}

	@Override
	public void messageSent(IoSession session, Object arg1) throws Exception {
		String InetSocketAddress= ((InetSocketAddress) session.getRemoteAddress()).toString().substring(1);		
		log.info("<message has sent to>:"+InetSocketAddress);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		String InetSocketAddress= ((InetSocketAddress) session.getRemoteAddress()).toString().substring(1);
		log.info("<session closed>:"+InetSocketAddress);
		Zone zone=zoneService.findByIp(InetSocketAddress);
		sessions.remove(zone.getName());
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		String InetSocketAddress= ((InetSocketAddress) session.getRemoteAddress()).toString().substring(1);		
		log.info("<session created>:"+InetSocketAddress);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus arg1) throws Exception {
		String InetSocketAddress= ((InetSocketAddress) session.getRemoteAddress()).toString().substring(1);
		log.info("<session idle>:"+InetSocketAddress);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		String InetSocketAddress= ((InetSocketAddress) session.getRemoteAddress()).toString().substring(1);
		log.info("<session opened>:"+InetSocketAddress);		
	}
}
