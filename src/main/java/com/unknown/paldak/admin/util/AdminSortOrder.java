package com.unknown.paldak.admin.util;

public enum AdminSortOrder {
	ASC("asc"),
    DESC("desc");

    private final String value;
    public static final int FIRST_PAGE_NUM = 1;
    
    AdminSortOrder(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
