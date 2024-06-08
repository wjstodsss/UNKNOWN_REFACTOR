<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/tld/custom.tld" prefix="custom"%>
<!-- 추가된 부분 -->

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome Unknown</title>
<link rel="stylesheet" href="/resources/css/search.css">
<script src="https://code.jquery.com/jquery-3.4.1.js"
	integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
	crossorigin="anonymous"></script>
</head>
<body>

	<%@ include file="/WEB-INF/views/includes/header.jsp"%>
	<div class="content_area">
		<!-- 게시물 o -->
		<c:if test="${listcheck != 'empty'}">
			<div class="list_search_result">
				<table class="type_list">
					<colgroup>
						<col width="110">
						<col width="*">
						<col width="120">
						<col width="120">
						<col width="120">
					</colgroup>
					<tbody id="searchList>">
						<c:forEach items="${list}" var="list">
							<tr>
								<td class="image">
									<div class="image_wrap"
										data-itemid="${list.imageList[0].itemId}"
										data-path="${list.imageList[0].uploadPath}"
										data-uuid="${list.imageList[0].uuid}"
										data-filename="${list.imageList[0].fileName}">
										<img>
									</div>
								</td>
								<td class="detail">
									<div class="category">[${list.cateName}]</div>
									<div class="title">
										<a href="/goodsDetail/${list.itemId}"> ${list.itemName} </a>
									</div>

									<div class="brand">
										<fmt:parseDate var="mnfcYear" value="${list.mnfcYear}"
											pattern="yyyy-MM-dd" />
										${list.brandName}| ${list.manufacturer} |
										<fmt:formatDate value="${mnfcYear}" pattern="yyyy-MM-dd" />
									</div>
								</td>
								<td class="info">
									<div class="rating">평점(추후 추가)</div>
								</td>
								<td class="price">
									<div class="org_price">
										<del>
											<fmt:formatNumber
												value="${custom:truncatePrice(list.itemPrice)}"
												pattern="#,### 원" />
										</del>
									</div>
									<div class="sell_price">
										<strong> <fmt:formatNumber
												value="${custom:truncatePrice(list.itemPrice * (1-list.itemDiscount))}"
												pattern="#,### 원" />
										</strong>
									</div>
								</td>
								<td class="option"></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

			<!-- 페이지 이동 인터페이스 -->
			<div class="pageMaker_wrap">
				<ul class="pageMaker">

					<!-- 이전 버튼 -->
					<c:if test="${pageMaker.prev }">
						<li class="pageMaker_btn prev"><a
							href="${pageMaker.pageStart -1}">이전</a></li>
					</c:if>

					<!-- 페이지 번호 -->
					<c:forEach begin="${pageMaker.pageStart }"
						end="${pageMaker.pageEnd }" var="num">
						<li
							class="pageMaker_btn ${pageMaker.cri.pageNum == num ? 'active':''}">
							<a href="${num}">${num}</a>
						</li>
					</c:forEach>

					<!-- 다음 버튼 -->
					<c:if test="${pageMaker.next}">
						<li class="pageMaker_btn next"><a
							href="${pageMaker.pageEnd + 1 }">다음</a></li>
					</c:if>
				</ul>
			</div>

			<form id="moveForm" action="/search" method="get">
				<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}">
				<input type="hidden" name="amount" value="${pageMaker.cri.amount}">
				<input type="hidden" name="keyword" value="${pageMaker.cri.keyword}">
				<input type="hidden" name="type" value="${pageMaker.cri.type}">
			</form>

		</c:if>
		<!-- 게시물 x -->
		<c:if test="${listcheck == 'empty'}">
			<div class="table_empty">검색결과가 없습니다.</div>
		</c:if>
	</div>

	<%@ include file="/WEB-INF/views/includes/footer.jsp"%>

	<script>
		/* gnb_area 로그아웃 버튼 작동 */
		$("#gnb_logout_button").click(function() {
			//alert("버튼 작동");
			$.ajax({
				type : "POST",
				url : "/member/logout.do",
				success : function(data) {
					alert("로그아웃 성공");
					document.location.reload();
				}
			}); // ajax 
		});

		/* 페이지 이동 버튼 */
		const moveForm = $('#moveForm');

		$(".pageMaker_btn a").on("click", function(e) {

			e.preventDefault();

			moveForm.find("input[name='pageNum']").val($(this).attr("href"));

			moveForm.submit();

		});

		$(document)
				.ready(
						function() {
							// 검색 타입 selected
							const selectedType = '<c:out value="${pageMaker.cri.type}"/>';
							if (selectedType != "") {
								$("select[name='type']").val(selectedType)
										.attr("selected", "selected");
							}

							/* 이미지 삽입 */
							$(".image_wrap")
									.each(
											function(i, obj) {

												const bobj = $(obj);

												if (bobj.data("itemid")) {
													const uploadPath = bobj
															.data("path");
													const uuid = bobj
															.data("uuid");
													const fileName = bobj
															.data("filename");

													const fileCallPath = encodeURIComponent(uploadPath
															+ "/t_"
															+ uuid
															+ "_" + fileName);

													$(this)
															.find("img")
															.attr(
																	'src',
																	'/display?fileName='
																			+ fileCallPath);
												} else {
													$(this)
															.find("img")
															.attr('src',
																	'/resources/img/goodsNoImage.png');
												}

											});

						});
	</script>
</body>
</html>
