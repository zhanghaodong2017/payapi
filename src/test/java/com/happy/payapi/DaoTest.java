package com.happy.payapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.happy.payapi.dto.ReqDTO;
import com.happy.payapi.dto.RspDTO;
import com.happy.payapi.service.PayService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class DaoTest {
	@Autowired
	private PayService payService;

	@Test
	public void fun1() {
		try {
			ReqDTO reqDTO = new ReqDTO();
			reqDTO.setAmount(100);
			reqDTO.setAppid("mytest");
			reqDTO.setExt("test");
			reqDTO.setAppname("游戏");
			reqDTO.setGoodsDesc("描述");
			reqDTO.setPaytype("wx");
			reqDTO.setReturnurl("http://www.baidu.com");
			reqDTO.setSource("1");
			RspDTO rspDTO = payService.pay(reqDTO);
			System.out.println(new Gson().toJson(rspDTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
