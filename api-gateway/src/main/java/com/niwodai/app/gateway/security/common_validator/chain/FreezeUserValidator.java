package com.niwodai.app.gateway.security.common_validator.chain;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author tim.yin
 * @date 2015年11月30日 上午11:40:56
 * @version 1.0
 * @Description:验证用户uid对应是否冻结账户-(如果uid为空,不进行这个环节的验证.)
 */
@Component
public class FreezeUserValidator extends Validator{
	
	
	private static Logger logger = LoggerFactory
			.getLogger(FreezeUserValidator.class);
	
	private static final String UID = "uid";

	@Override
	public boolean requestHandler(Map<String, String> params) throws Exception {
		for (Entry<String, String> entry : params.entrySet()) {
			if (UID.equals(entry.getKey())) {
				if (!StringUtils.isEmpty(entry.getValue())) {
					logger.info("uid:"+entry.getValue());
					//TODO 这里调用 判断是否黑名单的边缘服务 
				}
			}
		}
		if (getSuccessor() != null) {
			getSuccessor().requestHandler(params);
		}
		return true;
	}

}
