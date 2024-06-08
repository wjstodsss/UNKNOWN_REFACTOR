<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<%@include file="includes/header.jsp"%>
<%@include file="includes/leftNav.jsp"%>

	<div class="col-lg-10">
		<div class="panel panel-default">
			<div class="panel-heading">
				Benefits List Page
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
			<!-- 검색조건 -->
				  <div class="row">       
				    <div class="col-lg-12">                    
				        <form id='searchForm' action="/admin/cart/list" method='get' class='searchForm'>
				            <select class="custom-select" name='type'>
				                <option value=""
				                    <c:out value="${pageMaker.cri.type == null?'selected':''}"/>>--</option>
				                <option value="I"
				                    <c:out value="${pageMaker.cri.type eq 'I'?'selected':''}"/>>장바구니ID</option>
				                <option value="M"
				                    <c:out value="${pageMaker.cri.type eq 'M'?'selected':''}"/>>회원ID</option>
				          
				            </select> 
				            <input type='text' class='custom-keyword' name='keyword' value='<c:out value="${pageMaker.cri.keyword}"/>' /> 
				            <input type='hidden' name='pageNum' value='<c:out value="${pageMaker.cri.pageNum}"/>' /> 
				            <input type='hidden' name='amount' value='<c:out value="${pageMaker.cri.amount}"/>' />
				            <button class='btn btn-default'>Search</button>
				        </form>
				    </div>
				    
				    <div class="col-lg-12 button-add">
				    	<a href="/admin/cart/list" type="button" class="btn btn-board btn-xs btn-dark pull-right btn-info col-lg-1.5 mx-2 my-2" data-sortColumn='itemId'>상품ID 오름차순 </a>
				        <a href="/admin/cart/descList" type="button" class="btn btn-board btn-xs pull-right btn-info btn-warning col-lg-1.5 mx-2 my-2" data-sortColumn='itemId' >상품ID 내림차순</a>
					    <a href="/admin/cart/list" type="button" class="btn btn-board btn-xs btn-dark pull-right btn-info col-lg-1.5 mx-2 my-2" data-sortColumn='memberId'>회원 오름차순 </a>
				        <a href="/admin/cart/descList" type="button" class="btn btn-board btn-xs pull-right btn-info btn-warning col-lg-1.5 mx-2 my-2" data-sortColumn='memberId' >회원 내림차순</a>
				     	<a href="/admin/cart/list" type="button" class="btn btn-board btn-xs btn-dark pull-right btn-info col-lg-1.5 mx-2 my-2" data-sortColumn='cartId'>순번 오름차순 </a>
				        <a href="/admin/cart/descList" type="button" class="btn btn-board btn-xs pull-right btn-info btn-warning col-lg-1.5 mx-2 my-2" data-sortColumn='cartId' >순번 내림차순 </a>							      
				    </div>
				   
					</div>
					<!-- end 검색조건 --> 
					<a href="/admin/cart/list" type="button" class="btn btn-board btn-xs btn-light pull-right btn-info col-lg-2 mx-2 my-2">검색해제 일반리스트 </a>
					<table class="table table-striped table-bordered table-hover" id="dataTables-example">
						
						<caption class="table-caption">회원 장바구니</caption>
						<thead>
							<tr>
								<th class="col-lg-1">장바구니ID</th>
	                            <th class="col-lg-3">회원ID</th>
	                            <th class="col-lg-3">상품ID</th>
	                            <th class="col-lg-3">상품명</th>
	                            <th class="col-lg-3">상품수량</th>
	                           
							</tr>
						</thead>
						<tbody>
							 <c:forEach var="cart" items="${carts}" varStatus="status">
						        <tr class="odd gradeX">
								    <td>${cart.cartId}</td>
								    <td>${cart.memberId}</td>
								    <td>${cart.itemId}</td>
								    <td>${cart.itemName}</td>
								    <td>${cart.itemCount}</td>
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
					<form id='actionForm' action="/admin/cart/list" method='get'>
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
		
<%@include file="includes/footer.jsp"%>

<script>
function updateActionUrl() {
    var currentUrl = window.location.href;
    var newPath;

    if (currentUrl.includes("desc")) {
    	newPath = "/admin/cart/descList";
    } else {
    	newPath = "/admin/cart/list";
    }
    $("#currentPath").val(extractPageName(newPath));
    return newPath;
}
</script>