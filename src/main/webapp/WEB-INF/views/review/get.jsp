<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="../includes/header.jsp"%>
<link rel="stylesheet" href="/resources/css/review/get.css">
<div class="content_area">
	<div class="left_menu">
		<%@ include file="/WEB-INF/views/includes/leftmenu.jsp"%>
	</div>
	<div class="main_content">


		<div class="panel panel-default">
			<div class="panel-heading">리뷰하기</div>
			<div class="panel-body">

				<div class="form-group">
					<label>제목</label> <input class="form-control" name='reviewTitle'
						value='${review.reviewTitle}' readonly="readonly">
				</div>

				<div class="form-group">
					<label>상품</label> <input class="form-control" name='itemName'
						value='${itemName}' readonly="readonly">
				</div>

				<div class="form-group">

					<img id="reviewImage" src="/download/${review.reviewImageURL}"
						alt="Review Image" class="img-responsive">
				</div>

				<div class="form-group">
					<label>리뷰 내용</label>
					<textarea class="form-control" rows="3" name='reviewContent'
						readonly="readonly">${review.reviewContent}</textarea>
				</div>

				<button data-oper='modify' class="btn btn-submit"
					onclick="location.href='/review/modify?reviewId=${review.reviewId}'">수정하기</button>
				<button data-oper='list' class="btn btn-reset"
					onclick="location.href='/review/review/${review.reviewWriter}?pageNum=${cri.pageNum}&amount=${cri.amount}&type=${cri.type}&keyword=${cri.keyword}'">목록으로</button>

				<form id='operForm' action="/review/modify" method='get'>
					<input type='hidden' id="reviewId" name='reviewId'
						value='${review.reviewId}'> <input type='hidden'
						name='pageNum' value='${cri.pageNum}'> <input
						type='hidden' name='amount' value='${cri.amount}'> <input
						type='hidden' name='type' value='${cri.type}'> <input
						type='hidden' name='keyword' value='${cri.keyword}'>
				</form>
			</div>
		</div>
	<!-- /.panel -->
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3>답변</h3>
				
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<ul class="chat">
					<li class="left clearfix" data-replyId="12">
						<div>
							<div class="header">
								<strong class="primary-font"></strong> <small
									class="pull-right text-muted"></small>
							</div>
						</div>
					</li>
				</ul>
				<!-- ./ end ul -->
			</div>
			<!-- /.panel .chat-panel -->
			<div class="panel-footer"></div>
		</div>
	</div>
</div>
<!-- 댓글추가 -->

		
	
<%@ include file="../includes/footer.jsp"%>
<script type="text/javascript" src="/resources/js/reviewReply.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    var replyUL = $(".chat");
    var reviewIdValue = $("#reviewId").val();
    console.log(reviewIdValue); // 확인용 콘솔 출력

    showList(1);

    function showList(page) {
        replyService.getList(
            {
                reviewId : reviewIdValue,
                page : page || 1
            },
            function(list) {
                console.log("댓글 목록:", list); // 댓글 데이터를 콘솔에 출력
                var str = "";

                if (list == null || list.length == 0) {
                    replyUL.html("<li class='left clearfix'>댓글이 없습니다.</li>");
                    return;
                }

                for (var i = 0, len = list.length || 0; i < len; i++) {
                    str += "<li class='left clearfix' data-replyId='"+list[i].replyId+"'>";
                    str += "  <div><div class='header'><strong class='primary-font'>["
                            + list[i].replyId
                            + "] "
                            + list[i].replyer
                            + "</strong>";
                    str += "    <small class='pull-right text-muted'>"
                            + replyService.displayTime(list[i].replyDate)
                            + "</small></div>";
                    str += "    <p>"
                            + list[i].reply
                            + "</p></div></li>";
                }

                replyUL.html(str);
            },
            function(error) {
                console.error("댓글 목록 불러오기 실패:", error);
            }
        );
    }
});

						/* $("#addReplyBtn").on("click", function(e) {
							modal.find("input").val(""); // 모달 내의 입력 필드 초기화
							modalInputReplyDate.closest("div").hide(); //댓글 작성일 숨김
							modal.find("button[id !='modalCloseBtn']").hide(); //모달 내 버튼 숨김
							modalRegisterBtn.show(); //등록버튼 표시
							$(".modal").modal("show"); // 모달 창 표시
						});

						modalRegisterBtn.on("click", function(e) {
							var reviewIdValue = "${review.reviewId}";
							console.log(reviewIdValue);
							var reply = {
								reply : modalInputReply.val(), //댓글내용
								replyer : modalInputReplyer.val(), //작성자
								reviewId : reviewIdValue
							//게시글 번호
							};

							replyService.add(reply, function(result) {
								alert(result);
								modal.find("input").val("");
								modal.modal("hide");
								showList(1);
							})
						});

						$(".chat")
								.on(
										"click",
										"li",
										function(e) {
											var replyId = $(this).data(
													"replyid"); // 댓글의 번호(replyId)를 추출
											console.log("Clicked Reply ID:", $(
													this).data("replyid"));
											console.log("Clicked Element:",
													$(this));
											replyService
													.get(
															replyId,
															function(reply) {
																console
																		.log(
																				"Retrieved Reply:",
																				reply);
																modalInputReply
																		.val(reply.reply); // 댓글내용
																modalInputReplyer
																		.val(reply.replyer); // 작성자
																modalInputReplyDate
																		.val(
																				replyService
																						.displayTime(reply.replyDate))
																		.attr(
																				"readonly",
																				"readonly");
																modal
																		.data(
																				"replyid",
																				reply.replyId); // 모달에 현재 조회된 댓글의 번호(replyId)를 저장
																modal
																		.find(
																				"button[id !='modalCloseBtn']")
																		.hide(); // 다른 버튼들은 숨김
																modalModBtn
																		.show(); // 조회된 댓글은 수정과 삭제가 가능하므로
																modalRemoveBtn
																		.show(); // 수정과 삭제 버튼을 표시
																$(".modal")
																		.modal(
																				"show");
															});
										});

						modalModBtn.on("click", function(e) {
							var reply = {
								replyId : modal.data("replyid"),
								reply : modalInputReply.val()
							};
							replyService.update(reply, function(result) {
								alert(result);
								modal.modal("hide");
								showList(1);
							});
						});

						modalRemoveBtn.on("click", function(e) {
							var replyId = modal.data("replyid");
							replyService.remove(replyId, function(result) {
								alert(result);
								modal.modal("hide");
								showList(1);
							});
						});

						$("#modalCloseBtn").on("click", function(result) {
							$("#myModal").modal("hide");
						});
					}); */

	$(document).ready(function() {
		var operForm = $("#operForm");
		$("button[data-oper='modify']").on("click", function(e) {
			operForm.attr("action", "/review/modify").submit();
		});

		$("button[data-oper='list']").on("click", function(e) {
			operForm.find("#reviewId").remove();
			operForm.attr("action", "/review/review/${review.reviewWriter}")
			operForm.submit();
		});
	});

	window.onload = function() {
		var image = document.getElementById('reviewImage');
		if (image.src.endsWith('/download/default')) {
			image.style.display = 'none';
		}
	}
</script>
