package com.unknown.paldak.admin.common.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class Criteria {
   private Integer pageNum;
   private Integer amount;
   private String type;  // 검색조건, T(제목), C(내용), W(작성자)
   private String keyword; 
   private String sortType;
   private String sortColumn;
   private String groupColumn;// item, order,member..그룸 카테고리 
   
   
   public Criteria() {
	   this(1,10); // 기본값
   }
   
   public Criteria(int pageNum, int amount) {
	   this.pageNum = pageNum;   
	   this.amount = amount;   
   }
   
   public String[] getTypeArr() {
   	  return type==null? new String[] {} : type.split("");
   }

}





