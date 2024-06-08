<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<%@include file="includes/header.jsp"%>
<%@include file="includes/leftNav.jsp"%>

	<div class="col-lg-10">
		<div class="panel panel-default">
			<div class="panel-heading">
				Review List Page
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
			<!-- 검색조건 -->
				  <div class="row">       
				    <div class="col-lg-12">                    
				        <form id='searchForm' action="/admin/review/list" method='get' class='searchForm'>
				            <select class="custom-select" name='type'>
				                <option value=""
				                    <c:out value="${pageMaker.cri.type == null?'selected':''}"/>>--</option>
				                <option value="W"
				                    <c:out value="${pageMaker.cri.type eq 'W'?'selected':''}"/>>작성자</option>
				                <option value="T"
				                    <c:out value="${pageMaker.cri.type eq 'T'?'selected':''}"/>>제목</option>
				                <option value="I"
				                    <c:out value="${pageMaker.cri.type eq 'I'?'selected':''}"/>>상품ID</option>
				                <option value="WT"
				                    <c:out value="${pageMaker.cri.type eq 'WT'?'selected':''}"/>>작성자 or 제목</option>
				                <option value="WI"
				                    <c:out value="${pageMaker.cri.type eq 'WI'?'selected':''}"/>>작성자 or 상품ID</option>
				                <option value="WTI"
				                    <c:out value="${pageMaker.cri.type eq 'WTI'?'selected':''}"/>>작성자 or 제목 or 상품ID</option>
				            </select> 
				            <input type='text' class='custom-keyword' name='keyword' value='<c:out value="${pageMaker.cri.keyword}"/>' /> 
				            <input type='hidden' name='pageNum' value='<c:out value="${pageMaker.cri.pageNum}"/>' /> 
				            <input type='hidden' name='amount' value='<c:out value="${pageMaker.cri.amount}"/>' />
				            <button class='btn btn-default'>Search</button>
				        </form>
				    </div>
				    
				    <div class="col-lg-12 button-add">	
					    <a href="/admin/review/list" type="button" class="btn btn-board btn-xs btn-dark pull-right btn-info col-lg-1.5 mx-2 my-2" data-sortColumn='reviewWriter'>작성자 오름차순 </a>
					    <a href="/admin/review/list" type="button" class="btn btn-board btn-xs btn-dark pull-right btn-info col-lg-1.5 mx-2 my-2" data-sortColumn='reviewTitle'>제목 오름차순 </a>
				     	<a href="/admin/review/list" type="button" class="btn btn-board btn-xs btn-dark pull-right btn-info col-lg-1.5 mx-2 my-2" data-sortColumn='reviewId'>순번 오름차순 </a>
				        <a href="/admin/review/descList" type="button" class="btn btn-board btn-xs pull-right btn-info btn-warning col-lg-1.5 mx-2 my-2" data-sortColumn='reviewWriter' > 작성자 내림차순 </a>
				        <a href="/admin/review/descList" type="button" class="btn btn-board btn-xs pull-right btn-info btn-warning col-lg-1.5 mx-2 my-2" data-sortColumn='reviewTitle' > 제목 내림차순 </a>
				        <a href="/admin/review/descList" type="button" class="btn btn-board btn-xs pull-right btn-info btn-warning col-lg-1.5 mx-2 my-2" data-sortColumn='reviewRegdate' >순번 내림차순 </a>							      
				    </div>
				   
				</div>
				<!-- end 검색조건 --> 
				<button id="regBtn" type="button" class="btn btn-board btn-xs pull-right btn-info col-lg-1 mx-2 my-2" onclick="goToModalForm()"> 새글 </button>
				<a href="/admin/review/list" type="button" class="btn btn-board btn-xs btn-light pull-right btn-info col-lg-2 mx-2 my-2">검색해제 일반리스트 </a>
				<table width="80%"
					class="table table-striped table-bordered table-hover"
					id="dataTables-example">
					
					<caption class="table-caption">구매 후기</caption>
					<thead>
						<tr>
							<th>순번</th>
							<th>상품ID</th>
                            <th>제목</th>
                            <th>작성자</th>
                            <th>등록일</th>
                            <th>답변 상태</th>
                           
						</tr>
					</thead>
					<tbody>
						 <c:forEach var="review" items="${reviews}" varStatus="status">
					        <tr class="odd gradeX">
					            <td><a href='#' id="${review.reviewId}" onclick="goToDetailModalForm(this)">${review.reviewId}</a></td>
					            <td>${review.itemId}</td>
					            <td>${review.reviewTitle}</td>
					            <td>${review.reviewWriter}</td>
					            <td><fmt:formatDate pattern="yyyy-MM-dd" value="${review.reviewRegdate}" /></td>
					           <td>${review.answer != null ? review.answer : 'N'}</td>
					        </tr>
					    </c:forEach>
					</tbody>
				</table>

				<!-- 페이지 처리 -->
				<div class="pull-right">
					<ul class="pagination">
						<c:if test="${pageMaker.prev}">
							<li class="paginate_button"><a class="page-link"
								href="${pageMaker.startPage-1}">Previous</a></li>
						</c:if>

						<c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
							
							<li class="paginate_button ${pageMaker.cri.pageNum == num ? 'active': ''} ">
							<a href="${num}">${num}</a></li>
						</c:forEach>

						<c:if test="${pageMaker.next}">
							<li class="paginate_button"><a href="${pageMaker.endPage+1}">Next</a></li>
						</c:if>
					</ul>
				</div>
				<!-- end 페이지 처리 -->

				<form id='actionForm' action="/admin/review/list" method='get'>
					<input type='hidden' name='pageNum'
						value='${pageMaker.cri.pageNum}'> <input type='hidden'
						name='amount' value='${pageMaker.cri.amount}'>
								<input type='hidden' name='type' value='${pageMaker.cri.type}'>
								<input type='hidden' name='keyword' value='${pageMaker.cri.keyword}'>
								<input type='hidden' name='sortColumn' id='sortColumn'>
				</form>


				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
		<!-- /.col-lg-12 -->
<!-- 등록 모달 -->
<div class="modal" id="formModal" tabindex="-1" role="dialog"
	aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-reviewName" id="cartModalLabel">구매 후기 등록</h5>
				<button type="button" class="close" aria-label="Close"
					onclick="closeModal('#formModal')">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form id="registerForm" name="registerForm" role="form" action="register" method="post" enctype="multipart/form-data">
					<div class="form-group">
					    <label for="itemId">상품ID</label>
					    <input type="text" class="form-control" data-type="register" name="itemId" placeholder="상품ID를 숫자로 입력하세요" required>
					    <button type="button" class="btn btn-default col-lg-3 btn-dark my-2" onclick="checkItem(this, 'register')">상품 확인</button>					  
					</div>

					<div class="form-group">
						<label for="reviewTitle">제목</label> <input type="text"
							class="form-control" name="reviewTitle"
							placeholder="제목을 입력하세요" required>
					</div>

					<div class="form-group">
						<label for="reviewContent">내용</label> <textarea 
                        class="form-control" name="reviewContent" rows="3" 
						placeholder="내용을 입력하세요" required></textarea>
					</div>
				
					<div class="form-group">
						<label for="reviewWriter">작성자</label> <input type="text"
							class="form-control" name="reviewWriter"
							placeholder="작성자를 입력하세요" required>
					</div>

                    <div class="form-group">
                        <label for="uploadFile">파일</label>
                        <input type="file" id="uploadFile" name="uploadFile" multiple>
                        <input type="hidden" value='defaltImage.jpg' name='reviewImageURL'>
                    </div>
                    
                    
					<button type="submit" class="btn btn-default btn-success" id="submitBtn">Submit Button</button>
					<button type="reset" class="btn btn-secondary">다시 작성</button>
					<button type="button" class="btn btn-secondary" onclick="closeModal(this)">list</button>
				</form>
			</div>
		</div>
	</div>
</div>

<!-- 상세 수정 삭제 모달 -->
<div class="modal" id="formModal2" tabindex="-1" role="dialog"
	aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-reviewName" id="cartModalLabel">구매 후기 수정/삭제/댓글</h5>
				<button type="button" class="close" aria-label="Close"
					onclick="closeModal(this)">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form id="modifyForm" action="modify" method="post" enctype="multipart/form-data">
	        
				<div class="form-group">
					<label>게시글 ID</label> <input class="form-control" name='reviewId' id='reviewId' readonly>
				</div>
				
				<div class="form-group">
					<label>제목</label> <input class="form-control" id='itemId' name='itemId' readonly>
                </div>
				<div class="form-group">
					<label>제목</label> <input class="form-control"
						id='reviewTitle' name='reviewTitle'>
                </div>

				<div class="form-group">
					<label>내용</label> <textarea class="form-control"
						id='reviewContent' rows="3" name='reviewContent'></textarea>
				</div>
				
						
				<div class="form-group">
					<label>작성자</label> <input class="form-control"
						id='reviewWriter' name='reviewWriter'>
                </div>

                <div class="form-group">
                    <input type='file' name='uploadFile' multiple>
                </div>

                <div class="form-group">
                    <label>변경전 이미지</label>
                    <img src="" alt="공지이미지" style="max-width: 400px" id='imageSRC'>
                    <input type="hidden" name='reviewImageURL' id='imageID' name='reviewImageURL'>        
                </div>
			
                <div class="form-group">
					<label for="reviewRegdate">등록일</label> <input type="text"
						id="reviewRegdate" class="form-control" readonly>					
				</div>

                <div class="form-group">
					<label for="reviewUpdateDate">수정일</label> <input type="text"
						id="reviewUpdateDate" class="form-control" readonly>					
				</div>
				
				<div class="form-group">
					<label for="reply">후기 감사 인사</label> <textarea 
                       class="form-control" id="reply" name="reply" rows="6" placeholder="관리자 입력" required></textarea>
				</div>
				
				
				<button type="submit" class="btn btn-default">Modify</button>
				<button type="submit" onclick="removeAction()" class="btn btn-danger">Remove</button>
				<button type="button" class="btn btn-secondary" onclick="closeModal(this)">list</button>
				<input type="hidden" id="currentPath" name="currentPath" value="">
				<input type="hidden" id="replyId" name="replyId" value="">
				<input type="hidden" id="replyer" name="replyer" value="Finn">
				</form>
			
			</div>
		</div>
	</div>
</div>






<%@include file="includes/footer.jsp"%>

<script>
function checkItem(element) {
    var itemId = $(element).prevAll('input[data-type="register"]').val();
    if (itemId === "") {
        alert("상품ID를 입력하세요.");
        return false;
    }
    $.ajax({
		url: '/admin/item/checkItem/'+ itemId,
		type: 'get', 
		data: { itemId: itemId },
		success: function(response)  {
            if (response.result) {
            	alert("상품이 존재합니다. 이어서 작성해주세요.");
                document.getElementById("submitBtn").disabled = false;
            } else {
                alert("입력하신 아이템 정보가 없습니다.");
                document.getElementById("submitBtn").disabled = true;
            }
        },
		error: function(xhr, status, error) {
			console.error('AJAX 요청 실패:', error);
			document.getElementById("submitBtn").disabled = true;
		}
	});
}

function updateActionUrl() {
    var currentUrl = window.location.href;
    var newPath;

    if (currentUrl.includes("desc")) {
    	newPath = "/admin/review/descList";
    } else {
    	newPath = "/admin/review/list";
    }
   
    // 설정한 값으로 hidden input 업데이트
    $("#currentPath").val(extractPageName(newPath));
    return newPath;
}

function goToDetailModalForm(element) {
	console.log(element)
	let valueToSend = element.id;
	
	$.ajax({
		url: '/admin/review/get/'+ valueToSend, // 서버의 URL
		type: 'get',
		data: {
            reviewId: valueToSend,
        },
		success: function(response) {
			var reviewData = response.review;
			$("#reviewId").val(reviewData.reviewId);
			$("#itemId").val(reviewData.itemId);
			$("#reviewTitle").val(reviewData.reviewTitle);
			$("#reviewContent").text(reviewData.reviewContent);
			$("#reviewWriter").val(reviewData.reviewWriter);
			$("#imageSRC").attr("src", "/download/" + reviewData.reviewImageURL);
            $("#imageID").val(reviewData.reviewImageURL);
			var regDate = new Date(reviewData.reviewRegdate);
			var isoDateString = regDate.toISOString().substring(0, 10);
			console.log(isoDateString);
			$('#reviewRegdate').val(isoDateString);
            var upDateDate = new Date(reviewData.reviewUpdateDate);
			var isoDateString = upDateDate.toISOString().substring(0, 10);
			console.log(isoDateString);
			$('#reviewUpdateDate').val(isoDateString);
			$("#reply").text("");
			$("#replyId").val("");
			if(response.reply != null) {
				var replyData = response.reply;
				$("#reply").text(replyData.reply);
				$("#replyId").val(replyData.replyId);
			}
			$('#formModal2').modal('show');
		},
		error: function(xhr, status, error) {
			console.error('AJAX 요청 실패:', error);
		}
	});
}
</script>