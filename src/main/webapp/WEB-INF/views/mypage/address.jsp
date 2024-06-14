<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
    .left-menu {
    display : flex;
    
    }
    .detail_area {
        flex: 1;
        padding: 20px;
        height:2018px;
    }
    .detail_area h2 {
        margin-top: 0;
    }
    .add-address-btn {
        background-color: #f60;
        color: white;
        border: none;
        padding: 10px 20px;
        cursor: pointer;
        margin-bottom: 20px;
        display: inline-block;
        float: right;
        margin: 20px 0 20px 0;
    }
    .address-table {
        width: 100%;
        border-collapse: collapse;
    }
    .address-table th, .address-table td {
        border: 1px solid #ddd;
        padding: 10px;
        text-align: left;
    }
    .address-table th {
        background-color: #f2f2f2;
    }
    .manage-links {
        text-align: right;
    }
    .manage-links a {
        margin-right: 10px;
        color: #f60;
        text-decoration: none;
    }
</style>
</head>
<body>
    <%@ include file="/WEB-INF/views/includes/header.jsp"%>
    <div class="wrap">
        <div class="left-menu">
            <%@ include file="/WEB-INF/views/includes/leftmenu.jsp"%>
        
        <div class="detail_area">
        <div class="detail_top">
            <h2>배송지 관리</h2>
            <p>*배송지는 최대 30개까지 등록됩니다.</p>
            <button class="add-address-btn">새 배송지 추가</button>
        </div>
            
            <table class="address-table">
                <thead>
                    <tr>
                        <th>선택</th>
                        <th>받으실 분</th>
                        <th>배송주소</th>
                        <th>연락처</th>
                        <th>관리</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><input type="radio" name="defaultAddress"></td>
                        <td>김수수</td>
                        <td>강원특별자치도 양구군 국토정중앙면 정리 738-1 123</td>
                        <td>010-1234-5678</td>
                        <td class="manage-links">
                            <a href="#">수정</a>
                            <a href="#">삭제</a>
                        </td>
                    </tr>
                    <!-- Add more rows as needed -->
                </tbody>
            </table>
            <button class="add-address-btn">기본 배송지로 설정</button>
        </div>
    </div>
    </div>
    <%@ include file="/WEB-INF/views/includes/footer.jsp"%>
</body>
</html>
