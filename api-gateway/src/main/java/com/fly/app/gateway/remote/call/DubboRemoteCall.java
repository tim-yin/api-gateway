package com.fly.app.gateway.remote.call;

import java.util.Map;

import com.fly.app.gateway.exception.BusinessException;
import com.fly.app.gateway.route.dto.UniqueServiceDefined;

/**
 * @author tim.yin
 * @date 2015年12月2日 下午2:56:26
 * @version 1.0
 * @Description:dubbo 远程调用接口
 */

public interface DubboRemoteCall {

	/**
	 * 
	 * @param uniqueServiceDefined
	 *            服务定义
	 * @param params
	 *            解析出来的请求参数
	 * @return
	 * @throws ClassNotFoundException
	 */
	public Object call(UniqueServiceDefined uniqueServiceDefined,
			Map<String, Object> params) throws ClassNotFoundException,
			BusinessException;

}
