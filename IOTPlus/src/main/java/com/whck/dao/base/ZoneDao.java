package com.whck.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whck.domain.base.Zone;
public interface ZoneDao extends JpaRepository<Zone, String>{
	Zone findByIp(String ip);
	Zone findByName(String name);
	Zone deleteByName(String name);
}
