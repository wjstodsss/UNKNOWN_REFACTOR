package com.unknown.mapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unknown.model.ItemVO;
import com.unknown.model.CartDTO;
import com.unknown.model.MemberVO;
import com.unknown.model.OrderDTO;
import com.unknown.model.OrderItemDTO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class OrderMapperTests {

	
	@Autowired
	private OrderMapper mapper;
	

	
//	/* ��ǰ ����(�ֹ� ó��) */
//	@Test
//	public void getOrderInfoTest() {
//		
//		 OrderItemDTO orderInfo = mapper.getOrderInfo(61);
//		 
//		 System.out.println("result : " + orderInfo);
//	}
	
//	/* order ���̺� ��� */
//	@Test
//	public void enrollOrderTest() {
//		
//		OrderDTO ord = new OrderDTO();
//		List<OrderItemDTO> orders = new ArrayList();
//		
//		OrderItemDTO order1 = new OrderItemDTO();
//		
//		order1.setBookId(60);
//		order1.setBookCount(5);
//		order1.setBookPrice(70000);
//		order1.setBookDiscount(0.1);
//		order1.initSaleTotal();
//		
//		
//		
//		ord.setOrders(orders);
//		
//		ord.setOrderId("2024_test1");
//		ord.setAddressee("test");
//		ord.setMemberId("test2");
//		ord.setMemberAddr1("test");
//		ord.setMemberAddr2("test");
//		ord.setMemberAddr3("test");
//		ord.setOrderState("����غ�");
//		ord.getOrderPriceInfo();
//		ord.setUsePoint(1000);
//		
//		mapper.enrollOrder(ord);		
//		
//	}
//	
//	/* itemorder ���̺� ��� */
//	@Test
//	public void enrollOrderItemTest() {
//		
//		OrderItemDTO oid = new OrderItemDTO();
//		
//		oid.setOrderId("2024_test1");
//		oid.setBookId(61);
//		oid.setBookCount(1);
//		oid.setBookPrice(70000);
//		oid.setBookDiscount(0.1);
//				
//		oid.initSaleTotal();
//		
//		mapper.enrollOrderItem(oid);
//		
//	}	
//	
//	/* ȸ�� ��, ����Ʈ ���� ���� */
//	@Test
//	public void deductMoneyTest() {
//		
//		MemberVO member = new MemberVO();
//		
//		member.setMemberId("test2");
//		member.setMoney(500000);
//		member.setPoint(10000);
//		
//		mapper.deductMoney(member);
//	}
//	
//	/* ��ǰ ��� ���� */
//	@Test
//	public void deductStockTest() {
//		BookVO book = new BookVO();
//		
//		book.setBookId(61);
//		book.setBookStock(77);
//		
//		mapper.deductStock(book);
//	}
	


}


