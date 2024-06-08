package com.unknown.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unknown.model.CartDTO;
import com.unknown.model.MemberVO;
import com.unknown.service.CartService;

@Controller
public class CartController {

	@Autowired
	private CartService cartService;
	
	/* īƮ ��ǰ �߰� */
	@PostMapping("/cart/add")
	@ResponseBody
	public String addCartPOST(CartDTO cart, HttpServletRequest request) {
	    // �α��� üũ
	    HttpSession session = request.getSession();
	    MemberVO mvo = (MemberVO)session.getAttribute("member");
	    if(mvo == null) {
	        return "5";
	    }
	    
	    // īƮ ���
	    int result = cartService.addCart(cart);
	    
	    return String.valueOf(result);
	}
	
	/* īƮ ���� �������� �߰� */
	@GetMapping("/cart/{memberId}")
	public String cartPageGET(@PathVariable("memberId") String memberId, Model model) {
		
		model.addAttribute("cartInfo", cartService.getCartList(memberId));
		
		return "/cart";
	}
	
	/* ��ٱ��� ���� ���� */
	@PostMapping("/cart/update")
	public String updateCartPOST(CartDTO cart) {
		
		cartService.modifyCount(cart);
		
		return "redirect:/cart/" + cart.getMemberId();
		
	}
	
	/* ��ٱ��� ǰ�� ���� */
	@PostMapping("/cart/delete")
	public String deleteCartPOST(CartDTO cart) {
		
		cartService.deleteCart(cart.getCartId());
		
		return "redirect:/cart/" + cart.getMemberId();
		
	}
}
