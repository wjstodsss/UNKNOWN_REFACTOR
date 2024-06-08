package com.unknown.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttachImageVO {

	/* 상품 id */
	private int itemId;
	
	/* 파일 이름 */
	private String fileName;
	
	/* 경로 */
	private String uploadPath;
	
	/* uuid */
	private String uuid;
	

}
