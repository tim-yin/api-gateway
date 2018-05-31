package com.fly.app.gateway.remote.call.impl;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.fly.app.gateway.remote.call.util.DubboResultHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericException;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.google.common.collect.Lists;
import com.fly.app.gateway.exception.BusinessException;
import com.fly.app.gateway.remote.call.DubboRemoteCall;
import com.fly.app.gateway.route.dto.UniqueServiceDefined;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;

/**
 * @author tim.yin
 * @date 2015年12月2日 下午2:55:32
 * @version 1.0
 * @Description:TODO
 */
@Component
public class RemoteDubboCallComposite implements DubboRemoteCall {

	private static Logger logger = LoggerFactory
			.getLogger(RemoteDubboCallComposite.class);

	@Value("${api.gateway.dubbo.application.name}")
	String applicationName;

	@Value("${api.gateway.dubbo.registry.protocol}")
	String registryProtocol;

	@Value("${api.gateway.dubbo.registry.address}")
	String registryAddress;

	@Value("${api.gateway.dubbo.registry.client}")
	String registryClient;

	@Value("${api.gateway.dubbo.registry.timeout}")
	Integer registryTimeout;

	public Object call(UniqueServiceDefined uniqueServiceDefined,
			Map<String, Object> params) throws ClassNotFoundException,
			BusinessException {

		ApplicationConfig application = new ApplicationConfig();
		application.setName(applicationName);

		RegistryConfig registry = new RegistryConfig();
		registry.setProtocol(registryProtocol);
		registry.setAddress(registryAddress);
		registry.setClient(registryClient);
		registry.setTimeout(registryTimeout);

		ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
		reference.setApplication(application);
		reference.setRegistry(registry);
		reference.setInterface(uniqueServiceDefined.getServiceInterfaceName());
		reference.setGeneric(true);
		reference.setCheck(false);
		reference.setVersion(uniqueServiceDefined.getServiceVersion());
		ReferenceConfigCache cache = ReferenceConfigCache.getCache();

		long beforeTime = System.currentTimeMillis();
		GenericService genericService = cache.get(reference);
		long endTime = System.currentTimeMillis();
		logger.info("代理接口{},获取Reference代理对象耗时{}", new Object[] {
				uniqueServiceDefined.getServiceInterfaceName(),
				endTime - beforeTime });

		// 参数类型
		String[] targetParamType = {};
		Object[] targetParamValue = {};

		List<String> paramTypeList = Lists.newArrayList();
		List<Object> paramValueList = Lists.newArrayList();

		// 输入input 类型
		if (!StringUtils.isEmpty(uniqueServiceDefined.getServiceInputDto())) {
			paramTypeList.add(uniqueServiceDefined.getServiceInputDto());
			params.put("class", uniqueServiceDefined.getServiceInputDto());
			paramValueList.add(params);

		}
		// 输入普通多参 input 类型
		else if (!StringUtils.isEmpty(uniqueServiceDefined
				.getServiceEnumInput())) {

			Method[] methods = Class.forName(
					uniqueServiceDefined.getServiceInterfaceName())
					.getDeclaredMethods();
			for (Method m : methods) {
				if (m.getName().equals(uniqueServiceDefined.getServiceMethod())) {

					Class<?>[] type = m.getParameterTypes();
					for (int i = 0; i < m.getParameterTypes().length; i++) {
						paramTypeList.add(type[i].getName());
					}
					break;
				}

			}

			for (String str : uniqueServiceDefined.getServiceEnumInput().split(
					",")) {
				paramValueList.add(params.get(str));
			}

		}
		// 输入map类型
		else {
			Method[] methods = Class.forName(
					uniqueServiceDefined.getServiceInterfaceName())
					.getDeclaredMethods();
			for (Method m : methods) {
				if (m.getName().equals(uniqueServiceDefined.getServiceMethod())) {

					Class<?>[] type = m.getParameterTypes();
					if (type.length > 0) {
						paramTypeList.add("java.util.Map");
						paramValueList.add(params);
					}
					// 否则无参调用，不用做任何处理！
				}
			}
		}

		targetParamType = (String[]) paramTypeList
				.toArray(new String[paramTypeList.size()]);
		targetParamValue = (Object[]) paramValueList
				.toArray(new Object[paramValueList.size()]);

		Object remoteDataSet = new Object();
		try {
			logger.info("method name:"
					+ uniqueServiceDefined.getServiceMethod());
			// 目前支持 输入类型 包括 1.java.util.Map 2.多参 3.inputDTO 三者是互斥的！
			remoteDataSet = genericService.$invoke(
					uniqueServiceDefined.getServiceMethod(), targetParamType,
					targetParamValue);
		} catch (GenericException e) {
			throw new BusinessException("rpc 远程调用异常！");
		}
		Object targetDataSet = DubboResultHelper
				.handlerResultSet(remoteDataSet);
		try {
			String resultValue = JSONMapper.toJSON(targetDataSet).render(true);
			logger.info("最终输出json字符串:" + resultValue);
		} catch (MapperException e) {
			logger.error("转换json数据格式出现异常！");
		}
		return targetDataSet;
	}
}
