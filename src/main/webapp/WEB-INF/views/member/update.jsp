<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Member Update</title>
<link rel="stylesheet" href="/resources/css/member/update.css">
<script src="https://code.jquery.com/jquery-3.4.1.js"
	integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
	crossorigin="anonymous"></script>
<script
	src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>
<body>
	<%@ include file="/WEB-INF/views/includes/header.jsp"%>
	<div class="content_area">
		<%@ include file="/WEB-INF/views/includes/leftmenu.jsp"%>
		<div class="edit_area">
			<h2>회원정보 수정</h2>
			<div class="wrapper">
				<form id="update_form" method="post" action="/member/update">

					<!-- 비밀번호 확인 -->
					<c:if test="${not passwordConfirmed}">
						<div class="form-group">
							<label for="password">비밀번호 확인: </label> <input type="password"
								id="password" name="password" required>
							<button type="button" id="password_check_button">확인</button>
							<c:if test="${not empty errorMessage}">
								<p style="color: red;">${errorMessage}</p>
							</c:if>
						</div>
					</c:if>

					<!-- 정보 수정 단계 -->
					<c:if test="${passwordConfirmed}">
						<div class="form-group">
							<label for="memberId">아이디</label> <input type="text"
								id="memberId" name="memberId" readonly="readonly"
								value="${sessionScope.member.memberId}">
						</div>
						<div class="form-group">
							<button type="button" id="toggle_pw_fields_button">비밀번호
								수정</button>
						</div>
						<div class="form-group pw_input_wrap hidden">
							<label for="memberPw">비밀번호</label> <input type="password"
								id="memberPw" name="memberPw" class="pw_input">
						</div>
						<!-- 비밀번호 일치 여부 -->
						<div class="form-group pwck_wrap hidden">
							<label for="PwCk">비밀번호 확인</label> <input type="password"
								id="PwCk" name="PwCk" class="pwck_input"> <span
								class="pwck_input_re_1 hidden">비밀번호가 일치합니다.</span> <span
								class="pwck_input_re_2 hidden">비밀번호가 일치하지 않습니다.</span>
						</div>

						<div class="form-group">
							<label for="memberName">이름</label> <input type="text"
								id="memberName" name="memberName"
								value="${sessionScope.member.memberName}" class="user_input">
						</div>
						<div class="form-group">
							<label for="memberPhone">휴대폰</label> <input type="text"
								id="memberPhone" name="memberPhone"
								value="${sessionScope.member.memberPhone}" class="phone_input">
							<span class="final_phone_ck hidden">휴대폰 번호를 입력해주세요.</span> <span
								class="phone_input_box_warn"></span>
						</div>
						<div class="form-group">
							<label for="memberMail">이메일</label> <input type="email"
								id="memberMail" name="memberMail" readonly="readonly"
								value="${sessionScope.member.memberMail}" class="mail_input">
						</div>
						<div class="form-group">
							<label for="memberAddr1">우편번호</label> <input type="text"
								id="memberAddr1" name="memberAddr1" readonly="readonly"
								value="${sessionScope.member.memberAddr1}"
								class="address_input_1">
							<button type="button" onclick="execution_daum_address()">주소
								찾기</button>
						</div>
						<div class="form-group">
							<input type="text" id="memberAddr2" name="memberAddr2"
								readonly="readonly" value="${sessionScope.member.memberAddr2}"
								class="address_input_2">
						</div>
						<div class="form-group">
							<label for="memberAddr3">상세주소</label> <input type="text"
								id="memberAddr3" name="memberAddr3"
								value="${sessionScope.member.memberAddr3}"
								class="address_input_3"> <span
								class="final_addr_ck hidden">주소를 입력해주세요.</span>
						</div>
						<div class="button_wrap">
							<button type="reset" class="cancel_button" id="cancel_button">취소</button>
							<button type="button" class="update_button">수정하기</button>
						</div>
					</c:if>
				</form>
			</div>
		</div>
		<button type="button" id="withdraw_button" class="withdraw_button">회원
			탈퇴</button>
	</div>

	<%@ include file="/WEB-INF/views/includes/footer.jsp"%>

	<script>
		$(document)
				.ready(
						function() {
							// 비밀번호 확인 버튼 클릭 시
							$("#password_check_button")
									.click(
											function() {
												var password = $("#password")
														.val();
												$
														.ajax({
															type : "POST",
															url : "${pageContext.request.contextPath}/member/checkPassword",
															data : {
																password : password
															},
															success : function(
																	response) {
																if (response === "success") {
																	window.location
																			.reload(); // 비밀번호가 확인되면 페이지를 새로고침하여 정보 수정 화면 표시
																} else {
																	alert("비밀번호가 일치하지 않습니다.");
																}
															},
															error : function(
																	xhr,
																	status,
																	error) {
																alert("비밀번호 확인 중 오류가 발생했습니다.");
															}
														});
											});

							// 취소 버튼 클릭 시
							var cancelButton = document
									.getElementById("cancel_button");
							if (cancelButton) {
								cancelButton.addEventListener("click",
										function() {
											window.location.href = "/main"; // 메인 페이지로 이동합니다.
										});
							}

							// 비밀번호 수정 버튼 클릭 시 비밀번호 입력란과 확인란을 표시
							$("#toggle_pw_fields_button").click(
									function() {
										var button = $(this);
										var isHidden = $(".pw_input_wrap")
												.hasClass("hidden");

										$(".pw_input_wrap, .pwck_wrap")
												.toggleClass("hidden");
										button
												.text(isHidden ? "취소"
														: "비밀번호 수정");
									});

							// 회원정보 수정 버튼(회원정보 수정 기능 작동)
							$(".update_button")
									.click(
											function(event) {
												event.preventDefault();

												/* 입력값 변수 */
												var id = $('#memberId').val(); // id 입력란
												var pw = $('#memberPw').val(); // 비밀번호 입력란
												var pwck = $('#PwCk').val(); // 비밀번호 확인 입력란
												var name = $('#memberName')
														.val(); // 이름 입력란
												var phone = $('#memberPhone')
														.val(); // 전화번호 입력란
												var mail = $('#memberMail')
														.val(); // 이메일 입력란
												var addr = $('#memberAddr3')
														.val(); // 상세주소 입력란

												// 입력값이 비어있는지 확인하여 작동 여부 결정
												if (id === ""
														|| name === ""
														|| phone === ""
														|| mail === ""
														|| addr === ""
														|| ($(
																"#toggle_pw_fields_button")
																.text() === "취소" && (pw === "" || pwck === ""))) {
													alert("입력란을 모두 채워주세요.");
													return; // 비어있는 입력란이 있으면 작동하지 않음
												}

												// 비밀번호 확인
												if ($(
														"#toggle_pw_fields_button")
														.text() === "취소"
														&& pw !== pwck) {
													alert("비밀번호가 일치하지 않습니다.");
													return; // 비밀번호와 비밀번호 확인이 일치하지 않으면 작동하지 않음
												}

												var updateData = {
													memberId : id,
													memberName : name,
													memberPhone : phone,
													memberMail : mail,
													memberAddr1 : $(
															"#memberAddr1")
															.val(),
													memberAddr2 : $(
															"#memberAddr2")
															.val(),
													memberAddr3 : addr
												};

												// 비밀번호 수정 필드가 보이는 경우에만 비밀번호 데이터를 추가
												if ($(
														"#toggle_pw_fields_button")
														.text() === "취소") {
													updateData.memberPw = pw;
												}

												$
														.ajax({
															type : "POST",
															url : "/member/update",
															contentType : "application/json",
															data : JSON
																	.stringify(updateData),
															success : function(
																	response) {
																alert("회원 정보가 성공적으로 수정되었습니다.");
																window.location.href = "/main"; // 성공 시 메인 페이지로 이동
															},
															error : function(
																	xhr,
																	status,
																	error) {
																alert("회원 정보 수정 중 오류가 발생했습니다.");
															}
														});
											});

							/* 비밀번호 확인 일치 유효성 검사 */
							$('.pwck_input').on(
									"propertychange change keyup paste input",
									function() {
										var pw = $('.pw_input').val();
										var pwck = $('.pwck_input').val();
										$('.final_pwck_ck').css('display',
												'none');

										if (pw == pwck) {
											$('.pwck_input_re_1').css(
													'display', 'block');
											$('.pwck_input_re_2').css(
													'display', 'none');
										} else {
											$('.pwck_input_re_1').css(
													'display', 'none');
											$('.pwck_input_re_2').css(
													'display', 'block');
										}
									});

							// 휴대폰 번호 입력란에 전화번호 형식 유지
							$('.phone_input').on(
									'input',
									function() {
										// 숫자 이외의 문자를 모두 제거
										var phoneNumber = this.value.replace(
												/[^0-9]/g, '');

										// 전화번호 형식에 맞게 하이픈(-)을 추가
										var formattedPhoneNumber = '';
										if (phoneNumber.length < 4) {
											formattedPhoneNumber = phoneNumber;
										} else if (phoneNumber.length < 7) {
											formattedPhoneNumber = phoneNumber
													.substring(0, 3)
													+ '-'
													+ phoneNumber.substring(3);
										} else if (phoneNumber.length < 11) {
											formattedPhoneNumber = phoneNumber
													.substring(0, 3)
													+ '-'
													+ phoneNumber.substring(3,
															6)
													+ '-'
													+ phoneNumber.substring(6);
										} else {
											formattedPhoneNumber = phoneNumber
													.substring(0, 3)
													+ '-'
													+ phoneNumber.substring(3,
															7)
													+ '-'
													+ phoneNumber.substring(7,
															11);
										}

										// 휴대폰 번호 입력란에 형식을 적용
										this.value = formattedPhoneNumber;
									});

							/* 다음 주소 연동 */
							window.execution_daum_address = function() {
								new daum.Postcode(
										{
											oncomplete : function(data) {
												// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.
												var addr = ''; // 주소 변수
												var extraAddr = ''; // 참고항목 변수

												// 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
												if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
													addr = data.roadAddress;
												} else { // 사용자가 지번 주소를 선택했을 경우(J)
													addr = data.jibunAddress;
												}

												// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
												if (data.userSelectedType === 'R') {
													if (data.bname !== ''
															&& /[동|로|가]$/g
																	.test(data.bname)) {
														extraAddr += data.bname;
													}
													if (data.buildingName !== ''
															&& data.apartment === 'Y') {
														extraAddr += (extraAddr !== '' ? ', '
																+ data.buildingName
																: data.buildingName);
													}
													if (extraAddr !== '') {
														extraAddr = ' ('
																+ extraAddr
																+ ')';
													}
													addr += extraAddr;
												} else {
													addr += ' ';
												}

												// 우편번호와 주소 정보를 해당 필드에 넣는다.
												$("#memberAddr1").val(
														data.zonecode);
												$("#memberAddr2").val(addr);
												// 상세주소 입력란 readonly 속성 변경 및 커서를 상세주소 필드로 이동한다.
												$("#memberAddr3").attr(
														"readonly", false);
												$("#memberAddr3").focus();
											}
										}).open();
							};

							// 회원 탈퇴 버튼 클릭 시
							$("#withdraw_button").click(function() {
								if (confirm("정말로 탈퇴하시겠습니까?")) {
									$.ajax({
										type : "POST",
										url : "/member/withdraw",
										success : function(response) {
											alert("회원 탈퇴가 완료되었습니다.");
											window.location.href = "/main"; // 탈퇴 후 메인 페이지로 이동
										},
										error : function(xhr, status, error) {
											alert("회원 탈퇴 중 오류가 발생했습니다.");
										}
									});
								}
							});
						});
	</script>


</body>
</html>
