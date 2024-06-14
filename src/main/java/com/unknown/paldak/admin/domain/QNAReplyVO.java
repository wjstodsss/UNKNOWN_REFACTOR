package com.unknown.paldak.admin.domain;

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
    private String reply;
    private String replyer;
    private Date replyDate;
    private Date replyUpdateDate;
    private Character answer;
}