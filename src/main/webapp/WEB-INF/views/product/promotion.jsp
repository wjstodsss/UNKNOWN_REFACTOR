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
<title>Promotion</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/product/promotion.css">

<script>
    function sortItems() {
        let items = document.querySelectorAll('.product-item');
        let itemsArray = Array.prototype.slice.call(items);

        itemsArray.sort(function(a, b) {
            let discountA = parseFloat(a.querySelector('.discount-price').dataset.price);
            let originalA = parseFloat(a.querySelector('.original-price').dataset.price);
            let discountB = parseFloat(b.querySelector('.discount-price').dataset.price);
            let originalB = parseFloat(b.querySelector('.original-price').dataset.price);

            return (originalB - discountB) - (originalA - discountA);
        });

        let container = document.querySelector('.product-list');
        itemsArray.forEach(function(item) {
            container.appendChild(item);
        });
    }

    window.onload = sortItems;
</script>
</head>
<body>
    <%@ include file="/WEB-INF/views/includes/header.jsp"%>
    <div class="content_area">
        <div class="banner">
            <img src="${pageContext.request.contextPath}/resources/img/promotion.png" alt="Promotion Banner">
        </div>
        <div class="itemlist_wrap">
            <div class="product-list">
                <c:forEach var="item" items="${items}">
                    <c:if test="${item.itemDiscount > 0}">
                        <div class="product-item">
                            <a href="${pageContext.request.contextPath}/goodsDetail/${item.itemId}">
                                <c:choose>
                                    <c:when test="${not empty item.imageList}">
                                        <c:forEach var="image" items="${item.imageList}" begin="0" end="0">
                                            <img src="${pageContext.request.contextPath}/product/display?fileName=${fn:replace(image.uploadPath, '\\', '/')}/${image.uuid}_${image.fileName}"
                                                 alt="${item.itemName}">
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${pageContext.request.contextPath}/resources/img/noImage.png"
                                             alt="No Image">
                                    </c:otherwise>
                                </c:choose>
                            </a>
                            <span class="product-name">
                                <c:out value="${item.itemName}" />
                            </span>
                            <div class="product-details">
                                <p class="discount-rate">
                                    <fmt:formatNumber value="${item.itemDiscount * 100}" type="number" maxFractionDigits="0" />%
                                </p>
                                <p class="discount-price" data-price="${item.itemPrice * (1 - item.itemDiscount)}">
                                    <fmt:formatNumber value="${custom:truncatePrice(item.itemPrice * (1 - item.itemDiscount))}" pattern="#,###" />원
                                </p>
                                <p class="original-price" data-price="${item.itemPrice}">
                                    <fmt:formatNumber value="${custom:truncatePrice(item.itemPrice)}" pattern="#,###" />원
                                </p>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>
    </div>
    <%@ include file="/WEB-INF/views/includes/footer.jsp"%>
</body>
</html>
