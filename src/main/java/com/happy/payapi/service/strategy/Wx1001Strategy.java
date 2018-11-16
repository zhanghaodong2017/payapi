package com.happy.payapi.service.strategy;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.happy.payapi.dto.ReqDTO;
import com.happy.payapi.dto.RspDTO;
import com.happy.payapi.utils.HttpUtils;
import com.happy.payapi.utils.MD5Utill;
import com.happy.payapi.utils.StringUtils;

@Component
public class Wx1001Strategy extends GeneralStrategy {

	private static Logger logger = LoggerFactory.getLogger(Wx1001Strategy.class);

	public static final String payway = "weixin";
	public static final String paytype = "weixin.app";
	public static final String appkey = "4efd468a8f2281ed0eee630cb639cd83";
	public static final String appid = "20000";
	public static final String notifyurl = "http://api.shanLide.cn/appdemo/paycb.php";

	@Override
	public RspDTO pay(ReqDTO reqDTO) throws Exception {

		String preorderApi = "http://api.daduci.com/";
		Map<String, String> params = getReqParam(reqDTO);
		String res = sendGet(preorderApi, params);
		logger.info(params.toString());
		logger.info(res);
		return null;
	}

	private Map<String, String> getReqParam(ReqDTO reqDTO) throws Exception {
		String amount = reqDTO.getAmount().intValue() + "";
		String itemname = reqDTO.getGoodsDesc();
		String ordersn = getOrderId(24);
		String orderdesc = "test";
		String notifyurl = "http://api.shanLide.cn/appdemo/paycb.php";

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
