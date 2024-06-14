package com.unknown.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BenefitsReplyVO {
	private Long benefitsReplyId;
	private Long benefitsId;
	private String reply;
	private String replyer;
	private Date replyDate;
}
