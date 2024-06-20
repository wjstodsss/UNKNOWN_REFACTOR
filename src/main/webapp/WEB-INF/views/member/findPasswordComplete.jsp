<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기 완료</title>
<style>
    .find_area {
        background: white;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
        max-width: 500px;
        min-height: 300px;
        margin: 50px auto;
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-items: center;
    }

    h2 {
        margin-bottom: 80px;
    }

    form {
        flex-direction: column;
        align-items: center;
    }

    form label {
        margin-right: 10px;
    }

    form input {
        margin-bottom: 20px;
        padding: 10px;
        border-radius: 5px;
        border: 1px solid #ccc;
    }

    form button {
        padding: 10px 20px;
        border: none;
        border-radius: 5px;
        background-color: #007bff;
        color: white;
        cursor: pointer;
    }

    form button:hover {
        background-color: #0056b3;
    }

    .btn-secondary {
        margin-top: 20px;
        padding: 10px 20px;
        border: none;
        border-radius: 5px;
        background-color: #6c757d;
        color: white;
        cursor: pointer;
    }

    .btn-secondary:hover {
        background-color: #5a6268;
    }
</style>
</head>
<body>
    <%@ include file="/WEB-INF/views/includes/header.jsp"%>

    <div class="wrap">
        <div class="find_area">
            <h2>비밀번호 찾기 완료</h2>
            <form action="/member/findPasswordComplete" method="post">
                <label for="verificationCode">인증 코드:</label>
                <input type="text" id="verificationCode" name="verificationCode" required>
                <button type="submit">인증번호 확인</button>
            </form>
            <c:if test="${not empty message}">
                <p style="display: none;" id="message">${message}</p>
            </c:if>
            <button class="btn-secondary" onclick="location.href='/member/login'">로그인 페이지로 이동</button>
        </div>
    </div>
    <%@ include file="/WEB-INF/views/includes/footer.jsp"%>

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
