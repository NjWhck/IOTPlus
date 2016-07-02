package com.whck.serviceImpl.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whck.dao.base.ZoneDao;
import com.whck.domain.base.Zone;
import com.whck.service.base.ZoneService;

@Service
public class ZoneServiceImpl implements ZoneService{

	@Autowired
	private ZoneDao zoneDao;
	@Override
	public Zone findByIp(String ip) {
		return zoneDao.findByIp(ip);
	}
	@Override
	public Zone findByName(String name) {
		// TODO Auto-generated method stub
		return zoneDao.findByName(name);
	}
	@Override
	public Zone deleteByName(String name) {
		// TODO Auto-generated method stub
		return zoneDao.deleteByName(name);
	}
	@Override
	public Zone addOrUpdate(Zone zone) {
		Zone z=zoneDao.findByName(zone.getName());
		if(z!=null){
			z.setAlias(zone.getAlias());
			z.setAdministrator(zone.getAdministrator());
			z.setArea(zone.getArea());
			z.setIp(zone.getIp());
			z.setLongitude(zone.getLongitude());
			z.setLatitude(zone.getLatitude());
			z.setName(zone.getName());
			z.setRemark(zone.getRemark());
			return zoneDao.save(z);
		}
		return zoneDao.save(zone);
	}
	@Override
	public List<Zone> findAll() {
		return zoneDao.findAll();
	}

}
