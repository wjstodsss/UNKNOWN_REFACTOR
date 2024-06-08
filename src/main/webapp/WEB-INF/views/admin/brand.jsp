<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<%@include file="includes/header.jsp"%>
<%@include file="includes/leftNav.jsp"%>

	<div class="col-lg-10">
		<div class="panel panel-default">
			<div class="panel-heading">
				브랜드 List Page
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
			<!-- 검색조건 -->
				  <div class="row">       
				    <div class="col-lg-12">                    
				        <form id='searchForm' action="/admin/brand/list" method='get' class='searchForm'>
				            <select class="custom-select" name='type'>
				                <option value=""
				                    <c:out value="${pageMaker.cri.type == null?'selected':''}"/>>--</option>
				                <option value="I"
				                    <c:out value="${pageMaker.cri.type eq 'I'?'selected':''}"/>>브랜드ID</option>
				                <option value="N"
				                    <c:out value="${pageMaker.cri.type eq 'N'?'selected':''}"/>>브랜드명</option>
				          
				            </select> 
				            <input type='text' class='custom-keyword' name='keyword' value='<c:out value="${pageMaker.cri.keyword}"/>' /> 
				            <input type='hidden' name='pageNum' value='<c:out value="${pageMaker.cri.pageNum}"/>' /> 
				            <input type='hidden' name='amount' value='<c:out value="${pageMaker.cri.amount}"/>' />
				            <button class='btn btn-default'>Search</button>
				        </form>
				    </div>
				    
				    <div class="col-lg-12 button-add">
				    
					    <a href="/admin/brand/list" type="button" class="btn btn-board btn-xs btn-dark pull-right btn-info col-lg-1.5 mx-2 my-2" data-sortColumn='brandName'>이름 오름차순 </a>
				        <a href="/admin/brand/descList" type="button" class="btn btn-board btn-xs pull-right btn-info btn-warning col-lg-1.5 mx-2 my-2" data-sortColumn='brandName' >이름 내림차순</a>
				     	<a href="/admin/brand/list" type="button" class="btn btn-board btn-xs btn-dark pull-right btn-info col-lg-1.5 mx-2 my-2" data-sortColumn='brandId'>순번 오름차순 </a>
				        <a href="/admin/brand/descList" type="button" class="btn btn-board btn-xs pull-right btn-info btn-warning col-lg-1.5 mx-2 my-2" data-sortColumn='brandId' >순번 내림차순 </a>							      
				    </div>
				   
					</div>
					<!-- end 검색조건 --> 
					<button id="regBtn" type="button" class="btn btn-board btn-xs pull-right btn-info col-lg-2 mx-2 my-2" onclick="goToModalForm()"> 브랜드 등록 </button>
					<a href="/admin/brand/list" type="button" class="btn btn-board btn-xs btn-light pull-right btn-info col-lg-2 mx-2 my-2">검색해제 일반리스트 </a>
					<table class="table table-striped table-bordered table-hover" id="dataTables-example">
						
						<caption class="table-caption">브랜드</caption>
						<thead>
							<tr>
								<th class="col-lg-1">브랜드ID</th>
	                            <th class="col-lg-1">브랜드명</th>
	                            <th class="col-lg-6">브랜드소개</th>
	                            <th class="col-lg-2">등록일</th>
	                            <th class="col-lg-2">수정일</th>
							</tr>
						</thead>
						<tbody>
							 <c:forEach var="brand" items="${brands}" varStatus="status">
						        <!-- 현재 리뷰에 대응하는 리플 -->
						        <tr class="odd gradeX">
								    <td><a href='#' id="${brand.brandId}" onclick="goToDetailModalForm(this)">${brand.brandId}</a></td>
								    <td>${brand.brandName}</td>
								    <td>${brand.brandIntro}</td>
								    <td><fmt:formatDate pattern="yyyy-MM-dd" value="${brand.regDate != null ? brand.regDate : '기본 등록일'}" /></td>
									<td><fmt:formatDate pattern="yyyy-MM-dd" value="${brand.updateDate != null ? brand.updateDate : '기본 수정일'}" /></td>
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
					<form id='actionForm' action="/admin/brand/list" method='get'>
						<input type='hidden' name='pageNum' value='${pageMaker.cri.pageNum}'> 
						<input type='hidden' name='amount' value='${pageMaker.cri.amount}'>
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
					<h5 class="modal-brandName" id="cartModalLabel">브랜드 등록</h5>
					<button type="button" class="close" aria-label="Close"
						onclick="closeModal('#formModal')">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form id="registerForm" name="registerForm" role="form" action="register" method="post">				
						<div class="form-group">
							<label>브랜드명</label>
							<input type="text" class="form-control" name="brandName" placeholder="브랜드명을 입력하세요" required>
						</div> 
						<div class="form-group">
							<label>브랜드소개</label>
							<textarea class="form-control" name="brandIntro" rows="5" placeholder="소개글을 입력하세요" required></textarea>
						</div>
						
						<button type="submit" class="btn btn-default btn-success">Submit Button</button>
						<button type="reset" class="btn btn-secondary">다시 작성</button>
						<button type="button" class="btn btn-secondary" onclick="closeModal(this)">list</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- 수정 모달 -->
	<div class="modal" id="formModal2" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-brandName" id="cartModalLabel">브랜드 수정</h5>
					<button type="button" class="close" aria-label="Close"
						onclick="closeModal(this)">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form id="modifyForm" action="modify" method="post">
		        
					<div class="form-group">
						<label>브랜드ID</label> <input class="form-control" name='brandId' id='brandId' readonly>
					</div>
					
					<div class="form-group">
						<label>브랜드명</label> <input class="form-control" name='brandName' id='brandName'>
					</div>
	
					<div class="form-group">
						<label>브랜드 소개</label> <textarea class="form-control" id='brandIntro' rows="5" name='brandIntro'></textarea>
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
    	newPath = "/admin/brand/descList";
    } else {
    	newPath = "/admin/brand/list";
    }
    $("#currentPath").val(extractPageName(newPath));
    return newPath;
}

function goToDetailModalForm(element) {
	let valueToSend = element.id;
	$.ajax({
		url: '/admin/brand/get/'+ valueToSend,
		type: 'get',
		data: {
            brandId: valueToSend,
        },
		success: function(brandData) {
			$("#brandId").val(brandData.brandId);
			$("#brandName").val(brandData.brandName);
			$("#brandIntro").text(brandData.brandIntro);			
			$('#formModal2').modal('show');
		},
		error: function(xhr, status, error) {
			console.error('AJAX 요청 실패:', error);
		}
	});
}
</script>