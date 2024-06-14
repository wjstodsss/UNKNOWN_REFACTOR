<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../includes/header.jsp"%>
<link rel="stylesheet" href="/resources/css/review/modify.css">
<div class="content_area">
    <div class="left_menu">
        <%@ include file="/WEB-INF/views/includes/leftmenu.jsp"%>
    </div>
    <div class="main_content">
        <div class="panel panel-default">
            <div class="panel-heading">리뷰 수정하기</div>
            <div class="panel-body">
                <form role="form" id="reviewForm" action="/review/modify" method="post" enctype="multipart/form-data">
                    <input type='hidden' name='pageNum' value='${cri.pageNum}'>
                    <input type='hidden' name='amount' value='${cri.amount}'>
                    <input type='hidden' name='type' value='${cri.type}'>
                    <input type='hidden' name='keyword' value='${cri.keyword}'>
                    <input type='hidden' name='reviewId' value='${review.reviewId}'>
                    <input type='hidden' name='reviewWriter' value='${review.reviewWriter}'>
                    
                    <div class="form-group">
                        <label>제목</label>
                        <input class="form-control" name='reviewTitle' value='${review.reviewTitle}'>
                    </div>
                    
                    <div class="form-group">
                        <label>상품</label>
                        <input class="form-control" name='itemName' value='${itemName}' readonly="readonly">
                    </div>
                    
                    <div class="form-group">
                        <label>내용</label>
                        <textarea class="form-control" rows="3" name='reviewContent'>${review.reviewContent}</textarea>
                    </div>
                    
                    <div class="form-group">
                        <label>파일 업로드</label>
                        <input type='file' name='uploadFile' multiple>
                    </div>
                    
                    <div class="form-group">
                        <label>변경전 이미지</label>
                        <img src="/download/${review.reviewImageURL}" alt="제품이미지" class="img-responsive"  style="max-width: 500px">
                        <input type="hidden" value='${review.reviewImageURL}' name='reviewImageURL'>
                        <label for="uploadFile">- 최대 15MB 이하의 JPG, PNG, GIF, BMP 파일 첨부 가능합니다.</label>
                    </div>
                    
                    <button type="button" data-oper='modify' class="btn btn-submit">수정하기</button>
                    <button type="button" data-oper='remove' class="btn btn-danger">삭제하기</button>
                    <button type="button" data-oper='list' class="btn btn-info">목록으로</button>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="../includes/footer.jsp"%>
<script type="text/javascript">
    $(document).ready(function() {
        var formObj = $("#reviewForm");

        $("button[data-oper='modify']").on("click", function(e) {
            formObj.attr("action", "/review/modify").submit();
        });

        $("button[data-oper='remove']").on("click", function(e) {
            if (confirm("삭제하시겠습니까?")) {
                formObj.attr("action", "/review/remove").attr("method", "post").submit();
            }
        });

        $("button[data-oper='list']").on("click", function(e) {
            formObj.attr("action", "/review/review").attr("method", "get");
            var pageNumTag = $("input[name='pageNum']").clone();
            var amountTag = $("input[name='amount']").clone();
            var keywordTag = $("input[name='keyword']").clone();
            var typeTag = $("input[name='type']").clone();

            formObj.empty();
            formObj.append(pageNumTag);
            formObj.append(amountTag);
            formObj.append(keywordTag);
            formObj.append(typeTag);
            formObj.submit();
        });
    });
</script>