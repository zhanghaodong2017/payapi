package com.happy.payapi.mapper;

import com.happy.payapi.entity.Paylog;

public interface PaylogMapper {
	int deleteByPrimaryKey(String uuid);

	int insert(Paylog record);

	int insertSelective(Paylog record);

	Paylog selectByPrimaryKey(String uuid);

	int updateByPrimaryKeySelective(Paylog record);

	int updateByPrimaryKey(Paylog record);

	Paylog queryByOrderno(String orderno);
}