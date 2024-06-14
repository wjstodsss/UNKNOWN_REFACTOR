<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../includes/header.jsp"%>
<link rel="stylesheet" href="/resources/css/notice/notice.css">
<div class="content_area">
    <div class="sideList">
        <div class="sideListTitle">고객센터</div>
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
            <div class="panel-heading">공지사항
                <div class='row'>
                    <div class="col-lg-12">
                        <form id='findForm' action="/board/notice" method='get'>
                            <select name='type' class="form-control" style="width: auto; display: inline-block;">
                                <option value="TC" <c:out value="${pageMaker.cri.type eq 'TC'?'selected':''}"/>>
                                    전체검색
                                </option>
                                <option value="T" <c:out value="${pageMaker.cri.type eq 'T'?'selected':''}"/>>
                                    제목
                                </option>
                                <option value="C" <c:out value="${pageMaker.cri.type eq 'C'?'selected':''}"/>>
                                    내용
                                </option>
                            </select>
                            <input type='text' name='keyword' value='<c:out value="${pageMaker.cri.keyword}"/>' class="form-control" style="width: auto; display: inline-block;" placeholder="검색어를 입력하세요" />
                            <input type='hidden' name='pageNum' value='<c:out value="${pageMaker.cri.pageNum}"/>' />
                            <input type='hidden' name='amount' value='<c:out value="${pageMaker.cri.amount}"/>' />
                            <button class='btn btn-default'>Search</button>
                        </form>
                    </div>
                </div>
                </div>
            <div class="panel-body">
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>#번호</th>
                            <th>제목</th>
                            <th>작성일</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${list}" var="board">
                            <tr class="odd gradeX">
                                <td>${board.noticeId}</td>
                                <td><a class="move" href='${board.noticeId}'>
                                    ${board.noticeTitle}</a></td>
                                <td><fmt:formatDate pattern="yyyy-MM-dd"
                                    value="${board.noticeUpdateDate}" /></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
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
                <form id='actionForm' action="/board/notice" method='get'>
                    <input type='hidden' name='pageNum' value='${pageMaker.cri.pageNum}'>
                    <input type='hidden' name='amount' value='${pageMaker.cri.amount}'>
                    <input type='hidden' name='type' value='${pageMaker.cri.type}'>
                    <input type='hidden' name='keyword' value='${pageMaker.cri.keyword}'>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
<script>
    $(document).ready(function() {
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
            self.location = "/board/register";
        });

        var actionForm = $("#actionForm");
        $(".paginate_button a").on("click", function(e) {
            e.preventDefault();
            actionForm.find("input[name='pageNum']").val($(this).attr("href"));
            actionForm.submit();
        });

        $(".move").on("click", function(e) {
            e.preventDefault();
            actionForm.append("<input type='hidden' name='noticeId' value='" + $(this).attr("href") + "'>");
            actionForm.attr("action", "/board/get");
            actionForm.submit();
        });

        var findForm = $("#findForm");
        $("#findForm button").on("click", function(e) {
            if (!findForm.find("option:selected").val()) {
                alert("검색종류를 선택하세요");
                return false;
            }
            if (!findForm.find("input[name='keyword']").val()) {
                alert("키워드를 입력하세요");
                return false;
            }
            findForm.find("input[name='pageNum']").val("1");
            e.preventDefault();
            findForm.submit();
        });
    });
</script>
