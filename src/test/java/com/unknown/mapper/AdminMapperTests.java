package com.unknown.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unknown.model.AttachImageVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class AdminMapperTests {
	@Autowired
	private AdminMapper mapper;

//	/* ��ǰ ��� */
//	@Test
//	public void bookEnrollTest() throws Exception {
//
//		BookVO book = new BookVO();
//
//		book.setBookName("mapper �׽�Ʈ");
//		book.setAuthorId(123);
//		book.setPubleYear("2021-03-18");
//		book.setPublisher("���ǻ�");
//		book.setCateCode("202001");
//		book.setBookPrice(20000);
//		book.setBookStock(300);
//		book.setBookDiscount(0.23);
//		book.setBookIntro("å �Ұ� ");
//		book.setBookContents("å ���� ");
//		
//		System.out.println("Before BookVO :" + book);
//		
//		mapper.bookEnroll(book);
//
//		System.out.println("After BookVO :" + book);
//	}

//	/* ī�װ� ����Ʈ */
//	@Test
//	public void cateListTest() throws Exception{
//		
//		System.out.println("cateList()..........." + mapper.cateList());
//		
//	}

//	/* ��ǰ ����Ʈ & ��ǰ �� ���� */
//	@Test
//	public void goodsGetListTest() {
//		
//		Criteria cri = new Criteria();
//		
//		/* �˻����� */
//		cri.setKeyword("�� ���� ����");
//		
//		/* �˻� ����Ʈ */
//		List list = mapper.goodsGetList(cri);
//		for(int i = 0; i < list.size(); i++) {
//			System.out.println("result......." + i + " : " + list.get(i));
//		}
//		
//		/* ��ǰ �� ���� */
//		int result = mapper.goodsGetTotal(cri);
//		System.out.println("resutl.........." + result);
//		
//		
//	}

//	/* ��ǰ ��ȸ ������ */
//	@Test
//	public void goodsGetDetailTest() {
//		
//		int bookId = 150;
//		
//		BookVO result = mapper.goodsGetDetail(bookId);
//		
//		System.out.println("��ǰ ��ȸ ������ : " + result);
//		
//		
//	}

//	/* ��ǰ ���� ���� */
//	@Test
//	public void goodsModifyTest() {
//		
//		BookVO book = new BookVO();
//		
//		book.setBookId(95);
//		book.setBookName("mapper �׽�Ʈ");
//		book.setAuthorId(94);
//		book.setPubleYear("2021-03-18");
//		book.setPublisher("���ǻ�");
//		book.setCateCode("103002");
//		book.setBookPrice(20000);
//		book.setBookStock(300);
//		book.setBookDiscount(0.23);
//		book.setBookIntro("å �Ұ� ");
//		book.setBookContents("å ���� ");
//		
//		mapper.goodsModify(book);
//		
//	}

//	/* ��ǰ ���� ���� */
//	@Test
//	public void goodsDeleteTest() {
//		
//		int bookId = 3072;
//		
//		int result = mapper.goodsDelete(bookId);
//		
//		if(result == 1) {
//			System.out.println("���� ����");
//		}
//		
//	}

//	/* �̹��� ��� */
//	@Test
//	public void imageEnrollTest() {
//		
//		AttachImageVO vo = new AttachImageVO();
//		
//		vo.setBookId(20);
//		vo.setFileName("test");
//		vo.setUploadPath("test");
//		vo.setUuid("test2");
//		
//		mapper.imageEnroll(vo);
//		
//	}
	
//	/* ������ ��¥ �̹��� ����Ʈ */
//	@Test
//	public void checkImageListTest() {
//		
//		mapper.checkFileList();
//		
//	}
	
	/* ���� ��ǰ �̹��� ���� ��� */
	@Test
	public void getAttachInfoTest() {
		
		int bookId = 3;
		
		List<AttachImageVO> list = mapper.getAttachInfo(bookId);
		
		System.out.println("list : " + list);
		
	}
}
