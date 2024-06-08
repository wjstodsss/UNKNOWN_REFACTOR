<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<%@include file="includes/header.jsp"%>
<%@include file="includes/leftNav.jsp"%>

	<div class="col-lg-10">
		<div class="panel panel-default">
			<div class="panel-heading">
				StockInfo List Page
				
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
			<!-- 검색조건 -->
				  <div class="row">       
				    <div class="col-lg-4">                    
				        <form id='searchForm' action="/admin/item/log/list" method='get' class='searchForm'>
				            <select class="custom-select" name='type'>
				                <option value=""
				                    <c:out value="${pageMaker.cri.type == null?'selected':''}"/>>--</option>
				                <option value="I"
				                    <c:out value="${pageMaker.cri.type eq 'I'?'selected':''}"/>>발주ID</option>
				                <option value="N"
				                    <c:out value="${pageMaker.cri.type eq 'N'?'selected':''}"/>>상품명</option>
			                    <option value="S"
				                    <c:out value="${pageMaker.cri.type eq 'S'?'selected':''}"/>>미입고</option>
				            </select> 
				            <input type='text' class='custom-keyword' name='keyword' value='<c:out value="${pageMaker.cri.keyword}"/>' /> 
				            <input type='hidden' name='pageNum' value='<c:out value="${pageMaker.cri.pageNum}"/>' /> 
				            <input type='hidden' name='amount' value='<c:out value="${pageMaker.cri.amount}"/>' />
				            <button class='btn btn-default'>Search</button>
				        </form>
				    </div>
				    <div class="row">
				    <div class="col-lg-12 button-add">
				    	<a href="/admin/item/log/list" type="button" class="btn btn-board btn-xs btn-dark pull-right btn-info col-lg-2" data-sortColumn='stockOrderQty'>발주량 오름차순 </a>
				        <a href="/admin/item/log/descList" type="button" class="btn btn-board btn-xs pull-right btn-info btn-warning col-lg-2" data-sortColumn='stockOrderQty'>발주량 내림차순</a>
				    	<a href="/admin/item/log/list" type="button" class="btn btn-board btn-xs btn-dark pull-right btn-info col-lg-2" data-sortColumn='itemStock'>재고량 오름차순 </a>
				        <a href="/admin/item/log/descList" type="button" class="btn btn-board btn-xs pull-right btn-info btn-warning col-lg-2" data-sortColumn='itemStock'>재고량 내림차순</a>
				     	<a href="/admin/item/log/list" type="button" class="btn btn-board btn-xs btn-dark pull-right btn-info col-lg-2" data-sortColumn='itemId'>오름차순 </a>
				        <a href="/admin/item/log/descList" type="button" class="btn btn-board btn-xs pull-right btn-info btn-warning col-lg-2" data-sortColumn='itemId'>내림차순</a>		       
				    </div>
				    </div>
				</div>
				<!-- end 검색조건 -->
				<div class="row">
					<a href="/admin/item/log/list" type="button" class="btn btn-board btn-xs btn-light pull-right btn-info col-lg-2 mx-3 my-2">검색해제 일반리스트 </a>
				</div>
				<table width="80%"
					class="table table-striped table-bordered table-hover"
					id="dataTables-example">
					
					<caption class="table-caption">상품</caption>
					<thead>
						<tr>
							<th>발주ID</th>
							<th>상품ID</th>
                            <th>상품명</th>
                            <th>상품재고</th>
                            <th>업체명</th>
                            <th>발주량</th>
                            <th>입고량</th>
                            <th>발주일</th>
                            <th>입고일</th>
                            <th>입고상태</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${items}">
							<tr class="odd gradeX">
								<td>${item.stockOrderId}</td>
								<td>${item.itemId}</td>
								<td>${item.itemName}</td>                             
                                <td>${item.itemStock}</td>
                                <td>${item.manufacturer}</td>
                                <td>${item.stockOrderQty}</td>
                                <td>${item.receivedQty}</td>
								<td><fmt:formatDate pattern="yyyy-MM-dd" value="${item.orderDate}" /></td>
								<c:choose>
								    <c:when test="${item.receivedDate != null}">
								        <td><fmt:formatDate pattern="yyyy-MM-dd" value="${item.receivedDate}" /></td>
								    </c:when>
								    <c:otherwise>
								        <td></td>
								    </c:otherwise>
								</c:choose>
                                <td>${item.isReceived}</td>
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

				<form id='actionForm' action="/admin/item/log/list" method='get'>
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
	
<%@include file="includes/footer.jsp"%>

<script>
function updateActionUrl() {
    var currentUrl = window.location.href;
    var newPath;

    if (currentUrl.includes("desc")) {
    	newPath = "/admin/item/log/descList";
    } else {
    	newPath = "/admin/item/log/list";
    }
   
    // 설정한 값으로 hidden input 업데이트
    $("#currentPath").val(extractPageName(newPath));
    return newPath;
}

</script>
