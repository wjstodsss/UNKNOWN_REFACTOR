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
	
	/* 카트 상품 추가 */
	@PostMapping("/cart/add")
	@ResponseBody
	public String addCartPOST(CartDTO cart, HttpServletRequest request) {
	    // 로그인 체크
	    HttpSession session = request.getSession();
	    MemberVO mvo = (MemberVO)session.getAttribute("member");
	    if(mvo == null) {
	        return "5";
	    }
	    
	    // 카트 등록
	    int result = cartService.addCart(cart);
	    
	    return String.valueOf(result);
	}
	
	/* 카트 정보 가져오기 추가 */
	@GetMapping("/cart/{memberId}")
	public String cartPageGET(@PathVariable("memberId") String memberId, Model model) {
		
		model.addAttribute("cartInfo", cartService.getCartList(memberId));
		
		return "/cart";
	}
	
	/* 장바구니 수량 수정 */
	@PostMapping("/cart/update")
	public String updateCartPOST(CartDTO cart) {
		
		cartService.modifyCount(cart);
		
		return "redirect:/cart/" + cart.getMemberId();
		
	}
	
	/* 장바구니 품목 삭제 */
	@PostMapping("/cart/delete")
	public String deleteCartPOST(CartDTO cart) {
		
		cartService.deleteCart(cart.getCartId());
		
		return "redirect:/cart/" + cart.getMemberId();
		
	}
}
