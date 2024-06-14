package com.unknown.model;

import java.util.List;

public class CartDTO {

	// cart 테이블의 속성 4개
	private int cartId;

	private String memberId;

	private int itemId;

	private int itemCount;

	// item 테이블의 속성 3개(join을 위해서)

	private String itemName;

	private int itemPrice;

	private double itemDiscount;

	// 추가
	private int salePrice;

	private int totalPrice;

	private int point;

	private int totalPoint;
	
	/* 상품 이미지 */
	private List<AttachImageVO> imageList;

	// Getter, Setter, ToString

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

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

	public int getTotalPrice() {
		return totalPrice;
	}
	
	public int getPoint() {
		return point;
	}

	public int getTotalPoint() {
		return totalPoint;
	}
	
	public List<AttachImageVO> getImageList() {
		return imageList;
	}

	public void setImageList(List<AttachImageVO> imageList) {
		this.imageList = imageList;
	}	


	public void initSaleTotal() {
		this.salePrice = (int) (this.itemPrice * (1 - this.itemDiscount));
		this.totalPrice = this.salePrice * this.itemCount;
		this.point = (int)(Math.floor(this.salePrice*0.01));
		this.totalPoint =this.point * this.itemCount;
	}

	@Override
	public String toString() {
		return "CartDTO [cartId=" + cartId + ", memberId=" + memberId + ", itemId=" + itemId + ", itemCount="
				+ itemCount + ", itemName=" + itemName + ", itemPrice=" + itemPrice + ", itemDiscount=" + itemDiscount
				+ ", salePrice=" + salePrice + ", totalPrice=" + totalPrice + ", point=" + point + ", totalPoint="
				+ totalPoint + ", imageList=" + imageList + "]";
	}

	
}
