<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.js"
	integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
	crossorigin="anonymous"></script>
<style>
/* 추가한 CSS */
#timer {
	font-weight: bold; /* 굵게 설정 */
}
</style>
</head>
<body>

	<div class="mail_wrap">
		<div class="mail_name">이메일</div>
		<div class="mail_input_box">
			<input class="mail_input" name="memberMail">
		</div>
		<span class="final_mail_ck">이메일을 입력해주세요.</span> <span
			class="mail_input_box_warn"></span>
		<div class="mail_check_wrap">
			<div class="mail_check_input_box" id="mail_check_input_box_false">
				<input class="mail_check_input" disabled="disabled">
			</div>
			<div class="mail_check_button">
				<span id="send_verification_btn">인증번호 전송</span> <span id="timer"
					style="display: none;"></span>
				<!-- 타이머 -->
			</div>
			<div class="clearfix"></div>
			<span id="mail_check_input_box_warn"></span>
		</div>
	</div>


	<div>
		<button id="confirm_button" disabled>확인</button>
		<!-- 초기에 비활성화 -->
		<button id="cancel_button">취소</button>
	</div>

	<script>
    var code = ""; // 이메일 전송 인증번호 저장
    var mailCheck = false;
    var mailnumCheck = false;
    var isResendAvailable = true; // 재전송 가능 여부
    var interval; // 타이머 Interval 객체를 저장할 변수

    $(document).ready(function() {
        // 페이지 로드 시 저장된 이메일 주소 불러오기
        var savedEmail = localStorage.getItem("memberMail");
        if (savedEmail) {
            $(".mail_input").val(savedEmail);
            $(".final_mail_ck").css("display", "none");
            mailCheck = true;
            $("#confirm_button").prop("disabled", false); // 확인 버튼 활성화
        }

        $(".mail_input").on("input", function() { // 이메일 입력란에 입력이 발생할 때마다 이벤트 발생
            var mail = $('.mail_input').val(); // 이메일 입력란

            // 이메일 유효성 검사
            if (mailFormCheck(mail)) {
                $('.final_mail_ck').css('display', 'none');
                mailCheck = true;
                $("#confirm_button").prop("disabled", false); // 확인 버튼 활성화
            } else {
                $('.final_mail_ck').css('display', 'block');
                mailCheck = false;
                $("#confirm_button").prop("disabled", true); // 확인 버튼 비활성화
            }

            // 입력한 이메일 주소를 로컬 저장소에 저장
            localStorage.setItem("memberMail", mail);
        });

        $("#confirm_button").click(function() {
        	console.log("확인 버튼 클릭됨!");
            if (mailCheck && mailnumCheck) {
                alert("이메일 인증이 완료되었습니다.");
                window.opener.location.href = "easyGeneral";
                window.close();
            }
	
            
        });

        // 취소 버튼 클릭 시
        $("#cancel_button").click(function() {
            // 취소 버튼 클릭 시 입력한 이메일 초기화
            $(".mail_input").val("");
            $('.final_mail_ck').css('display', 'none');
            $(".mail_check_input").val("");
            $("#mail_check_input_box_warn").html("");
            $(".mail_check_input").attr("disabled", "disabled");
            $(".mail_check_input_box").attr("id", "mail_check_input_box_false");
            mailCheck = false;
            mailnumCheck = false;
            //타이머 Interval 제거 및 숨김
            clearInterval(interval);
            $("#timer").html("");
            $("#timer").hide();
            //인증번호 전송 메시지 숨기기
            $(".mail_input_box_warn").html("");
            
            //재전송 가능 상태로 설정
            isResendAvailable = true;
            
            // 인증번호 비교 이벤트 해제
            $(".mail_check_input").off('blur');
            
            alert("입력을 취소합니다.");
        });

        // 인증번호 전송 버튼 클릭 시
        $("#send_verification_btn").click(function() {
            if (isResendAvailable && mailCheck) { // 재전송 가능한 상태일 때만 전송
                sendVerificationEmail();
                isResendAvailable = false; // 재전송 버튼 비활성화
                showTimer(); // 타이머 표시
                setTimeout(function() {
                    isResendAvailable = true; // 3분 후 재전송 가능
                    hideTimer(); // 타이머 숨김
                }, 180000); // 3분 (180,000 밀리초)
            } else if (!mailCheck) {
            	alert("이메일 형식을 올바르게 입력해주세요.");
            
            
            } else {
                alert("인증번호 전송은 3분에 한 번만 가능합니다.");
            }
        });

        // 함수: 인증번호 이메일 전송
        function sendVerificationEmail() {
			    var email = $(".mail_input").val(); // 입력한 이메일
			    var checkBox = $(".mail_check_input"); // 인증번호 입력란
			    var boxWrap = $(".mail_check_input_box"); // 인증번호 입력란 박스
			    var warnMsg = $(".mail_input_box_warn"); // 이메일 입력 경고글
			
			    // 이메일 형식 유효성 검사
			    if (mailFormCheck(email)) {
			        warnMsg.html("인증번호가 전송 되었습니다. 이메일을 확인해주세요.");
			        warnMsg.css("display", "inline-block");
			    } else {
			        warnMsg.html("올바르지 못한 이메일 형식입니다.");
			        warnMsg.css("display", "inline-block");
			        return false;
			    }
			
			    $.ajax({
			        type: "GET",
			        url: "mailCheck",
			        data: { email: email },
			        success: function(data) {
			            checkBox.attr("disabled", false);
			            boxWrap.attr("id", "mail_check_input_box_true");
			            code = data;
			        },
			        error: function(xhr, status, error) {
			            console.error(xhr.responseText);
			            console.error(error);
			        }
			    });
			}

        
     // 인증번호 비교
        $(".mail_check_input").blur(function() {
            var inputCode = $(".mail_check_input").val(); // 입력코드    
            var checkResult = $("#mail_check_input_box_warn"); // 비교 결과

            if (inputCode == code) { // 일치할 경우
                checkResult.html("인증번호가 일치합니다.");
                checkResult.attr("class", "correct");
                mailnumCheck = true;
            } else { // 일치하지 않을 경우
                checkResult.html("인증번호를 다시 확인해주세요.");
                checkResult.attr("class", "incorrect");
                mailnumCheck = false;
            }
        });

        // 함수: 입력 이메일 형식 유효성 검사
        function mailFormCheck(email) {
            var form = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
            return form.test(email);
        }

        // 함수: 타이머 표시
        function showTimer() {
            $("#timer").show();
            var sec = 180; // 3분 (180초)

            	 interval = setInterval(function() {
                var minutes = Math.floor(sec / 60); // 분
                var seconds = sec % 60; // 초

                $("#timer").html(
                    ("0" + minutes).slice(-2) + ":" + ("0" + seconds).slice(-2) // 시간 형식 설정
                );

                if (sec == 0) { // 타이머가 다 지나면
                    clearInterval(interval); // 타이머 중지
                    $("#timer").html(""); // 타이머 숨기기
                    // 기타 관련 정보 초기화
                    $(".mail_input").val(""); // 입력한 이메일 초기화
                    $('.final_mail_ck').css('display', 'none'); // 이메일 입력 경고 메시지 숨기기
                    $(".mail_check_input").val(""); // 인증번호 입력란 초기화
                    $("#mail_check_input_box_warn").html(""); // 인증번호 관련 메시지 초기화
                    $(".mail_check_input").attr("disabled", "disabled"); // 인증번호 입력란 비활성화
                    $(".mail_check_input_box").attr("id", "mail_check_input_box_false"); // 인증번호 입력란 스타일 초기화
                    mailCheck = false; // 이메일 유효성 검사 플래그 초기화
                    mailnumCheck = false; // 인증번호 유효성 검사 플래그 초기화
                    // 재전송 가능 상태로 설정
                    isResendAvailable = true;
                    // 인증번호 비교 이벤트 해제
                    $(".mail_check_input").off('blur');
                    alert("3분이 경과하여 정보가 만료되었습니다.");
                    // 팝업 창 닫기
                    window.close();
                } else {
                    sec--;
                }
            }, 1000); // 1초마다 업데이트
        }

        // 함수: 타이머 숨김
        function hideTimer() {
            $("#timer").hide();
        }
             
});
</script>

</body>
</html>
