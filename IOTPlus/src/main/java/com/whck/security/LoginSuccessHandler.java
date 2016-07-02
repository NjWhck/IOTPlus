package com.whck.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

//public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
//	@Override
//	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//			Authentication authentication) throws ServletException, IOException {
//		User userDetails=(User)authentication.getPrincipal();
//		System.out.println(userDetails.getName()+"Checked in!");
//		super.onAuthenticationSuccess(request, response, authentication);
//	}
//}
