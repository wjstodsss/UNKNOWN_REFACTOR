<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <script type="text/javascript">
        window.onload = function() {
            /* Check if errorMessage exists and display it */
            const errorMessage = "<c:out value='${errorMessage}'/>";
            if (errorMessage) {
                alert(errorMessage);
            }
        };
    </script>
</head>
<body>
    <h1>죄송합니다.</h1>
    <p>${errorMessage}</p>
    <c:choose>
        <c:when test="${not empty previousUrl}">
            <a href="${previousUrl}">이전페이지로 돌아가기</a>
        </c:when>
        <c:otherwise>
            <a href="/dashboard/main">관리자 대시보드</a>
        </c:otherwise>
    </c:choose>
</body>
</html>
