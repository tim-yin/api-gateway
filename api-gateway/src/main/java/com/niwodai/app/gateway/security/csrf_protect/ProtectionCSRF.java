package com.niwodai.app.gateway.security.csrf_protect;

/**
 * @author tim.yin
 * @date 2015年11月30日 上午12:25:23
 * @version 1.0
 * @Description:TODO
 */

public interface ProtectionCSRF {
	
	//生成随机token
	public String generatorRandomToken() throws Exception;


}
