package com.unknown.model;

import java.util.List;

public class OrderPageItemDTO {
	
	/* ��κ��� ���޹��� �� */
    private int itemId;
    
    private int itemCount;
    
	/* DB�κ��� ������ �� */
    private String itemName;
    
    private int itemPrice;
    
    private double itemDiscount;
    
	/* ����� �� �� */
    private int salePrice;
    
    private int totalPrice;
    
    private int point;
    
    private int totalPoint;
    
    /* ��ǰ �̹��� */
	private List<AttachImageVO> imageList;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}

	public double getItemDiscount() {
		return itemDiscount;
	}

	public void setItemDiscount(double itemDiscount) {
		this.itemDiscount = itemDiscount;
	}

	public int getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
	}
	
	public List<AttachImageVO> getImageList() {
		return imageList;
	}

	public void setImageList(List<AttachImageVO> imageList) {
		this.imageList = imageList;
	}

	/* DB���� ������ �� ���� ������ ���� �޼ҵ� */
	public void initSaleTotal() {
		this.salePrice = (int) (this.itemPrice * (1-this.itemDiscount));
		this.totalPrice = this.salePrice*this.itemCount;
		this.point = (int)(Math.floor(this.salePrice*0.05));
		this.totalPoint =this.point * this.itemCount;
	}

	@Override
	public String toString() {
		return "OrderPageItemDTO [itemId=" + itemId + ", itemCount=" + itemCount + ", itemName=" + itemName
				+ ", itemPrice=" + itemPrice + ", itemDiscount=" + itemDiscount + ", salePrice=" + salePrice
				+ ", totalPrice=" + totalPrice + ", point=" + point + ", totalPoint=" + totalPoint + ", imageList="
				+ imageList + "]";
	}

}
