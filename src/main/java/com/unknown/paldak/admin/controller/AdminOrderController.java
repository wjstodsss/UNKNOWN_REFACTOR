package com.unknown.paldak.admin.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.domain.OrderVO;
import com.unknown.paldak.admin.service.AdminOrderServiceImpl;
import com.unknown.paldak.admin.service.BaseServiceDefault;
import com.unknown.paldak.admin.util.AdminCommonAttributes;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin/order/*")
@RequiredArgsConstructor
public class AdminOrderController {

	private final BaseServiceDefault<OrderVO> orderService;
	private final AdminOrderServiceImpl orderServiceUtil;
	private final AdminCommonAttributes addAttribute;

	
	
	@GetMapping("/list")
	public String list(Criteria cri, Model model) {
		List<OrderVO> list = orderService.getList(cri);
		model.addAttribute("orders", list);
		
		model.addAttribute("pageMaker", orderService.getPageMaker(cri));
        return "admin/order";
	}

	
	@PostMapping("/register")
	public String register(
		OrderVO orderVO, 
		@ModelAttribute("cri") Criteria cri,
		RedirectAttributes rttr) {
	  
	    orderService.register(orderVO);
	    
	    addAttribute.addCommonAttributes(rttr, cri);
	    return "redirect:list";
	}

	@GetMapping(value = "/get/{orderId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<OrderVO> get(@PathVariable("orderId") String orderId) {
		OrderVO orderVO = orderServiceUtil.getByStringId(orderId);
		return new ResponseEntity<>(orderVO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/orderState/{orderId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<OrderVO> modifyOrderState(@PathVariable("orderId") String orderId) {
	    OrderVO orderVO = new OrderVO();
	    orderVO.setOrderId(orderId);
	    orderVO.setOrderState("주문 취소");
	    
	    boolean result = orderServiceUtil.modifyOrderState(orderVO);
	    if (result) {
	        return new ResponseEntity<>(orderVO, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	@PostMapping("/modify")
	public String modify(
		OrderVO orderVO, 
		@ModelAttribute("cri") Criteria cri, 
		RedirectAttributes rttr) {
	    
	    boolean result = orderService.modify(orderVO);
		if (!result) {
			rttr.addFlashAttribute("error", "주문 정보 수정에 실패했습니다.");
	    }
		
	    addAttribute.addCommonAttributes(rttr, cri);
	    return "redirect:list";
	}
	
	@PostMapping("/remove")
	public String remove(
		@RequestParam("orderId") String orderId, 
		@ModelAttribute("cri") Criteria cri,
		RedirectAttributes rttr) {
		
		boolean result = orderServiceUtil.removeByStringId(orderId);
		if (!result) {
			rttr.addFlashAttribute("error", "주문 정보 삭제에 실패했습니다.");
	    }
		addAttribute.addCommonAttributes(rttr, cri);
		return "redirect:list";
	}
	
}