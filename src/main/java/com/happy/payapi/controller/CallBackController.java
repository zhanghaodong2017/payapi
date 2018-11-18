package com.happy.payapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.happy.payapi.dto.Wx1001BackDTO;
import com.happy.payapi.service.impl.Wx1001Callback;

@RestController
@RequestMapping("/callback")
public class CallBackController {
	private static Logger logger = LoggerFactory.getLogger(CallBackController.class);

	@Autowired
	private Wx1001Callback wx1001Callback;

	@RequestMapping(value = "/wx1001")
	public String wx1001(Wx1001BackDTO backDTO) {
		try {
			logger.info("wx1001接收到数据：{}", new Gson().toJson(backDTO));
			wx1001Callback.handleResult(backDTO);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}

	@RequestMapping(value = "/wx1002")
	public String wx1002(Wx1001BackDTO backDTO) {
		try {
			logger.info("wx1002接收到数据：{}", new Gson().toJson(backDTO));
			wx1001Callback.handleResult(backDTO);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}
}
