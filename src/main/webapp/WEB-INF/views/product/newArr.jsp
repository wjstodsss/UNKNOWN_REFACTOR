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
<link rel="stylesheet" href="../resources/css/product/newArr.css">
<title>신상품</title>
</head>
<body>
    <%@ include file="/WEB-INF/views/includes/header.jsp"%>
    <div class="content_area">

        <div class="new_bnr">
            <img
                src="https://file.rankingdak.com/image/RANK/BANNER/EXPCLIST/20230303/IMG1677ifS816147355.jpg"
                style="width: 1100px;" onerror="fnNoImage(this);">
        </div>

        <div class="title_wrap">
            <h2>신상품</h2>
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
