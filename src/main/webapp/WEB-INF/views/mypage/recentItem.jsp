<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/tld/custom.tld" prefix="custom"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Recent Items</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mypage/recentItem.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mypage/main.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/includes/header.jsp"%>

    <div class="wrap">
        <%@ include file="/WEB-INF/views/includes/leftmenu.jsp"%>

        <div class="detail_area">
            <h2 class="title">최근 본 상품</h2>
            <div class="product-list">
                <c:if test="${empty recentItems}">
                    <p>최근 본 상품이 없습니다.</p>
                </c:if>
                <c:if test="${not empty recentItems}">
                    <c:forEach var="item" items="${recentItems}">
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
                </c:if>
            </div>
        </div>
    </div>

    <%@ include file="/WEB-INF/views/includes/footer.jsp"%>
</body>
</html>
