<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="footer_nav_bg">
	<div class="footer_nav">
		<div class="footer_nav_container">
			<ul style="margin: 0 auto;">
				<li>회사소개</li>
				<span class="line">|</span>
				<li>공지사항</li>
				<span class="line">|</span>
				<li>입점 · 제휴 · 광고문의</li>
				<span class="line">|</span>
				<li>이용약관</li>
				<span class="line">|</span>
				<li><strong>개인정보처리방침</strong></li>
			</ul>
		</div>
	</div>
</div>
<div class="footer">
	<div class="footer_container">
		<div class="footer_left">
			<img src="/resources/img/Logo.png">
		</div>
		<div class="footer_right">
			<strong>(주)팔닭</strong> <br>
			<br> 대표 : 신경훈 | 주소 : 경기도 수원시 팔달구 덕영대로 899 3F |<br> 사업자등록번호
			: ooo-oo-ooooo | 통신판매업 신고번호 : 제0000-경기수원-0000호 | 개인정보보호책임자 : 맹관묵 <br>
			<br> COPYRIGHT(C) <strong>TeamUnknown</strong> ALL RIGHTS
			RESERVED.
		</div>
		<div class="footer_end">
			<div class="cscenter">
				<ul class="csinfo">
					<li><strong>고객센터</strong><br>
					<br> <em class="cs_tel">031-0000-0000<br></em></li>
					<li>FAX. 031-0000-0000 E-mail. admin@paldak.com</li>
				</ul>
				<ul class="cs_btns">
					<li><a href="#" onclick="">고객의 제안</a></li>
					<li><a href="#" onclick="">1:1 문의</a></li>
				</ul>
				<ul class="footer-sns-list">
					<li><img src="/resources/img/facebook.png"><a href="#"
						title="" target="_blank"><i class="ico-sns-facebook"></i><span
							class="blind">페이스북</span></a></li>
					<li><img src="/resources/img/instagram.png"><a href="#"
						title="" target="_blank"><i class="ico-sns-instagram"></i><span
							class="blind">인스타그램</span></a></li>
					<li><img src="/resources/img/tiktok.png"><a href="#"
						title="" target="_blank"><i class="ico-sns-tiktok"></i><span
							class="blind">틱톡</span></a></li>
					<li><img src="/resources/img/kakaotalk.png"><a href="#"
						title="" target="_blank"><i class="ico-sns-kakaotalk"></i><span
							class="blind">카카오톡</span></a></li>
					<li><img src="/resources/img/youtube.png"><a href="#"
						title="" target="_blank"><i class="ico-sns-youtube"></i><span
							class="blind">유튜브</span></a></li>
					<li><img src="/resources/img/line.png"><a href="#"
						title="" target="_blank"><i class="ico-sns-line"></i><span
							class="blind">라인</span></a></li>
				</ul>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
</div>

<script>
$(document).ready(function() {
    $("#mypage-icon").click(function() {
        var isLoggedIn = "${member != null}";
        if (isLoggedIn === "true") {
            window.location.href = "/mypage/main";
        } else {
            alert("로그인이 필요합니다.");
        }
    });

    $("#gnb_logout_button").click(function() {
        $.ajax({
            type: "POST",
            url: "/member/logout.do",
            success: function(data) {
                alert("로그아웃 성공");
                document.location.reload();
            }
        });
    });

    $("#cart-icon").click(function(event) {
        var isLoggedIn = "${member != null}";
        if (isLoggedIn ==="false") {
            event.preventDefault();
            alert("로그인이 필요합니다.");
        }
    });
    
});
</script>
</body>
</html>
