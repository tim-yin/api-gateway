package com.fly.app.gateway.route.impl;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import com.fly.app.gateway.route.dto.UniqueServiceDefined;
import com.fly.app.gateway.route.dto.UriServiceMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.fly.app.gateway.exception.NoSuchRouteServiceException;
import com.fly.app.gateway.route.RegisterService;

/**
 * @author tim.yin
 * @date 2016年1月4日 下午4:55:45
 * @version 1.0
 * @Description:TODO
 */
@Component
@Configuration
public class RegisterServiceImpl implements RegisterService {

	private static Logger logger = LoggerFactory
			.getLogger(RegisterServiceImpl.class);

	static ConcurrentHashMap<String, UniqueServiceDefined> mapping = new ConcurrentHashMap<String, UniqueServiceDefined>();

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

	@PostConstruct
	public void init() throws Exception {
		logger.info("容器启动时加载文件[routeRule.json],构造对应映射map,开始加载.....");
		List<UriServiceMapping> serviceList = Lists.newArrayList();
		Set<UniqueServiceDefined> initProxySet = new HashSet<UniqueServiceDefined>();

		serviceList = loadAllExposeService();

		for (UriServiceMapping u : serviceList) {
			mapping.putIfAbsent(u.getServiceVersion() + "." + u.getUri() + "."
					+ u.getRequestMethod(), u);
			initProxySet.add(new UniqueServiceDefined(u.getServiceGroup(), u
					.getServiceInterfaceName(), u.getServiceVersion(), u
					.getServiceMethod(), u.getServiceInputDto(), u
					.getServiceEnumInput()));
		}

		logger.info("容器到注册中心订阅服务开始.....");
		initDubboSubscribe(initProxySet);
		logger.info("容器到注册中心订阅服务结束.....");

	}

	private List<UriServiceMapping> loadAllExposeService() throws Exception {
		logger.info("初始化开始加载所有暴露的服务.....");
		List<UriServiceMapping> serviceList = Lists.newArrayList();

		try {
			serviceList = Arrays.asList(new ObjectMapper().readValue(new File(
					"/opt/routeRule.json"), UriServiceMapping[].class));
		} catch (Exception e) {
			logger.info("转换发生异常:" + e.getMessage());
			throw new Exception(e);
		}
		logger.info("初始化加载所有暴露的服务成功完成.....");
		return serviceList;
	}

	// 这里以后可能还需要扩展 目前阶段在容器启动的时候进行服务的订阅 api网关不涉及到服务的注册
	private void initDubboSubscribe(Set<UniqueServiceDefined> initProxySet)
			throws ClassNotFoundException {

		// 当前应用配置
		ApplicationConfig application = new ApplicationConfig();
		application.setName(applicationName);

		RegistryConfig registry = new RegistryConfig();
		registry.setProtocol(registryProtocol);
		registry.setAddress(registryAddress);
		registry.setClient(registryClient);
		registry.setTimeout(registryTimeout);

		for (UniqueServiceDefined uniqueServiceDefined : initProxySet) {
			ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();

			reference.setApplication(application);
			reference.setRegistry(registry);
			// reference.setGroup(uniqueServiceDefined.getServiceGroup());
			reference.setInterface(uniqueServiceDefined
					.getServiceInterfaceName());
			reference.setGeneric(true);
			reference.setCheck(false);
			reference.setVersion(uniqueServiceDefined.getServiceVersion());

			ReferenceConfigCache cache = ReferenceConfigCache.getCache();
			cache.get(reference);
		}

	}

	@Override
	public void register(String requestSign,
			UniqueServiceDefined uniqueServiceDefined)
			throws ClassNotFoundException {
		Assert.hasText(requestSign, "前端唯一标识不为空！");
		Assert.notNull(uniqueServiceDefined, "后台映射服务不能为空！");

		uniqueServiceDefined = mapping.get(requestSign);

		Set<UniqueServiceDefined> initProxySet = new HashSet<UniqueServiceDefined>();
		initProxySet.add(uniqueServiceDefined);
		if (!mapping.containsKey(requestSign)) {
			mapping.put(requestSign, uniqueServiceDefined);
			initDubboSubscribe(initProxySet);
		}

	}

	@Override
	public UniqueServiceDefined getRegisterService(String requestSign)
			throws NoSuchRouteServiceException {
		UniqueServiceDefined ud = mapping.get(requestSign);
		if (ud == null) {
			logger.info("前端唯一标识 named '" + requestSign + "' 没有映射的后台服务");
			throw new NoSuchRouteServiceException(requestSign);
		}
		return ud;
	}

}
