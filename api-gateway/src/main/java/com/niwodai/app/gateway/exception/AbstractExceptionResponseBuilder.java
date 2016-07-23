package com.niwodai.app.gateway.exception;

import java.util.List;

/**
 * @author tim.yin
 * @date 2015年12月09日 上午10:02:38
 * @version 1.0
 * @Description:TODO
 */

public abstract class AbstractExceptionResponseBuilder implements
		ExceptionResponseBuilder {

	@Override
	public boolean support(Throwable t) {
		final List<Class<? extends Throwable>> classes = exceptionType();
		for (final Class<?> c : classes) {
			if (c.isAssignableFrom(t.getClass())) {
				return true;
			}
		}
		return false;
	}

	public abstract List<Class<? extends Throwable>> exceptionType();

}
