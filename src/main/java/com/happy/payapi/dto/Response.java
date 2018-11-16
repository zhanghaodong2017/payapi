package com.happy.payapi.dto;

public class Response<T> {
	private String code;

	private String msg;

	private T data;

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

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Response(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public Response(T data) {
		super();
		if (data != null) {
			this.code = "0";
			this.msg = "成功";
			this.data = data;
		} else {
			this.code = "1";
			this.msg = "失败";
		}
	}

}
