package com.fly.app.gateway.remote.call.util;

import java.util.List;
import java.util.Map;

/**
 * @author tim.yin
 * @date 2015年12月2日 下午3:07:37
 * @version 1.0
 * @Description:聚合服务返回结果帮助类
 */

public final class DubboResultHelper {

	// 工具类全部不能实例化
	private DubboResultHelper() {

	}

	@SuppressWarnings("unchecked")
	public static Object handlerResultSet(Object sourceDataSet) {

		if (sourceDataSet instanceof Map) {

			if (judgeRemoteReturnPojoClass(sourceDataSet)) {
				return remotePojoClassFilter(sourceDataSet);
			}

		}
		return ((Map<String, Object>) sourceDataSet);

	}

	// @SuppressWarnings("unchecked")
	private static boolean judgeRemoteReturnPojoClass(Object result) {
		Object obj = ((Map<String, Object>) result).get("result");

		if (obj instanceof Map) {
			return (((Map<String, Object>) obj).containsKey("class"));
		} else if (obj instanceof List) {
			List<Object> oj = (List<Object>) obj;
			// 防止list 里面嵌套list 结构
			if (oj.get(0) instanceof Map)
				return (((Map<String, Object>) (oj.get(0)))
						.containsKey("class"));
		}
		return false;

	}

	// 聚合服务返回pojo 最终我们拿到的结果也会转换为map e.g:{name=tim.yin, age=30,
	// class=com.fly.fortune.endpoint.impl.MockAggregationServiceApiImpl$pojo}
	@SuppressWarnings("unchecked")
	private static Object remotePojoClassFilter(Object object) {
		Object obj = ((Map<String, Object>) object).get("result");

		if (obj instanceof Map) {
			((Map<String, Object>) obj).remove("class");
			return object;
		} else if (obj instanceof List) {
			List<Object> ojs = (List<Object>) obj;
			if (ojs.get(0) instanceof Map) {
				for (Object oj : ojs) {
					((Map<String, Object>) oj).remove("class");
				}
				return object;
			}
		}

		return object;
	}
}
