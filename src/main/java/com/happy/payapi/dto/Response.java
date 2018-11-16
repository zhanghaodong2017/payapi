package com.happy.payapi.dto;

public class Response<T> {
	private String code;

	private String msg;

	private T t;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Response() {
		super();
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public Response(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public Response(T t) {
		super();
		if (t != null) {
			this.code = "0";
			this.msg = "成功";
			this.t = t;
		} else {
			this.code = "1";
			this.msg = "失败";
		}
	}

}
