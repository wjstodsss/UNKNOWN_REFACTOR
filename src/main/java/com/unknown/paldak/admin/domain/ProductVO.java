package com.unknown.paldak.admin.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductVO {
  private Long productId;
  private String productName;
  private String productDescription;
  private Long productPrice;
  private Integer productCategory;
  private String productBrand;
  private String productImageURL;
  private Date productRegdate;
  private Date productUpdateDate;
}

