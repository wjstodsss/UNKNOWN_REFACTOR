<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Order List</title>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/css/mypage/orderList.css">
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/css/mypage/main.css">
    
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>
    function setDateRange(event, months) {
        // 모든 버튼의 active 클래스 제거
        var buttons = document.querySelectorAll('.date_range_button');
        buttons.forEach(function(button) {
            button.classList.remove('active');
        });

        // 클릭된 버튼에 active 클래스 추가
        var clickedButton = event.target;
        clickedButton.classList.add('active');

        // 날짜 범위 설정
        var endDate = new Date();
        endDate.setDate(endDate.getDate() + 1); // 오늘 날짜의 다음 날로 설정
        var startDate = new Date();
        startDate.setMonth(startDate.getMonth() - months);

        document.getElementById('startDate').value = startDate.toISOString().split('T')[0];
        document.getElementById('endDate').value = endDate.toISOString().split('T')[0];
    }

    // Set initial date range based on server-side values
    document.addEventListener('DOMContentLoaded', function() {
        var endDate = new Date().toISOString().split('T')[0];
        var startDate = new Date();
        startDate.setMonth(startDate.getMonth() - 1); // 현재 날짜에서 한 달 전으로 설정
        startDate = startDate.toISOString().split('T')[0];

        document.getElementById('startDate').value = startDate;
        document.getElementById('endDate').value = endDate;

        // 초기에 설정된 날짜 범위에 맞는 버튼에 active 클래스 추가
        var initialRange = 1; // 기본적으로 1개월로 설정
        var buttons = document.querySelectorAll('.date_range_button');
        buttons.forEach(function(button) {
            if (parseInt(button.getAttribute('onclick').match(/\d+/)[0]) === initialRange) {
                button.classList.add('active');
            }
        });
    });

    function cancelOrder(orderId) {
        if (confirm("정말 주문을 취소하시겠습니까?")) {
            $.ajax({
                url: '${pageContext.request.contextPath}/mypage/cancelOrder',
                type: 'POST',
                data: {
                    orderId: orderId
                },
                success: function(response) {
                    alert("주문이 취소되었습니다.");
                    location.reload(); // 페이지 새로고침
                },
                error: function(xhr, status, error) {
                    alert("주문 취소에 실패했습니다. 다시 시도해주세요.");
                }
            });
        }
    }
</script>

</head>
<body>
    <%@ include file="/WEB-INF/views/includes/header.jsp"%>

    <div class="wrap">
        <%@ include file="/WEB-INF/views/includes/leftmenu.jsp"%>

        <div class="detail_area">
            <h1>주문내역</h1>
            <form id="dateForm" method="get" action="${pageContext.request.contextPath}/mypage/orderList">
                <label for="startDate"></label> 
                <input type="date" id="startDate" name="startDate"> 
                <label for="endDate">~</label>
                <input type="date" id="endDate" name="endDate" value="${endDate}">
                <button type="submit" class="filter_button">조회하기</button>
            </form>
            <div class="filter_buttons">
                <button type="button" onclick="setDateRange(event, 1)" class="date_range_button">1개월</button>
                <button type="button" onclick="setDateRange(event, 3)" class="date_range_button">3개월</button>
                <button type="button" onclick="setDateRange(event, 6)" class="date_range_button">6개월</button>
                <button type="button" onclick="setDateRange(event, 12)" class="date_range_button">12개월</button>
            </div>
            <c:if test="${empty orders}">
                <p>No orders found.</p>
            </c:if>
            <c:if test="${not empty orders}">
                <div class="order_list">
                    <c:forEach var="order" items="${orders}">
                        <div class="order_item">
                            <div class="order_header">
                                <span class="order_date"><fmt:formatDate value="${order.orderDate}" pattern="yyyy.MM.dd" /></span> 
                                <span class="order_id">${order.orderId}</span> 
                                <span class="order_address">[${order.memberAddr1}] ${order.memberAddr2} ${order.memberAddr3}</span>
                                <button class="order_detail_button">주문상세</button>
                                <button class="order_cancel_button" onclick="cancelOrder('${order.orderId}')">주문취소</button>
                            </div>
                            <c:forEach var="item" items="${order.orders}">
                                <div class="order_body">
                                    <div class="order_image">
                                        <img src="${pageContext.request.contextPath}/display?fileName=${fn:replace(item.imagePath, '\\', '/')}" alt="${item.itemName}">
                                    </div>
                                    <div class="order_info">
                                        <p class="order_name">${item.itemName}</p>
                                        <p class="order_status">${order.orderState}</p>
                                    </div>
                                    <div class="order_price">
                                        <p><fmt:formatNumber value="${item.itemPrice}" pattern="#,### 원" /></p>
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
