package com.unknown.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.unknown.model.MemberVO;

public class AdminInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession();

		MemberVO lvo = (MemberVO) session.getAttribute("member");

		if (lvo == null || lvo.getAdminCk() == 0) { // ������ ���� �ƴ� ���

			response.sendRedirect("/main"); // ������������ �����̷�Ʈ

			return false;

		}

		return true; // ������ ���� �α��� ���(lvo != null && lvo.getAdminCk() == 1)
	}

}
