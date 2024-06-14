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
				           <button class='btn custom-keyword-btn'>Search</button>
                        	<a href="/admin/item/log/list" type="button" class="btn custom-keyword-btn mx-2">검색해제</a>
				        </form>
				    </div>
				</div>
				<!-- end 검색조건 -->
	            <table class="table table-breviewed able-hover  custom-table">
					<caption class="table-caption">상품</caption>
					<thead class="button-add">
						<tr>
							<th>
								<div class="custom-sort-component">
					                <div class="label">발주ID</div>
					                <div class="sort-icons">
					                    <a href="#" class="no-anchor-text-decoration"
					                       data-groupColumn='/item/log' data-sortColumn='stockOrderId'
					                       data-sortType='asc'>▲</a>
					                    <a href="#"
					                       class="no-anchor-text-decoration"
					                       data-groupColumn='/item/log' data-sortColumn='stockOrderId'
					                       data-sortType='desc'>▼</a>
					                </div>
					            </div>
							</th>
							<th>
								<div class="custom-sort-component">
					                <div class="label">상품ID</div>
					                <div class="sort-icons">
					                    <a href="#" class="no-anchor-text-decoration"
					                       data-groupColumn='/item/log' data-sortColumn='itemId'
					                       data-sortType='asc'>▲</a>
					                    <a href="#"
					                       class="no-anchor-text-decoration"
					                       data-groupColumn='/item/log' data-sortColumn='itemId'
					                       data-sortType='desc'>▼</a>
					                </div>
					            </div>
							</th>
                            <th>
                            	<div class="custom-sort-component">
					                <div class="label">상품명</div>
					                <div class="sort-icons">
					                    <a href="#" class="no-anchor-text-decoration"
					                       data-groupColumn='/item/log' data-sortColumn='itemName'
					                       data-sortType='asc'>▲</a>
					                    <a href="#"
					                       class="no-anchor-text-decoration"
					                       data-groupColumn='/item/log' data-sortColumn='itemName'
					                       data-sortType='desc'>▼</a>
					                </div>
					            </div>
                            </th>
                            <th>
                            	<div class="custom-sort-component">
					                <div class="label">상품재고</div>
					                <div class="sort-icons">
					                    <a href="#" class="no-anchor-text-decoration"
					                       data-groupColumn='/item/log' data-sortColumn='itemStock'
					                       data-sortType='asc'>▲</a>
					                    <a href="#"
					                       class="no-anchor-text-decoration"
					                       data-groupColumn='/item/log' data-sortColumn='itemStock'
					                       data-sortType='desc'>▼</a>
					                </div>
					            </div>
                            </th>
                            <th>
                            	<div class="custom-sort-component">
					                <div class="label">제조사</div>
					                <div class="sort-icons">
					                    <a href="#" class="no-anchor-text-decoration"
					                       data-groupColumn='/item/log' data-sortColumn='manufacturer'
					                       data-sortType='asc'>▲</a>
					                    <a href="#"
					                       class="no-anchor-text-decoration"
					                       data-groupColumn='/item/log' data-sortColumn='manufacturer'
					                       data-sortType='desc'>▼</a>
					                </div>
					            </div>
                            </th>
                            <th>
                            	<div class="custom-sort-component">
					                <div class="label">발주량</div>
					                <div class="sort-icons">
					                    <a href="#" class="no-anchor-text-decoration"
					                       data-groupColumn='/item/log' data-sortColumn='stockOrderQty'
					                       data-sortType='asc'>▲</a>
					                    <a href="#"
					                       class="no-anchor-text-decoration"
					                       data-groupColumn='/item/log' data-sortColumn='stockOrderQty'
					                       data-sortType='desc'>▼</a>
					                </div>
					            </div>
                            </th>
                            <th>
                            	<div class="custom-sort-component">
					                <div class="label">입고량</div>
					                <div class="sort-icons">
					                    <a href="#" class="no-anchor-text-decoration"
					                       data-groupColumn='/item/log' data-sortColumn='receivedQty'
					                       data-sortType='asc'>▲</a>
					                    <a href="#"
					                       class="no-anchor-text-decoration"
					                       data-groupColumn='/item/log' data-sortColumn='receivedQty'
					                       data-sortType='desc'>▼</a>
					                </div>
					            </div>
                            </th>
                            <th>
                            	<div class="custom-sort-component">
					                <div class="label">발주일</div>
					                <div class="sort-icons">
					                    <a href="#" class="no-anchor-text-decoration"
					                       data-groupColumn='/item/log' data-sortColumn='orderDate'
					                       data-sortType='asc'>▲</a>
					                    <a href="#"
					                       class="no-anchor-text-decoration"
					                       data-groupColumn='/item/log' data-sortColumn='orderDate'
					                       data-sortType='desc'>▼</a>
					                </div>
					            </div>
                            </th>
                            <th>
                            	<div class="custom-sort-component">
					                <div class="label">입고일</div>
					                <div class="sort-icons">
					                    <a href="#" class="no-anchor-text-decoration"
					                       data-groupColumn='/item/log' data-sortColumn='receivedDate'
					                       data-sortType='asc'>▲</a>
					                    <a href="#"
					                       class="no-anchor-text-decoration"
					                       data-groupColumn='/item/log' data-sortColumn='receivedDate'
					                       data-sortType='desc'>▼</a>
					                </div>
					            </div>
                            </th>
                            <th>
                            	<div class="custom-sort-component">
					                <div class="label">입고상태</div>
					                <div class="sort-icons">
					                    <a href="#" class="no-anchor-text-decoration"
					                       data-groupColumn='/item/log' data-sortColumn='isReceived'
					                       data-sortType='asc'>▲</a>
					                    <a href="#"
					                       class="no-anchor-text-decoration"
					                       data-groupColumn='/item/log' data-sortColumn='isReceived'
					                       data-sortType='desc'>▼</a>
					                </div>
					            </div>
                            </th>
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
	            <form id='actionForm' action="/admin/item/log/list" method='get'>
	            </form>
				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
<%@include file="includes/footer.jsp"%>

