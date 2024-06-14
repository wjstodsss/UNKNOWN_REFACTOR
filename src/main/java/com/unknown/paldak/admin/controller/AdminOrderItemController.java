package com.unknown.paldak.admin.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.domain.OrderItemVO;
import com.unknown.paldak.admin.service.BaseServiceDefault;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin/orderItem/*")
@RequiredArgsConstructor
public class AdminOrderItemController {

	private final BaseServiceDefault<OrderItemVO> orderItemService;

	
	@GetMapping("/list")
	public String list(Criteria cri, Model model) {
		List<OrderItemVO> list = orderItemService.getList(cri);
		model.addAttribute("orderItems", list);
		
        model.addAttribute("pageMaker", orderItemService.getPageMaker(cri));
        return "admin/orderItem";
	}
	
}