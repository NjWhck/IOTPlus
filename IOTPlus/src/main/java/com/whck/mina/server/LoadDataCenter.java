package com.whck.mina.server;

import com.whck.domain.base.BinDevice;
import com.whck.domain.base.Sensor;
import com.whck.domain.base.SinDevice;
import com.whck.domain.base.Zone;
import com.whck.service.base.BinDeviceService;
import com.whck.service.base.SensorService;
import com.whck.service.base.SinDeviceService;
import com.whck.service.base.ZoneService;
import com.whck.util.XMLUtil;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class LoadDataCenter implements CommandLineRunner{

	@Value("${xmlamount}")
	private String xmlAmount;
	
	@Autowired
	private ZoneService zs;
	@Autowired
	private SinDeviceService sds;
	@Autowired
	private BinDeviceService bds;
	@Autowired
	private SensorService ss;
	@Override
	public void run(String... args) throws Exception {
		//TODO:Exception Handling
		int xmlMnt=Integer.valueOf(xmlAmount);
		for(int i=1;i<xmlMnt+1;i++){
			String fileName=i+".xml";
			XMLUtil.initDocument(fileName);
			Zone zone=XMLUtil.getZone();
			List<SinDevice> sinDevices=XMLUtil.getSinDevices(zone);
			List<BinDevice> binDevices=XMLUtil.getBinDevices(zone);
			List<Sensor> sensors=XMLUtil.getSensors(zone);
			
			zone.setSinDevices(sinDevices);
			zone.setBinDevices(binDevices);
			zone.setSensors(sensors);
			zs.addOrUpdate(zone);
			
//			for(Iterator<SinDevice> it=sinDevices.iterator();it.hasNext();){
//					sds.addOrUpdate(it.next());
//			}
//			for(Iterator<BinDevice> it=binDevices.iterator();it.hasNext();){
//					bds.addOrUpdate(it.next());
//			}
//			for(Iterator<Sensor> it=sensors.iterator();it.hasNext();){
//					ss.addOrUpdate(it.next());
//			}
		}
	}
}
