package com.chandra.microservice.v1.api.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * This class is to intercept the HTTP requests coming to API.
 * 
 * @author Chandra
 *
 */
@Configuration
public class RequestConfig extends WebMvcConfigurerAdapter {

	@Autowired
	RequestInterceptor requestInterceptor;

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(requestInterceptor);
	}
}
