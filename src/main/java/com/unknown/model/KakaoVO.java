package com.unknown.model;

import lombok.Data;

@Data
public class KakaoVO {

	private long k_number;
	private String k_name; // 카카오 닉네임
	private String k_email; // 카카오 이메일

	public void setK_name(String k_name) {
		this.k_name = k_name;
	}

	public void setK_email(String k_email) {
		this.k_email = k_email;
	}

}
