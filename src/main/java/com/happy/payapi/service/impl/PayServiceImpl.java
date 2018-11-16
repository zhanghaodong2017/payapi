package com.happy.payapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.payapi.dto.PayChannelNo;
import com.happy.payapi.dto.ReqDTO;
import com.happy.payapi.dto.RspDTO;
import com.happy.payapi.service.PayService;
import com.happy.payapi.service.strategy.GeneralStrategy;
import com.happy.payapi.service.strategy.Wx1001Strategy;
import com.happy.payapi.service.strategy.Wx1002Strategy;
import com.happy.payapi.utils.StringUtils;

@Service
public class PayServiceImpl implements PayService {

	@Autowired
	private Wx1001Strategy wx1001Strategy;
	@Autowired
	private Wx1002Strategy wx1002Strategy;

	@Override
	public RspDTO pay(ReqDTO reqDTO) throws Exception {
		// TODO 获取可用通道，目前只有wx1001，先写死
		GeneralStrategy strategy = getStrategy(PayChannelNo.wx1001);
		RspDTO rspDTO = strategy.pay(reqDTO);
		return rspDTO;
	}

	private GeneralStrategy getStrategy(PayChannelNo paychannelno) {
		if (StringUtils.isEmpty(paychannelno)) {
			return null;
		}
		switch (paychannelno) {
		case wx1001:
			return wx1001Strategy;
		case wx1002:
			return wx1002Strategy;
		default:
			return null;
		}
	}

}
