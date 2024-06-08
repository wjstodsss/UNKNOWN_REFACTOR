<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome Unknown</title>
<link rel="stylesheet" href="/resources/css/order.css">
<script src="https://code.jquery.com/jquery-3.4.1.js"
	integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
	crossorigin="anonymous"></script>

<!-- 다음 주소록 api -->
<script
	src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<script>
    function execution_daum_address() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 예제를 참고하여 다양한 활용법을 확인해 보세요.
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
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if (data.buildingName !== '' && data.apartment === 'Y') {
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if (extraAddr !== '') {
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    addr += extraAddr;
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.querySelector('.address1_input').value = data.zonecode;
                document.querySelector('.address2_input').value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.querySelector('.address3_input').focus();
            }
        }).open();
    }
</script>

<!-- 아임포트 JavaScript SDK -->
<script src="https://cdn.iamport.kr/js/iamport.payment-1.1.8.js"></script>

<!-- JSP Scriptlet을 사용하여 데이터를 JavaScript로 전달 -->
<script>
    var orderData = {
        memberId: '${memberInfo.memberId}',
        memberName: '${memberInfo.memberName}',
        memberEmail: '${memberInfo.memberMail}',
        memberPhone: '${memberInfo.memberPhone}',
        memberAddr1: '${memberInfo.memberAddr1}',
        memberAddr2: '${memberInfo.memberAddr2}',
        memberAddr3: '${memberInfo.memberAddr3}',
        orderItems: []
    };

    <c:forEach items="${orderList}" var="ol">
        orderData.orderItems.push({
            itemId: '${ol.itemId}',
            itemName: '${ol.itemName}',
            salePrice: '${ol.salePrice}',
            itemCount: '${ol.itemCount}',
            totalPrice: '${ol.totalPrice}'
        });
    </c:forEach>
</script>

<script>
$(document).ready(function() {
    // selectContact 클래스를 가진 요소가 변경될 때마다 실행되는 함수
    $('.selectContact').change(function() {
        // 선택한 접두사 번호 값을 가져옴
        var prefix = $(this).val();
        // 콘솔에 선택한 접두사 번호 출력
        console.log("선택한 접두사:", prefix);
    });

    /* 주문 조합정보란 최신화 */
    setTotalInfo();

    /* 이미지 삽입 */
    $(".image_wrap").each(function(i, obj) {
        const bobj = $(obj);

        if (bobj.data("itemid")) {
            const uploadPath = bobj.data("path");
            const uuid = bobj.data("uuid");
            const fileName = bobj.data("filename");

            const fileCallPath = encodeURIComponent(uploadPath + "/t_" + uuid + "_" + fileName);

            $(this).find("img").attr('src', '/display?fileName=' + fileCallPath);
        } else {
            $(this).find("img").attr('src', '/resources/img/goodsNoImage.png');
        }
    });

    // 아임포트 결제 요청 함수
    function requestPay(finalTotalPrice) {
        var IMP = window.IMP; // 생략해도 괜찮습니다.
        IMP.init('imp35876051'); // 아임포트 계정의 고유 코드 사용

        IMP.request_pay({
            pg: 'html5_inicis', // PG사
            pay_method: 'card', // 결제수단
            merchant_uid: 'merchant_' + new Date().getTime(), // 결제번호
            name: '주문명: ' + orderData.orderItems.map(item => item.itemName).join(', '), // 주문명
            amount: finalTotalPrice, // 결제금액
            buyer_email: orderData.memberEmail,
            buyer_name: orderData.memberName,
            buyer_phone: orderData.memberPhone,
            buyer_addr: orderData.memberAddr1 + ' ' + orderData.memberAddr2 + ' ' + orderData.memberAddr3,
            buyer_postcode: orderData.memberAddr1, // 우편번호가 memberAddr1에 포함된다고 가정
            m_redirect_url: '/main' // 결제 완료 후 리디렉션될 URL
        }, function(rsp) {
            if (rsp.success) {
                var msg = '결제가 완료되었습니다.';
                // msg += '고유ID : ' + rsp.imp_uid;
                // msg += '상점 거래ID : ' + rsp.merchant_uid;
                // msg += '결제 금액 : ' + rsp.paid_amount;
                // msg += '카드 승인번호 : ' + rsp.apply_num;

                // 결제가 완료되면 서버로 결제 정보를 전송합니다.
                completePayment(rsp);
            } else {
                var msg = '결제에 실패하였습니다.';
                msg += '에러내용 : ' + rsp.error_msg;
            }
            alert(msg);
        });
    }

    // 결제 완료 후 서버로 결제 정보를 전송하는 함수
    function completePayment(rsp) {
        // 여기에 결제 완료 후 처리할 로직을 추가합니다.
        // 예: 폼 데이터를 서버로 전송
        $(".order_form").submit();
    }

    /* 결제하기 버튼 클릭 이벤트 */
    $(".order_btn").on("click", function() {
        // 주소 정보 & 받는이
        $(".addressInfo_input_div").each(function(i, obj) {
            if ($(obj).find(".selectAddress").val() === 'T') {
                $("input[name='addressee']").val($(obj).find(".addressee_input").val());
                $("input[name='memberAddr1']").val($(obj).find(".address1_input").val());
                $("input[name='memberAddr2']").val($(obj).find(".address2_input").val());
                $("input[name='memberAddr3']").val($(obj).find(".address3_input").val());
            }
        });

        // 사용 포인트
        $("input[name='usePoint']").val($(".order_point_input").val());

        // 상품정보
        let form_contents = '';
        $(".goods_table_price_td").each(function(index, element) {
            let itemId = $(element).find(".individual_itemId_input").val();
            let itemCount = $(element).find(".individual_itemCount_input").val();
            let itemId_input = "<input name='orders[" + index + "].itemId' type='hidden' value='" + itemId + "'>";
            form_contents += itemId_input;
            let itemCount_input = "<input name='orders[" + index + "].itemCount' type='hidden' value='" + itemCount + "'>";
            form_contents += itemCount_input;
        });
        $(".order_form").append(form_contents);

        // 아임포트 결제 요청 함수 호출
        let finalTotalPrice = calculateFinalTotalPrice();
        requestPay(finalTotalPrice);
    });

    /* 포인트 입력 이벤트 핸들러 */
    $(".order_point_input").on("input", function() {
        const maxPoint = parseInt('${memberInfo.point}');
        let inputValue = parseInt($(this).val());

        if (inputValue < 0) {
            $(this).val(0);
        } else if (inputValue > maxPoint) {
            $(this).val(maxPoint);
        }

        // 포인트 입력 값이 변경될 때마다 총 주문 정보 최신화
        setTotalInfo();
    });

    /* 포인트 모두사용 취소 버튼 
     * Y: 모두사용 상태 / N : 모두 취소 상태
     */
    $(".order_point_input_btn").on("click", function() {
        const maxPoint = parseInt('${memberInfo.point}');
        let state = $(this).data("state");

        if (state == 'N') {
            console.log("n동작");
            /* 모두사용 */
            //값 변경
            $(".order_point_input").val(maxPoint);
            //글 변경
            $(".order_point_input_btn_Y").css("display", "inline-block");
            $(".order_point_input_btn_N").css("display", "none");
        } else if (state == 'Y') {
            console.log("y동작");
            /* 취소 */
            //값 변경
            $(".order_point_input").val(0);
            //글 변경
            $(".order_point_input_btn_Y").css("display", "none");
            $(".order_point_input_btn_N").css("display", "inline-block");
        }

        setTotalInfo();
    });

    /* 총 주문 정보 세팅(배송비, 총 가격, 마일리지, 물품 수, 종류) */
    function setTotalInfo() {
        let totalPrice = 0; // 총 가격
        let totalCount = 0; // 총 갯수
        let totalKind = 0; // 총 종류
        let totalPoint = 0; // 총 마일리지
        let deliveryPrice = 0; // 배송비
        let usePoint = parseInt($(".order_point_input").val()) || 0; // 사용 포인트(할인가격)
        let finalTotalPrice = 0; // 최종 가격(총 가격 + 배송비 - 사용 포인트)
        let estimatedPoints = 0; // 적립 예정 포인트

        $(".goods_table_price_td").each(function(index, element) {
            // 총 가격
            totalPrice += parseInt($(element).find(".individual_totalPrice_input").val());
            // 총 갯수
            totalCount += parseInt($(element).find(".individual_itemCount_input").val());
            // 총 종류
            totalKind += 1;
            // 총 마일리지
            totalPoint += parseInt($(element).find(".individual_totalPoint_input").val());
        });

        /* 배송비 결정 */
        if (totalPrice >= 30000) {
            deliveryPrice = 0;
        } else if (totalPrice == 0) {
            deliveryPrice = 0;
        } else {
            deliveryPrice = 3000;
        }

        // 최종 가격 계산
        finalTotalPrice = totalPrice + deliveryPrice - usePoint;

        // 적립 예정 포인트 (최종 가격에서 배송비를 제외한 금액의 5%)
        estimatedPoints = Math.floor((finalTotalPrice - deliveryPrice) * 0.01);

        /* 값 삽입 */
        // 총 가격
        $(".totalPrice_span").text(totalPrice.toLocaleString());
        // 총 갯수
        $(".goods_kind_div_count").text(totalCount);
        // 총 종류
        $(".goods_kind_div_kind").text(totalKind);
        // 총 마일리지
        $(".totalPoint_span").text(estimatedPoints.toLocaleString());
        // 배송비
        $(".delivery_price_span").text(deliveryPrice.toLocaleString());
        // 최종 가격(총 가격 + 배송비 - 사용 포인트)
        $(".finalTotalPrice_span").text(finalTotalPrice.toLocaleString());
        // 할인가(사용 포인트)
        $(".usePoint_span").text(usePoint.toLocaleString());
    }

    // 최종 가격을 계산하는 함수
    function calculateFinalTotalPrice() {
        let totalPrice = 0;
        let deliveryPrice = 0;
        let usePoint = parseInt($(".order_point_input").val()) || 0;

        $(".goods_table_price_td").each(function(index, element) {
            totalPrice += parseInt($(element).find(".individual_totalPrice_input").val());
        });

        if (totalPrice >= 30000) {
            deliveryPrice = 0;
        } else if (totalPrice == 0) {
            deliveryPrice = 0;
        } else {
            deliveryPrice = 3000;
        }

        return totalPrice + deliveryPrice - usePoint;
    }

    // 초기화 시점에 setTotalInfo 호출
    setTotalInfo();
});

function showAddress(type) {
    if (type === '1') {
        $('.addressInfo_input_div_1').show();
        $('.addressInfo_input_div_2').hide();
    } else if (type === '2') {
        $('.addressInfo_input_div_1').hide();
        $('.addressInfo_input_div_2').show();
    }
}
</script>




</head>

<body>
	<jsp:include page="/WEB-INF/views/includes/header.jsp" />

	<div class="content_area">
		<div class="content_subject">
			<div class="content_top">
				<span>주문/결제</span>
			</div>
			<div class="content_main">
				<!-- 회원 정보 -->
				<div class="member_info_div">
					<table class="table_text_align_center memberInfo_table">
						<tbody>
							<tr>
								<th style="width: 25%;">주문자</th>
								<td style="width: *">${memberInfo.memberName}|
									${memberInfo.memberMail}</td>
							</tr>
						</tbody>
					</table>
				</div>
				<!-- 배송지 정보 -->
				<div class="addressInfo_div">
					<div class="addressInfo_button_div">
						<button class="address_btn address_btn_1"
							onclick="showAddress('1')" style="background-color: #3c3838;">
							배송지 정보</button>

						<button class="address_btn address_btn_2"
							onclick="showAddress('2')">직접 입력</button>
					</div>
					<div class="addressInfo_input_div_wrap">
						<div class="addressInfo_input_div addressInfo_input_div_1"
							style="display: block">
							<table>
								<colgroup>
									<col width="25%">
									<col width="*">
								</colgroup>
								<tbody>
									<tr>
										<th>받는분</th>
										<td>${memberInfo.memberName}</td>
									</tr>
									<tr>
										<th>주소</th>
										<td>${memberInfo.memberAddr1}${memberInfo.memberAddr2}<br>${memberInfo.memberAddr3}
											<input class="selectAddress" value="T" type="hidden">
											<input class="addressee_input"
											value="${memberInfo.memberName}" type="hidden"> <input
											class="address1_input" type="hidden"
											value="${memberInfo.memberAddr1}"> <input
											class="address2_input" type="hidden"
											value="${memberInfo.memberAddr2}"> <input
											class="address3_input" type="hidden"
											value="${memberInfo.memberAddr3}">
										</td>
									</tr>
									<tr>
										<th>연락처</th>
										<td><select class="selectContact">
												<option value="010">010</option>
												<option value="011">011</option>
												<option value="016">016</option>
												<option value="017">017</option>
												<option value="018">018</option>
												<option value="019">019</option>
										</select> <input class="contact_input" value=""></td>

									</tr>

								</tbody>
							</table>
						</div>

						<div class="addressInfo_input_div addressInfo_input_div_2">
							<table>
								<colgroup>
									<col width="25%">
									<col width="*">
								</colgroup>
								<tbody>
									<tr>
										<th>받는분</th>
										<td><input class="addressee_input"></td>
									</tr>
									<tr>
										<th>주소</th>
										<td><input class="selectAddress" value="F" type="hidden">
											<input class="address1_input" readonly="readonly"> <a
											class="address_search_btn" onclick="execution_daum_address()">주소
												찾기</a><br> <input class="address2_input"
											readonly="readonly"><br> <input
											class="address3_input" readonly="readonly"></td>
									</tr>
									<tr>
										<th>연락처</th>
										<td><select class="selectContact">
												<option value="010">010</option>
												<option value="011">011</option>
												<option value="016">016</option>
												<option value="017">017</option>
												<option value="018">018</option>
												<option value="019">019</option>
										</select> <input class="contact_input" value=""></td>

									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<!-- 상품 정보 -->
				<div class="orderGoods_div">
					<!-- 상품 종류 -->
					<div class="goods_kind_div">
						주문상품 <span class="goods_kind_div_kind"></span>종 <span
							class="goods_kind_div_count"></span>개
					</div>
					<!-- 상품 테이블 -->
					<table class="goods_subject_table">
						<colgroup>
							<col width="15%">
							<col width="45%">
							<col width="40%">
						</colgroup>
						<tbody>
							<tr>
								<th>이미지</th>
								<th>상품 정보</th>
								<th>판매가</th>
							</tr>
						</tbody>
					</table>
					<table class="goods_table">
						<colgroup>
							<col width="15%">
							<col width="45%">
							<col width="40%">
						</colgroup>
						<tbody>
							<c:forEach items="${orderList}" var="ol">
								<tr>
									<td>
										<div class="image_wrap"
											data-itemid="${ol.imageList[0].itemId}"
											data-path="${ol.imageList[0].uploadPath}"
											data-uuid="${ol.imageList[0].uuid}"
											data-filename="${ol.imageList[0].fileName}">
											<img>
										</div>
									</td>
									<td>${ol.itemName}</td>
									<td class="goods_table_price_td"><fmt:formatNumber
											value="${ol.salePrice}" pattern="#,### 원" /> | 수량
										${ol.itemCount}개 <br> <fmt:formatNumber
											value="${ol.totalPrice}" pattern="#,### 원" /> <br>[<fmt:formatNumber
											value="${ol.totalPoint}" pattern="#,### P" />] <input
										type="hidden" class="individual_itemPrice_input"
										value="${ol.itemPrice}"> <input type="hidden"
										class="individual_salePrice_input" value="${ol.salePrice}">
										<input type="hidden" class="individual_itemCount_input"
										value="${ol.itemCount}"> <input type="hidden"
										class="individual_totalPrice_input"
										value="${ol.salePrice * ol.itemCount}"> <input
										type="hidden" class="individual_point_input"
										value="${ol.point}"> <input type="hidden"
										class="individual_totalPoint_input" value="${ol.totalPoint}">
										<input type="hidden" class="individual_itemId_input"
										value="${ol.itemId}"></td>
								</tr>
							</c:forEach>

						</tbody>
					</table>
				</div>
				<!-- 포인트 정보 -->
				<div class="point_div">
					<div class="point_div_subject">포인트 사용</div>
					<table class="point_table">
						<colgroup>
							<col width="25%">
							<col width="*">
						</colgroup>
						<tbody>
							<tr>
								<th>포인트 사용</th>
								<td>${memberInfo.point}P|<input class="order_point_input"
									value="0"> P <a
									class="order_point_input_btn order_point_input_btn_N"
									data-state="N">모두사용</a> <a
									class="order_point_input_btn order_point_input_btn_Y"
									data-state="Y" style="display: none;">사용취소</a>

								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<!-- 주문 종합 정보 -->
				<div class="total_info_div">
					<!-- 가격 종합 정보 -->
					<div class="total_info_price_div">
						<ul>
							<li><span class="price_span_label">상품 금액</span> <span
								class="totalPrice_span">100000</span>원</li>
							<li><span class="price_span_label">배송비</span> <span
								class="delivery_price_span">100000</span>원</li>
							<li><span class="price_span_label">할인금액</span> <span
								class="usePoint_span">100000</span>원</li>
							<li class="price_total_li"><strong
								class="price_span_label total_price_label">최종 결제 금액</strong> <strong
								class="strong_red"> <span
									class="total_price_red finalTotalPrice_span"> 1500000 </span>원
							</strong></li>
							<li class="point_li"><span class="price_span_label">적립예정
									포인트</span> <span class="totalPoint_span">7960</span>P</li>
						</ul>
					</div>
					<!-- 버튼 영역 -->
					<div class="total_info_btn_div">
						<a class="order_btn">결제하기</a>
					</div>
				</div>
			</div>

		</div>
	</div>

	<!-- 주문 요청 form -->
	<form class="order_form" action="/order" method="post">
		<!-- 주문자 회원번호 -->
		<input name="memberId" value="${memberInfo.memberId}" type="hidden">
		<!-- 주소록 & 받는이-->
		<input name="addressee" type="hidden"> <input
			name="memberAddr1" type="hidden"> <input name="memberAddr2"
			type="hidden"> <input name="memberAddr3" type="hidden">
		<!-- 사용 포인트 -->
		<input name="usePoint" type="hidden">
		<!-- 상품 정보 -->
	</form>

	<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
</body>
</html>
