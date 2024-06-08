<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@include file="../includes/header.jsp"%>
<link rel="stylesheet" href="/resources/css/faq/get.css">
<div class="content_area">
    <div class="sideList">
        <div class="sideListTitle">메뉴</div>
        <ul class="nav nav-pills nav-stacked">
            <li><a href="/board/notice">공지사항</a></li>
            <li><a href="/faq/faq">자주 묻는 질문</a></li>
            <li><a href="/qna/qna">1:1 문의하기</a></li>
            <li><a href="#">고객의 소리</a></li>
        </ul>
         <div class="contact_info">
            <p><strong>고객센터</strong></p>
            <p>02-0000-0000</p>
            <p>평일 09:00 ~ 18:00</p>
            <p>주말, 공휴일 휴무</p>
        </div>
        <div class="chat_info">
            <p><strong>실시간 채팅 상담</strong></p>
            <p>평일 09:00 ~ 21:00</p>
            <p>토요일 09:00 ~ 15:00</p>
        </div>
    </div>
    <div class="main_content">
        
        <div class="panel panel-default">
            <div class="panel-heading">자주묻는 FAQ</div>
            <div class="panel-body">
                
                <div class="form-group">
                    <label>카테고리</label>
                    <input class="form-control" name="faqCategory" value="${categoryValue}" readonly="readonly">
                </div>
                <div class="form-group">
                    <label>제목</label>
                    <input class="form-control" name="title" value="${faq.faqTitle}" readonly="readonly">
                </div>
                <div class="form-group">
                    <a class="move" href="${faq.faqId}">
                        <img src="/download/${faq.faqImageURL}" alt="FAQ Image" style="max-width: 800px;">
                    </a>
                </div>
                <div class="form-group">
                    <label>내용</label>
                    <textarea class="form-control" rows="3" name="content" readonly="readonly">${faq.faqDescription}</textarea>
                </div>
                <button data-oper="modify" class="btn btn-success" onclick="location.href='/faq/modify?faqId=${faq.faqId}'">수정</button>
                <button data-oper="list" class="btn btn-info" onclick="location.href='/faq/faq'">목록</button>
                <form id="operForm" action="/faq/modify" method="get">
                    <input type="hidden" id="faqId" name="faqId" value="${faq.faqId}">
                    <input type="hidden" name="pageNum" value="${cri.pageNum}">
                    <input type="hidden" name="amount" value="${cri.amount}">
                    <input type="hidden" name="type" value="${cri.type}">
                    <input type="hidden" name="keyword" value="${cri.keyword}">
                </form>
            </div>
        </div>
    </div>
</div>
<%@include file="../includes/footer.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
    var operForm = $("#operForm"); 
    $("button[data-oper='modify']").on("click", function(e){
        operForm.attr("action","/faq/modify").submit();
    });
    $("button[data-oper='list']").on("click", function(e){
        operForm.find("#faqId").remove();
        operForm.attr("action","/faq/faq").submit();
    });  
});
</script>
