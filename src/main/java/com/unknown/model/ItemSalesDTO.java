package com.unknown.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemSalesDTO {
    private int itemId;
    private String itemName;
    private int itemPrice;
    private double itemDiscount;
    private String brandName;
    private int totalSales;
    private List<AttachImageVO> imageList;

    public ItemSalesDTO(int itemId, String itemName, int itemPrice, double itemDiscount, String brandName, int totalSales) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemDiscount = itemDiscount;
        this.brandName = brandName;
        this.totalSales = totalSales;
    }
}
