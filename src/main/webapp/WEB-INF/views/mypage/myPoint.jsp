<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Points</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/mypage/main.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/mypage/myPoint.css">
</head>
<body>
	<%@ include file="/WEB-INF/views/includes/header.jsp"%>
	<div class="wrap">
		<%@ include file="/WEB-INF/views/includes/leftmenu.jsp"%>
		
		<div class="detail_area">
			<h2 class="points_title">포인트</h2>
			<div class="points_summary">
				<div class="points_box">
					<p>보유 포인트</p>
					<p class="points_amount">
						<img src="/resources/img/mypage/point-logo.png" alt="포인트 아이콘"
							class="point_icon">${member.point}P</p>
					<p>
						15일 이내 소멸 예정 포인트 <span>0P</span>
					</p>
				</div>
			</div>
			<div class="points_details">
				<table class="points_table">
					<thead>
						<tr>
							<th>날짜</th>
							<th>내용</th>
							<th>지급/차감</th>
							<th>변동 포인트</th>
							<th>유효기간</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><fmt:formatDate value="${member.regDate}"
									pattern="yyyy.MM.dd" /></td>
							<td>회원가입 포인트</td>
							<td>지급</td>
							<td>+5,000</td>
							<td><fmt:formatDate value="${member.regDate}"
									pattern="yyyy.MM.dd" /></td>
						</tr>
						<c:forEach var="order" items="${orderList}">
							<tr>
								<td><fmt:formatDate value="${order.orderDate}"
										pattern="yyyy.MM.dd" /></td>
								<td>사용 포인트</td>
								<td>차감</td>
								<td>-<fmt:formatNumber value="${order.usePoint}" /></td>
								<td><fmt:formatDate value="${order.orderDate}"
										pattern="yyyy.MM.dd" /></td>
							</tr>
						</c:forEach>
						<c:forEach var="orderItem" items="${orderItemList}">
							<tr>
								<td><fmt:formatDate value="${orderItem.orderDate}"
										pattern="yyyy.MM.dd" /></td>
								<td>적립 포인트</td>
								<td>지급</td>
								<td>+<fmt:formatNumber value="${orderItem.earnPoint}" /></td>
								<td><fmt:formatDate value="${orderItem.orderDate}"
										pattern="yyyy.MM.dd" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="pagination">
					<a href="#">&#60;</a> <span>1</span> <a href="#">&#62;</a>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
</body>
</html>
