package com.niwodai.app.api_gateway;

import static org.junit.Assert.assertArrayEquals;

import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.niwodai.app.gateway.reqest.parse.utils.MethodUtil;

/**
 * @author tim.yin
 * @date 2015年12月30日 下午4:04:14
 * @version 1.0
 * @Description:TODO
 */

public class JavassistTest {

	@Test
	public void methodTest() throws Exception {

		// Method method = Class
		// .forName(
		// "com.niwodai.fortune.endpoint.impl.MockAggregationServiceApiImpl")
		// .getDeclaredMethods()[1];

		Method method = TestClass.class.getDeclaredMethods()[0];
		String[] paramaterName = MethodUtil.getAllParamaterName(method);
		Class<?>[] type = method.getParameterTypes();

		List<String> list = Lists.newArrayList();
		for (int i = 0; i < method.getParameterTypes().length; i++) {
			list.add(type[i].getName());
		}
		assertArrayEquals(paramaterName, new String[] { "name", "age",
				"address" });
	}
}
