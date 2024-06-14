<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/tld/custom.tld" prefix="custom"%> <!-- 커스텀 태그 라이브러리 추가 -->

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/product/list.css">
<title>상품 리스트</title>
</head>
<body>
    <%@ include file="/WEB-INF/views/includes/header.jsp"%>
    <div class="content_area">

        <div class="title_wrap">
            <h2>
                <c:choose>
                    <c:when test="${1100 <= cateCode && cateCode < 1200}">
                        닭가슴살
                    </c:when>
                    <c:when test="${1200 <= cateCode && cateCode < 1300}">
                        도시락·볶음밥
                    </c:when>
                    <c:when test="${1300 <= cateCode && cateCode < 1400}">
                        음료·차·프로틴
                    </c:when>
                    <c:when test="${1400 <= cateCode && cateCode < 1500}">
                        즉석 간편식
                    </c:when>
                    <c:when test="${1500 <= cateCode && cateCode < 1600}">
                        소고기
                    </c:when>
                    <c:when test="${1600 <= cateCode && cateCode < 1700}">
                        견과·고구마·감자
                    </c:when>
                    <c:when test="${1700 <= cateCode && cateCode < 1800}">
                        샐러드·과일
                    </c:when>
                    <c:when test="${1800 <= cateCode && cateCode < 1900}">
                        계란·난백·콩
                    </c:when>
                    <c:when test="${1900 <= cateCode && cateCode < 2000}">
                        닭안심살
                    </c:when>
                    <c:when test="${2000 <= cateCode && cateCode < 2100}">
                        과자·간식·떡
                    </c:when>
                    <c:otherwise>
                        상품 리스트
                    </c:otherwise>
                </c:choose>
            </h2>
        </div>

        <div class="itemlist_wrap">
            <p class="itemCount">총 ${fn:length(items)}개 상품이 있습니다.</p>

            <div class="product-list">
                <c:forEach var="item" items="${items}">
                    <div class="product-item">
                        <a href="${pageContext.request.contextPath}/goodsDetail/${item.itemId}">
                            <c:choose>
                                <c:when test="${not empty item.imageList}">
                                    <c:forEach var="image" items="${item.imageList}" begin="0" end="0">
                                        <img
                                            src="${pageContext.request.contextPath}/product/display?fileName=${fn:replace(image.uploadPath, '\\', '/')}/${image.uuid}_${image.fileName}"
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
                        <span class="product-name">
                            <c:out value="${item.itemName}" />
                        </span>
                        <div class="product-details">
                            <c:choose>
                                <c:when test="${item.itemDiscount > 0}">
                                    <p class="discount-rate">
                                        <fmt:formatNumber value="${item.itemDiscount * 100}" type="number" maxFractionDigits="0" />%
                                    </p>
                                    <p class="discount-price">
                                        <fmt:formatNumber value="${custom:truncatePrice(item.itemPrice * (1 - item.itemDiscount))}" pattern="#,###" />원
                                    </p>
                                    <p class="original-price">
                                        <fmt:formatNumber value="${custom:truncatePrice(item.itemPrice)}" pattern="#,###" />원
                                    </p>
                                </c:when>
                                <c:otherwise>
                                    <p class="discount-price">
                                        <fmt:formatNumber value="${custom:truncatePrice(item.itemPrice)}" pattern="#,###" />원
                                    </p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <%@ include file="/WEB-INF/views/includes/footer.jsp"%>
</body>
</html>
