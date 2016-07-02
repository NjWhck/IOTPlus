package com.whck.serviceImpl.base;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whck.dao.base.RealDataDao;
import com.whck.domain.base.RealData;
import com.whck.service.base.RealDataService;
import com.whck.service.base.TimeSpan;

@Service
public class RealDataServiceImpl implements RealDataService{
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private RealDataDao realDataDao;
	@Override
	public RealData addOne(RealData realData) {
		return realDataDao.save(realData);
	}
	@Override
	public List<RealData> getByName(String zoneName, String name) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public RealData getLatest(String zoneName, String name) {
		return realDataDao.findTopByZoneNameAndNameOrderByDateTimeDesc(zoneName,name);
	}
	@Override
	public List<RealData> getAvg(String zoneName, String name, Date from,Date to,TimeSpan timeSpan, Integer order, Integer size) {
		String sql="";
		switch(timeSpan){
		case YEAR:
			sql="select year(date_time) year, AVG(value) val"
					+ " from tb_real_data " 
					+ "	where zone_name = '"+zoneName+"' and name ='"+name+"' "
					+ " and date_time between "+from+" and "+to
					+ " group by year(date_time) "
					+ " limit "+(order-1)*size+","+size;     //'limit': mysql peculiar
			break;
		case MONTH:
			sql="select year(date_time) year, month(date_time) month,AVG(value) val"
					+ " from tb_real_data " 
					+ "	where zone_name = '"+zoneName+"' and name ='"+name+"' "
					+ " and date_time between "+from+" and "+to
					+ " group by year(date_time),month(date_time)"
					+ " limit "+(order-1)*size+","+size;
			break;
		case DAY:
			sql="select year(date_time) year, month(date_time) month,day(date_time) day,AVG(value) val"
					+ " from tb_real_data " 
					+ "	where zone_name = '"+zoneName+"' and name ='"+name+"' "
					+ " and date_time between "+from+" and "+to
					+ " group by year(date_time),month(date_time),day(date_time) "
					+ " limit "+(order-1)*size+","+size;
			break;
		case HOUR:
			sql="select year(date_time) year, month(date_time) month,day(date_time) day,DATEPART(hour, date_time) hour,AVG(value) val"
					+ " from tb_real_data " 
					+ "	where zone_name = '"+zoneName+"' and name ='"+name+"' "
					+ " and date_time between "+from+" and "+to
					+ " group by year(date_time),month(date_time),day(date_time),DATEPART(hour, date_time) "
					+ " limit "+(order-1)*size+","+size;
			break;
		case SECOND:
			sql="select year(date_time) year, month(date_time) month,day(date_time) day,DATEPART(hour, date_time) hour,"
					+ "	DATEPART(minute, date_time) minute,DATEPART(second, date_time) second,AVG(value) val"
					+ " from tb_real_data " 
					+ "	where zone_name = '"+zoneName+"' and name ='"+name+"' "
					+ " and date_time between "+from+" and "+to
					+ " group by year(date_time),month(date_time),day(date_time),DATEPART(hour, date_time),DATEPART(minute, date_time),DATEPART(second, date_time) "
					+ " limit "+(order-1)*size+","+size;
			break;
		default:
			sql="select year(date_time) year, month(date_time) month,day(date_time) day,DATEPART(hour, date_time) hour,DATEPART(minute, date_time) minute,AVG(value) val"
					+ " from tb_real_data " 
					+ "	where zone_name = '"+zoneName+"' and name ='"+name+"' "
					+ " and date_time between "+from+" and "+to
					+ " group by year(date_time),month(date_time),day(date_time),DATEPART(hour, date_time),DATEPART(minute, date_time) "
					+ " limit "+(order-1)*size+","+size;
			break;
		}
		Query query=entityManager.createNativeQuery(sql);
		List objecArraytList = query.getResultList();
		for(int i=0;i<objecArraytList.size();i++) {         
            Object[] obj = (Object[]) objecArraytList.get(i);   
             //使用obj[0],obj[1],obj[2]取出属性   
        } 
		return null;
	}

}
