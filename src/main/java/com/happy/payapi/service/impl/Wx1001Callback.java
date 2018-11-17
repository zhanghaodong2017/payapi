package com.happy.payapi.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.happy.payapi.dto.Wx1001BackDTO;
import com.happy.payapi.entity.Paylog;
import com.happy.payapi.mapper.PaylogMapper;
import com.happy.payapi.utils.MD5Utill;
import com.happy.payapi.utils.StringUtils;

@Component
public class Wx1001Callback {
	private static Logger logger = LoggerFactory.getLogger(Wx1001Callback.class);

	public static final String appkey = "WEPQgf22dGk7376tF0VFzgs5TDCo11DH";

	@Autowired
	private PaylogMapper paylogMapper;

	public void handleResult(Wx1001BackDTO backDTO) throws Exception {
		// 验签：
		Map<String, String> treeMap = new TreeMap<String, String>();
		treeMap.put("appid", backDTO.getAppid());
		treeMap.put("amount", backDTO.getAmount());
		treeMap.put("itemname", backDTO.getItemname());
		treeMap.put("ordersn", backDTO.getOrdersn());
		treeMap.put("orderdesc", backDTO.getOrderdesc());
		treeMap.put("serialno", backDTO.getSerialno());

		StringBuilder signBuilder = new StringBuilder();
		for (String key : treeMap.keySet()) {
			String value = treeMap.get(key);
			if (StringUtils.isNotEmpty(value)) {
				signBuilder.append(value).append("|");
			}
		}
		String signstr = signBuilder.append(appkey).toString();
		String sign = MD5Utill.getMD5(signstr);
		if (!sign.equals(backDTO.getSign())) {
			logger.info("验签结果未通过，订单号；{}", backDTO.getOrdersn());
			// throw new BizException("1", "验签结果未通过");
		}
		Paylog paylog = paylogMapper.queryByOrderno(backDTO.getOrdersn());
		if (paylog != null) {
			paylog.setThirdorderno(backDTO.getSerialno());
			paylog.setRemark(new Gson().toJson(backDTO));
			paylog.setPaystatus("0");// 注：只有成功支付的订单才会参与回调
			paylog.setUpdatetime(new Date());
			paylogMapper.updateByPrimaryKeySelective(paylog);
		} else {
			logger.info("同步未查询到订单号：{}", backDTO.getOrdersn());
		}
	}
}
