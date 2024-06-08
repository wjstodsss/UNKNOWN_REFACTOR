<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../includes/header.jsp"%>
<link rel="stylesheet" href="/resources/css/review/review.css">
<div class="content_area">
	<div class="left_menuwrap">
		<%@ include file="/WEB-INF/views/includes/leftmenu.jsp"%>
	</div>
	<div class="main_content">
		<div class="panel-heading">상품후기</div>

		<div class="panel panel-default">

			<div class="panel-body table-container">
				<table class="table table-striped table-bordered table-hover"
					id="dataTables-example">
					<thead>
						<tr>
							<th>제목</th>
							<th>작성일</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="review">
							<tr class="odd gradeX">
								<td><a class="move" href="${review.reviewId}">${review.reviewTitle}</a></td>
								<td><fmt:formatDate pattern="yyyy-MM-dd"
										value="${review.reviewUpdateDate}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

			<div class="pull-right">
				<ul class="pagination">
					<c:if test="${pageMaker.prev}">
						<li class="paginate_button"><a class="page-link"
							href="${pageMaker.pageStart-1}">Previous</a></li>
					</c:if>
					<c:forEach var="num" begin="${pageMaker.pageStart}"
						end="${pageMaker.pageEnd}">
						<li
							class="paginate_button ${pageMaker.cri.pageNum == num ? 'active': ''} "><a
							href="${num}">${num}</a></li>
					</c:forEach>
					<c:if test="${pageMaker.next}">
						<li class="paginate_button"><a
							href="${pageMaker.pageEnd +1 }">Next</a></li>
					</c:if>
				</ul>
				<button id='regBtn' type="button"
					class="btn btn-xs pull-right btn-info">후기 작성</button>
			</div>
			<form id='actionForm' action="/review/review/${member.memberId}"
				method='get'>
				<input type='hidden' name='pageNum' value='${pageMaker.cri.pageNum}'>
				<input type='hidden' name='amount' value='${pageMaker.cri.amount}'>
				<input type='hidden' name='type' value='${pageMaker.cri.type}'>
				<input type='hidden' name='keyword' value='${pageMaker.cri.keyword}'>
			</form>


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
            self.location = "/review/register/";
        });

        var actionForm = $("#actionForm");
        $(".paginate_button a").on("click", function(e) {
            e.preventDefault();
            actionForm.find("input[name='pageNum']").val($(this).attr("href"));
            actionForm.submit();
        });

        $(".move").on("click", function(e) {
            e.preventDefault();
            actionForm.append("<input type='hidden' name='reviewId' value='" + $(this).attr("href") + "'>");
            actionForm.attr("action", "/review/get/");
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
