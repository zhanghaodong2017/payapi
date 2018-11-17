package com.happy.payapi;

import com.happy.payapi.dto.ReqDTO;
import com.happy.payapi.service.strategy.Wx1001Strategy;

public class AppTest {
	public static void main(String[] args) throws Exception {
		Wx1001Strategy strategy = new Wx1001Strategy();
		ReqDTO reqDTO = new ReqDTO();
		reqDTO.setAmount(100);
		reqDTO.setAppid("mytest");
		reqDTO.setExt("test");
		reqDTO.setGoodsDesc("描述");
		reqDTO.setReturnurl("http://www.baidu.com");
		reqDTO.setSource("1");
		strategy.pay(reqDTO, null);
	}
}
