package com.unknown.paldak.admin.domain;


import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class BrandVO {
	private long brandId;
    private String brandName;
    private String brandIntro;
    private Date regDate;
    private Date updateDate;
}
