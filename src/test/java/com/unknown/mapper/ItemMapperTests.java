package com.unknown.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unknown.model.ItemVO;
import com.unknown.model.Criteria;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class ItemMapperTests {

	@Autowired
	private ItemMapper mapper;

//	@Test
//	public void getGoodsListTest() {
//
//		Criteria cri = new Criteria();
//		// �׽�Ʈ Ű����
//		// cri.setKeyword("test");
//		System.out.println("cri : " + cri);
//
//		List<BookVO> list = mapper.getGoodsList(cri);
//		System.out.println("list : " + list);
//
//		System.out.println("==========");
//		int goodsTotal = mapper.goodsGetTotal(cri);
//		System.out.println("totla : " + goodsTotal);
//
//	}
//
//	/* �۰� id ����Ʈ ��û */
//
//	@Test
//	public void getAuthorId() {
//
//		String keyword = "��";
//
//		String[] list = mapper.getAuthorIdList(keyword);
//
//		System.out.println("��� : " + list.toString());
//
//		for (String id : list) {
//			System.out.println("���� ��� : " + id);
//		}
//
//	}
	
/* �˻� (���� ���� ����) - �۰�*/
	
//	@Test 
//	public void getGoodsListTest1() {
//		//
//		Criteria cri = new Criteria();
//		String type = "A";
//		String keyword = "��ȫ��";		// DB�� ��ϵ� �۰� ������
////      String keyword = "����";		// DB�� ��ϵ��� ���� �۰� ������
//		String catecode = "";
//		
//		cri.setType(type);
//		cri.setKeyword(keyword);
//		cri.setAuthorArr(mapper.getAuthorIdList(keyword));
//		cri.setCateCode(catecode);
//		
//		List<BookVO> list = mapper.getGoodsList(cri);
//		
//		System.out.println("cri : " + cri);
//		System.out.println("list : " + list);
//		
//	}
//	
//	
//	
//	
//	/* �˻� (���� ���� ����) - å����*/
//	
//	@Test 
//	public void getGoodsListTest2() {
//		Criteria cri = new Criteria();
//		String type = "T";
//		String keyword = "�׽�Ʈ";			// ���̺� ��ϵ� å ���� ������
//		//String keyword = "����";				// ���̺� ��ϵ��� ���� ������
//		String catecode = "";	
//		
//		cri.setType(type);
//		cri.setKeyword(keyword);
//		cri.setAuthorArr(mapper.getAuthorIdList(keyword));
//		cri.setCateCode(catecode);
//		
//		List<BookVO> list = mapper.getGoodsList(cri);
//		
//		System.out.println("cri : " + cri);
//		System.out.println("list : " + list);
//		
//	}
//	
//	
//	/* �˻� (���� ���� ����) - ī�װ�*/
//	
//	@Test 
//	public void getGoodsListTest3() {
//		Criteria cri = new Criteria();
//		String type = "C";
//		String keyword = "";
//		String catecode = "103002";		
//		
//		cri.setType(type);
//		cri.setKeyword(keyword);
//		cri.setAuthorArr(mapper.getAuthorIdList(keyword));
//		cri.setCateCode(catecode);
//		
//		List<BookVO> list = mapper.getGoodsList(cri);
//		
//		System.out.println("cri : " + cri);
//		System.out.println("list : " + list);
//	}
//	
//	
//	
//	/* �˻� (���� ���� ����) - ī�װ� + �۰� */
//	
//	@Test 
//	public void getGoodsListTest4() {
//		Criteria cri = new Criteria();
//		String type = "AC";
//		String keyword = "��ȫ��";	// ī�װ��� �����ϴ� �۰�
//		//String keyword = "�ӽ�ũ";	// ī�װ��� �������� �ʴ� �۰�
//		String catecode = "103002";
//		
//		cri.setType(type);
//		cri.setKeyword(keyword);
//		cri.setAuthorArr(mapper.getAuthorIdList(keyword));
//		cri.setCateCode(catecode);
//		
//		List<BookVO> list = mapper.getGoodsList(cri);
//		
//		System.out.println("cri : " + cri);
//		System.out.println("list : " + list);	
//		
//	}
//	
//	
//	
//	
//	/* �˻� (���� ���� ����) - ī�װ� + å ���� */
//	
//	@Test 
//	public void getGoodsListTest5() {
//		Criteria cri = new Criteria();
//		String type = "CT";			// ī�װ��� �����ϴ� å
//		String keyword = "�׽�Ʈ";	// ī�װ��� �������� �ʴ� å
//		//String keyword = "����";
//		String catecode = "102001";
//		
//		cri.setType(type);
//		cri.setKeyword(keyword);
//		cri.setAuthorArr(mapper.getAuthorIdList(keyword));
//		cri.setCateCode(catecode);
//		
//		List<BookVO> list = mapper.getGoodsList(cri);
//		
//		System.out.println("cri : " + cri);
//		System.out.println("list : " + list);	
//		
//	}
//	
	/* ��ǰ ���� */
	@Test
	public void getGoodsInfo() {
		int bookId = 26;
		ItemVO goodsInfo = mapper.getGoodsInfo(bookId);
		System.out.println("===========================");
		System.out.println(goodsInfo);
		System.out.println("===========================");
		
	}
}
