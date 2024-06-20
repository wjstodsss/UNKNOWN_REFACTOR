<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/resources/css/member/login.css">

<script src="https://code.jquery.com/jquery-3.4.1.js"
   integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
   crossorigin="anonymous"></script>
<script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
<script>
   $(document).ready(function() {
      $("#kakao_login").click(function() {
         window.location.href = "https://kauth.kakao.com/oauth/authorize?client_id=7348caf219d2fff8532a8961c20f78ab&redirect_uri=http://localhost:8080/member/easyGeneral&response_type=code";
      });

      $(".login_button").click(function() {
         $("#login_form").attr("action", "/member/login.do");
         $("#login_form").submit();
      });

      // Load saved ID if exists
      var savedId = localStorage.getItem("savedId");
      if (savedId) {
         $("#memberId").val(savedId);
         $("#save_id").prop("checked", true);
      }

      $("#save_id").change(function() {
         if (this.checked) {
            localStorage.setItem("savedId", $("#memberId").val());
         } else {
            localStorage.removeItem("savedId");
         }
      });

      $("#memberId").on("input", function() {
         if ($("#save_id").is(":checked")) {
            localStorage.setItem("savedId", $(this).val());
         }
      });
   });
</script>
</head>
<body>
   <%@ include file="/WEB-INF/views/includes/header.jsp"%>
   <div class="wrapper">
      <div class="wrap">
         <form id="login_form" method="post">
            <div class="logo_wrap">
               <span>Unknown</span>
            </div>
            <div class="login_wrap">
               <div class="id_wrap">
                  <div class="id_input_box">
                     <input class="id_input" id="memberId" name="memberId">
                  </div>
               </div>
               <div class="pw_wrap">
                  <div class="pw_input_box">
                     <input class="pw_input" name="memberPw" type="password">
                  </div>
               </div>
               <div class="options_wrap">
                  <input type="checkbox" id="auto_login" name="auto_login">
                  <label for="auto_login">자동 로그인</label> 
                  <input type="checkbox" id="save_id" name="save_id"> 
                  <label for="save_id">아이디 저장</label>
               </div>
               <c:if test="${not empty withdrawalMessage}">
                  <div class="login_warn">${withdrawalMessage}</div>
               </c:if>
               <c:if test="${result == 0}">
                  <div class="login_warn">사용자 ID 또는 비밀번호를 잘못 입력하셨습니다.</div>
               </c:if>
               <div class="login_button_wrap">
                  <input type="button" class="login_button" value="로그인">
               </div>
               <div class="links_wrap">
                  <a href="/member/findIdView">아이디 찾기</a> | <a href="/member/findPassword">비밀번호 찾기</a>
               </div>
               <div class="social_login_wrap">
                  <button type="button" class="social_login_button" id="kakao_login">K</button>
                  <button type="button" class="social_login_button" id="naver_login">N</button>
                  <button type="button" class="social_login_button" id="apple_login">A</button>
               </div>
               <div class="simple_signup">
                  <div class="simple_signup">
                     <a href="${pageContext.request.contextPath}/member/normalAuth">
                        <button type="button" class="simple_signup_button">간편회원가입</button>
                     </a>
                  </div>
               </div>
               <div class="icons_wrap">
                  <img src="/resources/img/login/loginbanner.png">
               </div>
            </div>
         </form>
      </div>
   </div>
   <%@ include file="/WEB-INF/views/includes/footer.jsp"%>

</body>
</html>
