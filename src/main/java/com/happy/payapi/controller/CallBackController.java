package com.happy.payapi.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.happy.payapi.service.Wx1001Callback;
import com.happy.payapi.service.strategy.Wx1001Strategy;

@RestController
@RequestMapping("/callback")
public class CallBackController {
	private static Logger logger = LoggerFactory.getLogger(Wx1001Strategy.class);

	@Autowired
	private Wx1001Callback wx1001Callback;

	@RequestMapping(value = "/wx1001")
	public String wx1001(HttpServletRequest request) {
		try {
			String result = IOUtils.toString(request.getInputStream(), "UTF-8");
			wx1001Callback.handleResult(result);
			logger.info("wx1001接收到数据：{}", request);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}

}
