package com.chandra.microservice.v1.api.service;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.chandra.microservice.v1.api.TestConfig;

/**
 * This is healthcheck service unit testing class to check the API healthcheck
 * status.
 * 
 * @author Chandra
 *
 */
public class HeatlhCheckServiceTest extends TestConfig {

	/* private Mockmvc instance */
	@Autowired
	private MockMvc mockMvc;

	/**
	 * This method returns the health check status of Api.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testHealthCheckStatus() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/com/chandra/microservice/v1/healthcheck")
				.header("Content-Type", MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().is2xxSuccessful());

	}
}
