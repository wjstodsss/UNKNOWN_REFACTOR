package com.unknown.paldak.admin.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class DashboardVO {
    private String regdateMem;
    private String regmontMem; 
    private int memberCount;
    private String orderDate;
    private String orderMonth;
    private int orderCount;
    private int itemCount;
    private int reviewCount;
    private int pendingCount;
    private int qnaCount;
    private int pendingQnaCount;
    private int itemsNeedOrderCount;
    private int totalOrdersCount;
    private int receivedItemsCount;
    private int waitingItemsCount;
    private int rank;
    private String itemName;
    private long salesAmount;  
}

