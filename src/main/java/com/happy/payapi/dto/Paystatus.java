package com.happy.payapi.dto;

/**
 * 
 *支付结果；0成功,1支付中，2支付失败，3未支付
 */
public enum Paystatus {
	success("0", "支付成功"), 
	pay_ing("1", "支付中"), 
	pay_fail("2", "支付失败"), 
	not_pay("3", "未支付");

	
	private String code;
	private String desc;

	private Paystatus(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	public static Paystatus getByCode(String code) {
		Paystatus[] values = values();
		for (Paystatus paystatus : values) {
			if (paystatus.getCode().equals(code)) {
				return paystatus;
			}
		}
		return null;
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
