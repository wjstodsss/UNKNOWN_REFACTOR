<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>아이디 찾기 결과</title>
<style>
    ul {
        list-style-type: none;
        padding: 0;
    }

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
        align-items: center;
    }

    .find_area h2 {
        margin-bottom: 20px;
        text-align: center;
        width: 100%;
    }

    .btn-primary {
        background-color: #007bff;
        border: none;
        color: white;
        padding: 10px 20px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 16px;
        margin: 4px 2px;
        cursor: pointer;
        border-radius: 5px;
    }

    .btn-primary:hover {
        background-color: #0056b3;
    }

    .member-id {
        background-color: #e9ecef;
        padding: 5px 10px;
        border-radius: 5px;
        margin: 20px 0;
    }

    .btn-group {
        display: flex;
        gap: 10px;
        margin-top: 20px;
    }
</style>
</head>
<body>
    <%@ include file="/WEB-INF/views/includes/header.jsp"%>
    <div class="wrap">
        <div class="find_area">
            <h2>아이디 찾기 결과</h2>
            <c:if test="${not empty members}">
                <ul>
                    <c:forEach var="member" items="${members}">
                        <li class="member-id">${member.memberId}</li>
                    </c:forEach>
                </ul>
            </c:if>
            <c:if test="${empty members}">
                <p>등록된 회원이 없습니다.</p>
            </c:if>
            <div class="btn-group">
                <button type="button" class="btn btn-primary"
                    onclick="location.href='/member/login'">로그인하기</button>
                <button type="button" class="btn btn-primary"
                    onclick="location.href='/main'">메인페이지</button>
            </div>
        </div>
    </div>
    <%@ include file="/WEB-INF/views/includes/footer.jsp"%>
</body>
</html>
