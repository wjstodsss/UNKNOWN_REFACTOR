<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<%@include file="includes/header.jsp"%>
<%@include file="includes/leftNav.jsp"%>
<%@include file="includes/common-cri.jsp"%>

	<div class="col-lg-10">
		<div class="panel panel-default">
			<div class="panel-heading">Benefits List Page</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<!-- 검색조건 -->
				<div class="row">
					<div class="col-lg-12">
						<form id='searchForm' action="/admin/benefits/list" method='get'
							class='searchForm'>
							<select class="custom-select custom-option" name='type'>
								<option value=""
									<c:out value="${pageMaker.cri.type == null?'selected':''}"/>>--</option>
								<option value="B"
									<c:out value="${pageMaker.cri.type eq 'B'?'selected':''}"/>>혜택ID</option>
								<option value="W"
									<c:out value="${pageMaker.cri.type eq 'W'?'selected':''}"/>>작성자</option>
								<option value="D"
									<c:out value="${pageMaker.cri.type eq 'D'?'selected':''}"/>>등록일</option>
							</select> 
							<input type='text' class='custom-keyword' name='keyword'
								value='<c:out value="${pageMaker.cri.keyword}"/>' /> 
							<button class='btn custom-keyword-btn'>Search</button>
							<a href="/admin/benefits/list" type="button"
								class="btn custom-keyword-btn mx-2">검색해제</a>
						</form>
					</div>
				</div>
				<!-- end 검색조건 -->
				<button id="regBtn" type="button"
					class="btn btn-board btn-xs pull-right btn-secondary col-lg-1 mx-2 my-2"
					onclick="goToModalForm()">등록</button>
					
				<table class="table table-bordered able-hover  custom-table">
					<caption class="table-caption">혜택 문의</caption>
					<thead class="button-add">
						<tr>
							<th>
								<div class="custom-sort-component">
									<div class="label">혜택ID</div>
									<div class="sort-icons">
										<a href="#" class="no-anchor-text-decoration"
											data-groupColumn='/benefits' data-sortColumn='benefitsId'
											data-sortType='asc'>▲</a> <a href="#"
											class="no-anchor-text-decoration"
											data-groupColumn='/benefits' data-sortColumn='benefitsId'
											data-sortType='desc'>▼</a>
									</div>
								</div>
							</th>
							<th>
								<div class="custom-sort-component">
									<div class="label">질문 내용</div>
									<div class="sort-icons">
										<a href="#" class="no-anchor-text-decoration"
											data-groupColumn='/benefits' data-sortColumn='benefitsDescription'
											data-sortType='asc'>▲</a> <a href="#"
											class="no-anchor-text-decoration"
											data-groupColumn='/benefits' data-sortColumn='benefitsDescription'
											data-sortType='desc'>▼</a>
									</div>
								</div>
							</th>
							<th>
								<div class="custom-sort-component">
									<div class="label">작성자</div>
									<div class="sort-icons">
										<a href="#" class="no-anchor-text-decoration"
											data-groupColumn='/benefits' data-sortColumn='benefitsWriter'
											data-sortType='asc'>▲</a> <a href="#"
											class="no-anchor-text-decoration"
											data-groupColumn='/benefits' data-sortColumn='benefitsWriter'
											data-sortType='desc'>▼</a>
									</div>
								</div>
							</th>
							<th>
								<div class="custom-sort-component">
									<div class="label">등록일</div>
									<div class="sort-icons">
										<a href="#" class="no-anchor-text-decoration"
											data-groupColumn='/benefits' data-sortColumn='benefitsRegDate'
											data-sortType='asc'>▲</a> <a href="#"
											class="no-anchor-text-decoration"
											data-groupColumn='/benefits' data-sortColumn='benefitsRegDate'
											data-sortType='desc'>▼</a>
									</div>
								</div>
							</th>
							<th>
								<div class="custom-sort-component">
									<div class="label">답변 상태</div>
									<div class="sort-icons">
										<a href="#" class="no-anchor-text-decoration"
											data-groupColumn='/benefits' data-sortColumn='answer'
											data-sortType='asc'>▲</a> <a href="#"
											class="no-anchor-text-decoration"
											data-groupColumn='/benefits' data-sortColumn='answer'
											data-sortType='desc'>▼</a>
									</div>
								</div>
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="benefit" items="${benefits}" varStatus="status">
							<tr class="odd gradeX">
								<td><a href='#' id="${benefit.benefitsId}"
									onclick="goToDetailModalForm(this)">${benefit.benefitsId}</a></td>
								<td class="benefits-description">${benefit.benefitsDescription}</td>
								<td>${benefit.benefitsWriter}</td>
								<td><fmt:formatDate pattern="yyyy-MM-dd"
										value="${benefit.benefitsRegDate}" /></td>
								<td>${benefit.answer != null ? benefit.answer : 'N'}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
	
				<!-- 페이지 처리 -->
				<div class="d-flex justify-content-center">
					<ul class="pagination">
						<c:if test="${pageMaker.prev}">
							<li class="paginate_button"><a class="page-link"
								href="${pageMaker.startPage-1}">Previous</a></li>
						</c:if>
						<c:forEach var="num" begin="${pageMaker.startPage}"
							end="${pageMaker.endPage}">
							<li
								class="paginate_button ${pageMaker.cri.pageNum == num ? 'active': ''} ">
								<a href="${num}">${num}</a>
							</li>
						</c:forEach>
	
						<c:if test="${pageMaker.next}">
							<li class="paginate_button"><a href="${pageMaker.endPage+1}">Next</a></li>
						</c:if>
					</ul>
				</div>
				<!-- end 페이지 처리 -->
				<form id='actionForm' action="/admin/benefits/list" method='get'>
				</form>
	
	
				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
	</div>
	<!-- /.row -->
	<!-- 등록 모달 -->
	<div class="modal" id="formModal" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header row custom-modal-header-row">
					<div class="col-lg-6">
						<h5 class="modal-itemName custom-modal-header-title">혜택 문의</h5>
					</div>
					<div class="col-lg-6">
						<button type="button" class="close" aria-label="Close"
							onclick="closeModal(this)">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
				</div>
				<div class="modal-body">
					<form id="registerForm" name="registerForm" role="form"
						action="register" method="post">
						<div class="form-group">
							<label for="benefitsDescription">질문 내용</label>
							<textarea class="form-control"
								name="benefitsDescription" rows="5" placeholder="질문을 입력하세요"
								required></textarea>
						</div>
						<div class="form-group">
							<label for="benefitsWriter">작성자</label> <input type="text"
								class="form-control" name="benefitsWriter"
								placeholder="작성자를 입력하세요" required>
						</div>
						<button type="submit" class="btn btn-default btn-success">Submit
							Button</button>
						<button type="reset" class="btn btn-secondary">다시 작성</button>
						<button type="button" class="btn btn-secondary"
							onclick="closeModal(this)">닫기</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 답글 모달 -->
	<div class="modal" id="formModal2" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header row custom-modal-header-row">
					<div class="col-lg-6">
						<h5 class="modal-itemName custom-modal-header-title">답글 작성</h5>
					</div>
					<div class="col-lg-6">
						<button type="button" class="close" aria-label="Close"
							onclick="closeModal(this)">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
				</div>
				<div class="modal-body">
					<form id="modifyForm" action="modify" method="post" >
	
						<div class="form-group">
							<label>혜택 ID</label> <input class="form-control" name='benefitsId'
								id='benefitsId' readonly>
						</div>
	
						<div class="form-group">
							<label>질문 내용</label>
							<textarea class="form-control" id='benefitsDescription' rows="5"
								name='benefitsDescription'></textarea>
						</div>
	
	
						<div class="form-group">
							<label>작성자</label> <input class="form-control" id='benefitsWriter'
								name='benefitsWriter'>
						</div>
	
						<div class="form-group">
							<label for="benefitsRegDate">등록일</label> <input type="text"
								id="benefitsRegDate" class="form-control" readonly>
						</div>
	
						<div class="form-group">
							<label for="reply">질문 답변</label>
							<textarea class="form-control" id="reply" name="reply" rows="6"
								placeholder="관리자 입력" required></textarea>
						</div>
	
	
						<button type="submit" class="btn btn-default">Modify</button>
						<button type="submit" onclick="removeAction()"
							class="btn btn-danger">Remove</button>
						<button type="button" class="btn btn-secondary"
							onclick="closeModal(this)">list</button>
						<input type="hidden" id="replyId" name="benefitsReplyId" value="">
						<input type="hidden" id="replyer" name="replyer" value="Admin">
					</form>
	
				</div>
			</div>
		</div>
	</div>

	<%@include file="includes/footer.jsp"%>

	
	<script>
	$(document).ready(function() {
		document.getElementById('registerForm').onsubmit = function() {
			appendCommonHiddenInputs('registerForm');
		};
		document.getElementById('modifyForm').onsubmit = function() {
			appendCommonHiddenInputs('modifyForm');
		};
		
		var typeSelect = document
		.querySelector('.custom-select[name="type"]');
		var keywordInput = document
		.querySelector('.custom-keyword');

		typeSelect.addEventListener('change', updateKeywordInputType);
		updateKeywordInputType();


		function updateKeywordInputType() {
			var selectedOption = typeSelect.value;

			if (selectedOption === 'D') {
				keywordInput.type = 'date';
			} else {
				keywordInput.type = 'text';
			}
		}
	});
	
	
	
	function goToDetailModalForm(element) {
		console.log(element)
		let valueToSend = element.id;
		
		$.ajax({
			url: '/admin/benefits/get/'+ valueToSend, // 서버의 URL
			type: 'get',
			data: {
	            benefitsId: valueToSend,
	        },
			success: function(response) {
				var benefitsData = response.benefits;
				$("#benefitsId").val(benefitsData.benefitsId);
				$("#benefitsDescription").text(benefitsData.benefitsDescription);
				$("#benefitsWriter").val(benefitsData.benefitsWriter);
				var regDate = new Date(benefitsData.benefitsRegDate);
				var isoDateString = regDate.toISOString().substring(0, 10);
				$("#benefitsRegDate").val(isoDateString);
				$("#reply").text("");
				$("#replyId").val("");
				if(response.reply != null) {
					var replyData = response.reply;
					$("#reply").text(replyData.reply);
					$("#replyId").val(replyData.benefitsReplyId);
				}
				$('#formModal2').modal('show');
			},
			error: function(xhr, status, error) {
				console.error('AJAX 요청 실패:', error);
			}
		});
	}
	</script>