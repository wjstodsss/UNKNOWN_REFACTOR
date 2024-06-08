<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>브랜드 선택</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h1>브랜드 선택</h1>
        <table class="table">
            <thead>
                <tr>
                    <th>브랜드 ID</th>
                    <th>브랜드 이름</th>
                    <th>선택</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>1</td>
                    <td>BigBrand</td>
                    <td><button class="btn btn-primary" onclick="selectBrand('1', 'BigBrand')">선택</button></td>
                </tr>
                <tr>
                    <td>2</td>
                    <td>Speed</td>
                    <td><button class="btn btn-primary" onclick="selectBrand('2', 'Speed')">선택</button></td>
                </tr>
                <tr>
                    <td>3</td>
                    <td>Dream</td>
                    <td><button class="btn btn-primary" onclick="selectBrand('3', 'Dream')">선택</button></td>
                </tr>
                <!-- 필요한 만큼 브랜드 데이터 추가 -->
            </tbody>
        </table>
    </div>

    <script>
    function selectBrand(id, name) {
        const brandData = { id: id, name: name };
        window.opener.postMessage(brandData, window.location.origin);
        window.close();
    }
    
    brandWindow.onload = function() {
        brandWindow.resizeTo(width, height);
        brandWindow.moveTo(left, top);
    };
    </script>
</body>
</html>
