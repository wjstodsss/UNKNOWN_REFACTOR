package com.unknown.model;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemVO {
	/* 상품 id */
	private int itemId;

	/* 상품 이름 */
	private String itemName;

	/* 브랜드 id */
	private int brandId;

	/* 브랜드 이름 */
	private String brandName;

	/* 제조년도 */
	private String mnfcYear;

	/* 제조사 */
	private String  manufacturer;

	/* 카테고리 코드 */
	private String cateCode;

	/* 카테고리 이름 */
	private String cateName;

	/* 상품 가격 */
	private int itemPrice;

	/* 상품 재고 */
	private int itemStock;

	/* 상품 할인률(백분율) */
	private double itemDiscount;

	/* 상품 소개 */
	private String itemIntro;

	/* 상품 상세 내용 */
	private String itemContents;

	/* 상품 등록 날짜 */
	private Date regDate;

	/* 수정 날짜 */
	private Date updateDate;
	
	/* 이미지 정보 */
	private List<AttachImageVO> imageList;
}
