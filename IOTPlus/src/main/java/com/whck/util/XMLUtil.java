package com.whck.util;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.ResourceUtils;
import com.whck.domain.base.BinDevice;
import com.whck.domain.base.Sensor;
import com.whck.domain.base.SinDevice;
import com.whck.domain.base.Zone;
public class XMLUtil {
	private static Document doc;
	
	public static void initDocument(String fileName) throws DocumentException{
		String path ="classpath:"+fileName;
		SAXReader saxReader = new SAXReader();
		try {
			doc = saxReader.read(ResourceUtils.getFile(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Zone getZone(){
		Zone zone =new Zone();
		Element root = doc.getRootElement();
		Element zoneElm = (Element)root.selectSingleNode("Project");
		String zoneName=zoneElm.elementText("ID");
		zone.setName(zoneName);
		return zone;
	}
	
	public static List<SinDevice> getSinDevices(Zone zone){
		List<SinDevice> sinDevices=new ArrayList<>();
		Element root = doc.getRootElement();
		Element zoneElm = (Element)root.selectSingleNode("ControlSysData");
		List data=zoneElm.elements("Data");
		for(Iterator it=data.iterator();it.hasNext();){
			Element dataElm=(Element)it.next();
			String mode=dataElm.elementText("Mode").trim();
			if(mode.equals("2"))
				continue;
			String name=dataElm.elementText("Name").trim();
			
			SinDevice sinDevice = new SinDevice();
			sinDevice.setZoneName(zone.getName());
			sinDevice.setName(name);
			sinDevice.setType(Integer.valueOf(mode));
			sinDevices.add(sinDevice);
		}
		return sinDevices;
	}
	public static List<BinDevice> getBinDevices(Zone zone){
		List<BinDevice> binDevices=new ArrayList<>();
		Element root = doc.getRootElement();
		Element zoneElm = (Element)root.selectSingleNode("ControlSysData");
		List data=zoneElm.elements("Data");
		for(Iterator it=data.iterator();it.hasNext();){
			Element dataElm=(Element)it.next();
			String mode=dataElm.elementText("Mode").trim();
			if(mode.equals("1"))
				continue;
			String name=dataElm.elementText("Name").trim();
			
			BinDevice binDevice = new BinDevice();
			binDevice.setZoneName(zone.getName());
			binDevice.setName(name);
			binDevice.setType(Integer.valueOf(mode));
			binDevices.add(binDevice);
		}
		return binDevices;
	}
	public static List<Sensor> getSensors(Zone zone){
		//List<String> variableNames=(List<String>) Constants.variables.values();
		List<Sensor> sensors=new ArrayList<>();
		Element root = doc.getRootElement();
		Element zoneElm = (Element)root.selectSingleNode("SensorSysData");
		List data=zoneElm.elements("Data");
		for(Iterator it=data.iterator();it.hasNext();){
			Element dataElm=(Element)it.next();
			String name=dataElm.elementText("Name").trim();
			
			String unit=dataElm.elementText("Unit").trim();
			
			Sensor sensor = new Sensor();
			sensor.setZoneName(zone.getName());
			sensor.setName(name);
			sensor.setUnit(unit);
			for(Map.Entry<String, String> entry:Constants.variables.entrySet()){
				String key=entry.getKey();
				if(name.contains(key)){
					String range=entry.getValue();
					String[] limits=range.split("~");
					sensor.setDownValue(Double.valueOf(limits[0]));
					sensor.setUpValue(Double.valueOf(limits[1]));
				}
			}
			sensors.add(sensor);
		}
		return sensors;
	}
}
