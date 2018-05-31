package com.fly.app.gateway.security.common_validator.chain;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.fly.app.gateway.exception.RequestValidatorException;

/**
 * @author tim.yin
 * @date 2015年11月30日 上午11:53:52
 * @version 1.0
 * @Description:TODO
 */
@Component
public class RequestParameterValidator extends Validator {

	private static Logger logger = LoggerFactory
			.getLogger(RequestParameterValidator.class);

	// 校验的非空白名单属性
	private static List<String> whiteList = Lists.newArrayList("app_version",
			"version", "interface_version", "channel", "current_time", "sign");

	@Override
	public boolean requestHandler(Map<String, String> params) throws Exception {
		// log.info("参数完整性校验开始............");
		List<String> paramList = Lists.newArrayList();
		for (Entry<String, String> entry : params.entrySet()) {
			String name = (String) entry.getKey();
			String value = entry.getValue();
			if (whiteList.contains(name)) {
				if (StringUtils.isEmpty(value)) {
					String errorMsg = "验证必填字段:" + name + "对应值为空,不能通过拦截器验证！";
					logger.info(errorMsg);
					throw new RequestValidatorException(errorMsg);
				}
			}
			paramList.add(name);
		}

		@SuppressWarnings("unchecked")
		List<String> c = (List<String>) CollectionUtils.subtract(whiteList,
				paramList);
		if (0 != c.size()) {
			String errorMsg = "客户端缺失如下必填字段:"
					+ java.util.Arrays.deepToString(c.toArray())
					+ ",不能通过拦截器验证！";
			logger.info(errorMsg);
			throw new RequestValidatorException(errorMsg);
		}

		if (getSuccessor() != null) {
			getSuccessor().requestHandler(params);
		}
		return true;
	}

}
