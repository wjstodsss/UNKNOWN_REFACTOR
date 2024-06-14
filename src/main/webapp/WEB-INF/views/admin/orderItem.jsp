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
					            <button class='btn custom-keyword-btn'>Search</button>
	                        	<a href="/admin/orderItem/list" type="button" class="btn custom-keyword-btn mx-2">검색해제</a>
					        </form>
					    </div>
					</div>
					<!-- end 검색조건 -->
		            <table class="table table-borderItemed able-hover  custom-table">
						
						<caption class="table-caption">주문 상품</caption>
						<thead class="button-add">
							<tr>
	                            <th>
	                            	<div class="custom-sort-component">
						                <div class="label">주문ID</div>
						                <div class="sort-icons">
						                    <a href="#" class="no-anchor-text-decoration"
						                       data-groupColumn='/orderItem' data-sortColumn='orderItemId'
						                       data-sortType='asc'>▲</a>
						                    <a href="#"
						                       class="no-anchor-text-decoration"
						                       data-groupColumn='/orderItem' data-sortColumn='orderItemId'
						                       data-sortType='desc'>▼</a>
						                </div>
						            </div>
	                            </th>
	                            <th>
	                            	<div class="custom-sort-component">
						                <div class="label">상품ID</div>
						                <div class="sort-icons">
						                    <a href="#" class="no-anchor-text-decoration"
						                       data-groupColumn='/orderItem' data-sortColumn='itemId'
						                       data-sortType='asc'>▲</a>
						                    <a href="#"
						                       class="no-anchor-text-decoration"
						                       data-groupColumn='/orderItem' data-sortColumn='itemId'
						                       data-sortType='desc'>▼</a>
						                </div>
						            </div>
	                            </th>
	                            <th>
	                            	<div class="custom-sort-component">
						                <div class="label">주문수량</div>
						                <div class="sort-icons">
						                    <a href="#" class="no-anchor-text-decoration"
						                       data-groupColumn='/orderItem' data-sortColumn='itemCount'
						                       data-sortType='asc'>▲</a>
						                    <a href="#"
						                       class="no-anchor-text-decoration"
						                       data-groupColumn='/orderItem' data-sortColumn='itemCount'
						                       data-sortType='desc'>▼</a>
						                </div>
						            </div>
	                            </th>
	                            <th>
	                            	<div class="custom-sort-component">
						                <div class="label">상품가격</div>
						                <div class="sort-icons">
						                    <a href="#" class="no-anchor-text-decoration"
						                       data-groupColumn='/orderItem' data-sortColumn='itemPrice'
						                       data-sortType='asc'>▲</a>
						                    <a href="#"
						                       class="no-anchor-text-decoration"
						                       data-groupColumn='/orderItem' data-sortColumn='itemPrice'
						                       data-sortType='desc'>▼</a>
						                </div>
						            </div>
	                            </th>
	                            <th>
	                            	<div class="custom-sort-component">
						                <div class="label">할인율</div>
						                <div class="sort-icons">
						                    <a href="#" class="no-anchor-text-decoration"
						                       data-groupColumn='/orderItem' data-sortColumn='itemDiscount'
						                       data-sortType='asc'>▲</a>
						                    <a href="#"
						                       class="no-anchor-text-decoration"
						                       data-groupColumn='/orderItem' data-sortColumn='itemDiscount'
						                       data-sortType='desc'>▼</a>
						                </div>
						            </div>
	                            </th>
	                            <th>
	                            	<div class="custom-sort-component">
						                <div class="label">적립포인트</div>
						                <div class="sort-icons">
						                    <a href="#" class="no-anchor-text-decoration"
						                       data-groupColumn='/orderItem' data-sortColumn='earnPoint'
						                       data-sortType='asc'>▲</a>
						                    <a href="#"
						                       class="no-anchor-text-decoration"
						                       data-groupColumn='/orderItem' data-sortColumn='earnPoint'
						                       data-sortType='desc'>▼</a>
						                </div>
						            </div>
	                            </th>
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
		            <form id='actionForm' action="/admin/orderItem/list" method='get'>
		            </form>
				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
	</div>
	<!-- /.row -->
<%@include file="includes/footer.jsp"%>

