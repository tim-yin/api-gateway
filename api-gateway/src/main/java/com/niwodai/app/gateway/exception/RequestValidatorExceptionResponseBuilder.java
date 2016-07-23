package com.niwodai.app.gateway.exception;

import java.util.Collections;
import java.util.List;

import com.niwodai.app.gateway.exception.enums.ResponseCode;

/**
 * @author tim.yin
 * @date 2016年1月5日 下午5:20:48
 * @version 1.0
 * @Description:TODO
 */

public class RequestValidatorExceptionResponseBuilder extends
		AbstractExceptionResponseBuilder {

	private static final List<Class<? extends Throwable>> CLASSES = Collections
			.<Class<? extends Throwable>> singletonList(RequestValidatorException.class);

	@Override
	public ExceptionResponse buildExceptionResponse(Throwable t) {

		return new ExceptionResponse(
				ResponseCode.REQUESTVALIDATORFAILURE.getCode(), t.getMessage());
	}

	@Override
	public List<Class<? extends Throwable>> exceptionType() {
		return CLASSES;
	}

}
