package com.happy.payapi.dto;

public enum PayChannelNo {
	wx1001("wx1001", "微信支付渠道1-乐享支付"), wx1002("wx1002", "微信支付渠道2"), alipay1001("alipay1001", "支付宝渠道1");

	private String code;
	private String desc;

	private PayChannelNo(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static PayChannelNo getByCode(String code) {
		PayChannelNo[] values = values();
		for (PayChannelNo channelNo : values) {
			if (channelNo.getCode().equals(code)) {
				return channelNo;
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
