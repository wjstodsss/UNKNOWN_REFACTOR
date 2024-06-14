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
						Notice List Page
						
					</div>
					<!-- /.panel-heading -->
					<div class="panel-body">
					<!-- 검색조건 -->
						  <div class="row">       
						    <div class="col-lg-4">                    
						        <form id='searchForm' action="/admin/notice/list" method='get' class='searchForm'>
						            <select class="custom-select" name='type'>
						                <option value=""
						                    <c:out value="${pageMaker.cri.type == null?'selected':''}"/>>--</option>
						                <option value="T"
						                    <c:out value="${pageMaker.cri.type eq 'T'?'selected':''}"/>>제목</option>
						                <option value="D"
						                    <c:out value="${pageMaker.cri.type eq 'D'?'selected':''}"/>>내용</option>
					                    <option value="I"
						                    <c:out value="${pageMaker.cri.type eq 'I'?'selected':''}"/>>등록번호</option>
						                <option value="TD"
						                    <c:out value="${pageMaker.cri.type eq 'TD'?'selected':''}"/>>제목 or 내용</option>
						                <option value="TDI"
						                    <c:out value="${pageMaker.cri.type eq 'TDI'?'selected':''}"/>>제목 or 내용 or 등록번호</option>
						            </select> 
						            <input type='text' class='custom-keyword' name='keyword' value='<c:out value="${pageMaker.cri.keyword}"/>' /> 
						            <button class='btn custom-keyword-btn'>Search</button>
						            <a href="/admin/notice/list" type="button"
		                           class="btn custom-keyword-btn mx-2">검색해제</a>
						        </form>
						    </div>
						</div>
						<!-- end 검색조건 -->
						 <button id="regBtn" type="button"
						class="btn btn-board btn-xs pull-right btn-secondary col-lg-1 mx-2 my-2"
						onclick="goToModalForm()"> 등록 </button>
						<table class="table table-bordered able-hover  custom-table">
							<caption class="table-caption">공지 사항</caption>
							<thead class="button-add">
								<tr>
									<th>
										<div class="custom-sort-component">
					                        <div class="label">순번</div>
					                        <div class="sort-icons">
					                            <a href="#" class="no-anchor-text-decoration"
					                               data-groupColumn='/notice' data-sortColumn='noticeId'
					                               data-sortType='asc'>▲</a>
					                            <a href="#"
					                               class="no-anchor-text-decoration"
					                               data-groupColumn='/notice' data-sortColumn='noticeId'
					                               data-sortType='desc'>▼</a>
					                        </div>
		                        		</div>
									</th>
		                            <th>
		                            	<div class="custom-sort-component">
					                        <div class="label">제목</div>
					                        <div class="sort-icons">
					                            <a href="#" class="no-anchor-text-decoration"
					                               data-groupColumn='/notice' data-sortColumn='noticeTitle'
					                               data-sortType='asc'>▲</a>
					                            <a href="#"
					                               class="no-anchor-text-decoration"
					                               data-groupColumn='/notice' data-sortColumn='noticeTitle'
					                               data-sortType='desc'>▼</a>
					                        </div>
		                        		</div>
		                            </th>
		                            <th>
		                            	<div class="custom-sort-component">
					                        <div class="label">등록일</div>
					                        <div class="sort-icons">
					                            <a href="#" class="no-anchor-text-decoration"
					                               data-groupColumn='/notice' data-sortColumn='noticeRegdate'
					                               data-sortType='asc'>▲</a>
					                            <a href="#"
					                               class="no-anchor-text-decoration"
					                               data-groupColumn='/notice' data-sortColumn='noticeRegdate'
					                               data-sortType='desc'>▼</a>
					                        </div>
		                        		</div>
		                            </th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="notice" items="${notices}">
									<tr class="odd gradeX">
										<td><a href='#' id="${notice.noticeId}" onclick="goToDetailModalForm(this)">${notice.noticeId}</a></td>
										<td>${notice.noticeTitle}</td>
										<td><fmt:formatDate pattern="yyyy-MM-dd" value="${notice.noticeRegdate}" /></td>
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
		            <form id='actionForm' action="/admin/notice/list" method='get'>
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
	                    <h5 class="modal-itemName custom-modal-header-title">공지사항 등록</h5>
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
							<label for="noticeTitle">제목</label> <input type="text"
								class="form-control" name="noticeTitle"
								placeholder="제목을 입력하세요" required>
						</div>
	
						<div class="form-group">
							<label for="noticeDescription">내용</label> <textarea 
	                        class="form-control" name="noticeDescription" rows="3" 
							placeholder="내용을 입력하세요" required></textarea>
						</div>
	
						<div class="form-group">
							<label for="noticeBrand">브랜드</label> <input type="text"
								class="form-control" name="noticeBrand"
								placeholder="브랜드 관련 공지시 브랜드를 입력하세요">
						</div>
	
	                    <div class="form-group">
	                        <label for="uploadFile">uploadFile</label>
	                        <input type="file" id="uploadFile" name="uploadFile" multiple>
	                        <input type="hidden" value='defaltImage.jpg' name='noticeImageURL'>
	                    </div>
	
						<button type="submit" class="btn btn-default btn-success">Submit Button</button>
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
	                    <h5 class="modal-itemName custom-modal-header-title">공지사항 수정</h5>
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
							<label>게시글 ID</label> <input class="form-control" name='noticeId' id='noticeId' readonly>
						</div>
						<div class="form-group">
							<label>제목</label> <input class="form-control"
								id='noticeTitle' name='noticeTitle'>
		                </div>
		
						<div class="form-group">
							<label>내용</label> <textarea class="form-control"
								id='noticeDescription' rows="3" name='noticeDescription'></textarea>
						</div>
						<div class="form-group">
							<label>브랜드</label> <input class="form-control"
								id='noticeBrand' name='noticeBrand'>
		                </div>
		
		                <div class="form-group">
		                    <input type='file' name='uploadFile' multiple>
		                </div>
		
		                <div class="form-group">
		                    <label>변경전 이미지</label>
		                    <img src="" alt="공지이미지" style="max-width: 400px" id='imageSRC'>
		                    <input type="hidden" name='noticeImageURL' id='imageID' name='noticeImageURL'>        
		                </div>
					
		                <div class="form-group">
							<label for="noticeRegdate">등록일</label>
							<input type="text" id="noticeRegdate" class="form-control" readonly>				
						</div>
		
		                <div class="form-group">
							<label for="noticeUpdateDate">수정일</label>
							<input type="text" id="noticeUpdateDate" class="form-control" readonly>					
						</div>
						
						<button type="submit" class="btn btn-default">Modify</button>
						<button type="submit" onclick="removeAction()" class="btn btn-danger">Remove</button>
						<button type="button" class="btn btn-secondary" onclick="closeModal(this)">list</button>			
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<%@include file="includes/footer.jsp"%>
	
	<script>
	function goToDetailModalForm(element) {
		console.log(element)
		
		let valueToSend = element.id;
		console.log(valueToSend)
		$.ajax({
			url: '/admin/notice/get/'+ valueToSend, // 서버의 URL
			type: 'get', // GET 또는 POST 요청
			data: { noticeId: valueToSend }, // post.id를 서버로 전달
			success: function(response) {
				$("#noticeId").val(response.noticeId);
				$("#noticeTitle").val(response.noticeTitle);
				$("#noticeDescription").text(response.noticeDescription);
				$("#noticeBrand").val(response.noticeBrand);
				$("#imageSRC").attr("src", "/download/" + response.noticeImageURL);
	            $("#imageID").val(response.noticeImageURL);
				var regDate = new Date(response.noticeRegdate);
				var isoDateString = regDate.toISOString().substring(0, 10);
				console.log(isoDateString);
				$('#noticeRegdate').val(isoDateString);
	            var upDateDate = new Date(response.noticeUpdateDate);
				var isoDateString = upDateDate.toISOString().substring(0, 10);
				console.log(isoDateString);
				$('#noticeUpdateDate').val(isoDateString);
				$('#formModal2').modal('show');
			},
			error: function(xhr, status, error) {
				console.error('AJAX 요청 실패:', error);
			}
		});
	}
	</script>