package com.unknown.service;

import java.net.URL;
import java.util.HashMap;
import java.util.List;

import com.unknown.model.MemberVO;

public interface MemberService {

	// 회원가입
	public void memberJoin(MemberVO member) throws Exception;

	// 아이디 중복 검사
	public int idCheck(String memberId) throws Exception;

	// 로그인
	public MemberVO memberLogin(String memberId, String memberPw) throws Exception;

	// 회원 정보 수정
	public void memberUpdate(MemberVO member) throws Exception;

	// 회원 탈퇴
	public boolean memberWithdraw(String memberId) throws Exception;

	// 회원이 탈퇴했는지 여부 확인
	public boolean isMemberWithdrawn(String memberId) throws Exception;

	// 주문자 정보
	public MemberVO getMemberInfo(String memberId);

	// Kakao 로그인을 위한 메서드
	public String getAccessToken(String authorize_code);

	// 카카오 로그인 시에 사용자 정보 가져오는 메서드
	public HashMap<String, Object> getUserInfo(String access_Token);
	
	public void saveKakaoUserInfo(String access_Token);
	
	// 회원 ID로 회원 정보 조회
    public MemberVO getMemberById(String memberId);

	// 이메일을 기준으로 회원 정보 조회
	public MemberVO getMemberByEmail(String memberMail);

	// 이메일 중복 확인
	public int checkEmailDuplicate(String memberMail);
	
	// 아이디 찾기
	public List<MemberVO> findId(String memberMail) throws Exception;
	
	// 찾은 아이디 확인
	public int findIdCheck(String memberMail) throws Exception;
	
	// 이메일을 기준으로 회원의 비밀번호를 업데이트
	public void updatePasswordByEmail(String memberMail, String newPassword);
	
	// 임시 비밀번호를 저장하는 메서드
	public void saveTemporaryPassword(String memberMail, String temporaryPassword);

}
