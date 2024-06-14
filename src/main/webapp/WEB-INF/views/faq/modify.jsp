<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@include file="../includes/header.jsp"%>
<link rel="stylesheet" href="/resources/css/faq/modify.css">
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
                <form role="form" action="/faq/modify" method="post" enctype="multipart/form-data">
                    <input type='hidden' name='pageNum' value='${cri.pageNum}'>
                    <input type='hidden' name='amount' value='${cri.amount}'>
                    <input type='hidden' name='type' value='${cri.type}'>
                    <input type='hidden' name='keyword' value='${cri.keyword}'>
                    <input type='hidden' name='faqId' value='${faq.faqId}'>
                    <input type='hidden' name='faqImageURL' value='${faq.faqImageURL}'>
                   <div class="form-group">
                        <label>문의유형</label>
                        <select class="form-control" name="faqCategory">
                            <option value="">유형선택</option>
                            <c:forEach var="category" items="${category}">
                                <option value="${category.categoryId}" <c:if test="${category.categoryId == faq.faqCategory}">selected</c:if>>${category.categoryValue}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>제목</label>
                        <input class="form-control" name='faqTitle' value="${faq.faqTitle}">
                    </div>
                    <div class="form-group">
                        <label>내용</label>
                        <textarea class="form-control" rows="3" name='faqDescription'>${faq.faqDescription}</textarea>
                    </div>
                    <div class="form-group">
                        <input type='file' name='uploadFile' multiple>
                    </div>
                    <div class="form-group">
                        <label>변경전 이미지</label>
                        <img src="/download/${faq.faqImageURL}" alt="제품이미지" style="max-width: 500px">
                        <input type="hidden" value='${faq.faqImageURL}' name='faqImageURL'>
                         <label for="uploadFile">- 최대 15MB 이하의 JPG, PNG, GIF, BMP 파일 첨부 가능합니다.</label>
                    </div>
                    <button type="submit" data-oper='modify' class="btn btn-default">수정하기</button>
                    <button type="submit" data-oper='remove' class="btn btn-danger">삭제하기</button>
                    <button type="submit" data-oper='list' class="btn btn-info">목록으로</button>
                </form>
            </div>
        </div>
    </div>
</div>

<%@include file="../includes/footer.jsp"%>

<script type="text/javascript">
$(document).ready(function() {
    var formObj = $("form");

    $("button").on("click", function(e) {
        e.preventDefault();

        var operation = $(this).data("oper");

        console.log(operation);

        if (operation === 'remove') {
            formObj.attr("action", "/faq/remove");
        } else if (operation === 'list') {
            formObj.attr("action", "/faq/faq").attr("method", "get");
            var pageNumTag = $("input[name='pageNum']").clone();
            var amountTag = $("input[name='amount']").clone();
            var keywordTag = $("input[name='keyword']").clone();
            var typeTag = $("input[name='type']").clone();

            formObj.empty();
            formObj.append(pageNumTag);
            formObj.append(amountTag);
            formObj.append(keywordTag);
            formObj.append(typeTag);
        } else {
            formObj.attr("action", "/faq/modify");
        }

        formObj.submit();
    });
});
</script>
