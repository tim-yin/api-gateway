package com.niwodai.app.gateway.exception;

/**
 * @author tim.yin
 * @date 2015年12月10日 下午12:40:21
 * @version 1.0
 * @Description:TODO
 */

public class BusinessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusinessException() {

	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Exception e) {
		super(e);
	}

}
