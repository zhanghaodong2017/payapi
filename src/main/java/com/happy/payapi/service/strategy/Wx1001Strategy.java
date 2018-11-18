package com.happy.payapi.service.strategy;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.happy.payapi.dto.BizException;
import com.happy.payapi.dto.Errorcode;
import com.happy.payapi.dto.ReqDTO;
import com.happy.payapi.dto.RspDTO;
import com.happy.payapi.entity.Paylog;
import com.happy.payapi.utils.HttpUtils;
import com.happy.payapi.utils.MD5Utill;
import com.happy.payapi.utils.StringUtils;

@Component
public class Wx1001Strategy extends GeneralStrategy {

	private static Logger logger = LoggerFactory.getLogger(Wx1001Strategy.class);

	public static final String payway = "weixin";
	public static final String paytype = "weixin.app";
	public static final String appkey = "13e541320037895f572da58c6be47a73";
	public static final String appid = "20000";
	public static final String notifyurl = "http://47.105.171.206:9999/callback/wx1001";
	public static final String preorderApi = "http://api.daduci.com/";

	@Override
	public RspDTO pay(ReqDTO reqDTO, Paylog paylog) throws Exception {
		RspDTO rspDTO = new RspDTO();
		String orderno = appid + "_" + getOrderId(24);
		paylog.setOrderno(orderno);
		rspDTO.setOrderno(orderno);

		Map<String, String> params = getReqParam(reqDTO, orderno);
		paylog.setReqdata(params.toString());
		String res = sendGet(preorderApi, params);
		paylog.setRspdata(res);
		JSONObject jsonObject = JSONObject.parseObject(res);
		String status = jsonObject.getString("status");
		String msg = jsonObject.getString("msg");
		if (!"1".equals(status)) {
			throw new BizException(Errorcode.fail_3.getCode(), msg);
		}
		JSONObject data = jsonObject.getJSONObject("data");
		String prepayid = data.getString("prepayid");
		String partnerid = data.getString("partnerid");
		String _appid = data.getString("appid");
		String noncestr = data.getString("noncestr");
		rspDTO.setAppid(_appid);
		rspDTO.setNoncestr(noncestr);
		rspDTO.setPartnerid(partnerid);
		rspDTO.setPrepayid(prepayid);
		return rspDTO;
	}

	private Map<String, String> getReqParam(ReqDTO reqDTO, String ordersn) throws Exception {
		String amount = reqDTO.getAmount().toString();
		String itemname = reqDTO.getGoodsDesc();
		String orderdesc = "test";

		Map<String, String> treeMap = new TreeMap<String, String>();
		treeMap.put("appid", appid);
		treeMap.put("amount", amount);
		treeMap.put("itemname", itemname);
		treeMap.put("ordersn", ordersn);
		treeMap.put("orderdesc", orderdesc);
		treeMap.put("notifyurl", notifyurl);

		StringBuilder signBuilder = new StringBuilder();
		for (String key : treeMap.keySet()) {
			String value = treeMap.get(key);
			if (StringUtils.isNotEmpty(value)) {
				signBuilder.append(value).append("|");
			}
		}

		String signstr = signBuilder.append(appkey).toString();
		String sign = MD5Utill.getMD5(signstr);

		logger.info("拼接签名字符串：" + signstr);
		logger.info("计算得到的MD5值" + sign);

		String returnurl = reqDTO.getReturnurl();
		String ext = reqDTO.getExt();
		// 有序map，按照放入的先后顺序
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("appid", appid);
		params.put("amount", amount);
		params.put("itemname", itemname);
		params.put("ordersn", ordersn);
		params.put("orderdesc", orderdesc);
		params.put("notifyurl", notifyurl);
		params.put("sign", sign);
		params.put("returnurl", returnurl);
		params.put("payway", payway);
		params.put("ext", ext);
		params.put("paytype", paytype);
		return params;
	}

	private static String sendGet(String url, Map<String, String> params) throws IOException {
		Map<String, String> headers = new HashMap<>();
		headers.put("accept", "*/*");
		headers.put("connection", "Keep-Alive");
		headers.put("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		String result = HttpUtils.doGet(url, params, headers);
		return result;
	}

}
