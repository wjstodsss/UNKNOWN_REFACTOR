package com.unknown.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unknown.model.ItemVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class ItemServiceTests {

	@Autowired
	private ItemService service;
	
	/* ��ǰ �� ���� */
	@Test
	public void getGoodsInfoTest() {

		int bookId = 26;

		ItemVO goodsInfo = service.getGoodsInfo(bookId);

		System.out.println("==���==");
		System.out.println("��ü : " + goodsInfo);
//		System.out.println("bookId : " + goodsInfo.getBookId());
		System.out.println("�̹��� ���� : " + goodsInfo.getImageList().isEmpty());

	}

}
