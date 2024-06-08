<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
</head>
<body>
	<h2>비밀번호 찾기</h2>
	<h3>이메일로 찾기</h3>
	<form action="/member/findPassword" method="post">
		<label for="memberMail">Email:</label> <input type="email"
			id="memberMail" name="memberMail" required>
		<button type="submit">인증번호 요청</button>
	</form>

	<c:if test="${not empty message}">
		<p style="display: none;" id="message">${message}</p>
	</c:if>

	<br>
	<button onclick="location.href='/member/login'">로그인 화면으로 이동</button>
	<button onclick="location.href='/main'">메인 페이지로 이동</button>

	<script>
        window.onload = function() {
            var messageElement = document.getElementById("message");
            if (messageElement) {
                var message = messageElement.innerText;
                if (message) {
                    alert(message);
                }
            }
        }
    </script>
</body>
</html>
