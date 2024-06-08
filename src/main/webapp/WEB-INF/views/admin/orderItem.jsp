<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<%@include file="includes/header.jsp"%>
<%@include file="includes/leftNav.jsp"%>

	<div class="col-lg-10">
		<div class="panel panel-default">
			<div class="panel-heading">
				OrderItem List Page
				
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
			<!-- 검색조건 -->
				  <div class="row">       
				    <div class="col-lg-4">                    
				        <form id='searchForm' action="/admin/orderItem/list" method='get' class='searchForm'>
				            <select class="custom-select" name='type'>
				                <option value=""
				                    <c:out value="${pageMaker.cri.type == null?'selected':''}"/>>--</option>
				                <option value="O"
				                    <c:out value="${pageMaker.cri.type eq 'O'?'selected':''}"/>>주문ID</option>
				            </select> 
				            <input type='text' class='custom-keyword' name='keyword' value='<c:out value="${pageMaker.cri.keyword}"/>' /> 
				            <input type='hidden' name='pageNum' value='<c:out value="${pageMaker.cri.pageNum}"/>' /> 
				            <input type='hidden' name='amount' value='<c:out value="${pageMaker.cri.amount}"/>' />
				          
				            <button class='btn btn-default'>Search</button>
				        </form>
				    </div>
				    <div class="row">
				    <div class="col-lg-12 button-add">
				     	<a href="/admin/orderItem/list" type="button" class="btn btn-board btn-xs btn-dark pull-right btn-info col-lg-2" data-sortColumn='orderItemId'>오름차순 </a>
				        <a href="/admin/orderItem/descList" type="button" class="btn btn-board btn-xs pull-right btn-info btn-warning col-lg-2" data-sortColumn='orderItemId'>내림차순</a>		       
				    </div>
				    </div>
				</div>
				<!-- end 검색조건 -->
				<div class="row">
					<a href="/admin/orderItem/list" type="button" class="btn btn-board btn-xs btn-light pull-right btn-info col-lg-2 mx-3 my-2">검색해제 일반리스트 </a>
				</div>
				<table width="80%"
					class="table table-striped table-bordered table-hover"
					id="dataTables-example">
					
					<caption class="table-caption">주문 상품</caption>
					<thead>
						<tr>
                            <th>주문ID</th>
                            <th>상품ID</th>
                            <th>주문수량</th>
                            <th>상품가격</th>
                            <th>할인율</th>
                            <th>적립포인트</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="orderItem" items="${orderItems}">
							<tr class="odd gradeX">
								<td>${orderItem.orderId}</td>                             
                                <td>${orderItem.itemId}</td>
                                <td>${orderItem.itemCount}</td>
                                <td>${orderItem.itemPrice}</td>
                                <td>${orderItem.itemDiscount}</td>
                                <td>${orderItem.earnPoint}</td>
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

				<form id='actionForm' action="/admin/orderItem/list" method='get'>
					<input type='hidden' name='pageNum'
						value='${pageMaker.cri.pageNum}'> <input type='hidden'
						name='amount' value='${pageMaker.cri.amount}'>
						<input type='hidden' name='type' value='${pageMaker.cri.type}'>
						<input type='hidden' name='keyword' value='${pageMaker.cri.keyword}'>
						<input type='hidden' name='sortColumn' id='sortColumn'>
						<input type='hidden' name='groupColumn' id='groupColumn'>
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
    	newPath = "/admin/orderItem/descList";
    } else {
    	newPath = "/admin/orderItem/list";
    }
   
    // 설정한 값으로 hidden input 업데이트
    $("#currentPath").val(extractPageName(newPath));
    return newPath;
}

</script>
