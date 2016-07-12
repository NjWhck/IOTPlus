package com.whck.service.base;

import java.util.List;
import org.springframework.stereotype.Service;

import com.whck.domain.base.Zone;

@Service
public interface ZoneService {
	Zone findByName(String name);
	Zone deleteByName(String name);
	Zone addOrUpdate(Zone zone);
	List<Zone> findAll();
}
	