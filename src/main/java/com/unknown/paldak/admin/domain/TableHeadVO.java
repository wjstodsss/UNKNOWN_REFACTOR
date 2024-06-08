package com.unknown.paldak.admin.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TableHeadVO {
    private String typeId;
    private String typeName;
    private String columnList;
}
