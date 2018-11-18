package com.happy.payapi.utils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhanghaodong
 * @create 2017-11-22 11:03
 * @desc 封装了httpcomponents的工具类,支持https
 */
public class HttpUtils {
	private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	private static PoolingHttpClientConnectionManager connMgr;
	private static RequestConfig requestConfig;
	private static final int MAX_TIMEOUT = 20000;// 20秒
	private static final String UTF_8 = "UTF-8";
	public static final String JSON = "json";
	public static final String XML = "xml";

	static {
		// 设置连接池
		connMgr = new PoolingHttpClientConnectionManager();
		// 设置连接池大小
		connMgr.setMaxTotal(100);
		connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
		RequestConfig.Builder configBuilder = RequestConfig.custom();
		// 设置连接超时
		configBuilder.setConnectTimeout(MAX_TIMEOUT);
		// 设置读取超时
		configBuilder.setSocketTimeout(MAX_TIMEOUT);
		// 设置从连接池获取连接实例的超时
		configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
		// 在提交请求之前 测试连接是否可用
		configBuilder.setStaleConnectionCheckEnabled(true);
		requestConfig = configBuilder.build();
	}

	/**
	 * 下载文件
	 * 
	 * @param url
	 * @param filePath
	 * @throws IOException
	 */
	public static void download(String url, File filePath) throws IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		HttpEntity httpEntity = null;
		ByteArrayOutputStream output = null;
		FileOutputStream fos = null;
		try {
			HttpGet httpGet = new HttpGet(url);
			response = httpclient.execute(httpGet);
			httpEntity = response.getEntity();
			long contentLength = httpEntity.getContentLength();
			InputStream is = httpEntity.getContent();
			// 根据InputStream 下载文件
			output = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			int r = 0;
			long totalRead = 0;
			while ((r = is.read(buffer)) > 0) {
				output.write(buffer, 0, r);
				totalRead += r;
			}
			fos = new FileOutputStream(filePath);
			output.writeTo(fos);
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(output);
			close(fos);
			EntityUtils.consume(httpEntity);
		}
	}

	private static void close(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送 GET 请求（HTTP），不带输入数据
	 *
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static String doGet(String url) throws IOException {
		return doGet(url, null);
	}

	/**
	 * 发送 GET 请求（HTTP），K-V形式
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public static String doGet(String url, Map<String, String> params) throws IOException {
		return doGet(url, params, null);
	}

	/**
	 * 发送 GET 请求（HTTP），K-V形式
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public static String doGet(String url, Map<String, String> params, Map<String, String> headers) throws IOException {
		boolean flag = true;
		StringBuffer param = new StringBuffer(url);
		if (params != null) {
			for (String key : params.keySet()) {
				if (flag) {
					param.append("?");
					flag = false;
				} else
					param.append("&");
				param.append(key).append("=").append(encode(params.get(key)));
			}
		}
		logger.info("请求参数：{}", param.toString());
		String result = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		try {
			HttpGet httpGet = new HttpGet(param.toString());
			if (headers != null) {
				for (String headerName : headers.keySet()) {
					httpGet.setHeader(headerName, headers.get(headerName));
				}
			}
			response = httpclient.execute(httpGet);
			// int statusCode = response.getStatusLine().getStatusCode();

			entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				result = IOUtils.toString(instream, UTF_8);
			}
		} finally {
			// 最后别忘了关闭应该关闭的资源，适当的释放资源
			try {
				// 这个方法也可以把底层的流给关闭了
				EntityUtils.consume(entity);
				// if (null != response) {
				// response.close();
				// }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private static String encode(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * 发送 POST 请求（HTTP），不带输入数据
	 * 
	 * @param apiUrl
	 * @return
	 * @throws ConnectTimeoutException
	 */
	public static String doPost(String apiUrl) throws IOException {
		return doPost(apiUrl, new HashMap<String, String>());
	}

	/**
	 * 发送 POST 请求（HTTP），K-V形式
	 * 
	 * @param apiUrl
	 *            API接口URL
	 * @param params
	 *            参数map
	 * @return
	 * @throws ConnectTimeoutException
	 */
	public static String doPost(String apiUrl, Map<String, String> params) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String httpStr = null;
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;

		try {
			httpPost.setConfig(requestConfig);
			List<NameValuePair> pairList = new ArrayList<>(params.size());
			for (Map.Entry<String, String> entry : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue());
				pairList.add(pair);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			httpStr = EntityUtils.toString(entity, "UTF-8");
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	/**
	 * 发送 POST 请求（HTTP），JSON形式
	 * 
	 * @param apiUrl
	 * @param content
	 *            内容
	 * @return
	 * @throws IOException
	 */
	public static String doPost(String apiUrl, String content, String type) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String httpStr = null;
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;

		try {
			httpPost.setConfig(requestConfig);
			StringEntity stringEntity = new StringEntity(content, "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			if (JSON.equals(type)) {
				stringEntity.setContentType("application/json");
			} else if (XML.equals(type)) {
				stringEntity.setContentType("application/xml");
			} else {
				stringEntity.setContentType("text/plain");
			}
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			// System.out.println(response.getStatusLine().getStatusCode());
			httpStr = EntityUtils.toString(entity, "UTF-8");
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	/**
	 * 发送 POST 请求（HTTP），JSON形式
	 * 
	 * @param apiUrl
	 * @param content
	 *            内容
	 * @return
	 * @throws IOException
	 */
	public static byte[] doPostByByte(String apiUrl, String content, String type) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		byte[] bytes = null;
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;

		try {
			httpPost.setConfig(requestConfig);
			StringEntity stringEntity = new StringEntity(content, "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			if (JSON.equals(type)) {
				stringEntity.setContentType("application/json");
			} else if (XML.equals(type)) {
				stringEntity.setContentType("application/xml");
			} else {
				stringEntity.setContentType("text/plain");
			}
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			// System.out.println(response.getStatusLine().getStatusCode());
			bytes = EntityUtils.toByteArray(entity);
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bytes;
	}

	public static String doPost(String apiUrl, String content, String type, Map<String, String> headers)
			throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String httpStr = null;
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;

		try {
			httpPost.setConfig(requestConfig);
			if (headers != null) {
				for (String headerName : headers.keySet()) {
					httpPost.setHeader(headerName, headers.get(headerName));
				}
			}
			StringEntity stringEntity = new StringEntity(content, "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			if (JSON.equals(type)) {
				stringEntity.setContentType("application/json");
			} else if (XML.equals(type)) {
				stringEntity.setContentType("application/xml");
			} else {
				stringEntity.setContentType("text/plain");
			}
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			// System.out.println(response.getStatusLine().getStatusCode());
			httpStr = EntityUtils.toString(entity, "UTF-8");
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	public static String doPost(String apiUrl, Map<String, String> params, Map<String, String> headers)
			throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String httpStr = null;
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;

		try {
			httpPost.setConfig(requestConfig);
			if (headers != null) {
				for (String headerName : headers.keySet()) {
					httpPost.setHeader(headerName, headers.get(headerName));
				}
			}
			List<NameValuePair> pairList = new ArrayList<>(params.size());
			for (Map.Entry<String, String> entry : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue());
				pairList.add(pair);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			httpStr = EntityUtils.toString(entity, "UTF-8");
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	/**
	 * 发送 SSL POST 请求（HTTPS），K-V形式
	 * 
	 * @param apiUrl
	 *            API接口URL
	 * @param params
	 *            参数map
	 * @return
	 */
	public static String doPostSSL(String apiUrl, Map<String, Object> params) {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
				.setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		String httpStr = null;

		try {
			httpPost.setConfig(requestConfig);
			List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
				pairList.add(pair);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			httpStr = EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	/**
	 * 发送 SSL POST 请求（HTTPS），JSON形式
	 * 
	 * @param apiUrl
	 *            API接口URL
	 * @param json
	 *            JSON对象
	 * @return
	 */
	public static String doPostSSL(String apiUrl, Object json) {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
				.setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		String httpStr = null;

		try {
			httpPost.setConfig(requestConfig);
			StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			httpStr = EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	/**
	 * 创建SSL安全连接
	 *
	 * @return
	 */
	private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
		SSLConnectionSocketFactory sslsf = null;
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}

				@Override
				public void verify(String host, SSLSocket ssl) throws IOException {
				}

				@Override
				public void verify(String host, X509Certificate cert) throws SSLException {
				}

				@Override
				public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
				}
			});
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		return sslsf;
	}

	/**
	 * 发送 SSL POST 请求（HTTPS），加载证书
	 * 
	 * @param apiUrl
	 *            API接口URL
	 * @param json
	 *            JSON对象
	 * @return
	 */
	public static String doPostSSL(String url, String content, String type, Map<String, String> headers,
			SSLConnectionSocketFactory sslsf) {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
				.setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		String httpStr = null;

		try {
			httpPost.setConfig(requestConfig);
			if (headers != null) {
				for (String headerName : headers.keySet()) {
					httpPost.setHeader(headerName, headers.get(headerName));
				}
			}
			StringEntity stringEntity = new StringEntity(content, "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			if (JSON.equals(type)) {
				stringEntity.setContentType("application/json");
			} else if (XML.equals(type)) {
				stringEntity.setContentType("application/xml");
			} else {
				stringEntity.setContentType("text/plain");
			}
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			httpStr = EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String rsp = doGet(
				"https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardNo=6214855124526621&cardBinCheck=true");
		System.out.println(rsp);
	}
}
