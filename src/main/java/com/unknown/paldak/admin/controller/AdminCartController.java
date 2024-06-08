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
import com.unknown.paldak.admin.domain.CartVO;
import com.unknown.paldak.admin.service.BaseService;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("admin/cart/*")
@RequiredArgsConstructor
public class AdminCartController {

	private final BaseService<CartVO> cartService;


	@GetMapping("/list")
	public String list(Criteria cri, Model model) {
		List<CartVO> list = cartService.getList(cri);
		model.addAttribute("carts", list);
        int total = cartService.getTotal(cri);
        model.addAttribute("pageMaker", new PageDTO(cri, total));
        return "admin/cart";
	}

	
	@GetMapping("/descList")
	public String descList(Criteria cri, Model model) {
		List<CartVO> list = cartService.getDescList(cri);
		model.addAttribute("carts", list);
		
        int total = cartService.getTotal(cri);
        
        model.addAttribute("pageMaker", new PageDTO(cri, total));
        return "admin/cart";
	}
	

	@PostMapping("/register")
	public String register(Model model, CartVO cartVO, RedirectAttributes rttr) {
	    cartService.register(cartVO);
	    rttr.addFlashAttribute("result", cartVO.getCartId());
	    return "redirect:descList";
	}

	@GetMapping(value = "/get/{cartId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<CartVO> get(@PathVariable("cartId") Long cartId) {
		return new ResponseEntity<>(cartService.get(cartId), HttpStatus.OK);
	}


	@PostMapping("/modify")
	public String modify(CartVO cartVO, @ModelAttribute("cri") Criteria cri, @RequestParam("currentPath") String currentPath, RedirectAttributes rttr) {
	    if (cartService.modify(cartVO)) {
	        rttr.addFlashAttribute("result", "success");
	    }
	    rttr.addAttribute("pageNum", cri.getPageNum());
	    rttr.addAttribute("amount", cri.getAmount());
	    return "redirect:" + currentPath;
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("cartId") Long cartId, @ModelAttribute("cri") Criteria cri, @RequestParam("currentPath") String currentPath, RedirectAttributes rttr) {
		if (cartService.remove(cartId)) {
			rttr.addFlashAttribute("result", "success");
		}
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		return "redirect:" + currentPath;
	}
}