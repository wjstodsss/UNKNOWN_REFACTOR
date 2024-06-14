<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기 완료</title>
</head>
<body>
	<h2>비밀번호 찾기 완료</h2>
	<form action="/member/findPasswordComplete" method="post">
		<label for="verificationCode">인증 코드:</label> <input type="text"
			id="verificationCode" name="verificationCode" required>
		<button type="submit">인증번호 확인</button>
	</form>
	<c:if test="${not empty message}">
		<p style="display: none;" id="message">${message}</p>
	</c:if>
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
	<p>
		<a href="/member/login">로그인 페이지로 이동</a>
	</p>
</body>
</html>
