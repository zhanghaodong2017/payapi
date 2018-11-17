package com.happy.payapi.service.strategy;

import org.springframework.util.Assert;

import com.happy.payapi.dto.ReqDTO;
import com.happy.payapi.dto.RspDTO;
import com.happy.payapi.entity.Paylog;

public abstract class GeneralStrategy {

	public abstract RspDTO pay(ReqDTO reqDTO, Paylog paylog) throws Exception;

	protected String getOrderId(int n) {
		Assert.isTrue(n >= 12, "订单号的长度不能小于12");
		String timeMillis = System.currentTimeMillis() + "";
		return timeMillis + getRandom(n - timeMillis.length());
	}

	private String getRandom(int n) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i <= n; i++) {
			int intFlag = (int) (Math.random() * 10);
			builder.append(intFlag);
		}
		return builder.toString();
	}
}