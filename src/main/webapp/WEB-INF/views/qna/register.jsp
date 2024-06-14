<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/WEB-INF/views/includes/header.jsp"%>
<link rel="stylesheet" href="/resources/css/qna/register.css">
<div class="content_area">
    <div class="left_menu">
        <%@ include file="/WEB-INF/views/includes/leftmenu.jsp"%>
    </div>
    <div class="main_content">
        

        <div class="panel panel-default">
            <div class="panel-heading">QNA 작성하기</div>
            <div class="panel-body">
                <form name="qnaForm" role="form" action="/qna/register" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
                    <div class="form-group">
                        <label>문의유형</label>
                        <select class="form-control" name="qnaCategory">
                            <option value="">유형선택</option>
                            <c:forEach var="category" items="${category}">
                                <option value="${category.categoryId}">${category.categoryValue}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>제목</label>
                        <input class="form-control" name='qnaTitle'>
                    </div>
                    <div class="form-group">
                        <label>작성자</label>
                        <input class="form-control" name='qnaWriter'>
                    </div>
                    <div class="form-group">
                        <label>문의내용</label>
                        <textarea class="form-control" rows="3" name='qnaContent'></textarea>
                    </div>
                    <div class="form-group">
                        <label for="uploadFile">uploadFile</label>
                        <input type="file" id="uploadFile" name="uploadFile">
                        <input type="hidden" value='defaultImage.jpg' name='qnaImageURL'>
                    </div>
                    <button type="submit" class="btn btn-submit">등록하기</button>
                    <button type="reset" class="btn btn-reset">리셋</button>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
function validateForm() {
    var category = document.forms["qnaForm"]["qnaCategory"].value;
    if (category == "") {
        alert("카테고리를 선택해주세요.");
        return false;
    }
    return true;
}
</script>
<%@ include file="../includes/footer.jsp"%>
