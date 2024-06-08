package com.unknown.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		System.out.println("LoginInterceptor preHandle 작동");

		HttpSession session = request.getSession(false); // 기존 세션을 가져오고, 없으면 null 반환
		if (session != null) {
			session.invalidate(); // 기존 세션이 있으면 무효화
		}

		return true;
	}

}
