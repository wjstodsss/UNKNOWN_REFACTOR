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
	
	/* 상품 상세 정보 */
	@Test
	public void getGoodsInfoTest() {

		int bookId = 26;

		ItemVO goodsInfo = service.getGoodsInfo(bookId);

		System.out.println("==결과==");
		System.out.println("전체 : " + goodsInfo);
//		System.out.println("bookId : " + goodsInfo.getBookId());
		System.out.println("이미지 정보 : " + goodsInfo.getImageList().isEmpty());

	}

}
