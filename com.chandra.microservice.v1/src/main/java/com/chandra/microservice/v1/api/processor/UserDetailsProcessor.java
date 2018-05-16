package com.chandra.microservice.v1.api.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.chandra.microservice.v1.api.exception.UserServiceException;
import com.chandra.microservice.v1.api.jpa.UserDetails;
import com.chandra.microservice.v1.api.jpa.UserDetailsDAO;
import com.chandra.microservice.v1.api.util.Timer;
import com.chandra.microservice.v1.model.UsersDetailsResponse;


/**
 * A class to test interactions with the MySQL database using the UserDetailsDao class.
 * Created by Chandra
 */
@Component
public class UserDetailsProcessor {

	private static final Logger logger = LoggerFactory.getLogger(UserDetailsProcessor.class);
	
	@Autowired
	UserDetailsDAO userDetailsDAO;
	
	/**
	 * @param applicationType
	 * @param xMessageId
	 * @param xAppCorrelationId
	 * @param usersDetailsResponse
	 * @return
	 * @throws UserServiceException
	 */
	public ResponseEntity<Boolean> saveUserDetails(String xOrganisationId, String xMessageId, String xAppCorrelationId, 
			UsersDetailsResponse usersDetailsResponse)
			throws UserServiceException {

		final String METHOD_NAME = "saveUserDetails";
		logger.info(METHOD_NAME, "{} Start");
		Timer timer = new Timer("saveUserDetails", "saveUserDetails");
		boolean isUserAdded = false;
		try {
			UserDetails user = new UserDetails(usersDetailsResponse.getUserid(), 
					usersDetailsResponse.getUsername(), usersDetailsResponse.getGender(), usersDetailsResponse.getOccupation());
			userDetailsDAO.save(user);
			isUserAdded = true;

		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			throw new UserServiceException(ex.getLocalizedMessage(), ex, 500);
		}
		logger.info(METHOD_NAME, "{} End");
		logger.info(
				"Execution Time taken :: {}, xMessageId : {}, xAppCorrelationId : {}, threadId : {}", timer.toString(), xMessageId, xAppCorrelationId,
				Thread.currentThread().getId());
		return new ResponseEntity(isUserAdded, HttpStatus.OK);
	}
	
	/**
	 * @param applicationType
	 * @param xMessageId
	 * @param xAppCorrelationId
	 * @return
	 * @throws UserServiceException
	 */
	public ResponseEntity<List<UsersDetailsResponse>> getUserDetails(String xMessageId, String xAppCorrelationId, String xOrganisationId)
			throws UserServiceException {

		final String METHOD_NAME = "getUserDetails";
		logger.info(METHOD_NAME, "{} Start");
		Timer timer = new Timer("getUserDetails", "getUserDetails");
		List<UsersDetailsResponse> usersDetailsListResp = new ArrayList<UsersDetailsResponse>();
		
		try {
			List<UserDetails> userDetails = (List<UserDetails>) userDetailsDAO.findAll();
			
			for (UserDetails userDetails2 : userDetails) {
				UsersDetailsResponse usersDetailsResponse = new UsersDetailsResponse();
				usersDetailsResponse.setGender(userDetails2.getGender());
				usersDetailsResponse.setOccupation(userDetails2.getOccupation());
				usersDetailsResponse.setUserid(userDetails2.getUserid());
				usersDetailsResponse.setUsername(userDetails2.getUsername());
				usersDetailsListResp.add(usersDetailsResponse);
			}
			

		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			throw new UserServiceException(ex.getLocalizedMessage(), ex, 500);
		}
		logger.info(METHOD_NAME, "{} End");
		logger.info(
				"Execution Time taken :: {}, xMessageId : {}, xAppCorrelationId : {}, threadId : {}", timer.toString(), xMessageId, xAppCorrelationId,
				Thread.currentThread().getId());
		return new ResponseEntity(usersDetailsListResp, HttpStatus.OK);
	}

}
