package com.unknown.util;

public class CustomFunctions {
	public static long truncatePrice(double price) {
        return (long) (Math.floor(price / 10) * 10);
    }
}
