package com.unknown.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		System.out.println("LoginInterceptor preHandle �۵�");

		HttpSession session = request.getSession(false); // ���� ������ ��������, ������ null ��ȯ
		if (session != null) {
			session.invalidate(); // ���� ������ ������ ��ȿȭ
		}

		return true;
	}

}
