package com.unknown.paldak.admin.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CartVO {
	private long cartId;
    private String memberId;
    private long itemId;
    private long itemCount;
    private String itemName;
}
