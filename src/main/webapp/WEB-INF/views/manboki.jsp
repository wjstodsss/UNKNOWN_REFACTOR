<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
    .page_wrap {
        display: flex;
        flex-direction: column;
        align-items: center;
        text-align:center;
    }
    
    .page_wrap img { 
    	width: 1300px;
    	margin-bottom: 30px;
    }

</style>

<body>	
	<%@ include file="/WEB-INF/views/includes/header.jsp"%>
	<div class="page_wrap">
	<img src="<%=request.getContextPath()%>/resources/img/manboki.png">

	</div>
	<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
</body>
</html>