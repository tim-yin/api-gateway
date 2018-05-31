package com.fly.app.gateway.reqest.parse.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.fly.app.gateway.reqest.parse.RequestParse;
import com.fly.app.gateway.reqest.parse.utils.StringToEnumConverterFactory;

/**
 * @author tim.yin
 * @date 2015年12月4日 下午2:52:36
 * @version 1.0
 * @Description:TODO
 */
@Component
public class RequestParseHandler implements RequestParse {

	private static Logger logger = LoggerFactory
			.getLogger(RequestParseHandler.class);

	@Autowired
	StringToEnumConverterFactory stringToEnumConverterFactory;

	@Override
	public Map<String, Object> convertRequestParam(HttpServletRequest request)
			throws UnsupportedEncodingException, IOException,
			ClassNotFoundException {
		Map<String, Object> map = Maps.newHashMap();

		if ("GET".equals(request.getMethod())
				|| "DELETE".equals(request.getMethod())) {

			map = this.acquireGetMethodParameter(request);
		}
		if ("POST".equals(request.getMethod())
				|| "PUT".equals(request.getMethod())) {
			map = this.acquirePostMethodParameter(request);
		}

		return map;

	}

	/**
	 * 获取get delete请求方式时的参数 中文查询参数已经做了转义。
	 * 
	 * @throws UnsupportedEncodingException
	 */
	private Map<String, Object> acquireGetMethodParameter(
			HttpServletRequest request) throws UnsupportedEncodingException {

		Map<String, Object> map = Maps.newHashMap();

		Map<String, String[]> params = request.getParameterMap();

		for (Entry<String, String[]> e : params.entrySet()) {

			map.put(e.getKey(), e.getValue()[0]);
		}
		return map;
	}

	/**
	 * 获取post put 请求方式时的body里的参数
	 * 
	 * @param request
	 * @param map
	 * @throws Exception
	 */
	private Map<String, Object> acquirePostMethodParameter(
			HttpServletRequest request) throws IOException {

		Map<String, Object> map = Maps.newHashMap();

		ObjectMapper mapper = new ObjectMapper();

		try {
			String bodyStr = getBodyString(request);
			map = mapper.readValue(bodyStr,
					new TypeReference<HashMap<String, Object>>() {
					});
		} catch (IOException e) {
			logger.error("post请求获取body参数转json字符串的时候发生网络异常!");
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * 获取到的body参数 转成String
	 * 
	 * @param br
	 * @return
	 */
	public static String getBodyString(HttpServletRequest request)
			throws IOException {
		InputStream input = request.getInputStream();
		String result = IOUtils.toString(input, "UTF-8");
		return result;
	}

}
