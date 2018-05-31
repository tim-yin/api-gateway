package com.fly.app.gateway.exception;

/**
 * @author tim.yin
 * @date 2015年12月09日 上午10:12:45
 * @version 1.0
 * @Description:TODO
 */

public final class ExceptionResponse {

	private String errorCode;// 响应code:100：处理失败;200:处理成功;401:验签失败
	private String message;// 错误信息

	public ExceptionResponse() {

	}

	public ExceptionResponse(String errorCode , String message) {
		super();
		this.errorCode  = errorCode;
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
