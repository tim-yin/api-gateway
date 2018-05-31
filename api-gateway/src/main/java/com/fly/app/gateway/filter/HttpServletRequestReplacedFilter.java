package com.fly.app.gateway.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author tim.yin
 * @date 2015年12月7日 上午10:39:42
 * @version 1.0
 * @Description:处理 request getReader 缓冲流不能重复读取的问题
 */
@Component
public class HttpServletRequestReplacedFilter implements Filter {

	private String[] exclusiveURIs;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String exclusiveURI = filterConfig.getInitParameter("exclusive");
		if (StringUtils.isNotEmpty(exclusiveURI)) {
			exclusiveURIs = exclusiveURI.split(",");
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		ServletRequest requestWrapper = null;
		if (request instanceof HttpServletRequest) {
			if (ArrayUtils.isNotEmpty(exclusiveURIs)) {
				String requestURI = ((HttpServletRequest) request)
						.getRequestURI();
				for (String uri : exclusiveURIs) {
					if (requestURI.contains(uri))
						break;
					requestWrapper = doWrapper((HttpServletRequest) request);
				}
			} else {
				requestWrapper = doWrapper((HttpServletRequest) request);
			}
		}
		if (null == requestWrapper) {
			chain.doFilter(request, response);
		} else {
			chain.doFilter(requestWrapper, response);
		}
	}

	private WrappedRequest doWrapper(HttpServletRequest request)
			throws IOException {
		return new WrappedRequest(request);
	}

	@Override
	public void destroy() {
	}

}
