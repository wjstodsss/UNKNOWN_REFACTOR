package com.unknown.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewReplyVO {
	private Long replyId; // 댓글 고유 번호
	private Long reviewId; // 게시글 번호
	private String reply; // 댓글 내용
	private String replyer; // 댓글 작성자
	private Date replyDate; // 댓글 작성일
	private Date replyUpdateDate; // 댓글 수정일
}
