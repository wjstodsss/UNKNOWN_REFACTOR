package com.unknown.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttachImageVO {

	/* ��ǰ id */
	private int itemId;
	
	/* ���� �̸� */
	private String fileName;
	
	/* ��� */
	private String uploadPath;
	
	/* uuid */
	private String uuid;
	

}
