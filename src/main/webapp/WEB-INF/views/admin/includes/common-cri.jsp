<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div id="commonHiddenInputs" style="display: none;">
	<input type='hidden' name='pageNum' value='<c:out value="${pageMaker.cri.pageNum}"/>' />
	<input type='hidden' name='amount' value='<c:out value="${pageMaker.cri.amount}"/>' />
	<input type='hidden' name='type' value='<c:out value="${pageMaker.cri.type}"/>' />
	<input type='hidden' name='keyword' value='<c:out value="${pageMaker.cri.keyword}"/>' />
	<c:choose>
		<c:when test="${pageMaker.cri.sortColumn != null}">
			<input type='hidden' name='sortColumn' id='sortColumn' value='<c:out value="${pageMaker.cri.sortColumn}"/>' />
		</c:when>
		<c:otherwise>
			<input type='hidden' name='sortColumn' id='sortColumn' value='' />
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${pageMaker.cri.groupColumn != null}">
			<input type='hidden' name='groupColumn' id='groupColumn' value='<c:out value="${pageMaker.cri.groupColumn}"/>' />
		</c:when>
		<c:otherwise>
			<input type='hidden' name='groupColumn' id='groupColumn' value='' />
		</c:otherwise>
	</c:choose>
	<input type='hidden' name='sortType' id='sortType' value='<c:out value="${pageMaker.cri.sortType}"/>' />
</div>

<input type='hidden' id='error' value="${error}" />
