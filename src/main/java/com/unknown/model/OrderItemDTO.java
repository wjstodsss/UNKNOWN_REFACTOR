package com.unknown.model;

import java.util.Date;

public class OrderItemDTO {

    private int orderItemId;
    private String orderId;
    private int itemId;
    private int itemCount;
    private int itemPrice;
    private double itemDiscount;
    private int earnPoint;
    private String itemName;
    private String imagePath;
    private Date orderDate; // 추가

    // getters and setters

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public int getEarnPoint() {
        return earnPoint;
    }

    public void setEarnPoint(int earnPoint) {
        this.earnPoint = earnPoint;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }


    public int getSalePrice() {
        return (int) (this.itemPrice * (1 - this.itemDiscount));
    }

    public int getTotalPrice() {
        return this.getSalePrice() * this.itemCount;
    }

    public int getTotalEarnPoint() {
        return (int) (this.getSalePrice() * this.itemCount * 0.01); // 예를 들어 총 판매 금액의 1%를 적립한다고 가정
    }

    public void initSaleTotal() {
        this.itemPrice = getTotalPrice();
        this.earnPoint = getTotalEarnPoint();
    }

    @Override
    public String toString() {
        return "OrderItemDTO [orderItemId=" + orderItemId + ", orderId=" + orderId + ", itemId=" + itemId
                + ", itemCount=" + itemCount + ", itemPrice=" + itemPrice + ", itemDiscount=" + itemDiscount
                + ", earnPoint=" + earnPoint + ", itemName=" + itemName + ", imagePath=" + imagePath + ", orderDate="
                + orderDate + "]";
    }
}
