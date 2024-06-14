package com.unknown.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BenefitsVO {
	private Long benefitsId;
	private String benefitsDescription;
	private String benefitsWriter;
	private Date benefitsRegDate;
	
}
