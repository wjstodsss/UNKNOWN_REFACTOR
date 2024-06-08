<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Page</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mypage/main.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/includes/header.jsp"%>

    <div class="wrap">
        <div class="content_area">
            <div class="top_section">
                <div class="additional_info">
                    <div class="user_info">
                        <img src="${pageContext.request.contextPath}/resources/img/mypage/manager.png" alt="회원 등급 이미지">
                        <div class="user_details">
                            <span>${member.memberId}님 반갑습니다</span>
                            <div class="button-container">
                                <div class="custom-button">1% 적립</div>
                                <div class="custom-button">나의 식단 유형 테스트 하기</div>
                            </div>
                            <p class="req_amount">100,000원 더 구매 시, 닭과장 진급!</p>
                        </div>
                    </div>
                    <div class="membership_button">
                        <button class="orange_member">
                            오렌지멤버스<br>3만 포인트 받기
                        </button>
                    </div>
                    <div class="order_status">
                        <div class="status_box">
                            <p>주문/배송</p>
                            <span>0건</span>
                        </div>
                        <div class="status_box">
                            <p>나의 쿠폰</p>
                            <span>1개</span>
                        </div>
                        <div class="status_box">
                            <p>포인트</p>
                            <span>${member.point}P</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="detail_area">
                <div class="order_summary">
                    <h3>최근 주문내역</h3>
                    <p>최근 3개월 내에 주문내역이 없습니다.</p>
                </div>
                <div class="recent_viewed_products">
                    <h3>최근 본 상품</h3>
                    <p>최근 본 상품이 없습니다.</p>
                </div>
                <div class="reviews">
                    <h3>평한상품</h3>
                    <p>평한 상품이 없습니다.</p>
                </div>
                <div class="product_reviews">
                    <h3>상품후기</h3>
                    <p>작성 가능한 후기가 없습니다.</p>
                </div>
                <div class="one_to_one_inquiry">
                    <h3>1:1 문의</h3>
                    <p>문의하신 사항이 없습니다.</p>
                </div>
            </div>
            <%@ include file="/WEB-INF/views/includes/leftmenu.jsp"%>
        </div>
    </div>

    <%@ include file="/WEB-INF/views/includes/footer.jsp"%>

</body>
</html>
