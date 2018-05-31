package com.fly.app.gateway.route.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * @author tim.yin
 * @date 2015年11月28日 下午8:56:36
 * @version 1.0
 * @Description:后台暴露的聚合服务定义 跟dubbo保持一致，确定唯一服务 由 group+interface name +version 确定
 */
public class UniqueServiceDefined {

	// service reference group
	private String serviceGroup;

	// service reference interface
	private String serviceInterfaceName;

	// service reference version
	private String serviceVersion;

	// call method
	private String serviceMethod;

	// input dto
	private String serviceInputDto;

	// enum input support normal param type sign "," such as linux IFS.
	private String serviceEnumInput;

	public String getServiceGroup() {
		return serviceGroup;
	}

	public void setServiceGroup(String serviceGroup) {
		this.serviceGroup = serviceGroup;
	}

	public String getServiceInterfaceName() {
		return serviceInterfaceName;
	}

	public void setServiceInterfaceName(String serviceInterfaceName) {
		this.serviceInterfaceName = serviceInterfaceName;
	}

	public String getServiceVersion() {
		return serviceVersion;
	}

	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}

	public String getServiceMethod() {
		return serviceMethod;
	}

	public void setServiceMethod(String serviceMethod) {
		this.serviceMethod = serviceMethod;
	}

	public String getServiceInputDto() {
		return serviceInputDto;
	}

	public void setServiceInputDto(String serviceInputDto) {
		this.serviceInputDto = serviceInputDto;
	}

	public String getServiceEnumInput() {
		return serviceEnumInput;
	}

	public void setServiceEnumInput(String serviceEnumInput) {
		this.serviceEnumInput = serviceEnumInput;
	}

	public UniqueServiceDefined() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UniqueServiceDefined(String serviceGroup,
			String serviceInterfaceName, String serviceVersion,
			String serviceMethod, String serviceInputDto,
			String serviceEnumInput) {
		super();
		this.serviceGroup = serviceGroup;
		this.serviceInterfaceName = serviceInterfaceName;
		this.serviceVersion = serviceVersion;
		this.serviceMethod = serviceMethod;
		this.serviceInputDto = serviceInputDto;
		this.serviceEnumInput = serviceEnumInput;
	}

	public String toString() {
		return "后台暴露的聚合服务:" + new ReflectionToStringBuilder(this).toString();
	}
}
