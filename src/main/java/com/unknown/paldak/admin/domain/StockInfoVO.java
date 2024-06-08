package com.unknown.paldak.admin.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class StockInfoVO {
	private long itemId;
    private String itemName;
    private String manufacturer;
    private Integer itemStock;
    private long stockOrderId;
    private Integer stockOrderQty;
    private Integer receivedQty;
    private Date orderDate;
    private Date receivedDate;
    private String isReceived;
}
