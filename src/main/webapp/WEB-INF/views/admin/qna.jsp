<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<%@include file="includes/header.jsp"%>
<%@include file="includes/leftNav.jsp"%>
<%@include file="includes/common-cri.jsp"%>

	<div class="col-lg-10">
		<div class="panel panel-default">
			<div class="panel-heading">
				Qna List Page
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
			<!-- 검색조건 -->
				  <div class="row">       
				    <div class="col-lg-12">                    
				        <form id='searchForm' action="/admin/qna/list" method='get' class='searchForm'>
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
				            <button class='btn custom-keyword-btn'>Search</button>
                        	<a href="/admin/qna/list" type="button" class="btn custom-keyword-btn mx-2">검색해제</a>
				        </form>
				    </div>
				</div>
				<!-- end 검색조건 -->
	            <button id="regBtn" type="button"
	            class="btn btn-board btn-xs pull-right btn-secondary col-lg-1 mx-2 my-2"
	            onclick="goToModalForm()"> 등록 </button>
	            <table class="table table-bqnaed able-hover  custom-table">
					<caption class="table-caption">1:1 문의</caption>
					<thead class="button-add">
						<tr>
							<th>
								<div class="custom-sort-component">
					                <div class="label">순번</div>
					                <div class="sort-icons">
					                    <a href="#" class="no-anchor-text-decoration"
					                       data-groupColumn='/qna' data-sortColumn='qnaId'
					                       data-sortType='asc'>▲</a>
					                    <a href="#"
					                       class="no-anchor-text-decoration"
					                       data-groupColumn='/qna' data-sortColumn='qnaId'
					                       data-sortType='desc'>▼</a>
					                </div>
					            </div>
							</th>
							<th>
								<div class="custom-sort-component">
					                <div class="label">상품ID</div>
					                <div class="sort-icons">
					                    <a href="#" class="no-anchor-text-decoration"
					                       data-groupColumn='/qna' data-sortColumn='itemId'
					                       data-sortType='asc'>▲</a>
					                    <a href="#"
					                       class="no-anchor-text-decoration"
					                       data-groupColumn='/qna' data-sortColumn='itemId'
					                       data-sortType='desc'>▼</a>
					                </div>
					            </div>
							</th>
                            <th>
                            	<div class="custom-sort-component">
					                <div class="label">제목</div>
					                <div class="sort-icons">
					                    <a href="#" class="no-anchor-text-decoration"
					                       data-groupColumn='/qna' data-sortColumn='qnaTitle'
					                       data-sortType='asc'>▲</a>
					                    <a href="#"
					                       class="no-anchor-text-decoration"
					                       data-groupColumn='/qna' data-sortColumn='qnaTitle'
					                       data-sortType='desc'>▼</a>
					                </div>
					            </div>
                            </th>
                            <th>
                            	<div class="custom-sort-component">
					                <div class="label">작성자</div>
					                <div class="sort-icons">
					                    <a href="#" class="no-anchor-text-decoration"
					                       data-groupColumn='/qna' data-sortColumn='qnaWriter'
					                       data-sortType='asc'>▲</a>
					                    <a href="#"
					                       class="no-anchor-text-decoration"
					                       data-groupColumn='/qna' data-sortColumn='qnaWriter'
					                       data-sortType='desc'>▼</a>
					                </div>
					            </div>
                            </th>
                            <th>
                            	<div class="custom-sort-component">
					                <div class="label">등록일</div>
					                <div class="sort-icons">
					                    <a href="#" class="no-anchor-text-decoration"
					                       data-groupColumn='/qna' data-sortColumn='qnaRegdate'
					                       data-sortType='asc'>▲</a>
					                    <a href="#"
					                       class="no-anchor-text-decoration"
					                       data-groupColumn='/qna' data-sortColumn='qnaRegdate'
					                       data-sortType='desc'>▼</a>
					                </div>
					            </div>
                            </th>
                            <th>
                            	<div class="custom-sort-component">
					                <div class="label">답변</div>
					                <div class="sort-icons">
					                    <a href="#" class="no-anchor-text-decoration"
					                       data-groupColumn='/qna' data-sortColumn='answer'
					                       data-sortType='asc'>▲</a>
					                    <a href="#"
					                       class="no-anchor-text-decoration"
					                       data-groupColumn='/qna' data-sortColumn='answer'
					                       data-sortType='desc'>▼</a>
					                </div>
					            </div>
                            </th>
						</tr>
					</thead>
					<tbody>
						 <c:forEach var="QNA" items="${qnas}" varStatus="status">
					        <tr class="odd gradeX">
					            <td><a href='#' id="${QNA.qnaId}" onclick="goToDetailModalForm(this)">${QNA.qnaId}</a></td>
					            <td>${QNA.itemId}</td>
					            <td>${QNA.qnaTitle}</td>
					            <td>${QNA.qnaWriter}</td>
					            <td><fmt:formatDate pattern="yyyy-MM-dd" value="${QNA.qnaRegdate}" /></td>
					            <td>${QNA.answer != null ? QNA.answer : 'N'}</td>
					        </tr>
					    </c:forEach>
					</tbody>
				</table>

				<!-- 페이지 처리 -->
	            <div class="d-flex justify-content-center">
	                <ul class="pagination">
	                    <c:if test="${pageMaker.prev}">
	                        <li class="paginate_button">
	                            <a class="page-link" href="${pageMaker.startPage-1}">Previous</a></li>
	                    </c:if>
	                    <c:forEach var="num" begin="${pageMaker.startPage}"
	                               end="${pageMaker.endPage}">
	                        <li class="paginate_button ${pageMaker.cri.pageNum == num ? 'active': ''} ">
	                            <a href="${num}">${num}</a>
	                        </li>
	                    </c:forEach>
	                    <c:if test="${pageMaker.next}">
	                        <li class="paginate_button"><a href="${pageMaker.endPage+1}">Next</a></li>
	                    </c:if>
	                </ul>
	            </div>
	            <!-- end 페이지 처리 -->
	            <form id='actionForm' action="/admin/qna/list" method='get'>
	            </form>

				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /row -->
<!-- 등록 모달 -->
<div class="modal" id="formModal" tabindex="-1" role="dialog"
	aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header row custom-modal-header-row">
                <div class="col-lg-6">
                    <h5 class="modal-qnaName custom-modal-header-title">qna 등록</h5>
                </div>
                <div class="col-lg-6">
                    <button type="button" class="close" aria-label="Close"
                            onclick="closeModal(this)">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
			<div class="modal-body">
				<form id="registerForm" name="registerForm" role="form" action="register" method="post" enctype="multipart/form-data">

					<div class="form-group">
						<label for="itemId">상품ID</label> <input type="text" class="form-control" data-type="register" name="itemId" placeholder="상품ID를 숫자로 입력하세요" required>
					    <button type="button" class="btn btn-default col-lg-3 btn-dark my-2" onclick="checkItem(this, 'register')">상품 확인</button>
					</div>
					
						
					
					<div class="form-group">
						<label for="qnaTitle">제목</label> <input type="text"
							class="form-control" name="qnaTitle"
							placeholder="제목을 입력하세요" required>
					</div>

					<div class="form-group">
						<label for="qnaContent">내용</label> <textarea 
                        class="form-control" name="qnaContent" rows="3" 
						placeholder="내용을 입력하세요" required></textarea>
					</div>
					
					<div class="form-group">
						<label for="qnaCategory">카테고리</label> 
						<select class="form-control" name="qnaCategory" required>
						    <option value="">카테고리를 선택하세요</option>
						    <option value="100">주문/결제</option>
						    <option value="200">취소/반품</option>
						    <option value="300">상품관련</option>
						    <option value="400">배송관련</option>
						    <option value="500">포인트</option>
						    <option value="600">만보기관련</option>
						</select>
					</div>

					<div class="form-group">
						<label for="qnaWriter">작성자</label> <input type="text"
							class="form-control" name="qnaWriter"
							placeholder="작성자를 입력하세요" required>
					</div>

                    <div class="form-group">
                        <label for="uploadFile">uploadFile</label>
                        <input type="file" id="uploadFile" name="uploadFile" multiple>
                        <input type="hidden" value='defaltImage.jpg' name='qnaImageURL'>
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
			<div class="modal-header row custom-modal-header-row">
                <div class="col-lg-6">
                    <h5 class="modal-qnaName custom-modal-header-title">qna 수정</h5>
                </div>
                <div class="col-lg-6">
                    <button type="button" class="close" aria-label="Close"
                            onclick="closeModal(this)">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
			<div class="modal-body">
				<form id="modifyForm" action="modify" method="post" enctype="multipart/form-data">
	        
				<div class="form-group">
					<label>게시글 ID</label> <input class="form-control" name='qnaId' id='qnaId' readonly>
				</div>
				<div class="form-group">
					<label>상품 ID</label> <input class="form-control" name='itemId' id='itemId' readonly>
				</div>
				<div class="form-group">
					<label>제목</label> <input class="form-control"
						id='qnaTitle' name='qnaTitle'>
                </div>

				<div class="form-group">
					<label>내용</label> <textarea class="form-control"
						id='qnaContent' rows="3" name='qnaContent'></textarea>
				</div>
				<div class="form-group">
					<label for="qnaCategory">카테고리</label> 
					<select class="form-control" id='qnaCategory' name="qnaCategory" required>
					    <option value="">카테고리를 선택하세요</option>
					    <option value="100">주문/결제</option>
					    <option value="200">취소/반품</option>
					    <option value="300">상품관련</option>
					    <option value="400">배송관련</option>
					    <option value="500">포인트</option>
					    <option value="600">만보기관련</option>
					</select>
				</div>
						
						
				<div class="form-group">
					<label>작성자</label> <input class="form-control"
						id='qnaWriter' name='qnaWriter'>
                </div>

                <div class="form-group">
                    <input type='file' name='uploadFile' multiple>
                </div>

                <div class="form-group">
                    <label>변경전 이미지</label>
                    <img src="" alt="공지이미지" style="max-width: 400px" id='imageSRC'>
                    <input type="hidden" name='qnaImageURL' id='imageID' name='qnaImageURL'>        
                </div>
			
                <div class="form-group">
					<label for="qnaRegdate">등록일</label> <input type="text"
						id="qnaRegdate" class="form-control" readonly>					
				</div>

                <div class="form-group">
					<label for="qnaUpdateDate">수정일</label> <input type="text"
						id="qnaUpdateDate" class="form-control" readonly>					
				</div>
				
				<div class="form-group">
					<label for="reply">답변 내용</label> <textarea 
                       class="form-control" id="reply" name="reply" rows="6" placeholder="관리자 입력" required></textarea>
				</div>

				<button type="submit" class="btn btn-default">Modify</button>
				<button type="submit" onclick="removeAction()" class="btn btn-danger">Remove</button>
				<button type="button" class="btn btn-secondary" onclick="closeModal(this)">list</button>
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


function goToDetailModalForm(element) {
	console.log(element)
	let valueToSend = element.id;
	
	$.ajax({
		url: '/admin/qna/get/'+ valueToSend, // 서버의 URL
		type: 'get',
		data: {
            qnaId: valueToSend,
        },
		success: function(response) {
			var QNAData = response.qna;
			$("#qnaId").val(QNAData.qnaId);
			$("#itemId").val(QNAData.itemId);
			$("#qnaTitle").val(QNAData.qnaTitle);
			$("#qnaContent").text(QNAData.qnaContent);
			$("#qnaCategory").val(QNAData.qnaCategory);
			$("#qnaWriter").val(QNAData.qnaWriter);
			$("#imageSRC").attr("src", "/download/" + QNAData.qnaImageURL);
            $("#imageID").val(QNAData.qnaImageURL);
			var regDate = new Date(QNAData.qnaRegdate);
			var isoDateString = regDate.toISOString().substring(0, 10);
			console.log(isoDateString);
			$('#qnaRegdate').val(isoDateString);
            var upDateDate = new Date(QNAData.qnaUpdateDate);
			var isoDateString = upDateDate.toISOString().substring(0, 10);
			console.log(isoDateString);
			$('#qnaUpdateDate').val(isoDateString);
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