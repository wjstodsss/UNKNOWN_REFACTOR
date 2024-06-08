<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<%@include file="includes/header.jsp"%>
<%@include file="includes/leftNav.jsp"%>

	<div class="col-lg-10">
		<div class="panel panel-default">
			<div class="panel-heading">
				카테고리 List Page
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
			<!-- 검색조건 -->
				  <div class="row">       
				    <div class="col-lg-12">                    
				        <form id='searchForm' action="/admin/itemCate/list" method='get' class='searchForm'>
				            <select class="custom-select" name='type'>
				                <option value=""
				                    <c:out value="${pageMaker.cri.type == null?'selected':''}"/>>--</option>
				                <option value="I"
				                    <c:out value="${pageMaker.cri.type eq 'I'?'selected':''}"/>>카테고리ID</option>
				                <option value="N"
				                    <c:out value="${pageMaker.cri.type eq 'N'?'selected':''}"/>>카테고리명</option>
				          
				            </select> 
				            <input type='text' class='custom-keyword' name='keyword' value='<c:out value="${pageMaker.cri.keyword}"/>' /> 
				            <input type='hidden' name='pageNum' value='<c:out value="${pageMaker.cri.pageNum}"/>' /> 
				            <input type='hidden' name='amount' value='<c:out value="${pageMaker.cri.amount}"/>' />
				            <button class='btn btn-default'>Search</button>
				        </form>
				    </div>
				    
				    <div class="col-lg-12 button-add">
				    
					    <a href="/admin/itemCate/list" type="button" class="btn btn-board btn-xs btn-dark pull-right btn-info col-lg-1.5 mx-2 my-2" data-sortColumn='cateName'>이름 오름차순 </a>
				        <a href="/admin/itemCate/descList" type="button" class="btn btn-board btn-xs pull-right btn-info btn-warning col-lg-1.5 mx-2 my-2" data-sortColumn='cateName' >이름 내림차순</a>
				     	<a href="/admin/itemCate/list" type="button" class="btn btn-board btn-xs btn-dark pull-right btn-info col-lg-1.5 mx-2 my-2" data-sortColumn='cateCode'>순번 오름차순 </a>
				        <a href="/admin/itemCate/descList" type="button" class="btn btn-board btn-xs pull-right btn-info btn-warning col-lg-1.5 mx-2 my-2" data-sortColumn='cateCode' >순번 내림차순 </a>							      
				    </div>
				   
					</div>
					<!-- end 검색조건 --> 
					<button id="regBtn" type="button" class="btn btn-board btn-xs pull-right btn-info col-lg-2 mx-2 my-2" onclick="goToModalForm()"> 카테고리 등록 </button>
					<a href="/admin/itemCate/list" type="button" class="btn btn-board btn-xs btn-light pull-right btn-info col-lg-2 mx-2 my-2">검색해제 일반리스트 </a>
					<table class="table table-striped table-bordered table-hover" id="dataTables-example">
						
						<caption class="table-caption">카테고리</caption>
						<thead>
							<tr>
								<th class="col-lg-2">카테고리ID</th>
	                            <th class="col-lg-4">카테고리명</th>
	                            <th class="col-lg-2">카테고리계층</th>
	                            <th class="col-lg-2">상위카테고리</th>
	                           
							</tr>
						</thead>
						<tbody>
							 <c:forEach var="itemCate" items="${itemCates}" varStatus="status">
						      
						        <tr class="odd gradeX">
								    <td><a href='#' id="${itemCate.cateCode}" onclick="goToDetailModalForm(this)">${itemCate.cateCode}</a></td>
								    <td>${itemCate.cateName}</td>
								    <td>${itemCate.tier}</td>
								    <td>${itemCate.cateParent}</td>
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
					<form id='actionForm' action="/admin/itemCate/list" method='get'>
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
					<h5 class="modal-cateName" id="cartModalLabel">카테고리 등록</h5>
					<button type="button" class="close" aria-label="Close"
						onclick="closeModal('#formModal')">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form id="registerForm" name="registerForm" role="form" action="register" method="post">				
						<div class="form-group">
							<label>카테고리계층</label>
							<select id="categorySelect" class="btn btn-outline-dark my-2" onchange="handleCategoryChange()">
									<option disabled selected>계층선택</option>
						  			<option>1</option>
						  			<option>2</option>
						  	</select>
						
						</div> 
						<div class="form-group">
							<label>상위카테고리ID</label>
							<input type="text" class="form-control" name="cateParent" id="inputCateName" placeholder="상위카테고리 코드 입력하세요/ 1계층인 등록 경우 비워두세요">
						</div>
						<div class="form-group">
							<label>카테고리ID</label>
							<input type="text" class="form-control" name="cateCode" placeholder="카테고리ID 입력하세요" required>
						</div>
						<div class="form-group">
							<label>카테고리명</label>
							<input type="text" class="form-control" name="cateName" placeholder="카테고리명을 입력하세요" required>
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
					<h5 class="modal-cateName" id="cartModalLabel">카테고리 수정</h5>
					<button type="button" class="close" aria-label="Close"
						onclick="closeModal(this)">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form id="modifyForm" action="modify" method="post">
		        
					<div class="form-group">
						<label>카테고리ID</label> <input class="form-control" name='cateCode' id='cateCode'>
					</div>
					
					<div class="form-group">
						<label>카테고리계층</label> <input class="form-control" name='tier' id='tier'>
					</div>
					
					<div class="form-group">
						<label>카테고리명</label> <input class="form-control" name='cateName' id='cateName'>
					</div>
					
					<div class="form-group">
						<label>상위카테고리ID</label>
						<input class="form-control" name='cateParent' id='cateParent'>
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
    	newPath = "/admin/itemCate/descList";
    } else {
    	newPath = "/admin/itemCate/list";
    }
    $("#currentPath").val(extractPageName(newPath));
    return newPath;
}

function handleCategoryChange() {
    const categorySelect = document.getElementById('categorySelect');
    const inputCateName = document.getElementById('inputCateName');
    
    if (categorySelect.value === '1') {
        inputCateName.value = ''; // 내용 클리어
        inputCateName.readOnly = true; // 읽기 전용으로 설정
        inputCateName.placeholder = '1계층인 등록 경우 비워두세요';
    } else {
        inputCateName.readOnly = false; // 읽기 전용 해제
        inputCateName.placeholder = '상위카테고리 코드 입력하세요';
    }
}


function goToDetailModalForm(element) {
	let valueToSend = element.id;
	$.ajax({
		url: '/admin/itemCate/get/'+ valueToSend,
		type: 'get',
		success: function(itemCateData) {
			$("#tier").val(itemCateData.tier);
			$("#cateCode").val(itemCateData.cateCode);
			$("#cateName").val(itemCateData.cateName);
			$("#cateParent").val(itemCateData.cateParent);			
			$('#formModal2').modal('show');
		},
		error: function(xhr, status, error) {
			console.error('AJAX 요청 실패:', error);
		}
	});
}
</script>