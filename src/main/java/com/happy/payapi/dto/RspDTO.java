package com.happy.payapi.dto;

public class RspDTO {
	private String orderno;
	private String prepayid;
	private String partnerid;
	private String appid;
	private String noncestr;

	public String getOrderno() {
		return orderno;
	}

	public String getPrepayid() {
		return prepayid;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}

	public String getPartnerid() {
		return partnerid;
	}

	public String getAppid() {
		return appid;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

}
