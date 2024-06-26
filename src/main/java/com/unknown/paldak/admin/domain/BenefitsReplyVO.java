package com.unknown.paldak.admin.domain;

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
   private Date replyUpdateDate;
   private Character answer;
}