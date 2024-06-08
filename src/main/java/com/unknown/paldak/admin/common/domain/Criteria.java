package com.unknown.paldak.admin.common.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class Criteria {
   private Integer pageNum; //페이지 번호
   private Integer amount; //페이지 보여줄 갯수
   private String type;  //검색조건, T(제목), C(내용), W(작성자)
   private String keyword;  //검색조건
   private String sortColumn;
   private String groupColumn;
   
   
   public Criteria() {
	   this(1,10); //디폴트
   }
   
   public Criteria(int pageNum, int amount) {
	   this.pageNum = pageNum;   
	   this.amount = amount;   
   }
   
   //TCW(문자열)        T C W   검색조건
   public String[] getTypeArr() {
   	  return type==null? new String[] {} : type.split("");
   }

}





