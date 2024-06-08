<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@include file="includes/header.jsp"%>
<%@include file="includes/leftNav.jsp"%>

	<div class="col-lg-10">
		<div class="panel panel-default">
			<div class="panel-heading">
				멤버 List Page
				
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
			<!-- 검색조건 -->
				  <div class="row">       
				    <div class="col-lg-4">                    
				        <form id='searchForm' action="/admin/member/list" method='get' class='searchForm'>
				            <select class="custom-select" name='type'>
				                <option value=""
				                    <c:out value="${pageMaker.cri.type == null?'selected':''}"/>>--</option>
				                <option value="I"
				                    <c:out value="${pageMaker.cri.type eq 'I'?'selected':''}"/>>아이디</option>
				                <option value="N"
				                    <c:out value="${pageMaker.cri.type eq 'N'?'selected':''}"/>>회원이름</option>
			                    <option value="M"
				                    <c:out value="${pageMaker.cri.type eq 'M'?'selected':''}"/>>메일주소ID</option>
				            </select> 
				            <input type='text' class='custom-keyword' name='keyword' value='<c:out value="${pageMaker.cri.keyword}"/>' /> 
				            <input type='hidden' name='pageNum' value='<c:out value="${pageMaker.cri.pageNum}"/>' /> 
				            <input type='hidden' name='amount' value='<c:out value="${pageMaker.cri.amount}"/>' />
				            <button class='btn custom-btn'>Search</button>
				        </form>
				    </div>
				    
				    <div class="col-lg-8 button-add">	
				        <a href="/admin/member/list" type="button" class="btn btn-board btn-xs btn-dark pull-right btn-info col-lg-2 mx-2 my-2"> 오름차순 </a>
				        <a href="/admin/member/descList" type="button" class="btn btn-board btn-xs pull-right btn-info btn-warning col-lg-2 mx-2 my-2"> 내림차순</a>		       
				    </div>
				</div>
				<!-- end 검색조건 -->
				<button id="regBtn" type="button" class="btn btn-board btn-xs pull-right btn-info col-lg-2 mx-2 my-2" onclick="goToModalForm()">회원등록</button>
				<a href="/admin/member/list" type="button" class="btn btn-board btn-xs btn-light pull-right btn-info col-lg-2 mx-2 my-2">검색해제 일반리스트 </a>
				<table class="table table-striped table-bordered table-hover"
					id="dataTables-example">
					
					<caption class="table-caption">회원</caption>
					<thead>
						<tr>
							<th>아이디</th>
							<th>회원이름</th>
                            <th>메일주소</th>
                            <th>가입일</th>
                            <th>연락처</th>
                            <th>권한</th>
                            <th>휴면여부</th>
                            <th>탈퇴여부</th>
                            
						</tr>
					</thead>
					<tbody>
						<c:forEach var="member" items="${members}">
							<tr class="odd gradeX">
								<td><a href='#' id="${member.memberId}" onclick="goToDetailModalForm(this); ; ">${member.memberId}</a></td>
								<td>${member.memberName}</td>                             
                                <td>${member.memberMail}</td>
								<td><fmt:formatDate pattern="yyyy-MM-dd" value="${member.regDate}" /></td>
                                <td>${member.memberPhone}</td>
                                <c:choose>
								    <c:when test="${member.adminCk == 1}">
								        <td>관리자</td>
								    </c:when>
								    <c:otherwise>
								        <td>일반회원</td>
								    </c:otherwise>
								</c:choose>
								<c:choose>
								    <c:when test="${member.suspended == 'N'}">
								        <td>활동</td>
								    </c:when>
								    <c:otherwise>
								        <td>휴면</td>
								    </c:otherwise>
								</c:choose>
								<c:choose>
								    <c:when test="${member.withdrawal == 'N'}">
								        <td>활동</td>
								    </c:when>
								    <c:otherwise>
								        <td>탈퇴</td>
								    </c:otherwise>
								</c:choose>                               
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

				<form id='actionForm' action="/admin/member/list" method='get'>
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
				<h5 class="modal-memberName" id="cartModalLabel">회원 등록</h5>
				<button type="button" class="close" aria-label="Close"
					onclick="closeModal('#formModal')">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				 <form id="registerForm" name="registerForm" role="form" action="register" method="post" enctype="multipart/form-data">
			        <div class="form-group row">
			            <label>아이디</label>
			            <input class="form-control" name="memberId" placeholder="아이디" required>
			            <button type="button" class="btn btn-default col-lg-3 btn-dark my-2" onclick="checkId()">아이디 중복 확인</button>
			        </div>
			
			        <div class="form-group">
			            <label>비밀번호</label>
			            <input class="form-control" name="memberPw" placeholder="비밀번호" required>
			        </div>
			
			        <div class="form-group">
			            <label>이름</label>
			            <input type="text" class="form-control" name="memberName" placeholder="이름를 입력하세요" required>
			        </div>
			
			        <div class="form-group">
			            <label>연락처</label>
			            <input class="form-control" name="memberPhone" placeholder="연락처" required>
			        </div>
			
			        <div class="form-group">
			            <label>메일주소</label>
			            <input class="form-control" name="memberMail" placeholder="메일주소" required>
			        </div>
			        
                     <div class="btn btn-dark my-2" onclick="execution_daum_address()">
                     	 <span>주소 찾기</span>
					 </div>
                     
			
			        <div class="form-group">
			            <label>우편번호</label>
			            <input class="form-control" name="memberAddr1" placeholder="우편번호" required>
			        </div>
			
			        <div class="form-group">
			            <label>도로명주소</label>
			            <input class="form-control" name="memberAddr2" placeholder="도로명주소" required>
			        </div>
			        
			        <div class="form-group">
			            <label>상세주소</label>
			            <input class="form-control" name="memberAddr3" placeholder="상세주소" required>
			        </div>
			
			        <div class="form-group">
			            <label>권한유형</label>
			            <input class="form-control" name="adminCk" value="0" readonly>
			        </div>
			
			        <div class="form-group">
			            <label>포인트</label>
			            <input class="form-control" name="point" value="5000" readonly>
			        </div>
			
			        <div class="form-group">
			            <label>휴면 여부</label>
			            <input class="form-control" name="suspended" value="N" readonly>
			        </div>
			
			        <div class="form-group">
			            <label>탈퇴 여부</label>
			            <input class="form-control" name="withdrawal" value="N" readonly>
			        </div>
			
			        <button type="submit" class="btn btn-default btn-success"  id="submitBtn" disabled>>Submit Button</button>
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
				<h5 class="modal-memberName" id="cartModalLabel">회원 수정</h5>
				<button type="button" class="close" aria-label="Close"
					onclick="closeModal(this)">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form id="modifyForm" action="modify" method="post" enctype="multipart/form-data">
	        		<div class="form-group">
                        <label>아이디</label> <input class="form-control"
                           id='memberId' name='memberId' readonly>
                     </div>
                    
					 <div class="form-group">
						<label>이름</label> <input type="text"
							class="form-control" name="memberName"
							id="memberName" readonly>
					</div>
                     
                     <div class="form-group">
                        <label>연락처</label> <input class="form-control"
                            id="memberPhone"  name='memberPhone' readonly>
                     </div>
                     

                     <div class="form-group">
                        <label>메일주소</label> <input class="form-control"
                            id="memberMail"  name='memberMail' readonly>
                     </div>
                     
                     <div class="form-group">
                        <label>우편번호</label> <input class="form-control"
                             id='memberAddr1' name='memberAddr1' readonly>
                     </div>
                     
                     <div class="form-group">
                        <label>도로명주소</label> <input class="form-control"
                             id='memberAddr2' name='memberAddr2' readonly>
                     </div>
                     <div class="form-group">
                        <label>상세주소</label> <input class="form-control"
                            id='memberAddr3' name='memberAddr3' readonly>
                     </div>
                     
                     <div class="form-group">
                        <label>권한유형</label>
                        <select class="form-control" id='adminCk' name='adminCk' required>
		                    <option value="1">관리자</option>
		                    <option value="0">일반회원</option>
	                    </select>
                     </div>
                     
                     <div class="form-group">
                        <label>포인트</label> <input class="form-control"
                         id='point' name='point' readonly>
                     </div>

					<div class="form-group">
                        <label>휴면 여부</label>
                        <select class="form-control" id="suspended" name="suspended" required>
		                    <option value="N">활동</option>
		                    <option value="Y">휴면</option>
		                </select>
                     </div>

					<div class="form-group">
                        <label>탈퇴 여부</label>
                        <input type="hidden" class="form-control"  id='withdrawal' name='withdrawal' readonly>
                        <input type="text" class="form-control"  id='withdrawalText' readonly>
                       
                     </div>

                <div class="form-group">
					<label for="regDate">가입일</label> <input type="text"
						id="regDate" class="form-control" readonly>					
				</div>

				<button type="submit" class="btn btn-default">Modify</button>
				<input type="button" onclick="modifyWithdrawal()" class="btn btn-danger" value="탈퇴 처리" id="handleWithdrawal" disabled>
				<button type="button" class="btn btn-secondary" onclick="closeModal(this)">list</button>
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
function modifyWithdrawal() {
	var memberId = document.getElementById("memberId").value
	$.ajax({
		url: '/admin/member/handleWithdrawal/'+ memberId,
		type: 'get',
		data: { memberId: memberId },
		success: function(response)  {
			window.location.href = '/admin/member/list';
			alert("탈퇴 처리를 완료하였습니다.")
		},
		error: function(xhr, status, error) {
			window.location.href = '/admin/member/list';
			alert("탈퇴 처리를 실패하였습니다.")
		}
	});
}

function updateActionUrl() {
    var currentUrl = window.location.href;
    var newPath;

    if (currentUrl.includes("desc")) {
    	newPath = "/admin/member/descList";
    } else {
    	newPath = "/admin/member/list";
    }
   
    // 설정한 값으로 hidden input 업데이트
    $("#currentPath").val(extractPageName(newPath));
    return newPath;
}


function goToDetailModalForm(element) {
	console.log(element)
	let memberId = element.id;
	$.ajax({
		url: '/admin/member/get/'+ memberId,
		type: 'get',
		data: { memberId: memberId },
		success: function(response) {
			$("#memberId").val(response.memberId);
			$("#memberName").val(response.memberName);
			$("#memberPhone").val(response.memberPhone);
			$("#memberMail").val(response.memberMail);
			$("#memberAddr1").val(response.memberAddr1);
			$("#memberAddr2").val(response.memberAddr2);
			$("#memberAddr3").val(response.memberAddr3);
			$("#adminCk").val(response.adminCk);
			$("#point").val(response.point);
			$("#suspended").val(response.suspended);
			$("#withdrawal").val(response.withdrawal);
			if(response.withdrawal === 'N') {
				$("#withdrawalText").val('활동');	
			} else {
				$("#withdrawalText").val('탈퇴');	
			}
			var regDate = new Date(response.regDate);
			var regDateString = regDate.toISOString().substring(0, 10);
			$('#regDate').val(regDateString);
			$('#formModal2').modal('show');
			checkWithdrawalStatus()
		},
		error: function(xhr, status, error) {
			console.error('AJAX 요청 실패:', error);
		}
	});
}




function checkId() {
    var memberId = document.forms["registerForm"]["memberId"].value;
    if (memberId === "") {
        alert("아이디를 입력하세요.");
        return false;
    }
    $.ajax({
		url: '/admin/member/checkId/'+ memberId, // 서버의 URL
		type: 'get', // GET 또는 POST 요청
		data: { memberId: memberId }, // post.id를 서버로 전달
		success: function(response)  {
            if (response.result) {
                alert("사용 가능한 아이디입니다.");
                document.getElementById("submitBtn").disabled = false;
            } else {
                alert("이미 사용중인 아이디입니다.");
                document.getElementById("submitBtn").disabled = true;
            }
        },
		error: function(xhr, status, error) {
			console.error('AJAX 요청 실패:', error);
			document.getElementById("submitBtn").disabled = true;
		}
	});

    
}

function checkWithdrawalStatus() {
    var withdrawalSelect = document.getElementById('withdrawal');
    var handleWithdrawal = document.getElementById('handleWithdrawal');
    if (withdrawalSelect.value === 'N') {
        handleWithdrawal.disabled = false;
    } else {
        handleWithdrawal.disabled = true;
    }
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
					$("[name=memberAddr3]3").focus();

				}
			}).open();

}

</script>