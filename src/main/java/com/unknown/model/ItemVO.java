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
	/* ��ǰ id */
	private int itemId;

	/* ��ǰ �̸� */
	private String itemName;

	/* �귣�� id */
	private int brandId;

	/* �귣�� �̸� */
	private String brandName;

	/* �����⵵ */
	private String mnfcYear;

	/* ������ */
	private String  manufacturer;

	/* ī�װ� �ڵ� */
	private String cateCode;

	/* ī�װ� �̸� */
	private String cateName;

	/* ��ǰ ���� */
	private int itemPrice;

	/* ��ǰ ��� */
	private int itemStock;

	/* ��ǰ ���η�(�����) */
	private double itemDiscount;

	/* ��ǰ �Ұ� */
	private String itemIntro;

	/* ��ǰ �� ���� */
	private String itemContents;

	/* ��ǰ ��� ��¥ */
	private Date regDate;

	/* ���� ��¥ */
	private Date updateDate;
	
	/* �̹��� ���� */
	private List<AttachImageVO> imageList;
}
