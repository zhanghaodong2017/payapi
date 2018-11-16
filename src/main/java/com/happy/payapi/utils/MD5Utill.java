package com.happy.payapi.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utill {

	static MessageDigest getDigest() {
		try {
			return MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] md5(byte[] data) {
		return getDigest().digest(data);
	}

	public static byte[] md5(String data) {
		return md5(data.getBytes());
	}

	public static String md5Hex(byte[] data) {
		return toHexString(md5(data));
	}

	public static String md5Hex(String data) {
		return toHexString(md5(data));
	}

	public static String toHexString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			sb.append("0123456789abcdef".charAt(b[i] >>> 4 & 0xF));
			sb.append("0123456789abcdef".charAt(b[i] & 0xF));
		}
		return sb.toString();
	}

	public static byte[] toByteArray(String s) {
		byte[] buf = new byte[s.length() / 2];
		int j = 0;
		for (int i = 0; i < buf.length; i++) {
			buf[i] = ((byte) (Character.digit(s.charAt(j++), 16) << 4 | Character.digit(s.charAt(j++), 16)));
		}
		return buf;
	}

	public static String appendParam(String returnStr, String paramId, String paramValue) {
		if (!returnStr.equals("")) {
			if (!paramValue.equals("")) {
				returnStr = returnStr + "&" + paramId + "=" + paramValue;
			}
		} else if (!paramValue.equals("")) {
			returnStr = paramId + "=" + paramValue;
		}

		return returnStr;
	}

	public static String appendParam_all(String returnStr, String paramId, String paramValue) {
		if (!returnStr.equals(""))
			returnStr = returnStr + "&" + paramId + "=" + paramValue;
		else {
			returnStr = paramId + "=" + paramValue;
		}
		return returnStr;
	}

	public static String getMD5(String str) {
		try {
			// 生成一个MD5加密计算摘要
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 计算md5函数
			md.update(str.getBytes());
			// digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
			// BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
			return new BigInteger(1, md.digest()).toString(16);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}