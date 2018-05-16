package com.chandra.microservice.v1.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chandra.microservice.v1.model.UsersDetailsResponse;
import com.chandra.microservice.v1.api.exception.UserServiceException;
import com.chandra.microservice.v1.api.processor.UserDetailsProcessor;
import com.chandra.microservice.v1.model.InlineResponse400;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

/**
 * @author Chandra
 *
 */
@RestController
@Api(value = "UserDetails", description = "the UserDetails API")
@RequestMapping(value = "/com/chandra/microservice/v1")
public class RetrieveAllUsersService {
	
	@Autowired
	UserDetailsProcessor userDetailsProcessor;

	@ApiOperation(value = "", notes = "retrieve all users", response = UsersDetailsResponse.class, responseContainer = "List", authorizations = {
			@Authorization(value = "API Key") }, tags = { "User details", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "UsersList response", response = UsersDetailsResponse.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad Request", response = InlineResponse400.class),
			@ApiResponse(code = 404, message = "Resource Not Found", response = InlineResponse400.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = InlineResponse400.class) })

	@RequestMapping(value = "/users", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	public ResponseEntity<List<UsersDetailsResponse>> getUsersList(
			@ApiParam(value = "Globally unique message identifier  - GUID. This uniquely identifies this call.", required = true) @RequestHeader(value = "x-messageId", required = true) String xMessageId,
			@ApiParam(value = "Consumer generated message identifier for correlation purposes. e.g. Can be used to group together a number of API calls making up a business transaction.", required = true) @RequestHeader(value = "x-appCorrelationId", required = true) String xAppCorrelationId,
			@ApiParam(value = "The organisation the caller is operating in.", required = true, allowableValues = "CHANDRA") @RequestHeader(value = "x-organisationId", required = true) String xOrganisationId) throws UserServiceException {

		return userDetailsProcessor.getUserDetails(xMessageId, xAppCorrelationId, xOrganisationId);
	}
}
