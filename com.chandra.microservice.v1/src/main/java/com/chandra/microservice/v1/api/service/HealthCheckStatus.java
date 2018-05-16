package com.chandra.microservice.v1.api.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chandra.microservice.v1.api.GetHealthCheckStatusApi;
import com.chandra.microservice.v1.model.InlineResponse200;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@RestController
@Api(value = "healthcheck")
@RequestMapping(value = "/com/chandra/microservice/v1")
public class HealthCheckStatus implements GetHealthCheckStatusApi {

    private final Logger logger = LoggerFactory.getLogger(HealthCheckStatus.class);

    @ApiOperation(value = "", notes = "retrieves HealthCheck Status of API.", response = InlineResponse200.class, authorizations = {
            @Authorization(value = "API Key")
    }, tags={ "health check", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = InlineResponse200.class) })

    @RequestMapping(value = "/healthcheck",
            produces = { "application/json" },
            consumes = { "*/*" },
            method = RequestMethod.GET)
    @Override
    public ResponseEntity<InlineResponse200> getHealthCheckStatus() {
        logger.info("Health check success");
        return new ResponseEntity(HttpStatus.OK);
    }

}
