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
    $(document).ready(function() {
        var code = ""; // 이메일 전송 인증번호 저장
        var mailCheck = false;
        var mailnumCheck = false;
        var isResendAvailable = true; // 재전송 가능 여부
        var interval; // 타이머 Interval 객체를 저장할 변수

        // 페이지 로드 시 저장된 이메일 주소 불러오기
        var savedEmail = localStorage.getItem("memberMail");
        if (savedEmail) {
            $(".mail_input").val(savedEmail);
            $(".final_mail_ck").css("display", "none");
            mailCheck = true;
            $("#confirm_button").prop("disabled", false); // 확인 버튼 활성화
        }

        $(".mail_input").on("input", function() {
            var email = $('.mail_input').val();
            if (mailFormCheck(email)) {
                checkEmailDuplicate(email); // 이메일 중복 확인 함수 호출
            } else {
                $('.final_mail_ck').css('display', 'block');
                mailCheck = false;
                $("#confirm_button").prop("disabled", true); // 확인 버튼 비활성화
            }
            localStorage.setItem("memberMail", email);
        });

        $("#confirm_button").click(function() {
            console.log("확인 버튼 클릭됨!");
            if (mailCheck && mailnumCheck) {
                alert("이메일 인증이 완료되었습니다.");
                if (window.opener) {
                    console.log("부모 창 URL 변경 시도 중...");
                    window.opener.location.href = "easyGeneral";
                    window.close();
                } else {
                    console.log("부모 창을 찾을 수 없음!");
                }
            } else {
                console.log("mailCheck:", mailCheck, "mailnumCheck:", mailnumCheck);
            }
        });

        $("#cancel_button").click(function() {
            $(".mail_input").val("");
            $('.final_mail_ck').css('display', 'none');
            $(".mail_check_input").val("");
            $("#mail_check_input_box_warn").html("");
            $(".mail_check_input").attr("disabled", "disabled");
            $(".mail_check_input_box").attr("id", "mail_check_input_box_false");
            mailCheck = false;
            mailnumCheck = false;
            clearInterval(interval);
            $("#timer").html("");
            $("#timer").hide();
            $(".mail_input_box_warn").html("");
            isResendAvailable = true;
            $(".mail_check_input").off('blur');
            alert("입력을 취소합니다.");
        });

        $("#send_verification_btn").click(function() {
            if (isResendAvailable && mailCheck) {
                checkEmailDuplicateBeforeSending();
            } else if (!mailCheck) {
                alert("이메일 형식을 올바르게 입력해주세요.");
            } else {
                alert("인증번호 전송은 3분에 한 번만 가능합니다.");
            }
        });

        $(".mail_check_input").on("input", function() {
            var inputCode = $(this).val();
            if (inputCode === code) {
                console.log("인증번호 일치");
                mailnumCheck = true;
                $("#confirm_button").prop("disabled", false);
            } else {
                console.log("인증번호 불일치");
                mailnumCheck = false;
                $("#confirm_button").prop("disabled", true);
            }
        });

        function checkEmailDuplicateBeforeSending() {
            var email = $(".mail_input").val();
            $.ajax({
                type: "POST",
                url: "/member/checkEmailDuplicate",
                data: { memberMail: email },
                success: function(response) {
                    console.log("중복 확인 응답:", response);
                    if (response.isDuplicate) {
                        alert("해당 이메일 주소로 가입된 계정이 이미 존재합니다.");
                        if (window.opener) {
                            console.log("부모 창 URL 변경 시도 중...");
                            window.opener.location.href = "/member/login";
                            window.close();
                        } else {
                            console.log("부모 창을 찾을 수 없음!");
                        }
                    } else {
                        sendVerificationEmail();
                        isResendAvailable = false;
                        showTimer();
                        setTimeout(function() {
                            isResendAvailable = true;
                            hideTimer();
                        }, 180000);
                    }
                },
                error: function(xhr, status, error) {
                    console.error("중복 확인 오류:", xhr.responseText);
                    console.error("오류 상태:", status);
                    console.error("오류 내용:", error);
                }
            });
        }

        function sendVerificationEmail() {
            var email = $(".mail_input").val();
            var checkBox = $(".mail_check_input");
            var boxWrap = $(".mail_check_input_box");
            var warnMsg = $(".mail_input_box_warn");

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

        function checkEmailDuplicate(email) {
            $.ajax({
                type: "POST",
                url: "/member/checkEmailDuplicate",
                data: { memberMail: email },
                success: function(response) {
                    console.log("중복 확인 응답:", response);
                    if (response.isDuplicate) {
                        alert("해당 이메일 주소로 가입된 계정이 이미 존재합니다.");
                        if (window.opener) {
                            console.log("부모 창 URL 변경 시도 중...");
                            window.opener.location.href = "/member/login";
                            window.close();
                        } else {
                            console.log("부모 창을 찾을 수 없음!");
                        }
                    } else {
                        $('.final_mail_ck').css('display', 'none');
                        mailCheck = true;
                        $("#confirm_button").prop("disabled", false);
                    }
                },
                error: function(xhr, status, error) {
                    console.error("중복 확인 오류:", xhr.responseText);
                    console.error("오류 상태:", status);
                    console.error("오류 내용:", error);
                }
            });
        }

        function mailFormCheck(email) {
            var form = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
            return form.test(email);
        }

        function showTimer() {
            $("#timer").show();
            var sec = 180;
            interval = setInterval(function() {
                var minutes = Math.floor(sec / 60);
                var seconds = sec % 60;
                $("#timer").html(("0" + minutes).slice(-2) + ":" + ("0" + seconds).slice(-2));
                if (sec == 0) {
                    clearInterval(interval);
                    $("#timer").html("");
                    $(".mail_input").val("");
                    $('.final_mail_ck').css('display', 'none');
                    $(".mail_check_input").val("");
                    $("#mail_check_input_box_warn").html("");
                    $(".mail_check_input").attr("disabled", "disabled");
                    $(".mail_check_input_box").attr("id", "mail_check_input_box_false");
                    mailCheck = false;
                    mailnumCheck = false;
                    isResendAvailable = true;
                    $(".mail_check_input").off('blur');
                    alert("3분이 경과하여 정보가 만료되었습니다.");
                    window.close();
                } else {
                    sec--;
                }
            }, 1000);
        }

        function hideTimer() {
            $("#timer").hide();
        }
    });

</script>

</body>
</html>
