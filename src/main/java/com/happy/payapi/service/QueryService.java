package com.happy.payapi.service;

import com.happy.payapi.dto.Paystatus;

public interface QueryService {
	public Paystatus queryByOrderno(String orderno);
}
