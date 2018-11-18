package com.happy.payapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.payapi.dto.Paystatus;
import com.happy.payapi.entity.Paylog;
import com.happy.payapi.mapper.PaylogMapper;
import com.happy.payapi.service.QueryService;
import com.happy.payapi.utils.StringUtils;

@Service
public class QueryServiceImpl implements QueryService {

	@Autowired
	private PaylogMapper paylogMapper;

	@Override
	public Paystatus queryByOrderno(String orderno) {
		if (StringUtils.isEmpty(orderno)) {
			return Paystatus.not_pay;
		}
		Paylog paylog = paylogMapper.queryByOrderno(orderno);
		if (paylog == null) {
			return Paystatus.not_pay;
		}
		String paystatus = paylog.getPaystatus();
		Paystatus paystatus2 = Paystatus.getByCode(paystatus);
		if (paystatus2 == null) {
			return Paystatus.not_pay;
		}
		return paystatus2;
	}

}
