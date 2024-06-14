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
<title>Welcome Unknown</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/main.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/main_content.css">
<script src="${pageContext.request.contextPath}/resources/js/slide.js"></script>
</head>
<body>
	<%@ include file="/WEB-INF/views/includes/header.jsp"%>

	<div class="wrap">
		<div class="content_area">
			<div class="slideshow-container">
				<div class="mySlides fade">
					<img
						src="${pageContext.request.contextPath}/resources/img/slides/1.png"
						alt="Slide 1">
				</div>
				<div class="mySlides fade">
					<img
						src="${pageContext.request.contextPath}/resources/img/slides/2.png"
						alt="Slide 2">
				</div>
				<div class="mySlides fade">
					<img
						src="${pageContext.request.contextPath}/resources/img/slides/3.png"
						alt="Slide 3">
				</div>
				<div class="mySlides fade">
					<img
						src="${pageContext.request.contextPath}/resources/img/slides/4.png"
						alt="Slide 4">
				</div>
				<div class="mySlides fade">
					<img
						src="${pageContext.request.contextPath}/resources/img/slides/5.png"
						alt="Slide 5">
				</div>
				<div class="mySlides fade">
					<img
						src="${pageContext.request.contextPath}/resources/img/slides/6.png"
						alt="Slide 6">
				</div>
				<div class="mySlides fade">
					<img
						src="${pageContext.request.contextPath}/resources/img/slides/7.png"
						alt="Slide 7">
				</div>
				<div class="mySlides fade">
					<img
						src="${pageContext.request.contextPath}/resources/img/slides/8.png"
						alt="Slide 8">
				</div>
				<!-- Next and previous buttons -->
				<a class="prev" onclick="plusSlides(-1)">&#10094;</a> <a
					class="next" onclick="plusSlides(1)">&#10095;</a>
			</div>
			<br>
			<!-- The dots/circles -->
			<div style="text-align: center">
				<span class="dot" onclick="currentSlide(1)"></span> <span
					class="dot" onclick="currentSlide(2)"></span> <span class="dot"
					onclick="currentSlide(3)"></span> <span class="dot"
					onclick="currentSlide(4)"></span> <span class="dot"
					onclick="currentSlide(5)"></span> <span class="dot"
					onclick="currentSlide(6)"></span> <span class="dot"
					onclick="currentSlide(7)"></span> <span class="dot"
					onclick="currentSlide(8)"></span>
			</div>

			<!-- 상품 정보 표시 영역 -->
			<div class="product-container">
				<h2 class="product-MDrecommend">뭘 사야 할 지 모르겠다면? MD 추천 상품</h2>
				<c:forEach var="item" items="${productList}">
					<div class="product-card">
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
						<div class="product-info">
							<h3>${item.itemName}</h3>
							<p>${item.brandName}</p>
							<div class="price-info">
								<c:if test="${item.itemDiscount > 0}">
									<p class="discount">
										<fmt:formatNumber value="${item.itemDiscount * 100}"
											type="number" maxFractionDigits="0" />%
									</p>
								</c:if>
								<p class="price">
									<fmt:formatNumber
										value="${custom:truncatePrice(item.itemPrice * (1 - item.itemDiscount))}"
										pattern="#,###" />원
								</p>
								<c:if test="${item.itemDiscount > 0}">
									<p class="original-price">
										<fmt:formatNumber
											value="${custom:truncatePrice(item.itemPrice)}"
											pattern="#,###" />원
									</p>
								</c:if>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
			<div class="product-container">
				<h2 class="product-MDrecommend">${member.memberId}님 이런 상품은 어때요?</h2>
				<c:forEach var="item" items="${bottomRankedItems}"
					varStatus="status">
					<c:if test="${status.index < 4}">
						<div class="product-card">
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
							<div class="product-info">
								<h3>${item.itemName}</h3>
								<p>${item.brandName}</p>
								<div class="price-info">
									<c:if test="${item.itemDiscount > 0}">
										<p class="discount">
											<fmt:formatNumber value="${item.itemDiscount * 100}"
												type="number" maxFractionDigits="0" />%
										</p>
									</c:if>
									<p class="price">
										<fmt:formatNumber
											value="${custom:truncatePrice(item.itemPrice * (1 - item.itemDiscount))}"
											pattern="#,###" />원
									</p>
									<c:if test="${item.itemDiscount > 0}">
										<p class="original-price">
											<fmt:formatNumber
												value="${custom:truncatePrice(item.itemPrice)}"
												pattern="#,###" />원
										</p>
									</c:if>
								</div>
							</div>
						</div>
					</c:if>
				</c:forEach>
			</div>

			<div class="product-container">
				<h2 class="product-MDrecommend">인기 급상승! 랭킹 TOP 4</h2>
				<c:forEach var="item" items="${topSellingItems}" varStatus="status">
					<c:if test="${status.index < 4}">
						<div class="product-card">
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
							<div class="product-info">
								<h3>${item.itemName}</h3>
								<p>${item.brandName}</p>
								<div class="price-info">
									<c:if test="${item.itemDiscount > 0}">
										<p class="discount">
											<fmt:formatNumber value="${item.itemDiscount * 100}"
												type="number" maxFractionDigits="0" />%
										</p>
									</c:if>
									<p class="price">
										<fmt:formatNumber
											value="${custom:truncatePrice(item.itemPrice * (1 - item.itemDiscount))}"
											pattern="#,###" />원
									</p>
									<c:if test="${item.itemDiscount > 0}">
										<p class="original-price">
											<fmt:formatNumber
												value="${custom:truncatePrice(item.itemPrice)}"
												pattern="#,###" />원
										</p>
									</c:if>
								</div>
							</div>
						</div>
					</c:if>
				</c:forEach>
			</div>


			<div class="product-container">
				<h2 class="product-MDrecommend">금주의 BEST 신상품✨</h2>
				<c:forEach var="item" items="${bestNewItems}" varStatus="status">
					<c:if test="${status.index < 4}">
						<div class="product-card">
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
							<div class="product-info">
								<h3>${item.itemName}</h3>
								<p>${item.brandName}</p>
								<div class="price-info">
									<c:if test="${item.itemDiscount > 0}">
										<p class="discount">
											<fmt:formatNumber value="${item.itemDiscount * 100}"
												type="number" maxFractionDigits="0" />%
										</p>
									</c:if>
									<p class="price">
										<fmt:formatNumber
											value="${custom:truncatePrice(item.itemPrice * (1 - item.itemDiscount))}"
											pattern="#,###" />원
									</p>
									<c:if test="${item.itemDiscount > 0}">
										<p class="original-price">
											<fmt:formatNumber
												value="${custom:truncatePrice(item.itemPrice)}"
												pattern="#,###" />원
										</p>
									</c:if>
								</div>
							</div>
						</div>
					</c:if>
				</c:forEach>
			</div>

			<div class="product-container">
				<h2 class="product-MDrecommend">간편하게 먹자! 요즘 대세 쫄깃 닭다리살</h2>
				<c:forEach var="item" items="${cateCode1402Items}"
					varStatus="status">
					<c:if test="${status.index < 4}">
						<div class="product-card">
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
							<div class="product-info">
								<h3>${item.itemName}</h3>
								<p>${item.brandName}</p>
								<div class="price-info">
									<c:if test="${item.itemDiscount > 0}">
										<p class="discount">
											<fmt:formatNumber value="${item.itemDiscount * 100}"
												type="number" maxFractionDigits="0" />%
										</p>
									</c:if>
									<p class="price">
										<fmt:formatNumber
											value="${custom:truncatePrice(item.itemPrice * (1 - item.itemDiscount))}"
											pattern="#,###" />원
									</p>
									<c:if test="${item.itemDiscount > 0}">
										<p class="original-price">
											<fmt:formatNumber
												value="${custom:truncatePrice(item.itemPrice)}"
												pattern="#,###" />원
										</p>
									</c:if>
								</div>
							</div>
						</div>
					</c:if>
				</c:forEach>
			</div>

			<div class="product-container">
				<h2 class="product-MDrecommend">뭘 살지 고민된다면, 추천 패키지</h2>
				<c:forEach var="item" items="${packageItems}">
					<div class="product-card">
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
						<div class="product-info">
							<h3>${item.itemName}</h3>
							<p>${item.brandName}</p>
							<div class="price-info">
								<c:if test="${item.itemDiscount > 0}">
									<p class="discount">
										<fmt:formatNumber value="${item.itemDiscount * 100}"
											type="number" maxFractionDigits="0" />%
									</p>
								</c:if>
								<p class="price">
									<fmt:formatNumber
										value="${custom:truncatePrice(item.itemPrice * (1 - item.itemDiscount))}"
										pattern="#,###" />원
								</p>
								<c:if test="${item.itemDiscount > 0}">
									<p class="original-price">
										<fmt:formatNumber
											value="${custom:truncatePrice(item.itemPrice)}"
											pattern="#,###" />원
									</p>
								</c:if>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>

			<div class="product-container">
				<h2 class="product-MDrecommend">NO.1 베스트셀러 [맛있닭] 🏃‍♀️</h2>
				<c:forEach var="item" items="${brandId2Items}">
					<div class="product-card">
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
						<div class="product-info">
							<h3>${item.itemName}</h3>
							<p>${item.brandName}</p>
							<div class="price-info">
								<c:if test="${item.itemDiscount > 0}">
									<p class="discount">
										<fmt:formatNumber value="${item.itemDiscount * 100}"
											type="number" maxFractionDigits="0" />%
									</p>
								</c:if>
								<p class="price">
									<fmt:formatNumber
										value="${custom:truncatePrice(item.itemPrice * (1 - item.itemDiscount))}"
										pattern="#,###" />원
								</p>
								<c:if test="${item.itemDiscount > 0}">
									<p class="original-price">
										<fmt:formatNumber
											value="${custom:truncatePrice(item.itemPrice)}"
											pattern="#,###" />원
									</p>
								</c:if>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
		</div>

			<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
</body>
</html>
