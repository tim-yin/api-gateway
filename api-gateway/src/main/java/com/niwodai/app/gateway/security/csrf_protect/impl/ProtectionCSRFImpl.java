package com.niwodai.app.gateway.security.csrf_protect.impl;

/*import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.niwodai.app.gateway.security.csrf_protect.ProtectionCSRF;


/**
 * @author tim.yin
 * @date 2015年11月30日 上午12:31:30
 * @version 1.0
 * @Description:TODO
 */

public class ProtectionCSRFImpl implements ProtectionCSRF {
	
	private static Logger logger = LoggerFactory
			.getLogger(ProtectionCSRFImpl.class);
	

	@Override
	public String generatorRandomToken() throws Exception{
		
		
		/*SecureRandom sr;
		
		try 
		{
			sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
		}
		catch (NoSuchAlgorithmException e)
		{
			String err = "Failed to generate CSRFToken using SecureRandom"
					+ ", exceptionMessage=" + e.getMessage();
			logger.error(err);
			throw new Exception(err + ", exceptionMessage="
					+ e.getMessage());
		
		}
		catch (NoSuchProviderException e)
		{
			try 
			{
				sr = SecureRandom.getInstance("SHA1PRNG");
			} 
			catch (NoSuchAlgorithmException e1) {
				String err = "Failed to generate CSRFToken using SecureRandom"
						+ ", exceptionMessage=" + e1.getMessage();
				logger.error(err);
				throw new Exception(err + ", exceptionMessage="
						+ e1.getMessage());
			}
		}
		
		byte[] randomBytes = new byte[32];
		sr.nextBytes(randomBytes);		*/	
		return null;
		//return Base64.encode(randomBytes);
	}

}
