package com.unknown.paldak.admin.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemCateVO {
	
	private int tier;
	
	private String cateName;
	
	private String cateCode;
	
	private String cateParent;

}