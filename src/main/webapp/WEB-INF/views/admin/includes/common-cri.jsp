<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div id="commonHiddenInputs" style="display: none;">
	<input type='hidden' name='pageNum' value='${pageMaker.cri.pageNum}'>
	<input type='hidden' name='amount' value='${pageMaker.cri.amount}'>
	<input type='hidden' name='type' value='${pageMaker.cri.type}'>
	<input type='hidden' name='keyword' value='${pageMaker.cri.keyword}'>
	<input type='hidden' name='sortColumn' id='sortColumn' 
	value='${pageMaker.cri.sortColumn != null ? pageMaker.cri.sortColumn : ""}'> 
	<input type='hidden' name='groupColumn' id='groupColumn' 
	value='${pageMaker.cri.groupColumn != null ? pageMaker.cri.groupColumn : ""}'>
	<input type='hidden' name='sortType' id='sortType' value='${sortType}'>
</div>