package com.unknown.paldak.admin.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttachImageVO {
    
    /* 업로드 경로 */
    private String uploadPath;
    
    /* UUID */
    private String uuid;
    
    /* 파일 이름 */
    private String fileName;
    
    /* 아이템 ID */
    private long itemId;
}
