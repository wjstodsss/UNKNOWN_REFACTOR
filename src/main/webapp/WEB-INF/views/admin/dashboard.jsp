<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<%@include file="includes/header.jsp"%>
<%@include file="includes/leftNav.jsp"%>

	<div class="col-lg-10 dashboard-item">
            <!-- 상단 정보 -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="row">
                        <div class="col-lg-4">
                            <div class="dashboard-item text-center border-none">
                                <h2 id="datetime"></h3>
                            </div>
                        </div>
                        <div class="col-lg-4">
                            <div class="dashboard-item text-center">
                                <h4>매출 현황</h4>
                                <p>금일 매출액: ${todaySales}원</p>
                                <p>월간 매출액: ${monthlySales}원</p>
                            </div>
                        </div>
                        <div class="col-lg-4">
                            <div class="dashboard-item text-center">
                                <h4>회원 현황</h4>
                                <p>금일 가입자 수: ${todayRegistrations.memberCount}명</p>
                                <p>월간 가입자 수: ${thisMonthRegistrations.memberCount}명</p>
                            </div>
                        </div>
                    </div>
                </div> 
            </div>

            <!-- 상단 정보2 -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="row">
                        <div class="col-lg-4">
                            <div class="dashboard-item text-center">
                                <h4>문의 현황</h4>
                                <p>금일 후기: ${qnaCountToday}건</p>
                                <p>답변 대기: ${pendingQnaCount}건</p>
                            </div>
                        </div>
                        <div class="col-lg-4">
                            <div class="dashboard-item text-center">
                                <h4>구매 후기 현황</h4>
                                <p>신규 후기: ${reviewCountToday}건</p>
                                <p>답변 대기: ${pendingReviewCount}건</p>
                            </div>
                        </div>
                        <div class="col-lg-4">
                            <div class="dashboard-item text-center">
                                <h4>상품 현황</h4>
                                <p>신규 상품 : ${newItemsCount}개</p>
                                <p>전체 상품 : ${totalItemsCount}개</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 상단 정보3 -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="row">
                        <div class="col-lg-4">
                            <div class="dashboard-item text-center">
                                <h4>발주 현황</h4>
                                <p>발주 필요 품목 수: ${itemsNeedOrderCount}건</p>
                                <p>누적 발주 품목 수: ${totalOrdersCount}건</p>
                            </div>
                        </div>
                        <div class="col-lg-4">
                            <div class="dashboard-item text-center">
                                <h4>입고 현황</h4>
                                <p>입고 품목: ${receivedItemsCount}건</p>
                                <p>입고 대기 품목: ${waitingItemsCount}건</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 테이블 -->
            <div class="row">
                <!-- 테이블1 -->
                <div class="col-lg-6">
                     <!-- 테이블2-2 -->
                    <div class="dashboard-item">
                        <div class="row">   
                            <div class="table-container">
                                <table class="table table-striped table-bordered mt-5">
                                    <caption class="table-caption">최다 누적 리뷰 상품 TOP5</caption>
                                    <thead class="table-dark">
                                        <tr>
                                            <th scope="col">순위</th>
                                            <th scope="col">상품명</th>
                                            <th scope="col">리뷰수</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="reviewTop" items="${topFiveByReviewCount}">
                                        <tr>
                                        	<td>${reviewTop.RANK}</td>
                                        	<td>${reviewTop.ITEMNAME}</td>
                                        	<td>${reviewTop.REVIEWCOUNT}</td>
                                        </tr>
                                     </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- 테이블 2-2 -->
                </div>

                <!-- 테이블2 -->
                <div class="col-lg-6">
                    <!-- 테이블2-1 -->
                    <div class="dashboard-item">
                        <div class="row">
                            <div class="table-container">
                                <table class="table table-striped table-bordered mt-5">
                                    <caption class="table-caption">일일 최대 판매액 상품 TOP5</caption>
                                    <thead class="table-dark">
                                        <tr>
                                            <th scope="col">순위</th>
                                            <th scope="col">상품명</th>
                                            <th scope="col">판매액</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:choose>
									    <c:when test="${not empty todayTopFive}">
									        <c:forEach var="todayTop" items="${todayTopFive}">
									            <tr>
									                <td>${todayTop.rank}</td>
									                <td>${todayTop.itemName}</td>
									                <td>${todayTop.salesAmount}</td>
									            </tr>
									        </c:forEach>
									    </c:when>
									    <c:otherwise>
									        <c:forEach var="i" begin="1" end="5">
									            <tr>
									                <td><c:out value="${i}"/></td>
									                <td>미등록</td>
									                <td></td>
									            </tr>
									        </c:forEach>
									    </c:otherwise>
									</c:choose>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                   
                </div>
            </div>
        </div>

<%@include file="includes/footer.jsp"%>

<script>

function updateDateTime() {
  var now = new Date();
  var datetimeElement = document.getElementById('datetime');
  
  // 날짜 포맷 설정
  var options = { year: 'numeric', month: 'long', day: 'numeric', hour: 'numeric', minute: 'numeric', second: 'numeric', hour12: true };
  var formattedDateTime = now.toLocaleString('ko-KR', options);
  
  datetimeElement.textContent = formattedDateTime;
}

updateDateTime();
setInterval(updateDateTime, 1000);

</script>