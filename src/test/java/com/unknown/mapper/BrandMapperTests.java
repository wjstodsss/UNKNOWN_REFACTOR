package com.unknown.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unknown.model.BrandVO;
import com.unknown.model.Criteria;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class BrandMapperTests {

	@Autowired
    private BrandMapper mapper;
    
//    /* �۰� ��� �׽�Ʈ */
//    @Test
//    public void authorEnroll() throws Exception{
//        
//        AuthorVO author = new AuthorVO();
//        
//        
//        author.setNationId("01");
//        author.setAuthorName("�׽�Ʈ");
//        author.setAuthorIntro("�׽�Ʈ �Ұ�");
//        
//        mapper.authorEnroll(author);
//        
//    }
//    
//    /* �۰� ��� �׽�Ʈ */
//    @Test
//    public void authorGetListTest() throws Exception{
//        
//        Criteria cri = new Criteria(3,10);    // 3������ & 10�� �� ǥ��
//        cri.setKeyword("���۰�");
//        
//        List<AuthorVO> list = mapper.authorGetList(cri);
//        
//        for(int i = 0; i < list.size(); i++) {
//            System.out.println("list" + i + ".........." + list.get(i));
//        }
//        
//    }
//    
//    /* �۰� ��� �׽�Ʈ */
//    @Test
//    public void authorGetTotalTest() throws Exception{
//        
//        Criteria cri = new Criteria();    
//        cri.setKeyword("���۰�");
//        
//        int total = mapper.authorGetTotal(cri);
//        
//        System.out.println("total........." + total);
//        
//        }
//    
//    
//   /* 작가 상세 페이지 */
//	@Test
//	public void authorGetDetailTest() {
//		
//		int authorId = 30;
//		
//		AuthorVO author = mapper.authorGetDetail(authorId);
//		
//		System.out.println("author......." + author);
//		
//	}
//	
//	/* 작가 정보 수정 */
//	@Test
//	public void authorModifyTest() {
//		
//		AuthorVO author = new AuthorVO();
//				
//		author.setAuthorId(30);
//		System.out.println("수정 전...................." + mapper.authorGetDetail(author.getAuthorId()));
//		
//		author.setAuthorName("수정");
//		author.setNationId("01");
//		author.setAuthorIntro("소개 수정 하였습니다.");
//		
//		mapper.authorModify(author);
//		System.out.println("수정 후...................." + mapper.authorGetDetail(author.getAuthorId()));
//		
//	}
	
	/* 작가 정보 삭제 */
//	@Test
//	public void authorDeleteTest() {
//		
//		
//		int authorId = 487;
//		
//		int result = mapper.authorDelete(authorId);
//		
//		if(result == 1) {
//			System.out.println("삭제 성공");
//		}
//		
//	}

        
    }


    
    
 

 
