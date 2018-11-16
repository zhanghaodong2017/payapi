package com.happy.payapi.mapper;

import com.happy.payapi.entity.Paylog;
import com.happy.payapi.entity.PaylogKey;

public interface PaylogMapper {
    int deleteByPrimaryKey(PaylogKey key);

    int insert(Paylog record);

    int insertSelective(Paylog record);

    Paylog selectByPrimaryKey(PaylogKey key);

    int updateByPrimaryKeySelective(Paylog record);

    int updateByPrimaryKey(Paylog record);
}