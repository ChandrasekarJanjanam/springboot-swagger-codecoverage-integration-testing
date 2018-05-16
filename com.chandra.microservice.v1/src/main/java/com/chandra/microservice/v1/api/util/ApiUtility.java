package com.chandra.microservice.v1.api.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.chandra.microservice.v1.model.Error;
import com.chandra.microservice.v1.model.InlineResponse400;
import com.chandra.microservice.v1.model.InlineResponse400Result;

/**
 * This is utility class used across API orchestration.
 * 
 * @author Chandra
 *
 */
public class ApiUtility {

	private static final Logger logger = LoggerFactory.getLogger(ApiUtility.class);
	
	/**
	 * private constructor
	 */
	private ApiUtility(){
    	throw new IllegalStateException("ApiUtility class");
    }
	
	/**
	 * This method is to prepare error response message for request.
	 * 
	 * @param errormessage
	 * @param errorCode
	 * @param httpCode
	 * @return
	 */
	public static ResponseEntity<InlineResponse400> prepareErrorResponse(String errormessage, int errorCode, HttpStatus httpCode) {

		InlineResponse400 erroResp = new InlineResponse400();
		List<Error> errorsList = new ArrayList<Error>();
		InlineResponse400Result response400Result = new InlineResponse400Result();
		Error errorDetail = new Error();
		errorDetail.setMessage(errormessage);
		errorDetail.setCode(errorCode);
		errorDetail.setFields(errormessage);
		errorsList.add(errorDetail);
		response400Result.setErrors(errorsList);
		erroResp.setResult(response400Result);

		logger.debug("Error response prepared");
		
		return new ResponseEntity<InlineResponse400>(erroResp, httpCode);
		
	}

}
