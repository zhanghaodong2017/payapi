package com.happy.payapi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.happy.payapi.entity.PayChannelInfo;

public interface PayChannelInfoMapper {
	int deleteByPrimaryKey(String paychannelno);

	int insert(PayChannelInfo record);

	int insertSelective(PayChannelInfo record);

	PayChannelInfo selectByPrimaryKey(String paychannelno);

	int updateByPrimaryKeySelective(PayChannelInfo record);

	int updateByPrimaryKey(PayChannelInfo record);

	List<PayChannelInfo> queryAvailable(@Param("paytype") String paytype);
}