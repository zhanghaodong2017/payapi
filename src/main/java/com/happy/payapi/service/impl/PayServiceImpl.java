package com.happy.payapi.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.wall.violation.ErrorCode;
import com.google.gson.Gson;
import com.happy.payapi.dto.BizException;
import com.happy.payapi.dto.Errorcode;
import com.happy.payapi.dto.PayChannelNo;
import com.happy.payapi.dto.ReqDTO;
import com.happy.payapi.dto.RspDTO;
import com.happy.payapi.entity.PayChannelInfo;
import com.happy.payapi.entity.Paylog;
import com.happy.payapi.mapper.PayChannelInfoMapper;
import com.happy.payapi.mapper.PaylogMapper;
import com.happy.payapi.service.PayService;
import com.happy.payapi.service.strategy.Ap1001Strategy;
import com.happy.payapi.service.strategy.GeneralStrategy;
import com.happy.payapi.service.strategy.Wx1001Strategy;
import com.happy.payapi.service.strategy.Wx1002Strategy;
import com.happy.payapi.utils.StringUtils;

@Service
public class PayServiceImpl implements PayService {

	@Autowired
	private PaylogMapper paylogMapper;
	@Autowired
	private PayChannelInfoMapper payChannelInfoMapper;
	@Autowired
	private Wx1001Strategy wx1001Strategy;
	@Autowired
	private Wx1002Strategy wx1002Strategy;
	@Autowired
	private Ap1001Strategy ap1001Strategy;

	@Override
	public RspDTO pay(ReqDTO reqDTO) throws Exception {
		// TODO 获取可用通道，目前只有wx1001，先写死
		Paylog paylog = new Paylog();
		fillPaylog(reqDTO, paylog);
		RspDTO rspDTO = null;
		try {
			PayChannelInfo payChannelInfo = queryPaychannel(reqDTO.getPaytype());
			if (payChannelInfo == null) {
				throw new BizException(Errorcode.fail_5.getCode(), Errorcode.fail_5.getDesc());
			}
			PayChannelNo payChannelNo = PayChannelNo.getByCode(payChannelInfo.getPaychannelno());
			paylog.setPaychannelno(payChannelInfo.getPaychannelno());
			GeneralStrategy strategy = getStrategy(payChannelNo);
			rspDTO = strategy.pay(reqDTO, paylog);
			paylog.setPaystatus("1");// 支付结果；0成功,1支付中，2支付失败，3未支付
		} catch (BizException e) {
			paylog.setPaystatus("3");
			paylog.setErrorcode(e.getCode());
			paylog.setErrormsg(e.getMsg());
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			paylog.setPaystatus("3");
			paylog.setErrorcode(Errorcode.fail_4.getCode());
			paylog.setErrormsg(e.getMessage());
			throw new BizException(Errorcode.fail_4.getCode(), Errorcode.fail_4.getDesc());
		} finally {
			// 保存日志
			saveLog(paylog);
		}
		return rspDTO;
	}

	private PayChannelInfo queryPaychannel(String paytype) {
		List<PayChannelInfo> channelInfos = payChannelInfoMapper.queryAvailable(paytype);
		if (channelInfos == null || channelInfos.size() == 0) {
			return null;
		}
		return channelInfos.get(0);
	}

	private void fillPaylog(ReqDTO reqDTO, Paylog paylog) {
		paylog.setAmount(reqDTO.getAmount());
		paylog.setAppid(reqDTO.getAppid());
		paylog.setAppname(reqDTO.getAppname());
		paylog.setExt(reqDTO.getExt());
		paylog.setGoodsdesc(reqDTO.getGoodsDesc());
		paylog.setPaytype(reqDTO.getPaytype());
		paylog.setSource(reqDTO.getSource());
		paylog.setReturnurl(reqDTO.getReturnurl());
	}

	private void saveLog(Paylog paylog) {
		try {
			String uuid = UUID.randomUUID().toString().replace("-", "");
			paylog.setUuid(uuid);
			String reqdata = paylog.getReqdata();
			if (reqdata != null && reqdata.length() > 1024) {
				paylog.setReqdata(reqdata.substring(0, 1024));
			}
			String rspdata = paylog.getRspdata();
			if (rspdata != null && rspdata.length() > 1024) {
				paylog.setRspdata(rspdata.substring(0, 1024));
			}
			String errormsg = paylog.getErrormsg();
			if (errormsg != null && errormsg.length() > 128) {
				paylog.setErrormsg(errormsg.substring(0, 1024));
			}
			paylog.setCreatetime(new Date());
//			System.out.println(new Gson().toJson(paylog));
			paylogMapper.insert(paylog);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		case alipay1001:
			return ap1001Strategy;
		default:
			return null;
		}
	}

}
