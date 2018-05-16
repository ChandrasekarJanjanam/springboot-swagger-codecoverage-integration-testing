package com.chandra.microservice.v1.api.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chandra.microservice.v1.api.exception.UserServiceException;
import com.chandra.microservice.v1.api.processor.UserDetailsProcessor;
import com.chandra.microservice.v1.model.InlineResponse400;
import com.chandra.microservice.v1.model.UsersDetailsResponse;

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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-05-15T23:08:36.772+10:00")
@Api(value = "AddUserDetails", description = "the AddUserDetails API")
@RequestMapping(value = "/com/chandra/microservice/v1")
public class AddUserService {

	@Autowired
	UserDetailsProcessor userDetailsProcessor;

	@ApiOperation(value = "", notes = "Add new user details", response = Boolean.class, authorizations = {
			@Authorization(value = "API Key") }, tags = { "Add User details", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Boolean.class),
			@ApiResponse(code = 400, message = "Bad Request", response = InlineResponse400.class),
			@ApiResponse(code = 404, message = "Resource Not Found", response = InlineResponse400.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = InlineResponse400.class) })

	@RequestMapping(value = "/addUser", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	public ResponseEntity<Boolean> addUser(
			@ApiParam(value = "Globally unique message identifier  - GUID. This uniquely identifies this call.", required = true) @RequestHeader(value = "x-messageId", required = true) String xMessageId,
			@ApiParam(value = "Consumer generated message identifier for correlation purposes. e.g. Can be used to group together a number of API calls making up a business transaction.", required = true) @RequestHeader(value = "x-appCorrelationId", required = true) String xAppCorrelationId,
			@ApiParam(value = "The organisation the caller is operating in.", required = true, allowableValues = "CHANDRA") @RequestHeader(value = "x-organisationId", required = true) String xOrganisationId,
			@ApiParam(value = "The user to create.") @Valid @RequestBody UsersDetailsResponse user) throws UserServiceException {

		return userDetailsProcessor.saveUserDetails(xOrganisationId, xMessageId, xAppCorrelationId, user);
	}
}
