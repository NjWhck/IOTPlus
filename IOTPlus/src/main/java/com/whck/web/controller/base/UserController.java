package com.whck.web.controller.base;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.whck.domain.base.User;
import com.whck.domain.base.Zone;
import com.whck.service.base.UserService;
import com.whck.service.base.ZoneService;

@Controller
@RequestMapping(value="/user")
public class UserController {
	@Autowired
	private UserService us;
	@Autowired
	private ZoneService zs;
	@RequestMapping("/login")
	public String login(){
//		User user=new User();
//		user.setName("JSexy");
//		user.setPassword("123456");
//		us.add(user);
		return "login";
	}
	@RequestMapping("/checkuser")
	@ResponseBody
	public String check(User user){
		try{
			User usr=us.getUser(user);
			if(usr==null){
				return "invalid";
			}
			return "valid";
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}
	@RequestMapping("/main")
	public String index(Model model){
		List<Zone> zones=zs.findAll();
		model.addAttribute("zones", zones);
		return "main";
	}
}
