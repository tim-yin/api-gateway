package com.niwodai.app.gateway.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niwodai.app.gateway.metrics.RestApiTimerManager;
import com.niwodai.app.gateway.remote.call.DubboRemoteCall;
import com.niwodai.app.gateway.reqest.parse.RequestParse;
import com.niwodai.app.gateway.reqest.parse.utils.StringToEnumConverterFactory;
import com.niwodai.app.gateway.route.RegisterService;
import com.niwodai.app.gateway.route.dto.UniqueServiceDefined;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;

/**
 * @author tim.yin
 * @date 2015年11月28日 下午4:32:52
 * @version 1.0
 * @Description:open api gateway 的Restful 触点
 */

@RestController
public class RestEndpoint {

	private static Logger logger = LoggerFactory.getLogger(RestEndpoint.class);

	@Autowired
	private RequestParse requestParse;

	@Autowired
	StringToEnumConverterFactory stringToEnumConverterFactory;

	@Autowired
	private RegisterService registerService;

	@Autowired
	private DubboRemoteCall dubboRemoteCall;

	@RequestMapping(value = "/register")
	public void register(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			registerService
					.register(
							"dashboard.create.get",
							new UniqueServiceDefined(
									"",
									"com.niwodai.fortune.endpoint.MockAggregationServiceApi",
									"1.0",
									"createRecord",
									"com.niwodai.fortune.endpoint.impl.Product",
									""));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public Object entry(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Object result = new Object();

		Map<String, Object> params = requestParse.convertRequestParam(request);

		try {
			String inputParamValue = JSONMapper.toJSON(params).render(true);
			logger.info("前端输入:" + inputParamValue);
		} catch (MapperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// XXX 这里对 请求参数的加工 需要抽取出去
		String uniqueUri = (String) params.get("interface_version")
				+ StringUtils.replace(request.getRequestURI(), "/", ".") + "."
				+ request.getMethod().toLowerCase();

		// uniqueUri = uniqueUri.substring(1, uniqueUri.length());

		logger.info(("前端唯一标识:" + uniqueUri));

		UniqueServiceDefined uniqueServiceDefined = new UniqueServiceDefined();
		try {

			uniqueServiceDefined = registerService
					.getRegisterService(uniqueUri);

			RestApiTimerManager restApiTimerManager = new RestApiTimerManager(
					dubboRemoteCall);

			result = restApiTimerManager.process(uniqueServiceDefined, params);

		} catch (Exception e) {
			throw e;
		}

		return result;
	}
}
