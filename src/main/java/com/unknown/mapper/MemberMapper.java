package com.unknown.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.unknown.model.KakaoVO;
import com.unknown.model.MemberVO;

public interface MemberMapper {

	// ȸ������
	public void memberJoin(MemberVO member);

	// ���̵� �ߺ� �˻�
	public int idCheck(String memberId);

	// �α���
	public MemberVO memberLogin(@Param("memberId") String memberId, @Param("memberPw") String memberPw) throws Exception;

	// ȸ�� ���� ����
	public void memberUpdate(MemberVO member) throws Exception;

	// ȸ�� Ż��
	public void memberWithdraw(String memberId) throws Exception;

	// ȸ���� Ż�� ���θ� ��ȸ�ϴ� �޼���
	public Boolean isMemberWithdrawn(@Param("memberId") String memberId) throws Exception;

	// �ֹ��� �ּ� ����
	public MemberVO getMemberInfo(String memberId);

	// Kakao �α��� ���� ���� �޼���
	public void saveKakaoUserInfo(KakaoVO kakaoVO) throws Exception;
	
	 // ȸ�� ID�� ȸ�� ���� ��ȸ
    public MemberVO getMemberById(String memberId);

	// �̸����� �������� ȸ�� ���� ��ȸ
	public MemberVO getMemberByEmail(String memberMail);

	// �̸����� �������� īī�� ����� ���� ��ȸ
	public KakaoVO getKakaoUserByEmail(String k_email);

	// �̸��� �ߺ�Ȯ��
	public int checkEmailDuplicate(String memberMail);

	// ���̵� ã��
	public List<MemberVO> findId(String memberMail) throws Exception;

	public int findIdCheck(String memberMail) throws Exception;

	 // ��й�ȣ ã�⸦ ���� �̸��Ϸ� ��й�ȣ ����
    void updatePasswordByEmail(@Param("memberMail") String memberMail, @Param("newPassword") String newPassword);
    
}
