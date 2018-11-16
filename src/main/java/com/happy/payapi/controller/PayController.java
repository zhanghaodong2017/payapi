package com.happy.payapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.happy.payapi.dto.BizException;
import com.happy.payapi.dto.ReqDTO;
import com.happy.payapi.dto.Response;
import com.happy.payapi.dto.RspDTO;
import com.happy.payapi.service.PayService;

@RestController
public class PayController {
	@Autowired
	private PayService payService;

	@RequestMapping(value = "/pay", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Response pay(ReqDTO reqDTO) {
		try {
			RspDTO pay = payService.pay(reqDTO);
			return new Response<RspDTO>(pay);
		} catch (BizException e) {
			return new Response(e.getCode(), e.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			return new Response("2", "异常");
		}
	}

}
