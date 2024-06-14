<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
    body {
        display: flex;
        min-height: 100vh;
        margin: 0;
        font-family: Arial, sans-serif;
        box-sizing: border-box; /* Ensure padding and border are included in width calculations */
    }
    .container {
        display: flex;
        width: 100%;
    }

    .main-content {
        flex: 1;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        padding: 20px; /* Add padding for spacing */
    }
    .page-intro {
        text-align: center;
    }
    .page-intro img {
        display: block;
        margin: 10px 0;
        max-width: 100%; /* Ensure images are responsive */
        height: auto; /* Maintain aspect ratio */
    }
    ul {
        list-style-type: none;
        padding: 0;
        text-align: center;
    }
    ul li {
        margin: 5px 0;
    }
</style>
</head>
<body>
    <div class="container">

        <div class="main-content">
            <%@ include file="/WEB-INF/views/includes/header.jsp"%>

            <div class="page-intro">
                <img src='<%=request.getContextPath()%>/resources/img/orangeMembers/2.jpg' alt="123213">
                <img src='<%=request.getContextPath()%>/resources/img/orangeMembers/1.jpg' alt="123213">
            </div>

            <ul>
                <li>유의사항</li>
                <li>오렌지멤버스 서비스 연회비는 3만원이며, 가입 시 등록한 신용카드로 연 1회 정기 결제됩니다.</li>
                <li>연회비 결제 시, 가입 혜택 30,000 포인트가 즉시 적립됩니다.</li>
                <li>매월 1일 카카오톡 알림톡을 통해, 쿠폰 다운로드 링크가 발송됩니다. 카카오톡 미설치자의 경우, LMS를 통해 발송됩니다.</li>
                <li>쿠폰은 상황에 따라 변경될 수 있습니다.</li>
                <li>일부 프로모션 품목은 혜택이 적용되지 않을 수 있습니다.</li>
            </ul>
            
            <%@ include file="/WEB-INF/views/includes/footer.jsp"%>
        </div>
    </div>
</body>
</html>
