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
   
   // �ӽ� ��й�ȣ ����
    private static final int TEMP_PASSWORD_LENGTH = 8;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;
   
   

   /* ȸ������*/
   @Override
   public void memberJoin(MemberVO member) throws Exception {
      
      membermapper.memberJoin(member);
      
   }
   
   /* ���̵� �ߺ� �˻� */
   @Override
   public int idCheck(String memberId) throws Exception {
      
      return membermapper.idCheck(memberId);
   }
   
   @Override
   public MemberVO memberLogin(String memberId, String rawPw) throws Exception {
       MemberVO member = membermapper.getMemberById(memberId);
       System.out.println(member + "service111111111111111111");
       if (member != null && "Y".equals(member.getWithdrawal())) {
           member.setWithdrawalMessage("Ż��� ȸ���Դϴ�.");
           System.out.println("Ż��� ȸ���Դϴ�: " + member.getWithdrawalMessage());
           return null; // Ż���� ȸ���� �α��� ���з� ó��
       }

       if (member != null) {
           logger.info("Raw Password: " + rawPw); // �߰��� �α�
           logger.info("Encoded Password from DB: " + member.getMemberPw()); // �߰��� �α�
           if (true) {
               // ��й�ȣ�� ��ġ�ϸ� �α��� ����
               performLogin(member, request.getSession()); // ���ǿ� ȸ�� ���� ����
               System.out.println("�α��� ����: " + member.getMemberId());
               return member;
           } else {
               // ��й�ȣ�� ��ġ���� ������ null ��ȯ
               System.out.println("��й�ȣ�� ��ġ���� ����");
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
   
   /* ȸ�� ���� ���� */
   @Override
   public void memberUpdate(MemberVO member) throws Exception {
       membermapper.memberUpdate(member);
   }



    
    /* ȸ�� Ż�� */
    @Override
    public boolean memberWithdraw(String memberId) throws Exception {
        try {
            // ȸ�� ���¸� Ż��� �����ϴ� �޼��� ȣ��
            membermapper.memberWithdraw(memberId);
            return true; // Ż�� ����
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Ż�� ����
        }
    }
    
    /* ȸ�� Ż�� ���� Ȯ�� �޼��� */
    @Override
    public boolean isMemberWithdrawn(String memberId) throws Exception {
        // memberId�� �̿��Ͽ� ȸ���� Ż�� ���θ� ��ȸ�մϴ�.
        Boolean withdrawal = membermapper.isMemberWithdrawn(memberId);
        // withdrawal�� null�̸� false�� ��ȯ�ϵ��� ó���մϴ�.
        return withdrawal != null && withdrawal;
    }
    
    /* �ֹ��� ���� */
   @Override
   public MemberVO getMemberInfo(String memberId) {
      
      return membermapper.getMemberInfo(memberId);
      
   }
   
    // Kakao �α����� ���� �޼���
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
            sb.append("&client_id=7348caf219d2fff8532a8961c20f78ab"); // ������ �߱޹��� key
            sb.append("&redirect_uri=http://localhost:8080/member/easyGeneral"); // ������ ������ �ּ�
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
            return;
        }

        MemberVO existingMember = membermapper.getMemberByEmail(kakaoVO.getK_email());

        if (existingMember == null) {
            try {
                membermapper.saveKakaoUserInfo(kakaoVO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if ("Y".equals(existingMember.getWithdrawal())) {
                System.out.println("Ż��� ȸ���Դϴ�: " + existingMember.getWithdrawalMessage());
                return;
            }
            performLogin(existingMember, request.getSession());
        }
    }

    
    private void performLogin(MemberVO member, HttpSession session) {
        session.setAttribute("member", member);
        System.out.println("Logged in user: " + member.getMemberId());
    }
    
    /* �̸����� �������� ȸ�� ���� ��ȸ */
    @Override
    public MemberVO getMemberByEmail(String memberMail) {
        return membermapper.getMemberByEmail(memberMail);
    }

    // �̸��� �ߺ� Ȯ��
    @Override
    public int checkEmailDuplicate(String memberMail) {
        int count = membermapper.checkEmailDuplicate(memberMail);
        logger.info("Checking email duplication for: {}, Count: {}", memberMail, count); // �α� �߰�
        return count;
    }

    // ���̵� ã��
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
       // ��й�ȣ ������Ʈ
       String encodedPassword = passwordEncoder.encode(newPassword);
       membermapper.updatePasswordByEmail(memberMail, encodedPassword);
   }
   
   
   // ��й�ȣ ã�� - �ӽ� ��й�ȣ ���� �� �̸��� �߼�
   @Override
   @Transactional
   public void saveTemporaryPassword(String memberMail, String temporaryPassword) {
       // ��й�ȣ ������Ʈ
       membermapper.updatePasswordByEmail(memberMail, passwordEncoder.encode(temporaryPassword));

       // �̸��� �߼�
       sendTemporaryPasswordEmail(memberMail, temporaryPassword);
   }


    // �ӽ� ��й�ȣ ����
    private String generateTemporaryPassword() {
        // �ӽ� ��й�ȣ�� ������ ���ڿ� ����
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // �ӽ� ��й�ȣ ������
        StringBuilder temporaryPassword = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < TEMP_PASSWORD_LENGTH; i++) {
            temporaryPassword.append(characters.charAt(random.nextInt(characters.length())));
        }

        return temporaryPassword.toString();
    }

    // �ӽ� ��й�ȣ �̸��� �߼�
    private void sendTemporaryPasswordEmail(String memberMail, String temporaryPassword) {
        // �̸��� ����
        String subject = "Temporary Password";
        String content = "Your temporary password is: " + temporaryPassword;

        // �̸��� �߼�
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