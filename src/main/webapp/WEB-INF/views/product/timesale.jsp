<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/tld/custom.tld" prefix="custom"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Time Sale</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/product/timesale.css">

<script>
    function initializeCountdown() {
        const countdownElements = document.querySelectorAll('.countdown');
        countdownElements.forEach(element => {
            const endTime = new Date();
            endTime.setHours(20, 0, 0, 0); // Set end time to 8 PM today
            const timer = setInterval(() => {
                const now = new Date().getTime();
                const distance = endTime - now;
                if (distance < 0) {
                    clearInterval(timer);
                    element.innerHTML = "SALE ENDED";
                    return;
                }
                const hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                const minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
                const seconds = Math.floor((distance % (1000 * 60)) / 1000);
                element.innerHTML = hours + "h " + minutes + "m " + seconds + "s";
            }, 1000);
        });
    }

    document.addEventListener('DOMContentLoaded', initializeCountdown);
</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/includes/header.jsp"%>
	<div class="content_area">
		<div class="banner">
			<img
				src="${pageContext.request.contextPath}/resources/img/timesale.png"
				alt="Time Sale Banner">
		</div>
		<div class="itemlist_wrap">
			<div class="product-list">
				<c:forEach var="item" items="${timeSaleItems}">
					<div class="product-item">
						<a
							href="${pageContext.request.contextPath}/goodsDetail/${item.itemId}">
							<c:choose>
								<c:when test="${not empty item.imageList}">
									<c:forEach var="image" items="${item.imageList}" begin="0"
										end="0">
										<img
											src="${pageContext.request.contextPath}/display?fileName=${fn:replace(image.uploadPath, '\\', '/')}/${image.uuid}_${image.fileName}"
											alt="${item.itemName}">
									</c:forEach>
								</c:when>
								<c:otherwise>
									<img
										src="${pageContext.request.contextPath}/resources/img/noImage.png"
										alt="No Image">
								</c:otherwise>
							</c:choose>
						</a>
						<div class="countdown">Loading...</div>
						<span class="product-name"> <c:out value="${item.itemName}" />
						</span>
						<div class="product-details">
							<p
								class="discount-rate ${item.itemDiscount == 0 ? 'hidden' : ''}">
								<fmt:formatNumber value="${item.itemDiscount * 100}"
									type="number" maxFractionDigits="0" />%
							</p>
							<p class="discount-price"
								data-price="${item.itemPrice * (1 - item.itemDiscount)}">
								<fmt:formatNumber
									value="${custom:truncatePrice(item.itemPrice * (1 - item.itemDiscount))}"
									pattern="#,###" />원
							</p>
							<p
								class="original-price ${item.itemDiscount == 0 ? 'hidden' : ''}"
								data-price="${item.itemPrice}">
								<fmt:formatNumber
									value="${custom:truncatePrice(item.itemPrice)}" pattern="#,###" />원
							</p>
						</div>

					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
</body>
</html>
