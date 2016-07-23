package com.niwodai.app.gateway.route;

import com.niwodai.app.gateway.exception.NoSuchRouteServiceException;
import com.niwodai.app.gateway.route.dto.UniqueServiceDefined;

/**
 * @author tim.yin
 * @date 2016年1月4日 下午4:55:05
 * @version 1.0
 * @Description:TODO
 */

public interface RegisterService {

	public void register(String requestSign,
			UniqueServiceDefined uniqueServiceDefined)
			throws ClassNotFoundException;

	public UniqueServiceDefined getRegisterService(String requestSign)
			throws NoSuchRouteServiceException;

}
