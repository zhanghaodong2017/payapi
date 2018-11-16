package com.happy.payapi.service.strategy;

import org.springframework.stereotype.Component;

import com.happy.payapi.dto.BizException;
import com.happy.payapi.dto.ReqDTO;
import com.happy.payapi.dto.RspDTO;

@Component
public class Wx1001Strategy extends GeneralStrategy {

	@Override
	public RspDTO pay(ReqDTO reqDTO) throws BizException {
		
		return null;
	}
	

}
