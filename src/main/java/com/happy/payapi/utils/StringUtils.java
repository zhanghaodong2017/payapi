package com.happy.payapi.utils;

public class StringUtils {

	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static boolean isEmpty(Object value) {
		if (value != null) {
			if (!((value instanceof String) ? (String) value : value.toString()).isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public static boolean eq(String a, String b) {
		if (a == null && b == null) {
			return true;
		}
		if (a == null || b == null) {
			return false;
		}
		return a.equals(b);
	}
}
