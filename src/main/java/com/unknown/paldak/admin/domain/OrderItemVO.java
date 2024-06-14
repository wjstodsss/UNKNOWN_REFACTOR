package com.unknown.paldak.admin.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class OrderItemVO {
    private long orderItemId;
    private String orderId;
    private int itemId;
    private int itemCount;
    private long itemPrice;
    private double itemDiscount;
    private int earnPoint;
    private long totalPriceByItem;
    private long totalPriceByOrderId;
    private String orderState;
}
