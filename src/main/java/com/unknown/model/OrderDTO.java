package com.unknown.model;

import java.util.Date;
import java.util.List;

public class OrderDTO {

    /* 주문 번호 */
    private String orderId;

    /* 배송 받는이 */
    private String receiver;

    /* 주문 회원 아이디 */
    private String memberId;

    /* 우편번호 */
    private String memberAddr1;

    /* 회원 주소 */
    private String memberAddr2;

    /* 회원 상세주소 */
    private String memberAddr3;

    /* 주문 상태 */
    private String orderState;

    /* 주문 상품 */
    private List<OrderItemDTO> orders;

    /* 배송비 */
    private int deliveryCost;

    /* 사용 포인트 */
    private int usePoint;

    /* 주문 날짜 */
    private Date orderDate;

    /* 판매가(모든 상품 비용) */
    private int orderSalePrice;

    /* 적립 포인트 */
    private int orderEarnPoint;

    /* 최종 판매 비용 */
    private int orderFinalSalePrice;
    
    /* 이미지 정보 리스트 */
    private List<AttachImageVO> imageList;  

    // 기존 getter, setter 및 toString 메서드 ...

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberAddr1() {
        return memberAddr1;
    }

    public void setMemberAddr1(String memberAddr1) {
        this.memberAddr1 = memberAddr1;
    }

    public String getMemberAddr2() {
        return memberAddr2;
    }

    public void setMemberAddr2(String memberAddr2) {
        this.memberAddr2 = memberAddr2;
    }

    public String getMemberAddr3() {
        return memberAddr3;
    }

    public void setMemberAddr3(String memberAddr3) {
        this.memberAddr3 = memberAddr3;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public List<OrderItemDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderItemDTO> orders) {
        this.orders = orders;
    }

    public int getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(int deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public int getUsePoint() {
        return usePoint;
    }

    public void setUsePoint(int usePoint) {
        this.usePoint = usePoint;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
        // 주문 항목에 주문 날짜를 설정합니다.
        if (orders != null) {
            for (OrderItemDTO orderItem : orders) {
                orderItem.setOrderDate(orderDate);
            }
        }
    }

    public int getOrderSalePrice() {
        return orderSalePrice;
    }

    public void setOrderSalePrice(int orderSalePrice) {
        this.orderSalePrice = orderSalePrice;
    }

    public int getOrderEarnPoint() {
        return orderEarnPoint;
    }

    public void setOrderEarnPoint(int orderEarnPoint) {
        this.orderEarnPoint = orderEarnPoint;
    }

    public int getOrderFinalSalePrice() {
        return orderFinalSalePrice;
    }

    public void setOrderFinalSalePrice(int orderFinalSalePrice) {
        this.orderFinalSalePrice = orderFinalSalePrice;
    }

    public List<AttachImageVO> getImageList() {
		return imageList;
	}

	public void setImageList(List<AttachImageVO> imageList) {
		this.imageList = imageList;
	}

	@Override
	public String toString() {
		return "OrderDTO [orderId=" + orderId + ", receiver=" + receiver + ", memberId=" + memberId + ", memberAddr1="
				+ memberAddr1 + ", memberAddr2=" + memberAddr2 + ", memberAddr3=" + memberAddr3 + ", orderState="
				+ orderState + ", orders=" + orders + ", deliveryCost=" + deliveryCost + ", usePoint=" + usePoint
				+ ", orderDate=" + orderDate + ", orderSalePrice=" + orderSalePrice + ", orderEarnPoint="
				+ orderEarnPoint + ", orderFinalSalePrice=" + orderFinalSalePrice + ", imageList=" + imageList + "]";
	}

    public void getOrderPriceInfo() {
        /* 상품 비용 & 적립포인트 */
        for (OrderItemDTO order : orders) {
            orderSalePrice += order.getTotalPrice();
            orderEarnPoint += order.getTotalEarnPoint();
        }
        /* 배송비용 */
        if (orderSalePrice >= 30000) {
            deliveryCost = 0;
        } else {
            deliveryCost = 3000;
        }
        /* 최종 비용(상품 비용 + 배송비 - 사용 포인트) */
        orderFinalSalePrice = orderSalePrice + deliveryCost - usePoint;
    }

    public int calculateEarnedPoints() {
        // 포인트 적립 계산 로직
        // 예를 들어 총 주문 금액의 1%를 적립한다고 가정
        int totalOrderAmount = this.orders.stream()
                .mapToInt(item -> item.getSalePrice() * item.getItemCount())
                .sum();
        return (int) (totalOrderAmount * 0.01);
    }
}
