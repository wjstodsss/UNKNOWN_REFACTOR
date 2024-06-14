<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/resources/css/member/login.css">

<script src="https://code.jquery.com/jquery-3.4.1.js"
	integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
	crossorigin="anonymous"></script>
<script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
<script>
    $(document).ready(function() {
        $("#kakao_login").click(function() {
            window.location.href = "https://kauth.kakao.com/oauth/authorize?client_id=7348caf219d2fff8532a8961c20f78ab&redirect_uri=http://localhost:8080/member/easyGeneral&response_type=code";
        });

        $(".login_button").click(function(){
            $("#login_form").attr("action", "/member/login.do");
            $("#login_form").submit();
        });
    });
</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/includes/header.jsp"%>
	<div class="wrapper">
		<div class="wrap">
			<form id="login_form" action="/member/login.do" method="post">
				<div class="logo_wrap">
					<span>Unknown</span>
				</div>
				<div class="login_wrap">
					<div class="id_wrap">
						<div class="id_input_box">
							<input class="id_input" name="memberId">
						</div>
					</div>
					<div class="pw_wrap">
						<div class="pw_input_box">
							<input class="pw_input" name="memberPw" type="password">
						</div>
					</div>
					<div class="options_wrap">
						<input type="checkbox" id="auto_login" name="auto_login">
						<label for="auto_login">자동로그인</label> <input type="checkbox"
							id="save_id" name="save_id"> <label for="save_id">아이디저장</label>
					</div>
					<c:if test="${not empty withdrawalMessage}">
						<div class="login_warn">${withdrawalMessage}</div>
					</c:if>
					<c:if test="${result == 0}">
						<div class="login_warn">사용자 ID 또는 비밀번호를 잘못 입력하셨습니다.</div>
					</c:if>
					<div class="login_button_wrap">
						<input type="button" class="login_button" value="로그인">
					</div>
					<div class="links_wrap">
						<a href="/member/findIdView">아이디 찾기</a> | <a
							href="/member/findPassword">비밀번호 찾기</a>
					</div>
					<div class="social_login_wrap">
						<button type="button" class="social_login_button" id="kakao_login">K</button>
						<button type="button" class="social_login_button" id="naver_login">N</button>
						<button type="button" class="social_login_button" id="apple_login">A</button>
					</div>
					<div class="simple_signup">
						<button class="simple_signup_button">간편회원가입</button>
					</div>
					<div class="icons_wrap">
						<div class="icon_row">
							<div class="icon">O</div>
							<div class="icon">O</div>
							<div class="icon">O</div>
							<div class="icon">O</div>
						</div>
						<div class="icon_row">
							<div class="icon">O</div>
							<div class="icon">O</div>
							<div class="icon">O</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/includes/footer.jsp"%>

	<script>
        $(document)
                .ready(
                        function() {
                            // 이전에 자동 로그인 확인란이 선택되었는지 확인하고 해당 여부를 설정합니다.
                            var rememberMeChecked = localStorage
                                    .getItem('rememberMeChecked');
                            if (rememberMeChecked === 'true') {
                                $('#remember_me').prop('checked', true);
                            }

                            // 이전에 아이디 저장 확인란이 선택되었는지 확인하고 해당 여부를 설정합니다.
                            var rememberIdChecked = localStorage
                                    .getItem('rememberIdChecked');
                            if (rememberIdChecked === 'true') {
                                $('#remember_id').prop('checked', true);
                                // 저장된 아이디가 있으면 입력 필드에 채웁니다.
                                var rememberedId = localStorage
                                        .getItem('rememberedId');
                                if (rememberedId) {
                                    $('#member_id').val(rememberedId);
                                }
                            }

                            // 자동 로그인 확인란 변경 이벤트 처리
                            $('#remember_me').change(
                                    function() {
                                        // 자동 로그인 확인란의 상태를 localStorage에 저장합니다.
                                        localStorage.setItem(
                                                'rememberMeChecked', $(this)
                                                        .prop('checked'));
                                        // 해당하는 숨은 필드 값을 업데이트합니다.
                                        $('#remember_me_value').val(
                                                $(this).prop('checked'));
                                    });

                            // 아이디 저장 확인란 변경 이벤트 처리
                            $('#remember_id').change(
                                    function() {
                                        // 아이디 저장 확인란의 상태를 localStorage에 저장합니다.
                                        localStorage.setItem(
                                                'rememberIdChecked', $(this)
                                                        .prop('checked'));
                                    });

                            // 입력된 아이디를 localStorage에 저장합니다.
                            $('#member_id').on(
                                    'input',
                                    function() {
                                        localStorage.setItem('rememberedId', $(
                                                this).val());
                                    });

                            // 페이지 로드 시 자동 로그인 확인란 변경 이벤트를 트리거합니다.
                            $('#remember_me').trigger('change');
                            // 페이지 로드 시 아이디 저장 확인란 변경 이벤트를 트리거합니다.
                            $('#remember_id').trigger('change');
                        });
    </script>

</body>
</html>
