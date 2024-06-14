package com.unknown.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewVO {
	private Long reviewId;
	private int itemId;
	private String reviewTitle;
	private String reviewContent;
	private String reviewCategory;
	private String reviewImageURL;
	private String reviewWriter;
	private Date reviewRegdate;
	private Date reviewUpdateDate;
}
