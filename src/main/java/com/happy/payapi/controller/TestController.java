package com.happy.payapi.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.happy.payapi.dto.Response;

@RestController
public class TestController {

	@RequestMapping(value = "/test", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Response test(@RequestParam Boolean throwException) {
		return new Response("0", "test");
	}

}
