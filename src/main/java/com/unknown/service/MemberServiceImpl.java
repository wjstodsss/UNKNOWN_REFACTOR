package com.unknown.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unknown.mapper.MemberMapper;
import com.unknown.model.KakaoVO;
import com.unknown.model.MemberVO;

@Service
public class MemberServiceImpl  implements MemberService{

   @Autowired
   private MemberMapper membermapper;
   
   @Autowired
    private HttpServletRequest request;
   
   @Autowired
   private MemberService memberservice;
   
   private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
   
   // 임시 비밀번호 길이
    private static final int TEMP_PASSWORD_LENGTH = 8;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;
   
   

   /* 회원가입*/
   @Override
   public void memberJoin(MemberVO member) throws Exception {
      
      membermapper.memberJoin(member);
      
   }
   
   /* 아이디 중복 검사 */
   @Override
   public int idCheck(String memberId) throws Exception {
      
      return membermapper.idCheck(memberId);
   }
   
   @Override
   public MemberVO memberLogin(String memberId, String rawPw) throws Exception {
       MemberVO member = membermapper.getMemberById(memberId);

       if (member != null && "Y".equals(member.getWithdrawal())) {
           member.setWithdrawalMessage("탈퇴된 회원입니다.");
           System.out.println("탈퇴된 회원입니다: " + member.getWithdrawalMessage());
           return null; // 탈퇴한 회원은 로그인 실패로 처리
       }

       if (member != null) {
           logger.info("Raw Password: " + rawPw); // 추가된 로그
           logger.info("Encoded Password from DB: " + member.getMemberPw()); // 추가된 로그
           if (passwordEncoder.matches(rawPw, member.getMemberPw())) {
               // 비밀번호가 일치하면 로그인 성공
               performLogin(member, request.getSession()); // 세션에 회원 정보 저장
               System.out.println("로그인 성공: " + member.getMemberId());
               return member;
           } else {
               // 비밀번호가 일치하지 않으면 null 반환
               System.out.println("비밀번호가 일치하지 않음");
               return null;
           }
       } else {
           return null;
       }
   }


   @Override
   public MemberVO getMemberById(String memberId) {
       return membermapper.getMemberById(memberId);
   }
   
   /* 회원 정보 수정 */
   @Override
   public void memberUpdate(MemberVO member) throws Exception {
       membermapper.memberUpdate(member);
   }



    
    /* 회원 탈퇴 */
    @Override
    public boolean memberWithdraw(String memberId) throws Exception {
        try {
            // 회원 상태를 탈퇴로 변경하는 메서드 호출
            membermapper.memberWithdraw(memberId);
            return true; // 탈퇴 성공
        } catch (Exception e) {
            e.printStackTrace();
            return false; // 탈퇴 실패
        }
    }
    
    /* 회원 탈퇴 여부 확인 메서드 */
    @Override
    public boolean isMemberWithdrawn(String memberId) throws Exception {
        // memberId를 이용하여 회원의 탈퇴 여부를 조회합니다.
        Boolean withdrawal = membermapper.isMemberWithdrawn(memberId);
        // withdrawal이 null이면 false를 반환하도록 처리합니다.
        return withdrawal != null && withdrawal;
    }
    
    /* 주문자 정보 */
   @Override
   public MemberVO getMemberInfo(String memberId) {
      
      return membermapper.getMemberInfo(memberId);
      
   }
   
    // Kakao 로그인을 위한 메서드
    @Override
    public String getAccessToken(String authorize_code) {
        String access_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=7348caf219d2fff8532a8961c20f78ab"); // 본인이 발급받은 key
            sb.append("&redirect_uri=http://localhost:8080/member/easyGeneral"); // 본인이 설정한 주소
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return access_Token;
    }
    
    
    public HashMap<String, Object> getUserInfo(String access_Token) {
        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = null;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            }

            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            System.out.println("response body : " + result);

            JsonElement element = JsonParser.parseString(result.toString());

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();
            

            userInfo.put("nickname", nickname);
            userInfo.put("email", email);
            
            
           

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        
       return userInfo;
    }
    
    @Override
    public void saveKakaoUserInfo(String access_Token) {
        HashMap<String, Object> userInfo = getUserInfo(access_Token);

        KakaoVO kakaoVO = new KakaoVO();
        kakaoVO.setK_name((String) userInfo.get("nickname"));
        kakaoVO.setK_email((String) userInfo.get("email"));

        KakaoVO existingKakaoUser = membermapper.getKakaoUserByEmail(kakaoVO.getK_email());
        if (existingKakaoUser != null) {
            logger.info("Existing Kakao user found: {}", existingKakaoUser.getK_email());
            return;
        }

        MemberVO existingMember = membermapper.getMemberByEmail(kakaoVO.getK_email());

        if (existingMember == null) {
            try {
                logger.info("Saving new Kakao user info: {}", kakaoVO.getK_email());
                membermapper.saveKakaoUserInfo(kakaoVO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if ("Y".equals(existingMember.getWithdrawal())) {
                logger.info("탈퇴된 회원입니다: {}", existingMember.getWithdrawalMessage());
                return;
            }
            performLogin(existingMember, request.getSession());
        }
    }

    
    private void performLogin(MemberVO member, HttpSession session) {
        session.setAttribute("member", member);
        System.out.println("Logged in user: " + member.getMemberId());
    }
    
    /* 이메일을 기준으로 회원 정보 조회 */
    @Override
    public MemberVO getMemberByEmail(String memberMail) {
        return membermapper.getMemberByEmail(memberMail);
    }

    // 이메일 중복 확인
    @Override
    public int checkEmailDuplicate(String memberMail) {
        int count = membermapper.checkEmailDuplicate(memberMail);
        logger.info("Checking email duplication for: {}, Count: {}", memberMail, count); // 로그 추가
        return count;
    }

    // 아이디 찾기
    @Override
   public List<MemberVO> findId(String memberMail)throws Exception{
      return membermapper.findId(memberMail);
   }
   
   @Override
   public int findIdCheck(String memberMail)throws Exception{
      return membermapper.findIdCheck(memberMail);
   }
   
   @Override
   public void updatePasswordByEmail(String memberMail, String newPassword) {
       // 비밀번호 업데이트
       String encodedPassword = passwordEncoder.encode(newPassword);
       membermapper.updatePasswordByEmail(memberMail, encodedPassword);
   }
   
   
   // 비밀번호 찾기 - 임시 비밀번호 생성 및 이메일 발송
   @Override
   @Transactional
   public void saveTemporaryPassword(String memberMail, String temporaryPassword) {
       // 비밀번호 업데이트
       membermapper.updatePasswordByEmail(memberMail, passwordEncoder.encode(temporaryPassword));

       // 이메일 발송
       sendTemporaryPasswordEmail(memberMail, temporaryPassword);
   }


    // 임시 비밀번호 생성
    private String generateTemporaryPassword() {
        // 임시 비밀번호를 생성할 문자열 범위
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // 임시 비밀번호 생성기
        StringBuilder temporaryPassword = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < TEMP_PASSWORD_LENGTH; i++) {
            temporaryPassword.append(characters.charAt(random.nextInt(characters.length())));
        }

        return temporaryPassword.toString();
    }

    // 임시 비밀번호 이메일 발송
    private void sendTemporaryPasswordEmail(String memberMail, String temporaryPassword) {
        // 이메일 내용
        String subject = "Temporary Password";
        String content = "Your temporary password is: " + temporaryPassword;

        // 이메일 발송
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(memberMail);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }
   
    
   
}