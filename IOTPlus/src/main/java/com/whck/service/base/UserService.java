package com.whck.service.base;

import org.springframework.stereotype.Service;

import com.whck.domain.base.User;

@Service
public interface UserService {
	User getByNameAndPassword(String name,String password);
	User getByName(String name);
}
