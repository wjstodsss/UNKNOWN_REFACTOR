package com.unknown.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unknown.model.AttachImageVO;
import com.unknown.model.ItemVO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class AdminServiceTest {

	@Autowired
	private AdminService service;
	
//	/* ��ǰ ��� & ��ǰ �̹��� ��� �׽�Ʈ */
//	@Test
//	public void bookEnrollTEsts() {
//
//		ItemVO book = new ItemVO();
//		// ��ǰ ����
//		book.setBookName("service �׽�Ʈ");
//		book.setAuthorId(1000);
//		book.setPubleYear("2021-03-18");
//		book.setPublisher("���ǻ�");
//		book.setCateCode("202001");
//		book.setBookPrice(20000);
//		book.setBookStock(300);
//		book.setBookDiscount(0.23);
//		book.setBookIntro("å �Ұ� ");
//		book.setBookContents("å ���� ");
//
//		// �̹��� ����
//		List<AttachImageVO> imageList = new ArrayList<AttachImageVO>(); 
//		
//		AttachImageVO image1 = new AttachImageVO();
//		AttachImageVO image2 = new AttachImageVO();
//		
//		image1.setFileName("test Image 1");
//		image1.setUploadPath("test image 1");
//		image1.setUuid("test1111");
//		
//		 // ���� �߻� ��Ȳ �׽�Ʈ
//        image2.setFileName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//		image2.setUploadPath("test image 2");
//		image2.setUuid("test2222");
//		
//		imageList.add(image1);
//		imageList.add(image2);
//		
//		book.setImageList(imageList);        
//		
//		// bookEnroll() �޼��� ȣ��
//		service.bookEnroll(book);
//		
//		System.out.println("��ϵ� VO : " + book);
//		
//		
//	}

}
