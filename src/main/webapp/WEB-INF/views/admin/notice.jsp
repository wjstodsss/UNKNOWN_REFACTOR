<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<%@include file="includes/header.jsp"%>
<%@include file="includes/leftNav.jsp"%>

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
				            <input type='hidden' name='pageNum' value='<c:out value="${pageMaker.cri.pageNum}"/>' /> 
				            <input type='hidden' name='amount' value='<c:out value="${pageMaker.cri.amount}"/>' />
				            <button class='btn btn-default'>Search</button>
				        </form>
				    </div>
				    
				    <div class="col-lg-8 button-add">	
				     	<a href="/admin/notice/list" type="button" class="btn btn-board btn-xs btn-dark pull-right btn-info col-lg-2 mx-2 my-2"> 오름차순 </a>
				        <a href="/admin/notice/descList" type="button" class="btn btn-board btn-xs pull-right btn-info btn-warning col-lg-2 mx-2 my-2"> 내림차순</a>		       
				    </div>
				</div>
				<!-- end 검색조건 -->
				<button id="regBtn" type="button" class="btn btn-board btn-xs pull-right btn-info col-lg-1 mx-2 my-2" onclick="goToModalForm()"> 새글 </button> 
				<a href="/admin/notice/list" type="button" class="btn btn-xs btn-light pull-right btn-info col-lg-2 mx-2 my-2">검색해제 일반리스트 </a>
				<table width="80%"
					class="table table-striped table-bordered table-hover"
					id="dataTables-example">
					
					<caption class="table-caption">공지 사항</caption>
					<thead>
						<tr>
							<th>순번</th>
                            <th>제목</th>
                            <th>등록일</th>
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

				<form id='actionForm' action="/admin/notice/list" method='get'>
					<input type='hidden' name='pageNum'
						value='${pageMaker.cri.pageNum}'> <input type='hidden'
						name='amount' value='${pageMaker.cri.amount}'>
														<input type='hidden' name='type' value='${pageMaker.cri.type}'>
								<input type='hidden' name='keyword' value='${pageMaker.cri.keyword}'>
				</form>


				<!-- The Modal -->
				<div class="modal" id="myModal">
					<div class="modal-dialog">
						<div class="modal-content">

							<!-- Modal Header -->
							<div class="modal-header">
								<h4 class="modal-title">Modal Heading</h4>
								<button type="button" class="close" onclick="closeModal(this)">&times;</button>
							</div>
							<!-- Modal body -->
							<div class="modal-body-id">처리가 완료되었습니다.</div>

							<!-- Modal footer -->
							<div class="modal-footer">
								<button type="button" class="btn btn-danger" onclick="closeModal(this)">Close</button>
							</div>
						</div>
					</div>
					<!-- end Modal -->


				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
		<!-- /.col-lg-12 -->
	</div>

</div>
<!-- /#page-wrapper -->

</div>
<!-- 등록 모달 -->
<div class="modal" id="formModal" tabindex="-1" role="dialog"
	aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-noticeName" id="cartModalLabel">공지사항 등록</h5>
				<button type="button" class="close" aria-label="Close"
					onclick="closeModal('#formModal')">
					<span aria-hidden="true">&times;</span>
				</button>
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
			<div class="modal-header">
				<h5 class="modal-noticeName" id="cartModalLabel">회원 수정</h5>
				<button type="button" class="close" aria-label="Close"
					onclick="closeModal(this)">
					<span aria-hidden="true">&times;</span>
				</button>
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
					<label for="noticeRegdate">등록일</label> <input type="text"
						id="noticeRegdate" class="form-control" readonly>				
				</div>

                <div class="form-group">
					<label for="noticeUpdateDate">수정일</label> <input type="text"
						id="noticeUpdateDate" class="form-control" readonly>					
				</div>
				
				<button type="submit" class="btn btn-default">Modify</button>
				<button type="submit" onclick="removeAction()" class="btn btn-danger">Remove</button>
				<button type="button" class="btn btn-secondary" onclick="closeModal(this)">list</button>
				<input type="hidden" id="currentPath" name="currentPath" value="">
				</form>
			</div>
		</div>
	</div>
</div>

<%@include file="includes/footer.jsp"%>

<script>
function updateActionUrl() {
    var currentUrl = window.location.href;
    var newPath;

    if (currentUrl.includes("desc")) {
    	newPath = "/admin/notice/descList";
    } else {
    	newPath = "/admin/notice/list";
    }
   
    // 설정한 값으로 hidden input 업데이트
    $("#currentPath").val(extractPageName(newPath));
    return newPath;
}

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