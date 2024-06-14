//package com.unknown.mapper;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.unknown.model.MemberVO;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
//public class MemberMapperTests {
//
//	@Autowired
//	private MemberMapper membermapper;			//MemberMapper.java �������̽� ������ ����
//	
//	/*
//	//ȸ������ ���� �׽�Ʈ �޼���
//	@Test
//	public void memberJoin() throws Exception{
//		MemberVO member = new MemberVO();
//		
//		member.setMemberId("testzz");			//ȸ�� id
//		member.setMemberPw("test");			//ȸ�� ��й�ȣ
//		member.setMemberName("test");		//ȸ�� �̸�
//		member.setMemberMail("test");		//ȸ�� ����
//		member.setMemberAddr1("test");		//ȸ�� �����ȣ
//		member.setMemberAddr2("test");		//ȸ�� �ּ�
//		member.setMemberAddr3("test");		//ȸ�� ���ּ�
//		
//		membermapper.memberJoin(member);			//���� �޼��� ����
//		
//	}
//	*/
//	
//	// ���̵� �ߺ��˻�
//	/*
//		@Test
//		public void memberIdChk() throws Exception{
//			String id = "admin2";	// �����ϴ� ���̵�
//			String id2 = "test123";	// �������� �ʴ� ���̵�
//			membermapper.idCheck(id);
//			membermapper.idCheck(id2);
//		}
//	*/
//	
//	/* �α��� ���� mapper �޼��� �׽�Ʈ */
//    @Test
//    public void memberLogin() throws Exception{
//        
//        MemberVO member = new MemberVO();    // MemberVO ���� ���� �� �ʱ�ȭ
//        
//        /* �ùٸ� ���̵� ��� �Է°�� */
//        //member.setMemberId("dhkim");
//        //member.setMemberPw("1234");
//        
//        /* �ùٸ� ���� ���̵� ��� �Է°�� */
//        member.setMemberId("test1123");
//        member.setMemberPw("test1321321");
//        
//        membermapper.memberLogin(member);
//        System.out.println("��� �� : " + membermapper.memberLogin(member));
//        
//    }
//	
//}
