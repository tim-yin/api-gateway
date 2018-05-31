package com.fly.app.gateway.security.common_validator;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fly.app.gateway.security.common_validator.chain.Validator;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.fly.app.gateway.reqest.parse.RequestParse;

/**
 * @author tim.yin
 * @date 2015年11月30日 上午11:06:36
 * @version 1.0
 * @Description:TODO
 */

@Component
public class ValidatorInterceptor implements HandlerInterceptor {

	private static Logger logger = LoggerFactory
			.getLogger(ValidatorInterceptor.class);

	@Autowired
	RequestParse requestParse;

	@Resource(name = "freezeUserValidator")
    Validator freezeUserValidator;

	@Resource(name = "requestParameterValidator")
	Validator requestParameterValidator;

	@Resource(name = "checkSignValidator")
	Validator checkSignValidator;

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2) throws Exception {

		// XXX this need refactor see:RequestParse
		Map<String, String> map = Maps.newHashMap();

		if ("GET".equals(request.getMethod())
				|| "DELETE".equals(request.getMethod())) {
			this.acquireGetMethodParameter(request, map);
		}
		if ("POST".equals(request.getMethod())
				|| "PUT".equals(request.getMethod())) {
			this.acquirePostMethodParameter(request, map);
		}

		// XXX 目前用 patch 请求方法很少，待扩展
		// 处理路径带的参数
		@SuppressWarnings("unchecked")
		Map<String, String> PathVariableMap = (Map<String, String>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		for (Entry<String, String> e : PathVariableMap.entrySet()) {
			map.put(e.getKey(), e.getValue());
		}

		// 构建责任链验证规则，1.验证用户是不是冻结账户 2.先验证参数非空完整性 3.验签
		freezeUserValidator.setSuccessor(requestParameterValidator);
		requestParameterValidator.setSuccessor(checkSignValidator);
		freezeUserValidator.requestHandler(map);
		return true;
	}

	/**
	 * 获取get delete请求方式时的参数 中文查询参数已经做了转义。
	 * 
	 * @throws UnsupportedEncodingException
	 */
	private void acquireGetMethodParameter(HttpServletRequest request,
			final Map<String, String> map) throws UnsupportedEncodingException {
		Map<String, String[]> params = request.getParameterMap();
		for (Entry<String, String[]> e : params.entrySet()) {
			map.put(e.getKey(), e.getValue()[0]);
		}
	}

	/**
	 * 获取post put 请求方式时的body里的参数
	 * 
	 * @param request
	 * @param map
	 * @throws Exception
	 */
	private void acquirePostMethodParameter(HttpServletRequest request,
			final Map<String, String> map) throws IOException {
		Map<String, Object> tempMap = Maps.newHashMap();
		ObjectMapper mapper = new ObjectMapper();
		try {
			String bodyStr = getBodyString(request);
			// log.info("request body string:" + bodyStr);
			// convert JSON string to Map
			tempMap = mapper.readValue(bodyStr,
					new TypeReference<HashMap<String, Object>>() {
					});
			for (Entry<String, Object> e : tempMap.entrySet()) {
				// 如果对象是复杂对象比如 map 和 list 就丢弃 不参与验签
				if (e.getValue() == null || e.getValue() instanceof List<?>
						|| e.getValue() instanceof Map) {
					continue;
				}
				map.put(e.getKey(), e.getValue().toString());
			}

		} catch (IOException e) {
			logger.error("post请求获取body参数转json字符串的时候发生网络异常!");
			e.printStackTrace();
		}
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
