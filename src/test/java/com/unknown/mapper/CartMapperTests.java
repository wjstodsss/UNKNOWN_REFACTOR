package com.unknown.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unknown.model.CartDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class CartMapperTests {

	@Autowired
	private CartMapper mapper;
	
//	/* īƮ �߰� */
//	@Test
//	public void addCart() {
//		String memberId = "test2";
//		int bookId = 61;
//		int count = 2;
//		
//		CartDTO cart = new CartDTO();
//		cart.setMemberId(memberId);
//		cart.setBookId(bookId);
//		cart.setBookCount(count);
//		
//		int result = 0;
//		result = mapper.addCart(cart);
//		
//		System.out.println("��� : " + result);
//		
//	}	
//
//	
//	/* īƮ ���� */
//	@Test
//	public void deleteCartTest() {
//		int cartId = 2;
//		
//		mapper.deleteCart(cartId);
//
//	
//	
//	}
//	
//	/* īƮ ���� ���� */
//	@Test
//	public void modifyCartTest() {
//		int cartId = 3;
//		int count = 5;
//		
//		CartDTO cart = new CartDTO();
//		cart.setCartId(cartId);
//		cart.setBookCount(count);
//		
//		mapper.modifyCount(cart);
//		
//	}
//	
//	/* īƮ ��� */ 
//	@Test
//	public void getCartTest() {
//		String memberId = "test2";
//		
//		
//		List<CartDTO> list = mapper.getCart(memberId);
//		for(CartDTO cart : list) {
//			System.out.println(cart);
//			cart.initSaleTotal();
//			System.out.println("init cart : " + cart);
//		}
//		
//		
//	
//	}
//	
//	/* īƮ Ȯ�� */
//	@Test
//	public void checkCartTest() {
//		
//		String memberId = "test2";
//		int bookId = 50;
//		
//		CartDTO cart = new CartDTO();
//		cart.setMemberId(memberId);
//		cart.setBookId(bookId);
//		
//		CartDTO resutlCart = mapper.checkCart(cart);
//		System.out.println("��� : " + resutlCart);
//		
//	}
	
//	/* ��ٱ��� ����(�ֹ� ó��) */
//	@Test
//	public void deleteOrderCart() {
//		String memberId = "test2";
//		int bookId = 61;
//		
//		CartDTO dto = new CartDTO();
//		dto.setMemberId(memberId);
//		dto.setBookId(bookId);
//		
//		mapper.deleteOrderCart(dto);
//		
//	}

}
