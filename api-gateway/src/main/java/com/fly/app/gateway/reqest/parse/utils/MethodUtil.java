package com.fly.app.gateway.reqest.parse.utils;

import java.lang.reflect.Method;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

/**
 * @author tim.yin
 * @date 2015年12月30日 下午4:04:32
 * @version 1.0
 * @Description:TODO
 */

public class MethodUtil {

	public static String[] getAllParamaterName(Method method)
			throws NotFoundException {
		Class<?> clazz = method.getDeclaringClass();
		ClassPool pool = ClassPool.getDefault();
		CtClass clz = pool.get(clazz.getName());
		CtClass[] params = new CtClass[method.getParameterTypes().length];
		for (int i = 0; i < method.getParameterTypes().length; i++) {
			params[i] = pool
					.getCtClass(method.getParameterTypes()[i].getName());
		}
		CtMethod cm = clz.getDeclaredMethod(method.getName(), params);
		MethodInfo methodInfo = cm.getMethodInfo();
		CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
		LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
				.getAttribute(LocalVariableAttribute.tag);
		int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
		String[] paramNames = new String[cm.getParameterTypes().length];
		for (int i = 0; i < paramNames.length; i++) {
			paramNames[i] = attr.variableName(i + pos);
		}
		return paramNames;
	}

}