package com.happy.payapi.dto;

public enum Errorcode {
	success("0", "成功"), 
	fail_1("f1", "金额不正确"), 
	fail_2("f2", "订单号不正确"), 
	fail_3("f3", "其它错误"),
	fail_4("f4", "异常"),
	fail_5("f5", "无可用支付渠道");

	
	private String code;
	private String desc;

	private Errorcode(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
