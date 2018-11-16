package com.happy.payapi.service.strategy;

import com.happy.payapi.dto.BizException;
import com.happy.payapi.dto.ReqDTO;
import com.happy.payapi.dto.RspDTO;

public abstract class GeneralStrategy {

	public abstract RspDTO pay(ReqDTO reqDTO) throws BizException;
}
