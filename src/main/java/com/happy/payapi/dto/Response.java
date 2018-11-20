package com.happy.payapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Response<T> {
	private String code;

	private String msg;
	@JsonInclude(Include.NON_NULL)
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
			this.code = Errorcode.success.getCode();
			this.msg = Errorcode.success.getDesc();
			this.data = data;
		} else {
			this.code = Errorcode.fail_3.getCode();
			this.msg = Errorcode.fail_3.getDesc();
		}
	}

}
