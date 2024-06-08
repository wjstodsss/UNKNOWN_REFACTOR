package com.unknown.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QNAVO {
	private Long qnaId;
	private String qnaTitle;
	private String qnaContent;
	private Long qnaCategory;
	private String qnaImageURL;
	private String qnaWriter;
	private Date qnaRegdate;
	private Date qnaUpdateDate;
}
