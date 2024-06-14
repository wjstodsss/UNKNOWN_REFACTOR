package com.unknown.model;

import java.util.Date;

import lombok.Data;

@Data
public class BrandVO {

    /* 브랜드 아이디 */
    private long brandId;

    /* 브랜드 이름 */
    private String brandName;

    /* 브랜드 소개 */
    private String brandIntro;

    /* 등록 날짜 */
    private Date regDate;

    /* 수정 날짜 */
    private Date updateDate;

//    public void setNationId(String nationId) {
//        this.nationId = nationId;
//        if (nationId != null) {
//            if ("01".equals(nationId)) {
//                this.nationName = "국내";
//            } else {
//                this.nationName = "국외";
//            }
//        }
//    }
}
