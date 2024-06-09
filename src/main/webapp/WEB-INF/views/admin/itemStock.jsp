<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<%@include file="includes/header.jsp"%>
<%@include file="includes/leftNav.jsp"%>
<%@include file="includes/common-cri.jsp"%>

<div class="col-lg-10">
	<div class="panel panel-default">
		<div class="panel-heading">StockInfo List Page</div>
		<!-- /.panel-heading -->
		<div class="panel-body">
			<!-- 검색조건 -->
			<div class="row">
				<div class="col-lg-4">
					<form id='searchForm' action="/admin/item/stock/list" method='get'
						class='searchForm'>
						<select class="custom-select custom-option" name='type'>
							<option class="" value=""
								${pageMaker.cri.type == null ? 'selected' : ''}>--</option>
							<option value="I" ${pageMaker.cri.type eq 'I' ? 'selected' : ''}>발주ID</option>
							<option value="N" ${pageMaker.cri.type eq 'N' ? 'selected' : ''}>상품명</option>
							<option value="D" ${pageMaker.cri.type eq 'D' ? 'selected' : ''}>발주일</option>
						</select> <input type='text' class='custom-keyword' name='keyword'
							value='<c:out value="${pageMaker.cri.keyword}"/>' /> 
						<button class='btn custom-keyword-btn'>Search</button>
						<a href="/admin/item/stock/list" type="button"
							class="btn custom-keyword-btn mx-2">검색해제</a>
					</form>
				</div>
			</div>
			<!-- end 검색조건 -->
			
			
			<table class="table table-bordered able-hover  custom-table">
				<caption class="table-caption">재고</caption>
				<thead class="button-add">
					<tr>
						<th>
							<div class="custom-sort-component">
								<div class="label">상품ID</div>
								<div class="sort-icons">
									<a href="#" class="no-anchor-text-decoration"
										data-groupColumn='/item/stock' data-sortColumn='itemId'
										data-sortType='asc'>▲</a> <a href="#"
										class="no-anchor-text-decoration"
										data-groupColumn='/item/stock' data-sortColumn='itemId'
										data-sortType='desc'>▼</a>
								</div>
							</div>
						</th>
						<th>
							<div class="custom-sort-component">
								<div class="label">상품명</div>
								<div class="sort-icons">
									<a href="#" class="no-anchor-text-decoration"
										data-groupColumn='/item/stock' data-sortColumn='itemName'
										data-sortType='asc'>▲</a> <a href="#"
										class="no-anchor-text-decoration"
										data-groupColumn='/item/stock' data-sortColumn='itemName'
										data-sortType='desc'>▼</a>
								</div>
							</div>
						</th>
						<th>
							<div class="custom-sort-component">
								<div class="label">재고량</div>
								<div class="sort-icons">
									<a href="#" class="no-anchor-text-decoration"
										data-groupColumn='/item/stock' data-sortColumn='itemStock'
										data-sortType='asc'>▲</a> <a href="#"
										class="no-anchor-text-decoration"
										data-groupColumn='/item/stock' data-sortColumn='itemStock'
										data-sortType='desc'>▼</a>
								</div>
							</div>
						</th>
						<th>
							<div class="custom-sort-component">
								<div class="label">제조사</div>
								<div class="sort-icons">
									<a href="#" class="no-anchor-text-decoration"
										data-groupColumn='/item/stock' data-sortColumn='manufacturer'
										data-sortType='asc'>▲</a> <a href="#"
										class="no-anchor-text-decoration"
										data-groupColumn='/item/stock' data-sortColumn='manufacturer'
										data-sortType='desc'>▼</a>
								</div>
							</div>
						</th>

						<th>
							<div class="custom-sort-component">
								<div class="label">발주ID</div>
								<div class="sort-icons">
									<a href="#" class="no-anchor-text-decoration"
										data-groupColumn='/item/stock' data-sortColumn='stockOrderId'
										data-sortType='asc'>▲</a> <a href="#"
										class="no-anchor-text-decoration"
										data-groupColumn='/item/stock' data-sortColumn='stockOrderId'
										data-sortType='desc'>▼</a>
								</div>
							</div>
						</th>
						<th>
							<div class="custom-sort-component">
								<div class="label">발주량</div>
								<div class="sort-icons">
									<a href="#" class="no-anchor-text-decoration"
										data-groupColumn='/item/stock' data-sortColumn='stockOrderQty'
										data-sortType='asc'>▲</a> <a href="#"
										class="no-anchor-text-decoration"
										data-groupColumn='/item/stock' data-sortColumn='stockOrderQty'
										data-sortType='desc'>▼</a>
								</div>
							</div>
						</th>
						<th>
							<div class="custom-sort-component">
								<div class="label">발주일</div>
								<div class="sort-icons">
									<a href="#" class="no-anchor-text-decoration"
										data-groupColumn='/item/stock' 
										data-sortColumn='orderDate' data-sortType='asc'>▲</a> <a
										href="#" class="no-anchor-text-decoration" 
										data-groupColumn='/item/stock'
										data-sortColumn='orderDate' data-sortType='desc'>▼</a>
								</div>
							</div>
						</th>
						<th>발주작업</th>
						<th>입고처리</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${items}">
						<tr>
							<td>${item.itemId}</td>
							<td>${item.itemName}</td>
							<td>${item.itemStock}</td>
							<td>${item.manufacturer}</td>
							<td>${item.stockOrderId}</td>
							<td>${item.stockOrderQty}</td>
							<td><fmt:formatDate pattern="yyyy-MM-dd"
									value="${item.orderDate}" /></td>
							<td>
								<button type="button" class="btn btn-primary"
									data-itemid="${item.itemId}" onclick="goToRegModal(this)">발주</button>
							</td>
							<td>
								<button type="button" class="btn btn-info"
									onclick="goToReceivedModal(this)" data-itemid="${item.itemId}"
									data-stockid="${item.stockOrderId}">입고</button>
							</td>
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

			<form id='actionForm' action="/admin/item/stock/list" method='get'>
			</form>
		</div>
		<!-- /.panel-body -->
	</div>
	<!-- /.panel -->
</div>
<!-- 발주 작업 모달 -->
<div class="modal" id="regModal" tabindex="-1" role="dialog"
	aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header row custom-modal-header-row">
				<div class="col-lg-6">
					<h5 class="modal-itemName custom-modal-header-title">상품 발주</h5>
				</div>
				<div class="col-lg-6">
					<button type="button" class="close" aria-label="Close"
						onclick="closeModal(this)">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
			</div>
			<div class="modal-body">
				<form id="stockOrderRegForm" role="form" action="register"
					method="post" >
					<input type="hidden" name='itemId' id='regOrederItemId'> <input
						type="hidden" name='registerType' value='stockOrderReg'>
					<div class="form-group">
						<label>발주량</label> <input type="number" class="form-control"
							name="stockOrderQty" id="stockOrderQty"
							placeholder="발주 수량을 입려하세요" required>
					</div>
					<button type="submit" class="btn btn-default btn-success">Submit
						Button</button>
					<button type="button" class="btn btn-secondary"
						onclick="closeModal(this)">닫기</button>
				</form>
			</div>
		</div>
	</div>
</div>

<!-- 입고 처리 모달 -->
<div class="modal" id="receivedModal" tabindex="-1" role="dialog"
	aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header row custom-modal-header-row">
				<div class="col-lg-6">
					<h5 class="modal-itemName custom-modal-header-title">입고 처리</h5>
				</div>
				<div class="col-lg-6">
					<button type="button" class="close" aria-label="Close"
						onclick="closeModal(this)">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
			</div>
			<div class="modal-body">
				<form id="receivedRegForm" role="form" action="register"
					method="post">
					<input type="hidden" name='itemId' id='orederItemId'> <input
						type="hidden" name='stockOrderId' id='stockOrderId'> <input
						type="hidden" name='registerType' value='receivedReg'>

					<div class="form-group">
						<label>입고량</label> <input type="number" class="form-control"
							name="receivedQty" id="receivedQty" placeholder="입고 수량을 입력하세요"
							required>
					</div>
					<button type="submit" class="btn btn-default btn-success">Submit
						Button</button>
					<button type="button" class="btn btn-secondary"
						onclick="closeModal(this)">닫기</button>
				</form>
			</div>
		</div>
	</div>
</div>



<%@include file="includes/footer.jsp"%>

<script>
	$(document).ready(function() {
		$('#regModal').on('shown.bs.modal', function() {
			$('#stockOrderQty').focus();
		});

		$('#receivedModal').on('shown.bs.modal', function() {
			$('#receivedQty').focus();
		});

		document.getElementById('stockOrderRegForm').onsubmit = function() {
			appendCommonHiddenInputs('stockOrderRegForm');
		};

		document.getElementById('receivedRegForm').onsubmit = function() {
			appendCommonHiddenInputs('receivedRegForm');
		};

	});

	function goToRegModal(clickedElement) {
		var itemId = clickedElement.dataset.itemid;
		document.getElementById('regOrederItemId').value = itemId;
		$('#regModal').modal('show');
	}

	function goToReceivedModal(clickedElement) {
		var itemId = clickedElement.dataset.itemid;
		var stockOrderId = clickedElement.dataset.stockid;

		if (stockOrderId == null || stockOrderId == 0) {
			alert("처리할 입고 내역이 없습니다.");
			return;
		} else {
			$.ajax({
				url : '/admin/item/stock/getStockOrderQty/' + stockOrderId,
				type : 'get',
				success : function(response) {
					document.getElementById('orederItemId').value = itemId;
					document.getElementById('stockOrderId').value = stockOrderId;
					$("#receivedQty").val(
							response.stockInfo.stockOrderQty);
					$('#receivedModal').modal('show');
				},
				error : function(xhr, status, error) {
					console.error('AJAX 요청 실패:', error);
				}
			});
		}

	}
</script>
