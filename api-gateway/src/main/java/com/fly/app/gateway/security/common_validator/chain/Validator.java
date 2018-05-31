package com.fly.app.gateway.security.common_validator.chain;

import java.util.Map;

/**
 * @author tim.yin
 * @date 2015年11月30日 上午11:38:21
 * @version 1.0
 * @Description:TODO
 */

public abstract class Validator {
	
	 /**
    * 持有后继的责任对象
    */
   protected Validator successor;

   public abstract  boolean  requestHandler(Map<String,String> params) throws Exception;

	public void setSuccessor(Validator successor) {
		this.successor = successor;
	}

	public Validator getSuccessor() {
		return successor;
	}
}
