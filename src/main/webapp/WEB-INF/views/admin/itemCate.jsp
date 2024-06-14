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
				             	<button class='btn custom-keyword-btn'>Search</button>
	                        	<a href="/admin/itemCate/list" type="button"
	                           class="btn custom-keyword-btn mx-2">검색해제</a>
					        </form>
					    </div>
					</div>
					<!-- end 검색조건 --> 
					<button id="regBtn" type="button"
					class="btn btn-board btn-xs pull-right btn-secondary col-lg-1 mx-2 my-2"
					onclick="goToModalForm()"> 등록 </button>
					
					<table class="table table-breviewed able-hover  custom-table">
						<caption class="table-caption">카테고리</caption>
						<thead class="button-add">
							<tr>
								<th class="col-lg-2">
									<div class="custom-sort-component">
			                        <div class="label">카테고리ID</div>
				                        <div class="sort-icons">
				                            <a href="#" class="no-anchor-text-decoration"
				                               data-groupColumn='/itemCate' data-sortColumn='cateCode'
				                               data-sortType='asc'>▲</a>
				                            <a href="#"
				                               class="no-anchor-text-decoration"
				                               data-groupColumn='/itemCate' data-sortColumn='cateCode'
				                               data-sortType='desc'>▼</a>
				                        </div>
			                        </div>
								</th>
	                            <th class="col-lg-4">
		                            <div class="custom-sort-component">
				                        <div class="label">카테고리명</div>
				                        <div class="sort-icons">
				                            <a href="#" class="no-anchor-text-decoration"
				                               data-groupColumn='/itemCate' data-sortColumn='cateName'
				                               data-sortType='asc'>▲</a>
				                            <a href="#"
				                               class="no-anchor-text-decoration"
				                               data-groupColumn='/itemCate' data-sortColumn='cateName'
				                               data-sortType='desc'>▼</a>
				                        </div>
		                        	</div>
	                            </th>
	                            <th class="col-lg-2">
		                            <div class="custom-sort-component">
				                        <div class="label">카테고리계층</div>
				                        <div class="sort-icons">
				                            <a href="#" class="no-anchor-text-decoration"
				                               data-groupColumn='/itemCate' data-sortColumn='tier'
				                               data-sortType='asc'>▲</a>
				                            <a href="#"
				                               class="no-anchor-text-decoration"
				                               data-groupColumn='/itemCate' data-sortColumn='tier'
				                               data-sortType='desc'>▼</a>
				                        </div>
		                        	</div>
	                            </th>
	                            <th class="col-lg-2">
		                            <div class="custom-sort-component">
				                        <div class="label">상위카테고리</div>
				                        <div class="sort-icons">
				                            <a href="#" class="no-anchor-text-decoration"
				                               data-groupColumn='/itemCate' data-sortColumn='cateParent'
				                               data-sortType='asc'>▲</a>
				                            <a href="#"
				                               class="no-anchor-text-decoration"
				                               data-groupColumn='/itemCate' data-sortColumn='cateParent'
				                               data-sortType='desc'>▼</a>
				                        </div>
		                        	</div>
	                            </th>
	
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
	            <form id='actionForm' action="/admin/itemCate/list" method='get'>
	            </form>
				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<!-- 등록 모달 -->
	<div class="modal" id="formModal" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header row custom-modal-header-row">
                <div class="col-lg-6">
                    <h5 class="modal-itemName custom-modal-header-title">카테고리 등록</h5>
                </div>
                <div class="col-lg-6">
                    <button type="button" class="close" aria-label="Close"
                            onclick="closeModal(this)">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>				<div class="modal-body">
					<form id="registerForm" name="registerForm" role="form" action="register" method="post" >				
						<div class="form-group">
							<label>카테고리계층</label>
							<select id="categorySelect" name="tier" class="btn btn-outline-dark my-2" onchange="handleCategoryChange()">
			                    <option value="" disabled selected>계층선택</option>
			                    <option value="1">1</option>
			                    <option value="2">2</option>
			                </select>
						
						</div> 
						<div class="form-group">
							<label>상위카테고리ID</label>
							<input type="text" class="form-control" name="cateParent" id="inputCateName" placeholder="상위카테고리 코드 입력하세요/ 1계층 등록인 경우 비워두세요" >
						</div>
						<div class="form-group">
							<label>카테고리ID</label>
							<input type="text" class="form-control" name="cateCode" placeholder="카테고리ID 입력하세요" required>
							<div class="input-group-append my-2">
					            <button type="button" class="btn btn-default btn-dark" onclick="checkCateCode()">카테고리ID 확인</button>
					        </div>
						</div>
						
						<div class="form-group">
							<label>카테고리명</label>
							<input type="text" class="form-control" name="cateName" placeholder="카테고리명을 입력하세요" required>
						</div>

						<button type="submit" class="btn btn-default btn-success" id="submitBtn" onclick="return validateForm()" disabled>Submit Button</button>
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
				<div class="modal-header row custom-modal-header-row">
	                <div class="col-lg-6">
	                    <h5 class="modal-itemName custom-modal-header-title">카테고리 수정</h5>
	                </div>
	                <div class="col-lg-6">
	                    <button type="button" class="close" aria-label="Close"
	                            onclick="closeModal(this)">
	                        <span aria-hidden="true">&times;</span>
	                    </button>
	                </div>
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
					
					</form>
				
				</div>
			</div>
		</div>
	</div>
	

<%@include file="includes/footer.jsp"%>

<script>
function checkCateCode() {
    var cateCode = document.forms["registerForm"]["cateCode"].value;
    if (cateCode === "") {
        alert("카테고리ID를 입력하세요.");
        return false;
    }
    $.ajax({
		url: '/admin/itemCate/checkCateCode/'+ cateCode, // 서버의 URL
		type: 'get', // GET 또는 POST 요청
		success: function(response)  {
            if (response.result) {
                alert("사용 가능한 아이디입니다.");
                document.getElementById("submitBtn").disabled = false;
            } else {
                alert("이미 사용중인 아이디입니다.");
                document.getElementById("submitBtn").disabled = true;
            }
        },
		error: function(xhr, status, error) {
			console.error('AJAX 요청 실패:', error);
			document.getElementById("submitBtn").disabled = true;
		}
	});

    
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

function validateForm() {
    const categorySelect = document.getElementById("categorySelect");

    if (categorySelect.value === "계층선택" || categorySelect.value === "") {
        alert("계층을 선택해주세요.");
        return false;
    }

    return true;
}

function submitForm() {
    var form = document.getElementById("registerForm");
    form.submit();
}

function handleCategoryChange() {
    var categorySelect = document.getElementById("categorySelect");
    var inputCateName = document.getElementById("inputCateName");

    if (categorySelect.value === "1") {
    	inputCateName.required = false;
        inputCateName.value = "";
        inputCateName.disabled = true;
    } else {
        inputCateName.disabled = false;
        inputCateName.required = true;
    }
}
</script>