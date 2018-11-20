package com.happy.payapi;

import com.google.gson.Gson;
import com.happy.payapi.dto.ReqDTO;
import com.happy.payapi.dto.RspDTO;
import com.happy.payapi.entity.Paylog;
import com.happy.payapi.service.strategy.Ap1001Strategy;

public class AppTest {
	public static void main(String[] args) throws Exception {
		Ap1001Strategy strategy = new Ap1001Strategy();
		ReqDTO reqDTO = new ReqDTO();
		reqDTO.setAmount(1000);
		reqDTO.setAppid("mytest");
		reqDTO.setExt("test");
		reqDTO.setGoodsDesc("描述");
		reqDTO.setReturnurl("http://www.baidu.com");
		reqDTO.setSource("1");
		Paylog paylog = new Paylog();
		try {
			RspDTO rspDTO = strategy.pay(reqDTO, paylog);
			System.out.println(new Gson().toJson(rspDTO));
		} catch (Exception e) {
		}
		System.out.println(new Gson().toJson(paylog));
	}
}
