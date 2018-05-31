package com.fly.app.gateway.security.common_validator.chain;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author tim.yin
 * @date 2015年11月30日 上午11:58:13
 * @version 1.0
 * @Description:TODO
 */

@Component
public class CheckSignValidator extends Validator {

	private static Logger logger = LoggerFactory
			.getLogger(CheckSignValidator.class);

	@Override
	public boolean requestHandler(Map<String, String> params) throws Exception {
		// TODO log.info("验签校验开始............");
		return true;
	}

}