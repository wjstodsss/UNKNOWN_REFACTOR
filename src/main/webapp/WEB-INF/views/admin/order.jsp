<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@include file="includes/header.jsp"%>
<%@include file="includes/leftNav.jsp"%>

<div class="col-lg-10">
    <div class="panel panel-default">
        <div class="panel-heading">
            주문 List Page
            
        </div>
        <!-- /.panel-heading -->
        <div class="panel-body">
        <!-- 검색조건 -->
              <div class="row"> 
                <div class="col-lg-4">                    
                    <form id='searchForm' action="/admin/order/list" method='get' class='searchForm'>
                        <select class="custom-select" name='type'>
                            <option value=""
                                <c:out value="${pageMaker.cri.type == null?'selected':''}"/>>--</option>
                            <option value="I"
                                <c:out value="${pageMaker.cri.type eq 'I'?'selected':''}"/>>주문ID</option>
                            <option value="M"
                                <c:out value="${pageMaker.cri.type eq 'M'?'selected':''}"/>>회원ID</option>
                            <option value="R"
                            <c:out value="${pageMaker.cri.type eq 'R'?'selected':''}"/>>수령인</option>
                        </select> 
                        <input type='text' class='custom-keyword' name='keyword' value='<c:out value="${pageMaker.cri.keyword}"/>' /> 
                        <input type='hidden' name='pageNum' value='<c:out value="${pageMaker.cri.pageNum}"/>' /> 
                        <input type='hidden' name='amount' value='<c:out value="${pageMaker.cri.amount}"/>' />
                        <button class='btn custom-btn'>Search</button>
                    </form>
                </div>
                
                <div class="col-lg-8 button-add">	
                     <a href="/admin/order/list" type="button" class="btn btn-board btn-xs btn-dark pull-right btn-info col-lg-2 mx-2 my-2"> 오름차순 </a>
                    <a href="/admin/order/descList" type="button" class="btn btn-board btn-xs pull-right btn-info btn-warning col-lg-2 mx-2 my-2"> 내림차순</a>		       
                </div>
            </div>
            <!-- end 검색조건 -->
            <button id="regBtn" type="button" class="btn btn-board btn-xs pull-right btn-info col-lg-2 mx-2 my-2" onclick="goToModalForm()">주문등록</button>
            <a href="/admin/order/list" type="button" class="btn btn-board btn-xs btn-light pull-right btn-info col-lg-2 mx-2 my-2">검색해제 일반리스트 </a>
            <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                
                <caption class="table-caption">주문 & 배송</caption>
                <thead>
                    <tr>
                        <th>주문ID</th>
                        <th>수령인</th>
                        <th>회원ID</th>
                        <th>주문상태</th>
                        <th>주문일</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${orders}">
                        <tr class="odd gradeX">
                            <td><a href='#' id="${order.orderId}" onclick="goToDetailModalForm(this)">${order.orderId}</a></td>
                            <td>${order.receiver}</td>                             
                            <td>${order.memberId}</td>
                            <td>${order.orderState}</td>
                            <td><fmt:formatDate pattern="yyyy-MM-dd" value="${order.orderDate}" /></td>
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

            <form id='actionForm' action="/admin/order/list" method='get'>
                <input type='hidden' name='pageNum'
                    value='${pageMaker.cri.pageNum}'> <input type='hidden'
                    name='amount' value='${pageMaker.cri.amount}'>
                    <input type='hidden' name='type' value='${pageMaker.cri.type}'>
                    <input type='hidden' name='keyword' value='${pageMaker.cri.keyword}'>
            </form>
            <!-- /.panel-body -->
        </div>
        <!-- /.panel -->
    </div>
    <!-- /.col-lg-12 -->
</div>

</div>
<!-- /#page-wrapper -->

</div>
<!-- 등록 모달 -->
<div class="modal" id="formModal" tabindex="-1" role="dialog"
aria-hidden="true">
<div class="modal-dialog" role="document">
    <div class="modal-content row">
        <div class="modal-header">
            <h5 class="modal-title" id="cartModalLabel">주문 등록</h5>
            <button type="button" class="close" aria-label="Close" onclick="closeModal('#formModal')">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="modal-body">
             <form id="registerForm" name="registerForm" role="form" action="register" method="post">
                <div class="form-group">
                    <label>수령인</label>
                    <input class="form-control" name="receiver" placeholder="수령인" required>
                </div>
        
                <div class="form-group">
                    <label>회원ID</label>
                    <input type="text" class="form-control" name="memberId" placeholder="회원ID 입력하세요" required>
                    <button type="button" class="btn btn-default col-lg-3 btn-dark my-2" onclick="checkMemberId()">회원ID 확인</button>
                </div>
                
                 <div class="form-group btn btn-dark " onclick="execution_daum_address()">
                      <span>주소 찾기</span>
                 </div>
                 
                <div class="form-group">
                    <label>우편번호</label>
                    <input class="form-control" name="memberAddr1" placeholder="우편번호" required>
                </div>
        
                <div class="form-group">
                    <label>도로명/지번 주소</label>
                    <input class="form-control" name="memberAddr2" placeholder="도로명주소" required>
                </div>
                
                <div class="form-group">
                    <label>상세주소</label>
                    <input class="form-control" name="memberAddr3" placeholder="상세주소" required>
                </div>
        
                <div class="form-group">
                    <label>배송 요청 사항</label>
                    <textarea class="form-control" name="shipRequest"></textarea>
                </div>
        
                <div class="form-group">
                    <label>배송 상태</label>
                    <input class="form-control" name="orderState" value="배송 준비" readonly>
                </div>
        
                <div class="form-group">
                    <label>배송비</label>
                    <input class="form-control" name="deliveryCost">
                </div>
        
                <div class="form-group">
                    <label>사용 포인트</label>
                    <input class="form-control" name="usePoint" required>
                </div>
        
                <button type="submit" class="btn btn-default btn-success"  id="submitBtn" disabled>Submit Button</button>
                <button type="reset" class="btn btn-secondary">다시 작성</button>
                <button type="button" class="btn btn-secondary" onclick="closeModal(this)">list</button>
            </form>
        </div>
    </div>
</div>
</div>

<!-- 상세 수정 삭제 모달 -->
<div class="modal" id="formModal2" tabindex="-1" role="dialog"
aria-hidden="true">
<div class="modal-dialog" role="document">
    <div class="modal-content">
        <div class="modal-header">
            <h5 class="modal-title" id="cartModalLabel">주문 수정</h5>
            <button type="button" class="close" aria-label="Close"
                onclick="closeModal(this)">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="modal-body">
            <form id="modifyForm" action="modify" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label>아이디</label> <input class="form-control" id='orderId' name='orderId' readonly>
                 </div>
                
                 <div class="form-group">
                    <label>수령인</label> <input type="text"
                        class="form-control" name="receiver"
                        id="receiver" readonly>
                </div>
                <div class="form-group">
                    <label>회원ID</label> <input type="text"
                        class="form-control" name="memberId"
                        id="memberId" readonly>
                </div>

                <div class="btn btn-dark my-2" onclick="execution_daum_address()">
                    <span>주소 찾기</span>
                </div> 

                <div class="form-group">
                    <label>우편번호</label>
                    <input class="form-control" name="memberAddr1" id="memberAddr1" required>
                </div>
        
                <div class="form-group">
                    <label>도로명/지번 주소</label>
                    <input class="form-control" name="memberAddr2" id="memberAddr2" required>
                </div>
                
                <div class="form-group">
                    <label>상세주소</label>
                    <input class="form-control" name="memberAddr3" id="memberAddr3" required>
                </div>

                <div class="form-group">
                    <label>배송 상태</label>
                     <select id="categorySelect" name="orderState"  id="orderState" class="btn btn-outline-dark my-2" onchange="openSelection('itemCate', 1, 'register')">
					  		<option>준비 중</option>
					  		<option>배송 준비</option>
					  		<option>배송 완료</option>
					  </select>
                </div>

                <div class="form-group">
                    <label>배송 요청 사항</label>
                    <textarea class="form-control" rows="5" name="shipRequest" id="shipRequest"></textarea>
                </div>
                 
                 <div class="form-group">
                    <label>사용 포인트</label> <input class="form-control" id='usePoint' name='usePoint' readonly>
                 </div>

                <div class="form-group">
                    <label>배송료</label>
                    <input class="form-control" id='deliveryCost' name='deliveryCost' readonly>
                 </div>
                <div class="form-group">
                    <label for="orderDate">주문일</label> <input type="text"
                        id="orderDate" class="form-control" readonly>					
                </div>

            <button type="submit" class="btn btn-default">Modify</button>
            <input type="button" onclick="orderStateUpdate()" class="btn btn-danger" value="주문 취소">
            <button type="button" class="btn btn-secondary" onclick="closeModal(this)">닫기</button>
            <input type="hidden" id="currentPath" name="currentPath" value="">
            </form>
        </div>
    </div>
</div>
</div>

<%@include file="includes/footer.jsp"%>
<script
    src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js">
</script>
<script>
function orderStateUpdate() {
	var orderId = document.getElementById("orderId").value
	$.ajax({
		url: '/admin/order/orderState/'+ orderId,
		type: 'get',
		data: { orderId: orderId },
		success: function(response)  {
			window.location.href = '/admin/order/list';
		},
		error: function(xhr, status, error) {
			window.location.href = '/admin/order/list';
			alert('요청 실패');
		}
	});
	
}

function updateActionUrl() {
var currentUrl = window.location.href;
var newPath;

if (currentUrl.includes("desc")) {
    newPath = "/admin/order/descList";
} else {
    newPath = "/admin/order/list";
}

$("#currentPath").val(extractPageName(newPath));
return newPath;
}


function checkMemberId() {
    var memberId = document.forms["registerForm"]["memberId"].value;
    if (memberId === "") {
        alert("아이디를 입력하세요.");
        return false;
    }
    $.ajax({
		url: '/admin/member/checkId/'+ memberId,
		type: 'get',
		data: { memberId: memberId },
		success: function(response)  {
            if (response.result) {
            	alert("존재하지 않는 아이디입니다.");
            	 document.getElementById("submitBtn").disabled = true;
            } else {
            	alert("사용 가능한 아이디입니다.");
            	document.getElementById("submitBtn").disabled = false;
            }
        },
		error: function(xhr, status, error) {
			console.error('AJAX 요청 실패:', error);
			document.getElementById("submitBtn").disabled = true;
		}
	});

    
}

function goToDetailModalForm(element) {
console.log(element)
let orderId = element.id;
$.ajax({
    url: '/admin/order/get/'+ orderId,
    type: 'get',
    data: { orderId: orderId },
    success: function(response) {
        $("#orderId").val(response.orderId);
        $("#receiver").val(response.receiver);
        $("#orderState").val(response.orderState);
        $("#memberId").val(response.memberId);
        $("#memberAddr1").val(response.memberAddr1);
        $("#memberAddr2").val(response.memberAddr2);
        $("#memberAddr3").val(response.memberAddr3);
        $("#shipRequest").val(response.shipRequest);
        $("#deliveryCost").val(response.deliveryCost);
        $("#usePoint").val(response.usePoint);
        var orderDate = new Date(response.orderDate);
        var orderDateString = orderDate.toISOString().substring(0, 10);
        $('#orderDate').val(orderDateString);
        $('#formModal2').modal('show');
    },
    error: function(xhr, status, error) {
        console.error('AJAX 요청 실패:', error);
    }
});
}


/* 다음 주소 연동 */
function execution_daum_address() {

new daum.Postcode(
        {
            oncomplete : function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if (data.userSelectedType === 'R') {
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if (data.bname !== ''
                            && /[동|로|가]$/g.test(data.bname)) {
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if (data.buildingName !== ''
                            && data.apartment === 'Y') {
                        extraAddr += (extraAddr !== '' ? ', '
                                + data.buildingName
                                : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if (extraAddr !== '') {
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 주소변수 문자열과 참고항목 문자열 합치기
                    addr += extraAddr;

                } else {
                    addr += ' ';
                }
                
                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                $("[name=memberAddr1]").val(data.zonecode);
                $("[name=memberAddr2]").val(addr);
                $("[name=memberddr3]3").focus();

            }
        }).open();

}

</script>

