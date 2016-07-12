package com.whck.serviceImpl.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whck.dao.base.UserDao;
import com.whck.domain.base.User;
import com.whck.service.base.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	@Override
	public User getByNameAndPassword(String name, String password) {
		return userDao.findByNameAndPassword(name, password);
	}

	@Override
	public User getByName(String name) {
		return userDao.findByName(name);
	}

	@Override
	public User getUser(User user) {
		// TODO Auto-generated method stub
		return userDao.findByNameAndPassword(user.getName(), user.getPassword());
	}

	@Override
	public User add(User user) {
		return userDao.save(user);
	}

}
