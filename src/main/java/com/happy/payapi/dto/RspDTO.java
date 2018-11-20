package com.happy.payapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class RspDTO {
	@JsonInclude(Include.NON_NULL) 
	private String orderno;
	@JsonInclude(Include.NON_NULL) 
	private String prepayid;
	@JsonInclude(Include.NON_NULL) 
	private String partnerid;
	@JsonInclude(Include.NON_NULL) 
	private String appid;
	@JsonInclude(Include.NON_NULL) 
	private String _package;
	@JsonInclude(Include.NON_NULL) 
	private String timestamp;
	@JsonInclude(Include.NON_NULL) 
	private String noncestr;
	@JsonInclude(Include.NON_NULL) 
	private String sign;
	@JsonInclude(Include.NON_NULL) 
	private String payurl;

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

	public String get_package() {
		return _package;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getSign() {
		return sign;
	}

	public void set_package(String _package) {
		this._package = _package;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getPayurl() {
		return payurl;
	}

	public void setPayurl(String payurl) {
		this.payurl = payurl;
	}

}
