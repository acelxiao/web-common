package utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	private static final int TIMEOUT_SECONDS = 20;

	private static final int POOL_SIZE = 20;

	private static final String DEFAULT_ENCODING = "UTF-8";

	private static RequestConfig requestConfig;

	private static HttpClientBuilder httpClientBuilder;

	static {
		Registry<ConnectionSocketFactory> socketFactoryRegistry = null;
		try {
			//信任任何链接
			X509TrustManager anyTrustManager = new X509TrustManager() {
				@Override
				public void checkClientTrusted(
						java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
						String paramString) throws CertificateException {
				}

				@Override
				public void checkServerTrusted(
						java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
						String paramString) throws CertificateException {
				}

				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			SSLContext sslContext = SSLContext.getInstance("SSLv3");
			sslContext.init(null, new TrustManager[] {anyTrustManager}, null);
			// 设置协议http和https对应的处理socket链接工厂的对象
			socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", PlainConnectionSocketFactory.INSTANCE)
					.register("https", new SSLConnectionSocketFactory(sslContext))
					.build();

		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
		} catch (KeyManagementException e) {
			logger.error(e.getMessage(), e);
		}
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		requestConfig = RequestConfig.custom().setSocketTimeout(TIMEOUT_SECONDS * 1000)
				.setConnectTimeout(TIMEOUT_SECONDS * 1000).build();
		httpClientBuilder = HttpClients.custom().setConnectionManager(connManager).setMaxConnTotal(POOL_SIZE).setMaxConnPerRoute(POOL_SIZE)
				.setDefaultRequestConfig(requestConfig);
	}

	/**
	 * 创建一个客户端连接
	 * @return
     */
	private static CloseableHttpClient createHttpClient() {
		return httpClientBuilder.build();
	}


	public static String sendGetRequest(String url) {
		return doGet(url, new HashMap<String, String>(), null);
	}

	public static String sendGetRequest(String url, Map<String, String> params) {
		return doGet(url, params, null);
	}

	public static String sendPostRequest(String url, Map<String, String> params) {
		return doPost(url,params, DEFAULT_ENCODING);
	}


	/**
	 * get请求
	 * @param url 请求url
	 * @param queryString 请求的参数字符串
	 * @param encoding 编码类型
	 * @return
	 */
	public static String doGet(String url, String queryString, String encoding) {
		return doGet(url, queryStrTOMap(queryString), encoding);
	}

	public static String doGet(String url, Map<String, String> params) {
		return doGet(url, params, "utf-8");
	}

	/**
	 * get请求
	 * @param url 请求url
	 * @param params 请求参数map
	 * @param encoding 编码类型
	 * @return
	 */
	public static String doGet(String url, Map<String, String> params, String encoding) {
		if(StringUtils.isBlank(url)){
			return null;
		}
		CloseableHttpResponse response = null;
		try {

			if (params != null && params.size() != 0) {
				url += "?" + mapToQueryStr(params, encoding);
			}

			HttpGet httpGet = new HttpGet(url);
			CloseableHttpClient httpClient = createHttpClient();
			response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				httpGet.abort();
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}


			HttpEntity entity = response.getEntity();
			String result = "";
			if (entity != null) {
				result = EntityUtils.toString(entity, StringUtils.isNotBlank(encoding) ? encoding : DEFAULT_ENCODING);
			}
			EntityUtils.consume(entity);
			if (url.contains("pcLogin_loadGame") || url.contains("pcLogin_loadUserGameRelation")
					|| url.contains("getTokenMessage") || url.contains("assist_loadUserExpandMsg")){
				logger.info("url:{}", url); // 结果太多,只打印url
			}else{
				logger.info("url:{}, result:{}", url, result);
			}
			return result;
		} catch (Exception e) {
			logger.error(new StringBuilder("请求异常: url=").append(url).append(", message=").append(e.getMessage()).toString(), e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error(new StringBuilder("请求异常: url=").append(url).append(", message=").append(e.getMessage()).toString(), e);
				}
			}
		}
		return null;
	}

	/**
	 * post请求
	 * @param url
	 * @param params
	 * @param encoding
     * @return
     */
	public static String doPost(String url, Map<String, String> params, String encoding) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if (params != null && params.size() != 0) {

			for (Map.Entry<String, String> item : params.entrySet()) {
				list.add(new BasicNameValuePair(item.getKey(), item.getValue()));
			}

		}
		try {
			return doPost(url, new UrlEncodedFormEntity(list,encoding), StringUtils.isNotBlank(encoding) ? encoding : DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		return "";
	}

	/**
	 * post请求
	 * @param url 请求url
	 * @param httpEntity 请求实体
	 * @param encoding 编码类型
	 * @return
	 */
	public static String doPost(String url, HttpEntity httpEntity, String encoding) {
		if(StringUtils.isBlank(url)){
			return null;
		}
		CloseableHttpResponse response = null;
		try {
			logger.debug("post request url : " + url);
			HttpPost httpPost = new HttpPost(url);
			logger.debug("post request body : " + httpEntity.toString());
			httpPost.setEntity(httpEntity);
			CloseableHttpClient httpClient = createHttpClient();
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				httpPost.abort();
				logger.debug("http request failed");
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			String result = "";
			if (entity != null){
				result = EntityUtils.toString(entity, StringUtils.isNotBlank(encoding) ? encoding : DEFAULT_ENCODING);
			}
			EntityUtils.consume(entity);
			return result;
		} catch (Exception e) {
			logger.error(new StringBuilder("请求异常: url=").append(url).append(", message=").append(e.getMessage()).toString(), e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error(new StringBuilder("请求异常: url=").append(url).append(", message=").append(e.getMessage()).toString(), e);
				}
			}
		}
		return null;
	}

	/**
	 * 请求参数字符串转化为map
	 * @param queryString
	 * @return
	 */
	public static Map<String, String> queryStrTOMap(String queryString) {
		String[] subQueryStrs = null;
		if(StringUtils.isBlank(queryString)){
			return null;
		}
		subQueryStrs = queryString.split("&");
		Map<String, String> params = null;
		if (subQueryStrs != null) {
			params = new HashMap<String, String>(subQueryStrs.length);
			for(String item : subQueryStrs){
				if(StringUtils.isNotBlank(item)){
					String[] keyValue = item.split("=");
					if(keyValue.length == 2){
						params.put(keyValue[0], keyValue[1]);
					}
				}
			}
		}
		return params;
	}

	/**
	 * 请求参数map转化为字符串，并且按照编码转换
	 * @param params
	 * @param encoding
	 * @return
	 */
	public static String mapToQueryStr(Map<String, String> params, String encoding) {
		if(params != null && !params.isEmpty()){
			List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
			for(Map.Entry<String,String> entry : params.entrySet()){
				String value = entry.getValue();
				if(value != null){
					pairs.add(new BasicNameValuePair(entry.getKey(),value));
				}
			}
			try {
				return EntityUtils.toString(new UrlEncodedFormEntity(pairs, StringUtils.isNotBlank(encoding) ? encoding : DEFAULT_ENCODING));
			} catch (Exception e) {
				logger.debug("map to queryStr failed !");
				logger.error(e.getMessage(), e);
			}
		}
		return null;
	}



	public static String sendHttpRequest(String url, String method, List<NameValuePair> urlParameters) {
		CloseableHttpResponse response = null;
		StringBuffer result = new StringBuffer();
		try {
			//CloseableHttpClient httpclient = HttpClients.createDefault();
			CloseableHttpClient httpClient = createHttpClient();
			CloseableHttpClient httpclient = httpClient;
			if (StringUtils.equalsIgnoreCase(method, "get")) {
				HttpGet httpGet = new HttpGet(url);
				response = httpclient.execute(httpGet);
			} else if (StringUtils.equalsIgnoreCase(method, "post")) {
				HttpPost post = new HttpPost(url);
				post.setEntity(new UrlEncodedFormEntity(urlParameters));
				response = httpclient.execute(post);
			}
			HttpEntity entity = response.getEntity();
			if (entity != null){
				result.append(EntityUtils.toString(entity, DEFAULT_ENCODING));
			}
			EntityUtils.consume(entity);

		} catch (Exception e) {
			logger.error("URL=" + url + ",catchMsg=" + e.getMessage(), e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				logger.error("URL=" + url + ",finallyMsg=" + e.getMessage(), e);
			}
		}
		return result.toString();
	}

	/**
	 * HTTP请求方法
	 * 
	 * @param uriRequest 请求
	 * @return 请求返回数据
	 */
	public static String sendHttpRequest(HttpUriRequest uriRequest) {
		CloseableHttpResponse response = null;
		StringBuffer result = new StringBuffer();
		try {
			CloseableHttpClient httpClient = createHttpClient();
			CloseableHttpClient httpclient = httpClient;
			response = httpclient.execute(uriRequest);
			HttpEntity entity = response.getEntity();
			if (entity != null){
				result.append(EntityUtils.toString(entity, DEFAULT_ENCODING));
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			logger.error("catchMsg=" + e.getMessage(), e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				logger.error("finallyMsg=" + e.getMessage(), e);
			}
		}
		return result.toString();
	}

}
