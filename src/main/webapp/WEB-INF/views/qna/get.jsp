<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/WEB-INF/views/includes/header.jsp"%>
<link rel="stylesheet" href="/resources/css/qna/get.css">
<div class="content_area">
    <div class="left_menu">
        <%@ include file="/WEB-INF/views/includes/leftmenu.jsp"%>
    </div>
    <div class="main_content">
        <div class="panel panel-default">
            <div class="panel-heading">QNA</div>
            <div class="panel-body">
                <div class="form-group">
                    <label>제목</label>
                    <input class="form-control" name='title' value='${qna.qnaTitle}' readonly="readonly">
                </div>
                <div class="form-group">
                    <label>카테고리</label>
                    <input class="form-control" name="qnaCategory" value="${categoryValue}" readonly="readonly">
                </div>
                <div class="form-group">
                    <label>내용</label>
                    <textarea class="form-control" rows="3" name='content' readonly="readonly">${qna.qnaContent}</textarea>
                </div>
                <div class="form-group">
                    <a class="move" href='${qna.qnaId}'>
                        <img src="/download/${qna.qnaImageURL}" alt="Qna Image" class="img-responsive">
                    </a>
                </div>
                <button data-oper='modify' class="btn btn-submit" onclick="location.href='/qna/modify?qnaId=${qna.qnaId}'">수정하기</button>
                <button data-oper='list' class="btn btn-info" onclick="location.href='/qna/qna'">목록으로</button>
                <form id='operForm' action="/qna/modify" method='get'>
                    <input type='hidden' id="qnaId" name='qnaId' value='${qna.qnaId}'>
                    <input type='hidden' name='pageNum' value='${cri.pageNum}'>
                    <input type='hidden' name='amount' value='${cri.amount}'>
                    <input type='hidden' name='type' value='${cri.type}'>
                    <input type='hidden' name='keyword' value='${cri.keyword}'>
                </form>
            </div>
        </div>

        <!-- 댓글추가 -->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="reply">답글</i> 
                    </div>
                    <div class="panel-body">
                        <ul class="chat"></ul>
                    </div>
                    <div class="panel-footer"></div>
                </div>
            </div>
        </div>
        <!-- 댓글추가 end -->
    </div>
</div>
<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
<!-- jQuery 라이브러리 포함 -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<!-- qnaReply.js 파일 포함 -->
<script type="text/javascript" src="/resources/js/qnaReply.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    var replyUL = $(".chat");
    var qnaIdValue = $("#qnaId").val();
    console.log(qnaIdValue); // 확인용 콘솔 출력

    showList(1);

    function showList(page) {
        replyService.getList(
            {
                qnaId : qnaIdValue,
                page : page || 1
            },
            function(list) {
                console.log("댓글 목록:", list); // 댓글 데이터를 콘솔에 출력
                var str = "";

                if (list == null || list.length == 0) {
                    replyUL.html("<li class='left clearfix'>댓글이 없습니다.</li>");
                    return;
                }

                for (var i = 0, len = list.length || 0; i < len; i++) {
                    str += "<li class='left clearfix' data-replyId='"+list[i].replyId+"'>";
                    str += "  <div><div class='header'><strong class='primary-font'>["
                            + list[i].replyId
                            + "] "
                            + list[i].replyer
                            + "</strong>";
                    str += "    <small class='pull-right text-muted'>"
                            + replyService.displayTime(list[i].replyDate)
                            + "</small></div>";
                    str += "    <p>"
                            + list[i].reply
                            + "</p></div></li>";
                }

                replyUL.html(str);
            },
            function(error) {
                console.error("댓글 목록 불러오기 실패:", error);
            }
        );
    }
});
</script>
