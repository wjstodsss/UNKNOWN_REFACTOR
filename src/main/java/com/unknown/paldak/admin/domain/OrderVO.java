package com.unknown.paldak.admin.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class OrderVO {
    private String orderId;
    private String receiver;
    private String memberId;
    private String memberAddr1;
    private String memberAddr2;
    private String memberAddr3;
    private String shipRequest;
    private String orderState;
    private int deliveryCost;
    private int usePoint;
    private Date orderDate;
}
