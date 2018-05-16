package com.chandra.microservice.v1.api.interceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * This is the custom class to intercept request from payload.
 * 
 * @author Chandra
 *
 */
@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {

		String requestURI = request.getRequestURI();
		// Ignoring healthcheck logging
		if (!requestURI.contains("healthcheck")) {
			logger.info("----------------- REQUEST DETAILS - START ----------------------------");
			final StringBuilder logMessage = new StringBuilder(" @@@@ REST REQUEST @@@@ ").append("[HTTP METHOD:").append(request.getMethod())
					.append("] [PATH INFO:").append(requestURI).append("] [HEADER PARAMETERS:").append(this.getHeaderParamsMap(request).toString())
					.append("] [REQUEST PARAMETERS:").append(this.getRequestParamsMap(request).toString())
					// .append("] [REQUEST BODY:")
					// .append(new
					// BufferedRequestWrapper(request).getRequestBody())
					.append("] [REMOTE ADDRESS:").append(request.getRemoteAddr()).append("]");
			logger.info("REST Message : ", logMessage.toString());
			logger.info("------------------REQUEST DETAILS - END  ---------------------------");
		}
		return true;
	}

	/**
	 * This method is to iterate header params and put in Hashmap.
	 * 
	 * @param request
	 * @return Map<String, String>
	 */
	private Map<String, String> getHeaderParamsMap(HttpServletRequest request) {

		Map<String, String> headerParamsMap = new HashMap<String, String>();
		Enumeration<String> headerParamNames = request.getHeaderNames();
		while (headerParamNames.hasMoreElements()) {
			String requestParamName = (String) headerParamNames.nextElement();
			String requestParamValue = request.getHeader(requestParamName);
			headerParamsMap.put(requestParamName, requestParamValue);
		}
		return headerParamsMap;
	}

	/**
	 * This method is to iterate request params and put in Hashmap.
	 * 
	 * @param request
	 * @return Map<String, String>
	 */
	private Map<String, String> getRequestParamsMap(HttpServletRequest request) {

		Map<String, String> requestParamMap = new HashMap<String, String>();
		Enumeration<String> requestParamNames = request.getParameterNames();
		while (requestParamNames.hasMoreElements()) {
			String requestParamName = (String) requestParamNames.nextElement();
			String requestParamValue = request.getParameter(requestParamName);
			requestParamMap.put(requestParamName, requestParamValue);
		}
		return requestParamMap;
	}
}
