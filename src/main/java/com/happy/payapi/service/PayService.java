package com.happy.payapi.service;

import com.happy.payapi.dto.ReqDTO;
import com.happy.payapi.dto.RspDTO;

public interface PayService {
	public RspDTO pay(ReqDTO reqDTO);
}
