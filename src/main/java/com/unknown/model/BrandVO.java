package com.unknown.model;

import java.util.Date;

import lombok.Data;

@Data
public class BrandVO {

    /* �귣�� ���̵� */
    private long brandId;

    /* �귣�� �̸� */
    private String brandName;

    /* �귣�� �Ұ� */
    private String brandIntro;

    /* ��� ��¥ */
    private Date regDate;

    /* ���� ��¥ */
    private Date updateDate;

//    public void setNationId(String nationId) {
//        this.nationId = nationId;
//        if (nationId != null) {
//            if ("01".equals(nationId)) {
//                this.nationName = "����";
//            } else {
//                this.nationName = "����";
//            }
//        }
//    }
}
