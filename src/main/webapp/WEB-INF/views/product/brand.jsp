<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>전체 브랜드</title>
<link rel="stylesheet" href="/resources/css/product/brand.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/includes/header.jsp"%>
    <div class="content_area">
        <div class="search-container">
            <span class="search-title">브랜드관</span>
            <div class="search-input-container">
                <input type="text" class="search-input" placeholder="브랜드 명을 검색해보세요">
                <button class="search-button" onclick="filterBrands()"><img src= "https://s3.ap-northeast-2.amazonaws.com/cdn.wecode.co.kr/icon/search.png"></button>
            </div>
        </div>
        <p class="title">전체 브랜드</p>
        <div class="brand-container">
            <c:forEach var="brand" items="${brandList}">
                <div class="brand-item" onclick="redirectToBrand('${brand.brandName}')">
                    <div class="brand-image">
                        <img src="<c:url value='/resources/img/brand/${brand.brandId}.png' />" alt="${brand.brandName}" style="width: 100%; height: 100%; object-fit: cover;">
                    </div>
                    <div class="brand-name">${brand.brandName}</div>
                    <div class="heart-icon"></div>
                </div>
            </c:forEach>
        </div>
    </div>
    <%@ include file="/WEB-INF/views/includes/footer.jsp"%>
    <script>
        function filterBrands() {
            var input = document.querySelector('.search-input').value.toLowerCase();
            var brands = document.querySelectorAll('.brand-item');
            brands.forEach(function(brand) {
                var brandName = brand.querySelector('.brand-name').textContent.toLowerCase();
                if (brandName.includes(input)) {
                    brand.style.display = 'block';
                } else {
                    brand.style.display = 'none';
                }
            });
        }

        function redirectToBrand(brandName) {
            var url = 'http://localhost:8080/search?type=A&keyword=' + encodeURIComponent(brandName);
            window.location.href = url;
        }
    </script>
</body>
</html>
