<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cancelled Orders</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/css/mypage/cancelList.css">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/css/mypage/main.css">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>
function setDateRange(event, months) {
    var buttons = document.querySelectorAll('.date_range_button');
    buttons.forEach(function(button) {
        button.classList.remove('active');
    });

    var clickedButton = event.target;
    clickedButton.classList.add('active');

    var endDate = new Date();
    var displayedEndDate = new Date(); // 오늘 날짜로 보이게 하기 위한 변수
    var startDate = new Date();
    startDate.setMonth(startDate.getMonth() - months);

    endDate.setDate(endDate.getDate() + 1); // 오늘 날짜의 다음 날로 설정

    document.getElementById('startDate').value = startDate.toISOString().split('T')[0];
    document.getElementById('endDate').value = displayedEndDate.toISOString().split('T')[0];

    // 실제 필터링에 사용할 hidden input 설정
    document.getElementById('realEndDate').value = endDate.toISOString().split('T')[0];
}

document.addEventListener('DOMContentLoaded', function() {
    var endDate = new Date();
    var displayedEndDate = new Date(); // 오늘 날짜로 보이게 하기 위한 변수
    var startDate = new Date();
    startDate.setMonth(startDate.getMonth() - ${selectedRange}); // 선택된 기간에 따라 설정

    endDate.setDate(endDate.getDate() + 1); // 오늘 날짜의 다음 날로 설정

    document.getElementById('startDate').value = startDate.toISOString().split('T')[0];
    document.getElementById('endDate').value = displayedEndDate.toISOString().split('T')[0];

    // 실제 필터링에 사용할 hidden input 설정
    document.getElementById('realEndDate').value = endDate.toISOString().split('T')[0];

    var initialRange = ${selectedRange}; // 서버에서 전달된 선택된 기간
    var buttons = document.querySelectorAll('.date_range_button');
    buttons.forEach(function(button) {
        if (parseInt(button.getAttribute('onclick').match(/\d+/)[0]) === initialRange) {
            button.classList.add('active');
        }
    });

    // 1회만 폼을 자동으로 제출하여 1개월 동안의 취소 내역을 조회
    if (!sessionStorage.getItem('isFormSubmitted')) {
        document.getElementById('dateForm').submit();
        sessionStorage.setItem('isFormSubmitted', 'true');
    }
});
</script>


</head>
<body>
    <%@ include file="/WEB-INF/views/includes/header.jsp"%>
    <div class="wrap">
        <%@ include file="/WEB-INF/views/includes/leftmenu.jsp"%>
        <div class="detail_area">
            <h1>취소/반품 내역</h1>
            <form id="dateForm" method="get"
                action="${pageContext.request.contextPath}/mypage/cancelList">
                <label for="startDate"></label> 
                <input type="date" id="startDate" name="startDate" value="${startDate}"> 
                <label for="endDate">~</label>
                <input type="date" id="endDate" name="endDate" value="${endDate}">
                <input type="hidden" id="realEndDate" name="realEndDate" value="${realEndDate}">
                <button type="submit" class="filter_button">조회하기</button>
            </form>

            <div class="filter_buttons">
                <button type="button" onclick="setDateRange(event, 1)" class="date_range_button">1개월</button>
                <button type="button" onclick="setDateRange(event, 3)" class="date_range_button">3개월</button>
                <button type="button" onclick="setDateRange(event, 6)" class="date_range_button">6개월</button>
                <button type="button" onclick="setDateRange(event, 12)" class="date_range_button">12개월</button>
            </div>
            <c:if test="${empty orders}">
                <p>No canceled orders found.</p>
            </c:if>
            <c:if test="${not empty orders}">
                <div class="order_list">
                    <c:forEach var="order" items="${orders}">
                        <div class="order_item">
                            <div class="order_header">
                                <span class="order_date"><fmt:formatDate
                                        value="${order.orderDate}" pattern="yyyy.MM.dd" /></span>
                                <span class="order_id">${order.orderId}</span>
                                <span class="order_address">[${order.memberAddr1}] ${order.memberAddr2} ${order.memberAddr3}</span>
                                <button class="order_detail_button">주문상세</button>
                            </div>
                            <c:forEach var="item" items="${order.orders}">
                                <div class="order_body">
                                    <div class="order_image">
                                        <img src="${pageContext.request.contextPath}/display?fileName=${fn:replace(item.imagePath, '\\', '/')}"
                                            alt="${item.itemName}">
                                    </div>
                                    <div class="order_info">
                                        <p class="order_name">${item.itemName}</p>
                                        <p class="order_status">${order.orderState}</p>
                                    </div>
                                    <div class="order_price">
                                        <p>
                                            <fmt:formatNumber value="${item.itemPrice}" pattern="#,### 원" />
                                        </p>
                                        <p>${item.itemCount}개</p>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
        </div>
    </div>
    <%@ include file="/WEB-INF/views/includes/footer.jsp"%>
</body>
</html>
