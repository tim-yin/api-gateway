package com.niwodai.app.gateway.exception;

/**
 * @author tim.yin
 * @date 2015年12月09日 上午09:50:14
 * @version 1.0
 * @Description:TODO
 */

public interface ExceptionResponseBuilder {

	boolean support(Throwable t);

	ExceptionResponse buildExceptionResponse(Throwable t);

}
