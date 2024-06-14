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
					Item List Page
					
				</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
				<!-- 검색조건 -->
					  <div class="row">       
					    <div class="col-lg-4">                    
					        <form id='searchForm' action="/admin/item/list" method='get' class='searchForm'>
					            <select class="custom-select" name='type'>
					                <option value=""
					                    <c:out value="${pageMaker.cri.type == null?'selected':''}"/>>--</option>
					                <option value="P"
					                    <c:out value="${pageMaker.cri.type eq 'P'?'selected':''}"/>>상품명</option>
					                <option value="B"
					                    <c:out value="${pageMaker.cri.type eq 'B'?'selected':''}"/>>브랜드명</option>
				                    <option value="C"
					                    <c:out value="${pageMaker.cri.type eq 'C'?'selected':''}"/>>카테고리명</option>
					                <option value="I"
					                    <c:out value="${pageMaker.cri.type eq 'I'?'selected':''}"/>>상품번호</option>
					            </select> 
					            <input type='text' class='custom-keyword' name='keyword' value='<c:out value="${pageMaker.cri.keyword}"/>' /> 
					            <button class='btn custom-keyword-btn'>Search</button>
	                        	<a href="/admin/item/list" type="button" class="btn custom-keyword-btn mx-2">검색해제</a>
					        </form>
					    </div>
					</div>
					<!-- end 검색조건 -->
					<button id="regBtn" type="button" class="btn btn-board btn-xs pull-right btn-secondary col-lg-1 mx-2 my-2" onclick="goToModalForm(); openSelection('brand', 1, 'register')"> 등록 </button>
		            <table class="table table-breviewed able-hover  custom-table">
		            
						
						<caption class="table-caption">상품</caption>
						<thead class="button-add">
							<tr>
								<th>
									<div class="custom-sort-component">
						                <div class="label">상품ID</div>
						                <div class="sort-icons">
						                    <a href="#" class="no-anchor-text-decoration"
						                       data-groupColumn='/item' data-sortColumn='itemId'
						                       data-sortType='asc'>▲</a>
						                    <a href="#"
						                       class="no-anchor-text-decoration"
						                       data-groupColumn='/item' data-sortColumn='itemId'
						                       data-sortType='desc'>▼</a>
						                </div>
						            </div>
								</th>
								<th>
									상품이미지
								</th>
	                            <th>
	                            	<div class="custom-sort-component">
						                <div class="label">상품명</div>
						                <div class="sort-icons">
						                    <a href="#" class="no-anchor-text-decoration"
						                       data-groupColumn='/item' data-sortColumn='itemName'
						                       data-sortType='asc'>▲</a>
						                    <a href="#"
						                       class="no-anchor-text-decoration"
						                       data-groupColumn='/item' data-sortColumn='itemName'
						                       data-sortType='desc'>▼</a>
						                </div>
						            </div>
	                            </th>
	                            <th>
	                            	<div class="custom-sort-component">
						                <div class="label">상품재고</div>
						                <div class="sort-icons">
						                    <a href="#" class="no-anchor-text-decoration"
						                       data-groupColumn='/item' data-sortColumn='itemStock'
						                       data-sortType='asc'>▲</a>
						                    <a href="#"
						                       class="no-anchor-text-decoration"
						                       data-groupColumn='/item' data-sortColumn='itemStock'
						                       data-sortType='desc'>▼</a>
						                </div>
						            </div>
	                            </th>
	                            <th>
	                            	<div class="custom-sort-component">
						                <div class="label">가격</div>
						                <div class="sort-icons">
						                    <a href="#" class="no-anchor-text-decoration"
						                       data-groupColumn='/item' data-sortColumn='itemPrice'
						                       data-sortType='asc'>▲</a>
						                    <a href="#"
						                       class="no-anchor-text-decoration"
						                       data-groupColumn='/item' data-sortColumn='itemPrice'
						                       data-sortType='desc'>▼</a>
						                </div>
						            </div>
	                            </th>
	                            <th>
	                            	<div class="custom-sort-component">
						                <div class="label">할인율</div>
						                <div class="sort-icons">
						                    <a href="#" class="no-anchor-text-decoration"
						                       data-groupColumn='/item' data-sortColumn='itemDiscount'
						                       data-sortType='asc'>▲</a>
						                    <a href="#"
						                       class="no-anchor-text-decoration"
						                       data-groupColumn='/item' data-sortColumn='itemDiscount'
						                       data-sortType='desc'>▼</a>
						                </div>
						            </div>
	                            </th>
	                            <th>
	                            	<div class="custom-sort-component">
						                <div class="label">브랜드명</div>
						                <div class="sort-icons">
						                    <a href="#" class="no-anchor-text-decoration"
						                       data-groupColumn='/item' data-sortColumn='brandName'
						                       data-sortType='asc'>▲</a>
						                    <a href="#"
						                       class="no-anchor-text-decoration"
						                       data-groupColumn='/item' data-sortColumn='brandName'
						                       data-sortType='desc'>▼</a>
						                </div>
						            </div>
	                            </th>
	                            <th>
	                            	<div class="custom-sort-component">
						                <div class="label">카테고리명</div>
						                <div class="sort-icons">
						                    <a href="#" class="no-anchor-text-decoration"
						                       data-groupColumn='/item' data-sortColumn='cateName'
						                       data-sortType='asc'>▲</a>
						                    <a href="#"
						                       class="no-anchor-text-decoration"
						                       data-groupColumn='/item' data-sortColumn='cateName'
						                       data-sortType='desc'>▼</a>
						                </div>
						            </div>
	                            </th>
	                            <th>
	                            	<div class="custom-sort-component">
						                <div class="label">등록일</div>
						                <div class="sort-icons">
						                    <a href="#" class="no-anchor-text-decoration"
						                       data-groupColumn='/item' data-sortColumn='regDate'
						                       data-sortType='asc'>▲</a>
						                    <a href="#"
						                       class="no-anchor-text-decoration"
						                       data-groupColumn='/item' data-sortColumn='regDate'
						                       data-sortType='desc'>▼</a>
						                </div>
						            </div>
	                            </th>
	                            <th>
	                            	<div class="custom-sort-component">
						                <div class="label">판매/품절</div>
						                <div class="sort-icons">
						                    <a href="#" class="no-anchor-text-decoration"
						                       data-groupColumn='/item' data-sortColumn='itemStateId'
						                       data-sortType='asc'>▲</a>
						                    <a href="#"
						                       class="no-anchor-text-decoration"
						                       data-groupColumn='/item' data-sortColumn='itemStateId'
						                       data-sortType='desc'>▼</a>
						                </div>
						            </div>
	                            </th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${items}">
								<tr class="odd gradeX">
									<td><a href='#' id="${item.itemId}" onclick="goToDetailModalForm(this); openSelection('brand', 1, 'update')">${item.itemId}</a></td>
									<td><img src="/download/${item.itemImageURL}" alt="상품이미지" style="max-width: 70px"></td>
									<td>${item.itemName}</td>                             
	                                <td>${item.itemStock}</td>
	                                <td>${item.itemPrice}</td>
	                                <td>${item.itemDiscount}</td>
	                                <td>${item.brandName}</td>
	                                <td>${item.cateName}</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd" value="${item.regDate}" /></td>
									<td>${item.itemState}</td>
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
		            <form id='actionForm' action="/admin/item/list" method='get'>
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
	<div class="modal" id="formModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog mx-auto" role="document">
			<div class="modal-content" style="width: 500px;">
				<div class="modal-header row custom-modal-header-row">
	                <div class="col-lg-6">
	                    <h5 class="modal-itemName custom-modal-header-title">item 등록</h5>
	                </div>
	                <div class="col-lg-6">
	                    <button type="button" class="close" aria-label="Close"
	                            onclick="closeModal(this)">
	                        <span aria-hidden="true">&times;</span>
	                    </button>
	                </div>
	            </div>
				<div class="modal-body row">
					<form class="col-lg-6" id="registerForm" name="registerForm" role="form" action="register" method="post" enctype="multipart/form-data">
							 <div class="form-group">
								<label>상품명</label> <input type="text"
									class="form-control" name="itemName"
									placeholder="상품명을 입력하세요" required>
							 </div>
		                     <div class="form-group">
			                 <label>브랜드</label>
			                 
			                 <div class="form-group">
	                            <input type="text" class="form-control" id="brandRegName" placeholder="(우측 또는 하단)브랜드 목록에서 선택해주세요" readonly required>
	                            <input type="hidden" id="brandRegId" name="brandId">	                       
	                        </div>
				                
				             </div>
		                     <div class="form-group">
		                        <label>제조연월일</label>
								<input type="date" class="form-control" name="mnfcYear" placeholder="제조연월일" required>
		                     </div>
		                     <div class="form-group">
		                        <label>제조사</label> <input class="form-control"
		                             name='manufacturer'placeholder="제조사" required>
		                     </div>
		                     
		                     <div class="form-group">
								  <select id="categorySelect" class="btn btn-outline-dark my-2" onchange="openSelection('itemCate', 1, 'register')">
								  		<option disabled selected>카테고리</option>
									    <c:forEach var="category" items="${categorys}">
									      	<option value="${category.cateCode}">${category.cateName}</option>
									    </c:forEach>
								  </select>
							</div>
							<div class="form-group row">
								<div class="form-group-append col-lg-6">
									  <label>카테고리ID</label>
									  <input class="form-control" id="categoryRegId" name="cateCode" placeholder="카테고리" readonly required>
								</div>
								<div class="form-group-append col-lg-6">
									  <label>카테고리명</label>
									  <input class="form-control" id="categoryRegName" name="cateName" placeholder="카테고리" readonly required>
								</div>
		                     </div>
							 
							 
	                     <div class="form-group">
	                        <label>가격</label> <input class="form-control" name='itemPrice'placeholder="가격" onchange="validateInt(this)" required>
	                     </div>
	                     <div class="form-group">
	                        <label>재고</label> <input class="form-control"
	                             name='itemStock' placeholder="재고" onchange="validateInt(this)" required>
	                     </div>
	                     <div class="form-group">
	                        <label>할인율</label> <input class="form-control"
	                            name='itemDiscount' placeholder="할인율" onchange="validateDiscountRate(this)" required>
	                     </div>
	                     <div class="form-group">
	                        <label>상품 소개</label> <textarea class="form-control"
	                        rows="3" name='itemIntro' placeholder="상품 소개" required></textarea>
	                     </div>
	                     <div class="form-group">
	                        <label>상품 설명</label> <textarea class="form-control"
	                        rows="5" name='itemContents' placeholder="상품 설명" required></textarea>
	                     </div>
	
	                     <div class="form-group">
	                        <label for="uploadFile">uploadFile</label>
	                        <input type="file" id="uploadFile" name="uploadFile" multiple>
	                        <input type="hidden" value='defaltImage.jpg' name='itemImageURL'>
	                     </div>
	
						 <button type="submit" class="btn btn-default btn-success">Submit Button</button>
						 <button type="reset" class="btn btn-secondary">다시 작성</button>
						 <button type="button" class="btn btn-secondary" onclick="closeModal(this)">list</button>
					</form>
					 <!-- 브랜드 선택 목록 -->
					 <div class="panel panel-default col-lg-6">
	                    <div class="panel-body">
						    <label>브랜드</label>
						    <ul class="list-group" style="height: 252px;" id="brand-list"></ul>
						    <ul class='pull-right pagination' id="brand-pagination"></ul>
						</div>
						                    
						<div class="panel-body">
						    <label>카테고리 2</label>
						    <ul class="list-group" id="itemCate-list"></ul>
						    <ul class='pull-right pagination' id="itemCate-pagination"></ul>
						</div>
                    </div>
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
                    <h5 class="modal-itemName custom-modal-header-title">item 등록</h5>
                </div>
                <div class="col-lg-6">
                    <button type="button" class="close" aria-label="Close"
                            onclick="closeModal(this)">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
			<div class="modal-body row">
				<form class="col-lg-6" id="modifyForm" action="modify" method="post" enctype="multipart/form-data">
	        
				<div class="form-group">
					<label>상품 ID</label> 
					<input class="form-control" name='itemId' id='itemId' readonly required>
				</div>
				<div class="form-group">
					<label>상품명</label> 
					<input class="form-control" id='itemName' name='itemName' required>
                </div>

	             <div class="form-group">
                    <label>제조연도</label> 
                    <input class="form-control" id='mnfcYear' name='mnfcYear' required>
                </div>

                <div class="form-group">
                    <label>제조사</label> 
                    <input class="form-control" id='manufacturer' name='manufacturer' required>
                </div>
                
                <div class="form-group">
                    <label>브랜드ID</label> 
                    <input class="form-control" id='brandId' name='brandId' readonly required>
                </div>
                
                <div class="form-group">
                    <label>브랜드명</label>
                    <input class="form-control" id='brandName' readonly required>
                </div>
                
                <div class="form-group">
					  <select id="categorySelect-update" class="btn btn-outline-dark my-2" onchange="openSelection('itemCate', 1, 'update')" required>
					  		<option disabled selected>카테고리</option>
						    <c:forEach var="category" items="${categorys}">
						      	<option value="${category.cateCode}">${category.cateName}</option>
						    </c:forEach>
					  </select>
				</div>
                

                <div class="form-group">
                    <label>카테고리ID</label> 
                    <input class="form-control" id='cateCode' name='cateCode' readonly required>
                </div>
                  <div class="form-group">
                    <label>카테고리명</label> 
                    <input class="form-control" id='cateName' name='cateName' readonly required>
                </div>

                <div class="form-group">
                    <label>가격</label>
                    <input class="form-control" id='itemPrice' name='itemPrice' onchange="validateInt(this)" required>
                </div>

                <div class="form-group">
                    <label>재고</label>
                    <input class="form-control" onchange="validateInt(this)" id='itemStock' name='itemStock' readonly required>
                </div>

                <div class="form-group">
                    <label>할인율</label>
                    <input class="form-control" onchange="validateDiscountRate(this)" id='itemDiscount' name='itemDiscount' required>
                </div>

                <div class="form-group">
                    <label>상품 소개</label>
					<textarea class="form-control" rows="3" id='itemIntro' name='itemIntro' required></textarea>
                </div>

                <div class="form-group">
                    <label>상품 설명</label>
                    <textarea class="form-control" rows="5" id='itemContents' name='itemContents'></textarea>
                </div>
                
				
                <div class="form-group">
                    <input type='file' name='uploadFile' multiple>
                </div>

                <div class="form-group">
                    <label>변경전 이미지</label>
                    <div class="row">
                    	<img src="" alt="상품이미지" style="max-width: 250px" id='imageSRC'>
                    	<input type="hidden" name='itemImageURL' id='imageID'>
                    </div>
                </div>
			
                <div class="form-group">
					<label for="regDate">등록일</label>
					<input type="text" id="regDate" class="form-control" readonly>					
				</div>

                <div class="form-group">
					<label for="updateDate">수정일</label>
					<input type="text" id="updateDate" class="form-control" readonly>					
				</div>

				<button type="submit" class="btn btn-default">Modify</button>
				<input type="button" onclick="itemStateUpdate()" class="btn btn-danger" value="품절">
				<button type="button" class="btn btn-secondary" onclick="closeModal(this)">list</button>
				<input type="hidden" id="currentPath" name="currentPath" value="">
				</form>
				<div class="panel panel-default col-lg-6">
                    <div class="panel-body">
                        <label>브랜드</label>
                        <ul class="list-group" style="height: 252px;" id="brand-list-update"></ul>
                        <ul class='pull-right pagination' id="brand-pagination-update"></ul>
                    </div>
                                        
                    <div class="panel-body">
                        <label>카테고리 2</label>
                        <ul class="list-group" id="itemCate-list-update"></ul>
                        <ul class='pull-right pagination' id="itemCate-pagination-update"></ul>
                    </div>
                </div>
			</div>
		</div>
	</div>
</div>

<%@include file="includes/footer.jsp"%>
<script>

function validateInt(input) {
    var intValue = input.value.trim();
    
    // 입력값이 숫자인지 확인
    if (!(/^\d+$/.test(intValue))) {
        alert("가격은 숫자로 입력해주세요.");
        input.value = ""; // 입력값을 비워줌
        return false;
    }
    
    
    if (parseInt(intValue) <= 0) {
        alert("가격은 0보다 커야 합니다.");
        input.value = ""; // 입력값을 비워줌
        return false;
    }
    
    return true;
}

function validateDiscountRate(input) {
    var discountRateValue = input.value.trim();
    
    // 입력값이 숫자인지 확인
    if (!(/^\d+(\.\d+)?$/.test(discountRateValue))) {
        alert("할인율은 숫자 또는 소수로 입력해주세요.");
        input.value = ""; // 입력값을 비워줌
        return false;
    }
    
    // 숫자가 0 이상 1 미만인지 확인
    var discountRate = parseFloat(discountRateValue);
    if (discountRate < 0 || discountRate >= 1) {
        alert("할인율은 0 이상 1 미만의 소수여야 합니다.");
        input.value = ""; // 입력값을 비워줌
        return false;
    }
    
    return true;
}

function itemStateUpdate() {
	var itemId = document.getElementById("itemId").value
	$.ajax({
		url: '/admin/item/itemState/'+ itemId,
		type: 'get',
		success: function(response)  {
			window.location.href = '/admin/item/list';
		},
		error: function(xhr, status, error) {
			window.location.href = '/admin/item/list';
			alert('작업 실패 다시 확인해 주세요');
		}
	});
}

function openSelection(type, page, manipulation) {
    showList(type, page, manipulation);
}

function showList(type, page, manipulation) {
	var listUL = "";
	var pageNum = 1;
    var targeturl = "";
    var selectedCateCode="";
	if(manipulation == 'register'){
		listUL = document.getElementById(type + "-list");
		selectedCateCode = type === "itemCate" ? document.getElementById("categorySelect").value : null;
	} else {
		listUL = document.getElementById(type + "-list-update");
		selectedCateCode = type === "itemCate" ? document.getElementById("categorySelect-update").value : null;
	}
    
   	
	
    
    if(type === "itemCate"){
    	targeturl = "/admin/" + type + "/select/" + page + "/" + selectedCateCode;
    } else {
    	targeturl = "/admin/" + type + "/select/" + page;
    }
    
    
	
    $.ajax({
        url: targeturl,
        type: 'GET',
        dataType: 'json',
        success: function(response) {
            console.log(type + " 목록:", response);
            pageNum = response.pageMaker.cri.pageNum;
            var total = response.pageMaker.total;
            var list = response.list;
            console.log(total);

            if (page == -1) {
                pageNum = Math.ceil(replyCnt / 10.0);  // 페이지 수 계산
                showList(type, pageNum);
                return;
            }

            var str = "";

            if (list == null || list.length == 0) {
                listUL.innerHTML = "";
            }

            for (var i = 0, len = list.length || 0; i < len; i++) {
                if (type === "brand") {
                    str += "<li class='list-group-item' onclick=\"selectItem('" + type + "', '" + list[i].brandId + "', '" + list[i].brandName + "', '" + manipulation + "')\">" + list[i].brandName + "</li>";
                } else if (type === "itemCate") {
                    str += "<li class='list-group-item' onclick=\"selectItem('" + type + "', '" + list[i].cateCode + "', '" + list[i].cateName +  "', '" + manipulation + "')\">" + list[i].cateName + "</li>";
                }
            }
            listUL.innerHTML = str;
		
		console.log(total);
            showPageNum(type, pageNum, total, manipulation);
        },
        error: function(xhr, status, error) {
            console.error('AJAX 요청 실패:', error);
        }
    });
}

function showPageNum(type, pageNum, total, manipulation) {
	console.log(type)
	var paginationUL = "";
	if(manipulation == 'register'){
		console.log('fffffffffffffffffffffffffffffff')
		paginationUL = document.getElementById(type + "-pagination");
	} else {
		console.log('fffffffffffffffffffffffffffffffffffffffffffffffff')
		paginationUL = document.getElementById(type + "-pagination-update");
	}
    var endNum = Math.ceil(pageNum / 10.0) * 10;
    var startNum = endNum - 9;

    var prev = startNum != 1;
    var next = false;
    
    var str = "";

    if (endNum * 10 >= total) {
        endNum = Math.ceil(total / 10.0);
    }

    if (endNum * 10 < total) {
        next = true;
    }

    if (prev) {
        str += "<li class='paginate_button'><a class='page-link' data-page='" + (startNum - 1) + "' href='#' onclick='handlePageClick(event, \"" + type + "\", \"" + manipulation + "\")'>Previous</a></li>";
    }

    for (var i = startNum; i <= endNum; i++) {
        var active = pageNum == i ? "active" : "";
        str += "<li class='paginate_button " + active + "'><a class='page-link' data-page='" + i + "' href='#' onclick='handlePageClick(event, \"" + type + "\", \"" + manipulation + "\")'>" + i + "</a></li>";
    }

    if (next) {
        str += "<li class='paginate_button'><a class='page-link' data-page='" + (endNum + 1) + "' href='#' onclick='handlePageClick(event, \"" + type + "\", \"" + manipulation + "\")'>Next</a></li>";
    }
    console.log(str)
    paginationUL.innerHTML = str;
}

function handlePageClick(event, type, manipulation) {
    event.preventDefault();
    var targetPage = parseInt(event.target.getAttribute("data-page"));
    showList(type, targetPage, manipulation);
}

function selectItem(type, id, name, manipulation) {
	
	if(manipulation == 'register') {
		if (type === "brand") {
			console.log(id)
	        document.getElementById('brandRegId').value = id;
	        document.getElementById('brandRegName').value = name;
	    } else if (type === "itemCate") {
	    	console.log(id)
	        document.getElementById('categoryRegId').value = id;
	        document.getElementById('categoryRegName').value = name;
	    }
	} else {
		if (type === "brand") {
	        document.getElementById('brandId').value = id;
	        document.getElementById('brandName').value = name;
	    } else if (type === "itemCate") {
	    	console.log(id)
	        document.getElementById('cateCode').value = id;
	        document.getElementById('cateName').value = name;
	    }
		
	}
	
    
}



function updateActionUrl() {
    var currentUrl = window.location.href;
    var newPath;

    if (currentUrl.includes("desc")) {
        newPath = "/admin/item/descList";
    } else {
        newPath = "/admin/item/list";
    }

    $("#currentPath").val(extractPageName(newPath));
    return newPath;
}



function goToDetailModalForm(element) {
    console.log(element);

    let valueToSend = element.id;
    console.log(valueToSend);

    $.ajax({
        url: '/admin/item/get/' + valueToSend,
        type: 'get',
        data: { itemId: valueToSend },
        success: function (response) {
            $("#itemId").val(response.item.itemId);
            $("#itemName").val(response.item.itemName);
            $("#brandId").val(response.item.brandId);
            $("#brandName").val(response.item.brandName);
            var mnfcYear = new Date(response.item.mnfcYear);
            var mnfcYearDateString = mnfcYear.toISOString().substring(0, 10);
            $("#mnfcYear").val(mnfcYearDateString);
            $("#manufacturer").val(response.item.manufacturer);
            $("#cateCode").val(response.item.cateCode);
            $("#cateName").val(response.item.cateName);
            $("#itemPrice").val(response.item.itemPrice);
            $("#itemStock").val(response.item.itemStock);
            $("#itemDiscount").val(response.item.itemDiscount);
            $("#itemIntro").val(response.item.itemIntro);
            $("#itemContents").val(response.item.itemContents);
            var regDate = new Date(response.item.regDate);
            var regDateString = regDate.toISOString().substring(0, 10);
            $('#regDate').val(regDateString);
            var upDateDate = new Date(response.item.updateDate);
            var updateDateDateString = upDateDate.toISOString().substring(0, 10);
            $('#updateDate').val(updateDateDateString);
            $("#imageSRC").attr("src", "/download/" + response.item.itemImageURL);
            $("#imageID").val(response.item.itemImageURL);
            $('#formModal2').modal('show');
        },
        error: function (xhr, status, error) {
            console.error('AJAX 요청 실패:', error);
        }
    });
}

</script>