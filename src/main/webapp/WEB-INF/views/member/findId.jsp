<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>아이디 찾기 결과</title>
</head>
<body>
    <h2>아이디 찾기 결과</h2>
    <c:if test="${not empty members}">
        <ul>
            <c:forEach var="member" items="${members}">
                <li>${member.memberId}</li>
            </c:forEach>
        </ul>
    </c:if>
    <c:if test="${empty members}">
        <p>등록된 회원이 없습니다.</p>
    </c:if>
     <button type="button" class="btn btn-primary" onclick="location.href='/member/login'">로그인하기</button>
  <button type="button" class="btn btn-primary" onclick="location.href='/main'">메인페이지</button>
</body>
</html>