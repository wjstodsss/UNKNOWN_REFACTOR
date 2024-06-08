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
import com.unknown.paldak.admin.common.domain.PageDTO;
import com.unknown.paldak.admin.domain.OrderVO;
import com.unknown.paldak.admin.service.BaseService;
import com.unknown.paldak.admin.service.AdminOrderServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin/order/*")
@RequiredArgsConstructor
public class AdminOrderController {

	private final BaseService<OrderVO> orderService;
	private final AdminOrderServiceImpl orderServiceUtil;

	
	
	@GetMapping("/list")
	public String list(Criteria cri, Model model) {
		System.out.println("jlkjlkjl");
		System.out.println(cri.getPageNum());
		
		List<OrderVO> list = orderService.getList(cri);
		list.forEach(orderVO -> System.out.println(orderVO));
		model.addAttribute("orders", list);
		
        int total = orderService.getTotal(cri);
        PageDTO pageDTO = new PageDTO(cri, total);
        System.out.println(pageDTO);
        model.addAttribute("pageMaker", pageDTO);
        return "admin/order";
	}

	
	@GetMapping("/descList")
	public String descList(Criteria cri, Model model) {
		System.out.println("1");
		System.out.println(cri);
		
		List<OrderVO> list = orderService.getDescList(cri);
		list.forEach(orderVO -> System.out.println(orderVO));
		model.addAttribute("orders", list);
		
		//model.addAttribute("pageMaker", new PageDTO(cri, 123)); // ??�??? ??체갯??, 13page
        int total = orderService.getTotal(cri);
        
        model.addAttribute("pageMaker", new PageDTO(cri, total));
        return "admin/order";
	}
	
	@PostMapping("/register")
	public String register(Model model, OrderVO orderVO, RedirectAttributes rttr) {
	   System.out.println("kkkk");
	   System.out.println(orderVO);
	    orderService.register(orderVO);
	    System.out.println("kkkfsdfsdfsfsdfk");
	    rttr.addFlashAttribute("result", orderVO.getOrderId());
	    
	    return "redirect:descList";
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
	    orderVO.setOrderState("주문 취�??");
	    
	    boolean result = orderServiceUtil.modifyOrderState(orderVO);
	    if (result) {
	        return new ResponseEntity<>(orderVO, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	@PostMapping("/modify")
	public String modify(OrderVO orderVO, @ModelAttribute("cri") Criteria cri, @RequestParam("currentPath") String currentPath, RedirectAttributes rttr) {
	    if (orderService.modify(orderVO)) {
	        rttr.addFlashAttribute("result", "success");
	    }
	    rttr.addAttribute("pageNum", cri.getPageNum());
	    rttr.addAttribute("amount", cri.getAmount());
	    return "redirect:" + currentPath;
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("orderId") String orderId, @ModelAttribute("cri") Criteria cri, @RequestParam("currentPath") String currentPath, RedirectAttributes rttr) {
		if (orderServiceUtil.removeByStringId(orderId)) {
			rttr.addFlashAttribute("result", "success");
		}
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		System.out.println("remove..." + orderId);
		return "redirect:" + currentPath;
	}
	
}