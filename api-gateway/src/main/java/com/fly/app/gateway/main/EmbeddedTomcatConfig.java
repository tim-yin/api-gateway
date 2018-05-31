package com.fly.app.gateway.main;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.stereotype.Component;

/**
 * @author tim.yin
 * @date 2015年12月1日 下午2:15:51
 * @version 1.0
 * @Description:TODO
 */
@Component
public class EmbeddedTomcatConfig implements EmbeddedServletContainerCustomizer{
	
	@Value("${server.port}")
	int serverPort;
	
	@Value("${servlet.container.maxThreads}")
	String maxThreads;
	
	@Value("${servlet.container.minSpareThreads}")
	String minSpareThreads;
	
	@Value("${servlet.container.connectionTimeout}")
	String connectionTimeout;

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.setPort(serverPort);
		TomcatEmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory = (TomcatEmbeddedServletContainerFactory) container;
		tomcatEmbeddedServletContainerFactory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
			@Override
			public void customize(Connector connector) {
				//connector.setProperty("maxKeepAliveRequests", "1");
				connector.setProperty("connectionTimeout", connectionTimeout);
				connector.setEnableLookups(false);
				//connector.setProperty("keepAliveTimeout", "1");
				connector.setProperty("maxThreads", maxThreads);
				connector.setProperty("minSpareThreads", minSpareThreads);
				connector.setProtocol("org.apache.coyote.http11.Http11NioProtocol");
				connector.setProperty("compression", "on");
				connector.setProperty("compressionMinSize", "2048");
				connector.setURIEncoding("UTF-8");
				connector.setProperty("noCompressionUserAgents", "gozilla,traviata");
				connector.setProperty("compressableMimeType", "text/html,text/xml,text/javascript,application/javascript,text/css,text/plain");
			}
		});
		
	}

}
