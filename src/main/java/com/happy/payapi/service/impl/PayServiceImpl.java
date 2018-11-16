package com.happy.payapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.payapi.dto.ReqDTO;
import com.happy.payapi.dto.RspDTO;
import com.happy.payapi.service.PayService;
import com.happy.payapi.service.strategy.GeneralStrategy;
import com.happy.payapi.service.strategy.Wx1001Strategy;
import com.happy.payapi.utils.StringUtils;
import static com.happy.payapi.dto.PayChannelNo.*;

@Service
public class PayServiceImpl implements PayService {

	@Autowired
	private Wx1001Strategy wx1001Strategy;

	@Override
	public RspDTO pay(ReqDTO reqDTO) {
		// TODO 获取可用通道，目前只有wx1001，先写死
		return null;
	}

	private GeneralStrategy getStrategy(String paychannelno) {
		if (StringUtils.isEmpty(paychannelno)) {
			return null;
		}
		switch (paychannelno) {
		case wx1001.get:
			return wx1001Strategy;
		}
		return null;
	}

}
