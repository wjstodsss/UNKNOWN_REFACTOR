<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mypage/leftmenu.css">

<div class="left_menu">
    <h4>주문관리</h4>
    <ul>
        <li><a href="${pageContext.request.contextPath}/mypage/orderList">주문내역</a></li>
        <li><a href="${pageContext.request.contextPath}/mypage/cancelList">취소/반품 내역</a></li>
    </ul>
    <h4>혜택관리</h4>
    <ul>
        <li><a href="#">나의 쿠폰</a></li>
        <li><a href="${pageContext.request.contextPath}/mypage/myPoint">포인트</a></li>
    </ul>
    <h4>활동관리</h4>
    <ul>
        <li><a href="${pageContext.request.contextPath}/mypage/recentItem">최근 본 상품</a></li>
        <li><a href="#">찜한 상품</a></li>
        <li><a href="/qna/qna">1:1 문의 내역</a></li>
        <li><a href="/review/review/${member.memberId}">상품후기</a></li>
        <li><a href="#">상품 문의 내역</a></li>
    </ul>
    <h4><a href="${pageContext.request.contextPath}/mypage/orangeMembers">오렌지멤버스</a></h4>
    <h4>회원정보관리</h4>
    <ul>
        <li><a href="${pageContext.request.contextPath}/mypage/address">배송지 관리</a></li>
        <li><a href="${pageContext.request.contextPath}/member/update">정보 수정</a></li>
    </ul>
    <h4>
        <a href="#">고객의 소리</a>
    </h4>
</div>
