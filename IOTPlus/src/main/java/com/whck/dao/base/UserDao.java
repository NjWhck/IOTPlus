package com.whck.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whck.domain.base.User;

public interface UserDao extends JpaRepository<User,Integer>{
	User findByNameAndPassword(String name,String password);
	User findByName(String name);
}
