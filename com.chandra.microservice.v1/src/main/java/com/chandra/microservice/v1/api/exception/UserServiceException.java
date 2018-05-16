package com.chandra.microservice.v1.api.exception;

import java.util.List;

/**
 * This is custom exception class to handle the error message.
 * 
 * @author Chandra
 *
 */
public class UserServiceException extends Exception {

	private static final long serialVersionUID = 1L;
	private int errorCode;
	private String message;
	private List<Error> errors;

	public UserServiceException() {
		super();
	}
	
	/**
	 * @return the message
	 */
	@Override
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}
	

	/**
	 * @param message
	 * @param th
	 * @param errorCode
	 */
	public UserServiceException(String message, Throwable th, int errorCode) {
		super(message, th);
		this.message = message;
		this.errorCode = errorCode;
	}

	/**
	 * @return
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
