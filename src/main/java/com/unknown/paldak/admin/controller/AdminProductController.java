package com.unknown.paldak.admin.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.common.domain.PageDTO;

import com.unknown.paldak.admin.domain.ProductVO;
import com.unknown.paldak.admin.service.BaseService;
import com.unknown.paldak.admin.util.AdminFileUploadManager;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("admin/product/*")
@RequiredArgsConstructor
public class AdminProductController {

	private final BaseService<ProductVO> productService;
	private final AdminFileUploadManager fileUploadManager;
	
	
	
	@GetMapping("/list")
	public String list(Criteria cri, Model model) {
		System.out.println("jlkjlkjl");
		System.out.println(cri);
		
		List<ProductVO> list = productService.getList(cri);
		list.forEach(productVO -> System.out.println(productVO));
		model.addAttribute("products", list);
		
		//model.addAttribute("pageMaker", new PageDTO(cri, 123)); // ??�??? ??체갯??, 13page
        int total = productService.getTotal(cri);
        
        model.addAttribute("pageMaker", new PageDTO(cri, total));
        return "admin/product/list";
	}

	
	@PostMapping("/register")
	public String register(@RequestParam("uploadFile") MultipartFile[] uploadFile, Model model, ProductVO productVO, RedirectAttributes rttr) {
		
		if (!uploadFile[0].isEmpty()) { 
			String imageURL = fileUploadManager.uploadFiles(uploadFile).get("imageURLs");
			productVO.setProductImageURL(imageURL);
		}
	    
	    productService.register(productVO);
	    
	    rttr.addFlashAttribute("result", productVO.getProductId());
	    
	    return "redirect:list";
	}


	@GetMapping("/register")
	public String register() {
		return "admin/product/register";
	}

	@GetMapping({ "/get", "/modify" })
	public String get(HttpServletRequest request, @RequestParam("productId") Long productId, @ModelAttribute("cri") Criteria cri, Model model) {
		model.addAttribute("product", productService.get(productId));
		log.info(productService.get(productId));
		if (request.getRequestURI().endsWith("/get")) {
	        return "admin/product/get";
	    } else {
	        return "admin/product/modify";
	    }
	} 
	
	@PostMapping("/modify")
	public String modify(MultipartFile[] uploadFile, ProductVO productVO, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		if (!uploadFile[0].isEmpty()) { 
			String imageURL = fileUploadManager.uploadFiles(uploadFile).get("imageURLs");
		    productVO.setProductImageURL(imageURL);
		    
	    }
	    
	    if (productService.modify(productVO)) {
	        rttr.addFlashAttribute("result", "success");
	    }

	    rttr.addAttribute("pageNum", cri.getPageNum());
	    rttr.addAttribute("amount", cri.getAmount());

	    return "redirect:list";
	}
	

	@PostMapping("/remove")
	public String remove(@RequestParam("productId") Long productId, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("remove..." + productId);
		if (productService.remove(productId)) {
			rttr.addFlashAttribute("result", "success");
			log.info("remove..." + productId);
		}
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		
		return "redirect:list";
	}

}