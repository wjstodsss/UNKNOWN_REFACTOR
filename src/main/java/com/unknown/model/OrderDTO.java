package com.unknown.model;

import java.util.Date;
import java.util.List;

public class OrderDTO {

    /* �ֹ� ��ȣ */
    private String orderId;

    /* ��� �޴��� */
    private String receiver;

    /* �ֹ� ȸ�� ���̵� */
    private String memberId;

    /* �����ȣ */
    private String memberAddr1;

    /* ȸ�� �ּ� */
    private String memberAddr2;

    /* ȸ�� ���ּ� */
    private String memberAddr3;

    /* �ֹ� ���� */
    private String orderState;

    /* �ֹ� ��ǰ */
    private List<OrderItemDTO> orders;

    /* ��ۺ� */
    private int deliveryCost;

    /* ��� ����Ʈ */
    private int usePoint;

    /* �ֹ� ��¥ */
    private Date orderDate;

    /* �ǸŰ�(��� ��ǰ ���) */
    private int orderSalePrice;

    /* ���� ����Ʈ */
    private int orderEarnPoint;

    /* ���� �Ǹ� ��� */
    private int orderFinalSalePrice;
    
    /* �̹��� ���� ����Ʈ */
    private List<AttachImageVO> imageList;  

    // ���� getter, setter �� toString �޼��� ...

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
        // �ֹ� �׸� �ֹ� ��¥�� �����մϴ�.
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
        /* ��ǰ ��� & ��������Ʈ */
        for (OrderItemDTO order : orders) {
            orderSalePrice += order.getTotalPrice();
            orderEarnPoint += order.getTotalEarnPoint();
        }
        /* ��ۺ�� */
        if (orderSalePrice >= 30000) {
            deliveryCost = 0;
        } else {
            deliveryCost = 3000;
        }
        /* ���� ���(��ǰ ��� + ��ۺ� - ��� ����Ʈ) */
        orderFinalSalePrice = orderSalePrice + deliveryCost - usePoint;
    }

    public int calculateEarnedPoints() {
        // ����Ʈ ���� ��� ����
        // ���� ��� �� �ֹ� �ݾ��� 1%�� �����Ѵٰ� ����
        int totalOrderAmount = this.orders.stream()
                .mapToInt(item -> item.getSalePrice() * item.getItemCount())
                .sum();
        return (int) (totalOrderAmount * 0.01);
    }
}
