package com.niwodai.app.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.niwodai.app.gateway.security.common_validator.ValidatorInterceptor;

/**
 * @author tim.yin
 * @date 2015年11月28日 下午4:28:03
 * @version 1.0
 * @Description:app-gateway 启动类.
 */

@Configuration
@ComponentScan(basePackages = "com.niwodai.app.gateway")
@EnableAutoConfiguration
// 加入spring的bean的xml文件
@ImportResource({ "classpath*:/spring/*.xml" })
public class ApplicationBootstrap extends WebMvcConfigurerAdapter {

	@Autowired
	ValidatorInterceptor validatorInterceptor;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ApplicationBootstrap.class, args);
	}

	/**
	 * 配置数据验签拦截器
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(validatorInterceptor).addPathPatterns("/**");
	}

	/*
	 * 过滤 pojo 属性里面的null 属性
	 * 
	 * @Bean public HttpMessageConverters customConverters() { ObjectMapper
	 * jsonNullConvertToEmpty = new JsonNullConvertToEmpty();
	 * HttpMessageConverter<?> additional = new
	 * MappingJackson2HttpMessageConverter( jsonNullConvertToEmpty); return new
	 * HttpMessageConverters(additional); }
	 */

	/*
	 * @Override public void addResourceHandlers(ResourceHandlerRegistry
	 * registry) { super.addResourceHandlers(registry);
	 * registry.addResourceHandler("/**").addResourceLocations(
	 * "classpath:/spring/routeRule.properties"); }
	 */
}