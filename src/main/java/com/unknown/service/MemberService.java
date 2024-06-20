package com.unknown.service;

import java.net.URL;
import java.util.HashMap;
import java.util.List;

import com.unknown.model.MemberVO;

public interface MemberService {

	// ȸ������
	public void memberJoin(MemberVO member) throws Exception;

	// ���̵� �ߺ� �˻�
	public int idCheck(String memberId) throws Exception;

	// �α���
	public MemberVO memberLogin(String memberId, String memberPw) throws Exception;

	// ȸ�� ���� ����
	public void memberUpdate(MemberVO member) throws Exception;

	// ȸ�� Ż��
	public boolean memberWithdraw(String memberId) throws Exception;

	// ȸ���� Ż���ߴ��� ���� Ȯ��
	public boolean isMemberWithdrawn(String memberId) throws Exception;

	// �ֹ��� ����
	public MemberVO getMemberInfo(String memberId);

	// Kakao �α����� ���� �޼���
	public String getAccessToken(String authorize_code);

	// īī�� �α��� �ÿ� ����� ���� �������� �޼���
	public HashMap<String, Object> getUserInfo(String access_Token);
	
	public void saveKakaoUserInfo(String access_Token);
	
	// ȸ�� ID�� ȸ�� ���� ��ȸ
    public MemberVO getMemberById(String memberId);

	// �̸����� �������� ȸ�� ���� ��ȸ
	public MemberVO getMemberByEmail(String memberMail);

	// �̸��� �ߺ� Ȯ��
	public int checkEmailDuplicate(String memberMail);
	
	// ���̵� ã��
	public List<MemberVO> findId(String memberMail) throws Exception;
	
	// ã�� ���̵� Ȯ��
	public int findIdCheck(String memberMail) throws Exception;
	
	// �̸����� �������� ȸ���� ��й�ȣ�� ������Ʈ
	public void updatePasswordByEmail(String memberMail, String newPassword);
	
	// �ӽ� ��й�ȣ�� �����ϴ� �޼���
	public void saveTemporaryPassword(String memberMail, String temporaryPassword);

}
