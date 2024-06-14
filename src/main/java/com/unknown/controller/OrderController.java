package com.unknown.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.unknown.model.MemberVO;
import com.unknown.model.OrderDTO;
import com.unknown.model.OrderPageDTO;
import com.unknown.service.MemberService;
import com.unknown.service.OrderService;

@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/order/{memberId}")
	public String orderPgaeGET(@PathVariable("memberId") String memberId, OrderPageDTO opd, Model model) {
		model.addAttribute("orderList", orderService.getGoodsInfo(opd.getOrders()));
		model.addAttribute("memberInfo", memberService.getMemberInfo(memberId));
		
		return "/order";
	}
	
	@PostMapping("/order")
	public String orderPagePost(OrderDTO od, HttpServletRequest request) {
	    System.out.println(od);

	    if (od.getReceiver() == null || od.getReceiver().isEmpty()) {
	        od.setReceiver(od.getMemberId()); // �⺻�� ����
	    }

	    orderService.order(od);

	    try {
	        MemberVO member = memberService.getMemberInfo(od.getMemberId());

	        // ����Ʈ ���� ����
	        if (od.getUsePoint() > 0) {
	            int currentPoints = member.getPoint();
	            member.setPoint(currentPoints - od.getUsePoint());
	            memberService.memberUpdate(member);
	        }

	        // ����Ʈ ���� ���� �߰�
	        int earnedPoints = od.calculateEarnedPoints();
	        int updatedPoints = member.getPoint() + earnedPoints;
	        member.setPoint(updatedPoints);
	        memberService.memberUpdate(member);

	    } catch (Exception e) {
	        // ���� ó�� ���� (��: �α� ���, ����ڿ��� ���� �޽��� ���� ��)
	        e.printStackTrace();
	        // ���� �߻� �� ������ ���� �������� �����̷�Ʈ�ϰų� �޽��� ����
	        return "redirect:/errorPage";
	    }

	    return "redirect:/main";
	}


}
