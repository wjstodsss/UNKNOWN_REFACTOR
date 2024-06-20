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
<script src="https://code.jquery.com/jquery-3.4.1.js" crossorigin="anonymous"></script>

<!-- 다음 주소록 api -->
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<!-- 아임포트 JavaScript SDK -->
<script src="https://cdn.iamport.kr/js/iamport.payment-1.1.8.js"></script>

<script>
$(document).ready(function() {
    // 아임포트 초기화
    var IMP = window.IMP;
    IMP.init('imp35876051'); // 가맹점 식별코드를 정확히 입력하세요

    // JSP Scriptlet을 사용하여 데이터를 JavaScript로 전달
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

    $('.selectContact').change(function() {
        var prefix = $(this).val();
        console.log("선택한 접두사:", prefix);
    });

    setTotalInfo();

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

    $(".order_btn").on("click", function() {
        var defaultAddressSelected = $(".addressInfo_input_div_1").is(":visible");

        if (defaultAddressSelected) {
            $("input[name='addressee']").val($(".addressee_input").val());
            $("input[name='memberAddr1']").val($(".address1_input").val());
            $("input[name='memberAddr2']").val($(".address2_input").val());
            $("input[name='memberAddr3']").val($(".address3_input").val());
        } else {
            $("input[name='addressee']").val($(".addressee_input_2").val());
            $("input[name='memberAddr1']").val($(".address1_input_2").val());
            $("input[name='memberAddr2']").val($(".address2_input_2").val());
            $("input[name='memberAddr3']").val($(".address3_input_2").val());
        }

        $("input[name='usePoint']").val($(".order_point_input").val());

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

        let finalTotalPrice = calculateFinalTotalPrice();
        requestPay(finalTotalPrice);

        console.log("폼 데이터 확인:", $(".order_form").serialize());
    });

    function requestPay(finalTotalPrice) {
        console.log("IMP 요청 시작");
        IMP.request_pay({
            pg: 'html5_inicis',
            pay_method: 'card',
            merchant_uid: 'merchant_' + new Date().getTime(),
            name: '주문명: ' + orderData.orderItems.map(item => item.itemName).join(', '),
            amount: finalTotalPrice,
            buyer_email: orderData.memberEmail,
            buyer_name: orderData.memberName,
            buyer_phone: orderData.memberPhone,
            buyer_addr: orderData.memberAddr1 + ' ' + orderData.memberAddr2 + ' ' + orderData.memberAddr3,
            buyer_postcode: orderData.memberAddr1,
            m_redirect_url: '/main'
        }, function(rsp) {
            if (rsp.success) {
                var msg = '결제가 완료되었습니다.';
                completePayment(rsp);
            } else {
                var msg = '결제에 실패하였습니다. 에러내용 : ' + rsp.error_msg;
            }
            alert(msg);
        });
    }

    function completePayment(rsp) {
        console.log("결제 완료 후 폼 제출");
        $(".order_form").submit();
    }

    $(".order_point_input").on("input", function() {
        const maxPoint = parseInt('${memberInfo.point}');
        let inputValue = parseInt($(this).val());

        if (inputValue < 0) {
            $(this).val(0);
        } else if (inputValue > maxPoint) {
            $(this).val(maxPoint);
        }

        setTotalInfo();
    });

    $(".order_point_input_btn").on("click", function() {
        const maxPoint = parseInt('${memberInfo.point}');
        let state = $(this).data("state");

        if (state == 'N') {
            $(".order_point_input").val(maxPoint);
            $(".order_point_input_btn_Y").css("display", "inline-block");
            $(".order_point_input_btn_N").css("display", "none");
        } else if (state == 'Y') {
            $(".order_point_input").val(0);
            $(".order_point_input_btn_Y").css("display", "none");
            $(".order_point_input_btn_N").css("display", "inline-block");
        }

        setTotalInfo();
    });

    function setTotalInfo() {
        let totalPrice = 0;
        let totalCount = 0;
        let totalKind = 0;
        let totalPoint = 0;
        let deliveryPrice = 0;
        let usePoint = parseInt($(".order_point_input").val()) || 0;
        let finalTotalPrice = 0;
        let estimatedPoints = 0;

        $(".goods_table_price_td").each(function(index, element) {
            totalPrice += parseInt($(element).find(".individual_totalPrice_input").val());
            totalCount += parseInt($(element).find(".individual_itemCount_input").val());
            totalKind += 1;
            totalPoint += parseInt($(element).find(".individual_totalPoint_input").val());
        });

        if (totalPrice >= 30000) {
            deliveryPrice = 0;
        } else if (totalPrice == 0) {
            deliveryPrice = 0;
        } else {
            deliveryPrice = 3000;
        }

        finalTotalPrice = totalPrice + deliveryPrice - usePoint;
        estimatedPoints = Math.floor((finalTotalPrice - deliveryPrice) * 0.01);

        $(".totalPrice_span").text(totalPrice.toLocaleString());
        $(".goods_kind_div_count").text(totalCount);
        $(".goods_kind_div_kind").text(totalKind);
        $(".totalPoint_span").text(estimatedPoints.toLocaleString());
        $(".delivery_price_span").text(deliveryPrice.toLocaleString());
        $(".finalTotalPrice_span").text(finalTotalPrice.toLocaleString());
        $(".usePoint_span").text(usePoint.toLocaleString());
    }

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
										<td>${memberInfo.memberAddr1}<br>${memberInfo.memberAddr2}<br>${memberInfo.memberAddr3}
											<input class="selectAddress" value="T" type="hidden">
											<input class="addressee_input"
											value="${memberInfo.memberName}" type="hidden"> 
											<input class="address1_input" type="hidden"
											value="${memberInfo.memberAddr1}"> 
											<input class="address2_input" type="hidden"
											value="${memberInfo.memberAddr2}"> 
											<input class="address3_input" type="hidden"
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
										<td><input class="addressee_input_2"></td>
									</tr>
									<tr>
										<th>주소</th>
										<td><input class="selectAddress" value="F" type="hidden">
											<input class="address1_input_2" readonly="readonly"> 
											<a class="address_search_btn" onclick="execution_daum_address(2)">주소 찾기</a><br> 
											<input class="address2_input_2" readonly="readonly"><br> 
											<input class="address3_input_2" value=""></td>
									</tr>
									<tr>
										<th>연락처</th>
										<td><select class="selectContact_2">
												<option value="010">010</option>
												<option value="011">011</option>
												<option value="016">016</option>
												<option value="017">017</option>
												<option value="018">018</option>
												<option value="019">019</option>
										</select> <input class="contact_input_2" value=""></td>

									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<!-- 상품 정보 -->
				<div class="orderGoods_div">
					<div class="goods_kind_div">
						주문상품 <span class="goods_kind_div_kind"></span>종 <span
							class="goods_kind_div_count"></span>개
					</div>
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
					<div class="total_info_btn_div">
						<a class="order_btn">결제하기</a>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 주문 요청 form -->
	<form class="order_form" action="/order" method="post">
		<input name="memberId" value="${memberInfo.memberId}" type="hidden">
		<input name="addressee" type="hidden"> 
		<input name="memberAddr1" type="hidden"> 
		<input name="memberAddr2" type="hidden"> 
		<input name="memberAddr3" type="hidden">
		<input name="usePoint" type="hidden">
	</form>

	<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
</body>
</html>
