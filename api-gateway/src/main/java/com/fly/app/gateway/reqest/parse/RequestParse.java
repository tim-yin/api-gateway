package com.fly.app.gateway.reqest.parse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tim.yin
 * @date 2015年12月4日 下午2:51:12
 * @version 1.0
 * @Description:TODO
 */

public interface RequestParse {

	// 对不同的前端请求进行参数的转化
	public Map<String, Object> convertRequestParam(HttpServletRequest request)
			throws UnsupportedEncodingException, IOException,
			ClassNotFoundException;

}
