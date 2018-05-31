package com.fly.app.api_gateway;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author tim.yin
 * @date 2015年12月30日 下午1:14:06
 * @version 1.0
 * @Description:TODO
 */

public class ParamMappingTest {

	public static Class[] getMethodParamTypes(Object classInstance,
			String methodName) throws ClassNotFoundException {
		Class[] paramTypes = null;
		Method[] methods = classInstance.getClass().getMethods();// 全部方法
		for (int i = 0; i < methods.length; i++) {
			if (methodName.equals(methods[i].getName())) {// 和传入方法名匹配
				Class[] params = methods[i].getParameterTypes();
				Type[] type = methods[i].getGenericParameterTypes();
				// paramTypes = new Class[params.length];
				System.out.println(params[i].getName());
				System.out.println(type[i].getClass().getName());
				for (int j = 0; j < params.length; j++) {
					// paramTypes[j] = Class.forName(params[j].getName());
				}
				break;
			}
		}
		return paramTypes;
	}

	public void acquire(Long id, int age) {
		System.out.println(id);
	}

	public static void main(String[] args) {

		try {
			getMethodParamTypes(ParamMappingTest.class.newInstance(), "acquire");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * ParamMappingTest.class .newInstance() .getClass() .getDeclaredMethod(
		 * "方法名称", getMethodParamTypes( ParamMappingTest.class.newInstance(),
		 * "方法名称"));
		 */
	}

}
