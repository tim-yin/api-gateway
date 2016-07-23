package com.niwodai.app.gateway.exception;

/**
 * @author tim.yin
 * @date 2016年1月5日 下午5:19:15
 * @version 1.0
 * @Description:拦截器验证异常
 */

public class RequestValidatorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RequestValidatorException() {

	}

	public RequestValidatorException(String message) {
		super(message);
	}

	public RequestValidatorException(Exception e) {
		super(e);
	}

}
