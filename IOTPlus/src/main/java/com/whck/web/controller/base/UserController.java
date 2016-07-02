package com.whck.web.controller.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.whck.domain.base.User;
import com.whck.service.base.UserService;


@RestController
@RequestMapping(value="/")
public class UserController {
	@Autowired
	private UserService us;
	
//	@RequestMapping("/login")
	@ResponseBody
	public User login(@RequestBody User user){   
		return us.getByNameAndPassword(user.getName(), user.getPassword());
	}
}
