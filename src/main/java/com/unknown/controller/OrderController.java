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
    public String orderPageGET(@PathVariable("memberId") String memberId, OrderPageDTO opd, Model model) {
        model.addAttribute("orderList", orderService.getGoodsInfo(opd.getOrders()));
        model.addAttribute("memberInfo", memberService.getMemberInfo(memberId));
        return "/order";
    }

    @PostMapping("/order")
    public String orderPagePost(OrderDTO od, HttpServletRequest request) {
        System.out.println(od);

        // 기본값 설정
        if (od.getReceiver() == null || od.getReceiver().isEmpty()) {
            od.setReceiver(od.getMemberId());
        }

        if (od.getMemberAddr1() == null || od.getMemberAddr1().isEmpty()) {
            System.out.println("MemberAddr1 is missing!");
            // 오류 페이지로 리다이렉트
            return "redirect:/order/error";
        }

        orderService.order(od);

        try {
            MemberVO member = memberService.getMemberInfo(od.getMemberId());

            // 포인트 차감 로직
            if (od.getUsePoint() > 0) {
                int currentPoints = member.getPoint();
                member.setPoint(currentPoints - od.getUsePoint());
                System.out.println("포인트 차감: " + currentPoints + " - " + od.getUsePoint());
                memberService.memberUpdate(member);
            }

            // 포인트 적립 로직 추가
            int earnedPoints = od.calculateEarnedPoints();
            int updatedPoints = member.getPoint() + earnedPoints;
            member.setPoint(updatedPoints);
            System.out.println("포인트 적립: " + member.getPoint() + " + " + earnedPoints);
            memberService.memberUpdate(member);

        } catch (Exception e) {
            // 예외 처리 로직 (예: 로그 기록, 사용자에게 오류 메시지 전달 등)
            e.printStackTrace();
            // 예외 발생 시 적절한 에러 페이지로 리다이렉트하거나 메시지 전달
            return "redirect:/errorPage";
        }

        return "redirect:/main";
    }
}