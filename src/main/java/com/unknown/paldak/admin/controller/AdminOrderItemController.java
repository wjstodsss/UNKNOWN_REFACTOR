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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.common.domain.PageDTO;
import com.unknown.paldak.admin.domain.OrderItemVO;
import com.unknown.paldak.admin.service.BaseService;

import com.unknown.paldak.admin.util.AdminFileUploadManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("admin/orderItem/*")
@RequiredArgsConstructor
public class AdminOrderItemController {

	private final BaseService<OrderItemVO> orderItemService;

	
	@GetMapping("/list")
	public String list(Criteria cri, Model model) {
		System.out.println("jlkjlkjl");
		System.out.println(cri.getPageNum());
		List<OrderItemVO> list = orderItemService.getList(cri);
		list.forEach(orderItemVO -> System.out.println(orderItemVO + "kkkkkkkkkkkkkkkk"));
		model.addAttribute("orderItems", list);
        int total = orderItemService.getTotal(cri);
        model.addAttribute("pageMaker", new PageDTO(cri, total));
        return "admin/orderItem";
	}

	
	@GetMapping("/descList")
	public String descList(Criteria cri, Model model) {
		System.out.println("1");
		System.out.println(cri);
		List<OrderItemVO> list = orderItemService.getDescList(cri);
		list.forEach(orderItemVO -> System.out.println(orderItemVO));
		model.addAttribute("orderItems", list);
        int total = orderItemService.getTotal(cri);
        model.addAttribute("pageMaker", new PageDTO(cri, total));
        return "admin/orderItem";
	}
	

	
}