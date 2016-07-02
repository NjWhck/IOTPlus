package com.whck.mina.handler;

import java.io.IOException;
import java.net.InetAddress;
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
import com.whck.domain.base.SinDevice;
import com.whck.domain.base.SinDeviceParams;
import com.whck.mina.message.BinDeviceParamsMessage;
import com.whck.mina.message.DeviceStateMessage;
import com.whck.mina.message.FileRequestMessage;
import com.whck.mina.message.FileSegmentMessage;
import com.whck.mina.message.RealDataMessage;
import com.whck.mina.message.SinDeviceParamsMessage;
import com.whck.service.base.BinDeviceParamsService;
import com.whck.service.base.BinDeviceService;
import com.whck.service.base.RealDataService;
import com.whck.service.base.SinDeviceParamsService;
import com.whck.service.base.SinDeviceService;

@Component
public class ProtocolHandler implements IoHandler{
	private final static Logger log = LoggerFactory.getLogger(ProtocolHandler.class); 
	public static Map<String,IoSession> sessions=new HashMap<>();
	
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
	
	@Override
	public void exceptionCaught(IoSession session, Throwable e) throws Exception {
		InetAddress address=((InetSocketAddress) session.getRemoteAddress()).getAddress();
		String sessionAddress= ((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress();
		e.printStackTrace();
		log.warn("<session exception>:(address"+address+")"+e);
		sessions.remove(sessionAddress);
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		String sessionAddress= ((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress();
		log.info("<session received:>(address"+sessionAddress+")(content:"+message);
		messageHandler(sessionAddress,session,message);
	}
	private void messageHandler(String sessionAddress,IoSession session, Object message) throws IOException {
		
		if(message instanceof RealDataMessage){
			RealDataMessage m=(RealDataMessage)message;
			if(!m.checkCrc()){
				System.out.println("<Error:>CRC unCurrect");
				return;
			}
			RealData rData=(RealData)m.convert();
			System.out.println("<Messsage:>"+rData);
			rData.setDateTime(new Date());
			realDataService.addOne(rData);
		}else if(message instanceof DeviceStateMessage){
			DeviceStateMessage m=(DeviceStateMessage)message;
//			if(!m.checkCrc()){
//				System.out.println("<Error:>CRC unCurrect");
//				return;
//			}
			Object msg=m.convert();
			if(msg instanceof BinDevice){
				BinDevice binDevice=(BinDevice)msg;
				System.out.println("<Message:>"+binDevice);
				binDeviceService.addOrUpdate(binDevice);
			}else if(msg instanceof SinDevice){
				SinDevice sinDevice=(SinDevice)msg;
				System.out.println("<Message:>"+sinDevice);
				sinDeviceService.addOrUpdate(sinDevice);
			}
		}else if(message instanceof SinDeviceParamsMessage){
			SinDeviceParamsMessage m=(SinDeviceParamsMessage)message;
			if(!m.checkCrc()){
				System.out.println("<Error:>CRC unCurrect");
				return;
			}
			SinDeviceParams sdp=m.convert();
			System.out.println("<Message:>"+sdp);
			sinDeviceParamsService.addOrUpdate(sdp);
		}else if(message instanceof BinDeviceParamsMessage){
			BinDeviceParamsMessage m=(BinDeviceParamsMessage)message;
			if(!m.checkCrc()){
				System.out.println("<Error:>CRC unCurrect");
				return;
			}
			BinDeviceParams bdp=m.convert();
			System.out.println("<Message:>"+bdp);
			binDeviceParamsService.addOrUpdate(bdp);
		}else if(message instanceof FileRequestMessage){
			FileRequestMessage m=(FileRequestMessage)message;
			if(!m.checkCrc()){
				System.out.println("<Error:>CRC unCurrect");
				return;
			}
//			session.write(m.convert());
		}else if(message instanceof FileSegmentMessage){
			FileSegmentMessage m=(FileSegmentMessage)message;
			if(!m.checkCrc()){
				System.out.println("<Error:>CRC unCurrect");
				return;
			}
			session.write(m.convert());
		}else{
			log.warn("<Message Type unFound>");
		}
	}

	@Override
	public void messageSent(IoSession session, Object arg1) throws Exception {
		String sessionAddress= ((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress();		
		log.info("<message has sent to>:"+sessionAddress);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		String sessionAddress= ((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress();
		sessions.remove(sessionAddress);
		log.info("<session closed>:"+sessionAddress);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		String sessionAddress= ((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress();		
		sessions.put(sessionAddress, session);
		log.info("<session created>:"+sessionAddress);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus arg1) throws Exception {
		String sessionAddress= ((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress();
		log.info("<session idle>:"+sessionAddress);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		String sessionAddress= ((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress();
		log.info("<session opened>:"+sessionAddress);		
	}
}
