package com.niwodai.app.gateway.route.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * @author tim.yin
 * @date 2015年11月29日 下午2:05:24
 * @version 1.0
 * @Description:对应mapping.json 的配置
 */

public class UriServiceMapping extends UniqueServiceDefined {

	private String uri;

	private String requestMethod;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public UriServiceMapping() {
		super();
	}

	public UriServiceMapping(String uri, String requestMethod) {
		super();
		this.uri = uri;
		this.requestMethod = requestMethod;
	}

	public String toString() {
		return "映射参数:" + new ReflectionToStringBuilder(this).toString();
	}

}
