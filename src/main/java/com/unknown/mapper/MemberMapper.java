package com.unknown.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.unknown.model.KakaoVO;
import com.unknown.model.MemberVO;

public interface MemberMapper {

	// 회원가입
	public void memberJoin(MemberVO member);

	// 아이디 중복 검사
	public int idCheck(String memberId);

	// 로그인
	public MemberVO memberLogin(@Param("memberId") String memberId, @Param("memberPw") String memberPw) throws Exception;

	// 회원 정보 수정
	public void memberUpdate(MemberVO member) throws Exception;

	// 회원 탈퇴
	public void memberWithdraw(String memberId) throws Exception;

	// 회원의 탈퇴 여부를 조회하는 메서드
	public Boolean isMemberWithdrawn(@Param("memberId") String memberId) throws Exception;

	// 주문자 주소 정보
	public MemberVO getMemberInfo(String memberId);

	// Kakao 로그인 정보 저장 메서드
	public void saveKakaoUserInfo(KakaoVO kakaoVO) throws Exception;
	
	 // 회원 ID로 회원 정보 조회
    public MemberVO getMemberById(String memberId);

	// 이메일을 기준으로 회원 정보 조회
	public MemberVO getMemberByEmail(String memberMail);

	// 이메일을 기준으로 카카오 사용자 정보 조회
	public KakaoVO getKakaoUserByEmail(String k_email);

	// 이메일 중복확인
	public int checkEmailDuplicate(String memberMail);

	// 아이디 찾기
	public List<MemberVO> findId(String memberMail) throws Exception;

	public int findIdCheck(String memberMail) throws Exception;

	 // 비밀번호 찾기를 위한 이메일로 비밀번호 변경
    void updatePasswordByEmail(@Param("memberMail") String memberMail, @Param("newPassword") String newPassword);
    
}
