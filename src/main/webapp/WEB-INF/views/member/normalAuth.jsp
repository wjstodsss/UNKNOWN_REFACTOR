<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link rel="stylesheet" href="/resources/css/member/normalAuth.css">
</head>
<body>

   <%@ include file="/WEB-INF/views/includes/header.jsp"%>
   <section id="contents" class="container" style="padding-bottom: 0px;">
      <form name="reqKMCISForm" method="post"
         action="https://www.kmcert.com/kmcis/web/kmcisReq.jsp"
         target="KMCISWindow">
         <input type="hidden" name="tr_cert" value> <input
            type="hidden" name="tr_url" value>
      </form>
      <div class="join-certified frame-full">
         <div class="frame-sm">
            <div class="certified-wrap">
               <div class="certified-title">
                  <h2>회원가입</h2>
                  <p>본인인증</p>
                  <span>팔닭 회원가입을 위해 본인인증을 진행해주세요.</span>
               </div>
               <div class="certified-btn-wrap text-center">
                  <button class="certified-btn" onclick="sendEmailVerification()">이메일
                     인증</button>
               </div>
               <div class="sns-certified-wrap">
                  <div class="sns-certified-title text-center">
                     <p>SNS 간편 회원가입</p>
                  </div>
                  <div class="sns-login">
                     <ul class="sns-login-list">
                        <li><a href="javascript:loginWithKakao()"><img
                              src="/resources/img/login/kakao.png" alt="카카오 로그인" class="sns-icon"></a></li>
                        <li><a href="javascript:loginWithNaver()"><img
                              src="/resources/img/login/naver.png" alt="네이버 로그인" class="sns-icon"></a></li>
                        <li><a href="javascript:loginWithApple()"><img
                              src="/resources/img/login/apple.png" alt="애플 로그인" class="sns-icon"></a></li>
                     </ul>
                  </div>
               </div>
            </div>
         </div>
      </div>

      <%@ include file="/WEB-INF/views/includes/footer.jsp"%>

      <script type="text/javascript"
         src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
      <script type="text/javascript">
         // 카카오 초기화
         Kakao.init('c9f402725d734d99ec1ba008a987b952'); // 발급받은 앱 키

         function sendEmailVerification() {
            // 이메일 인증 버튼 클릭 시 mail.jsp로 이동
            window.open("mail", "_blank",
                  "width=600, height=400, top=100, left=100");
         }

         function setEmail(email) {
            document.querySelector(".mail_input").value = email; // join.jsp의 이메일 입력란에 값을 설정
         }

         function loginWithNaver() {
            // 네이버 로그인 로직 추가
            alert("네이버 로그인을 구현하세요.");
         }

         function loginWithApple() {
            // 애플 로그인 로직 추가
            alert("애플 로그인을 구현하세요.");
         }

         function loginWithKakao() {
            Kakao.Auth.authorize({
               redirectUri: 'http://localhost:8080/member/easyGeneral'
            });
         }
      </script>
   </section>
</body>
</html>
