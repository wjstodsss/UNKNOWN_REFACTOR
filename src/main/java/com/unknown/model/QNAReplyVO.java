package com.unknown.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QNAReplyVO {
	private Long replyId;
	private Long qnaId;
	private String replyer;
	private String reply;
	private Date replyDate;
}
