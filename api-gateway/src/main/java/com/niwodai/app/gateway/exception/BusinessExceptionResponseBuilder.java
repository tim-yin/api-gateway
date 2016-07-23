package com.niwodai.app.gateway.exception;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.niwodai.app.gateway.exception.enums.ResponseCode;

/**
 * @author tim.yin
 * @date 2015年12月10日 上午11:51:29
 * @version 1.0
 * @Description:TODO
 */
@Component
public class BusinessExceptionResponseBuilder extends
		AbstractExceptionResponseBuilder {

	private static final List<Class<? extends Throwable>> CLASSES = Collections
			.<Class<? extends Throwable>> singletonList(BusinessException.class);

	@Override
	public ExceptionResponse buildExceptionResponse(Throwable t) {

		return new ExceptionResponse(ResponseCode.ERROR.getCode(),
				t.getMessage());
	}

	@Override
	public List<Class<? extends Throwable>> exceptionType() {
		return CLASSES;
	}
}
