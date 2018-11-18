package com.happy.payapi;

import com.google.gson.Gson;
import com.happy.payapi.dto.ReqDTO;
import com.happy.payapi.entity.Paylog;
import com.happy.payapi.service.strategy.Wx1002Strategy;

public class AppTest {
	public static void main(String[] args) throws Exception {
		Wx1002Strategy strategy = new Wx1002Strategy();
		ReqDTO reqDTO = new ReqDTO();
		reqDTO.setAmount(100);
		reqDTO.setAppid("mytest");
		reqDTO.setExt("test");
		reqDTO.setGoodsDesc("描述");
		reqDTO.setReturnurl("http://www.baidu.com");
		reqDTO.setSource("1");
		Paylog paylog = new Paylog();
		try {
			strategy.pay(reqDTO, paylog);
		} catch (Exception e) {
		}
		System.out.println(new Gson().toJson(paylog));
	}
}
