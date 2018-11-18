package com.happy.payapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.happy.payapi.dto.Errorcode;
import com.happy.payapi.dto.Paystatus;
import com.happy.payapi.dto.Response;
import com.happy.payapi.service.QueryService;

@RestController
public class QueryController {
	@Autowired
	private QueryService queryService;

	@RequestMapping(value = "/queryByOrderno", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Response queryByOrderno(@RequestParam String orderno) {
		try {
			Paystatus paystatus = queryService.queryByOrderno(orderno);
			return new Response(paystatus.getCode(), paystatus.getDesc());
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(Errorcode.fail_4.getCode(), Errorcode.fail_4.getDesc());
		}
	}
}
