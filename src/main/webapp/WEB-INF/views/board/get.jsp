<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../includes/header.jsp"%>
<link rel="stylesheet" href="/resources/css/notice/get.css">
<div class="content_area">
    <div class="sideList">
        <div class="sideListTitle">메뉴</div>
        <ul class="nav nav-pills nav-stacked">
            <li><a href="/board/notice">공지사항</a></li>
            <li><a href="/faq/faq">자주 묻는 질문</a></li>
            <li><a href="/qna/qna">1:1 문의하기</a></li>
            <li><a href="#">고객의 소리</a></li>
        </ul>
    </div>
    <div class="main_content">
        <h1 class="page-header">공지사항</h1>
        <div class="panel-body">
           
            <div class="form-group">
                <label>제목</label>
                <input class="form-control" name="title" value="${board.noticeTitle}" readonly="readonly">
            </div>
            <div class="form-group">
                <a class="move" href="${board.noticeId}">
                    <img src="/download/${board.noticeImageURL}" alt="공지 이미지" style="max-width: 800px;">
                </a>
            </div>
            <div class="form-group">
                <label>내용</label>
                <textarea class="form-control" rows="3" name="content" readonly="readonly">${board.noticeDescription}</textarea>
            </div>
            
            <button data-oper="list" class="btn btn-info" onclick="location.href='/board/notice'">목록</button>
            <form id="operForm" action="/board/modify" method="get">
                <input type="hidden" id="noticeId" name="noticeId" value="${board.noticeId}">
                <input type="hidden" name="pageNum" value="${cri.pageNum}">
                <input type="hidden" name="amount" value="${cri.amount}">
                <input type="hidden" name="type" value="${cri.type}">
                <input type="hidden" name="keyword" value="${cri.keyword}">
            </form>
        </div>
    </div>
</div>
<%@ include file="../includes/footer.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
    var operForm = $("#operForm"); 
    $("button[data-oper='modify']").on("click", function(e){
        operForm.attr("action","/board/modify").submit();
    });
    $("button[data-oper='list']").on("click", function(e){
        operForm.find("#noticeId").remove();
        operForm.attr("action","/board/notice").submit();
    });  
});
</script>
