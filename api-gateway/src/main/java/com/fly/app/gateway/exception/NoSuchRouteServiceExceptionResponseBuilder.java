package com.fly.app.gateway.exception;

import java.util.Collections;
import java.util.List;

import com.fly.app.gateway.exception.enums.ResponseCode;
import org.springframework.stereotype.Component;

/**
 * @author tim.yin
 * @date 2016年1月5日 上午10:02:12
 * @version 1.0
 * @Description:TODO
 */
@Component
public class NoSuchRouteServiceExceptionResponseBuilder extends
		AbstractExceptionResponseBuilder {

	private static final List<Class<? extends Throwable>> CLASSES = Collections
			.<Class<? extends Throwable>> singletonList(NoSuchRouteServiceException.class);

	@Override
	public ExceptionResponse buildExceptionResponse(Throwable t) {

		return new ExceptionResponse(
				ResponseCode.ROUTESERVICEFAILURE.getCode(), t.getMessage());
	}

	@Override
	public List<Class<? extends Throwable>> exceptionType() {
		return CLASSES;
	}

}
