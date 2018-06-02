package com.chandra.microservice.v1.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.chandra.microservice.v1.api.util.ApiUtility;
import com.chandra.microservice.v1.model.InlineResponse400;

/**
 * This class is used to handle customer exception using
 * ResponseEntityExceptionHandler
 * 
 * @author Chandra
 *
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * This method is to handle any internal server error while processing the request.
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler({ UserServiceException.class })
	public ResponseEntity<InlineResponse400> handleUserServiceException(Exception ex, WebRequest request) {
		return ApiUtility.prepareErrorResponse(ex.getMessage(), 105, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}