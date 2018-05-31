package com.fly.app.gateway.exception;

/**
 * @author tim.yin
 * @date 2016年1月5日 上午9:59:52
 * @version 1.0
 * @Description:根据前端唯一标识路由不到后台服务异常
 */

public class NoSuchRouteServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSuchRouteServiceException() {

	}

	public NoSuchRouteServiceException(String message) {
		super("前端唯一标识 named '" + message + "' 没有映射的后台服务");
	}

	public NoSuchRouteServiceException(Exception e) {
		super(e);
	}

}
