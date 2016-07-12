package com.whck.util;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	public  static Map<String,String> variables=new HashMap<>();
	static{
		variables.put("风速", "0~70");
		variables.put("风向", "0~360");
		variables.put("叶面温度", "-10~50");
		variables.put("叶面湿度", "0~100");
		variables.put("温度", "-50~100");
		variables.put("湿度", "0~100");
		variables.put("数字压力", "10~1100");
		variables.put("压强", "0~1600000");
		variables.put("开度", "0~100");
		variables.put("压力", "0~999999");
		variables.put("总辐射", "0~2000");
		variables.put("紫外辐射", "0~200");
		variables.put("蒸发", "0~10");
		variables.put("照度", "0~200000");
		variables.put("雨量", "0~1000");
		
		variables.put("日照时数", "0~24");
		variables.put("光合有效辐射", "400~700");
		variables.put("PM2.5粉尘", "0~40");
		variables.put("PM10颗粒", "0~40");
		variables.put("大气负离子", "10~100000000");
		variables.put("PH值", "-10~50");
		variables.put("位移", "0~100");
		variables.put("盐导", "0~1500");
		variables.put("一氧化碳", "0~1000");
		variables.put("二氧化碳", "0~5000");
		variables.put("二氧化氮", "0~100");
		variables.put("硫化氢", "0~100");
		
		variables.put("氨气", "0~100");
		variables.put("氮气", "0~100");
		variables.put("甲烷", "0~100");
		variables.put("臭氧", "0~100");
		variables.put("甲醛", "0~5");
		variables.put("水分电导率", "0~100");
		variables.put("溶解氧", "0~20");
		variables.put("浊度", "0~1000");
		variables.put("绿叶素A", "0~400");
		variables.put("蓝绿藻", "0~100");
		variables.put("亚硝酸盐", "0~200");
		variables.put("氨氮", "0~60");
		variables.put("余氯", "0~10");
		variables.put("明渠流量", "0~100000");
		variables.put("流量", "0~800");
		variables.put("水位", "0~40");
		variables.put("位移", "0~100");
		
	}
}
