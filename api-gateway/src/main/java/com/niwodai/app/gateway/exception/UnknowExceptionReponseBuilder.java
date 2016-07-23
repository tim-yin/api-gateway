package com.niwodai.app.gateway.exception;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.niwodai.app.gateway.exception.enums.ResponseCode;

/**
 * @author tim.yin
 * @date 2015年12月10日 下午12:43:03
 * @version 1.0
 * @Description:未知异常
 */

@Component
public class UnknowExceptionReponseBuilder extends
		AbstractExceptionResponseBuilder {

	private static final List<Class<? extends Throwable>> CLASSES = Collections
			.<Class<? extends Throwable>> singletonList(Throwable.class);

	private static final String errorMsg = "系统繁忙,请稍后再试";

	@Override
	public ExceptionResponse buildExceptionResponse(Throwable t) {
		return new ExceptionResponse(ResponseCode.ERROR.getCode(), errorMsg);
	}

	@Override
	public List<Class<? extends Throwable>> exceptionType() {
		return CLASSES;
	}

}
