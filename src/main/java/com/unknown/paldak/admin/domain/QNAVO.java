package com.unknown.paldak.admin.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QNAVO {
   private Long qnaId;
   private Long itemId;
   private String qnaTitle;
   private String qnaContent;
   private String qnaCategory;
   private String qnaImageURL;
   private String qnaWriter;
   private Date qnaRegdate;
   private Date qnaUpdateDate;
   private Character answer;
}
