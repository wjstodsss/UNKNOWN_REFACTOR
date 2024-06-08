<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@include file="../includes/header.jsp"%>
<link rel="stylesheet" href="/resources/css/faq/faq.css">
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
            <div class="panel-heading">
               자주 묻는 질문 (FAQ)
            </div>
            <div class="panel-body">
                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th style="width: 30%;">카테고리</th>
                            <th style="width: 50%;">제목</th>
                            <th style="width: 20%;">작성일</th>
                          
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${list}" var="faq">
                            <tr>
                                <td>
                                    <c:choose>
                                        <c:when test="${faq.faqCategory eq '100'}">주문/결제</c:when>
                                        <c:when test="${faq.faqCategory eq '200'}">취소/반품</c:when>
                                        <c:when test="${faq.faqCategory eq '300'}">상품관련</c:when>
                                        <c:when test="${faq.faqCategory eq '400'}">배송관련</c:when>
                                        <c:when test="${faq.faqCategory eq '500'}">포인트</c:when>
                                        <c:when test="${faq.faqCategory eq '600'}">만보기</c:when>
                                        <c:otherwise>기타</c:otherwise>
                                    </c:choose>
                                </td>
                                <td><a class="move" href="${faq.faqId}">${faq.faqTitle}</a></td>
                               
                                <td><fmt:formatDate pattern="yyyy-MM-dd" value="${faq.faqUpdateDate}" /></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <button id='regBtn' type="button" class="btn btn-xs btn-info pull-right">작성</button>
                <div class="pull-right">
                    <ul class="pagination">
                        <c:if test="${pageMaker.prev}">
                            <li class="paginate_button"><a class="page-link" href="${pageMaker.pageStart-1}">Previous</a></li>
                        </c:if>
                        <c:forEach var="num" begin="${pageMaker.pageStart}" end="${pageMaker.pageEnd}">
                            <li class="paginate_button ${pageMaker.cri.pageNum == num ? 'active': ''}"><a href="${num}">${num}</a></li>
                        </c:forEach>
                        <c:if test="${pageMaker.next}">
                            <li class="paginate_button"><a href="${pageMaker.pageEnd +1 }">Next</a></li>
                        </c:if>
                    </ul>
                </div>
                <form id="actionForm" action="/faq/faq" method="get">
                    <input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}">
                    <input type="hidden" name="amount" value="${pageMaker.cri.amount}">
                    <input type="hidden" name="type" value="${pageMaker.cri.type}">
                    <input type="hidden" name="keyword" value="${pageMaker.cri.keyword}">
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="../includes/footer.jsp"%>
<script>
    $(document).ready(function() {
    	 // 세션 스토리지를 사용하여 페이지가 로드될 때마다 한 번만 새로고침
        if (!sessionStorage.getItem('reloaded')) {
            sessionStorage.setItem('reloaded', 'true');
            window.location.reload();
        } else {
            sessionStorage.removeItem('reloaded'); // 새로고침 이후에는 플래그 제거
        }

        var result = '${result}';
        checkModal(result);
        history.replaceState({}, null, null);
        function checkModal(result) {
            if (result === '' || history.state) {
                return;
            } else {
                if (parseInt(result) > 0) {
                    $(".modal-body").html("게시글 " + parseInt(result) + "번이 등록되었습니다.");
                }
                $("#myModal").modal("show");
            }
        }
        $("#regBtn").on("click", function() {
            self.location = "/faq/register";
        });
        var actionForm = $("#actionForm");
        $(".paginate_button a").on("click", function(e) {
            e.preventDefault();
            actionForm.find("input[name='pageNum']").val($(this).attr("href"));
            actionForm.submit();
        });
        $(".move").on("click", function(e) {
            e.preventDefault();
            actionForm.append("<input type='hidden' name='faqId' value='" + $(this).attr("href") + "'>");
            actionForm.attr("action", "/faq/get");
            actionForm.submit();
        });
    });
</script>
