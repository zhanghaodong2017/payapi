package com.happy.payapi.dto;

public class Wx1001BackDTO {
	private String appid;
	private String amount;
	private String itemname;
	private String ordersn;
	private String orderdesc;
	private String serialno;
	private String sign;
	private String ext;
	private String paytime;
	public String getAppid() {
		return appid;
	}
	public String getAmount() {
		return amount;
	}
	public String getItemname() {
		return itemname;
	}
	public String getOrdersn() {
		return ordersn;
	}
	public String getOrderdesc() {
		return orderdesc;
	}
	public String getSerialno() {
		return serialno;
	}
	public String getSign() {
		return sign;
	}
	public String getExt() {
		return ext;
	}
	public String getPaytime() {
		return paytime;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public void setOrdersn(String ordersn) {
		this.ordersn = ordersn;
	}
	public void setOrderdesc(String orderdesc) {
		this.orderdesc = orderdesc;
	}
	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public void setPaytime(String paytime) {
		this.paytime = paytime;
	}
	
	
}
