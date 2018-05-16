package com.chandra.microservice.v1.api.service;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.chandra.microservice.v1.api.TestConfig;
import com.chandra.microservice.v1.api.jpa.UserDetails;
import com.chandra.microservice.v1.api.jpa.UserDetailsDAO;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is integration testing class to test user details API operation.
 * 
 * @author Chandra
 *
 */
public class UserServiceTest extends TestConfig {

	/* private Mockmvc instance */
    @Autowired
    private MockMvc mockMvc;

    /* private UserDetailsDAO instance */
    @MockBean
    private UserDetailsDAO dataService;

    /* private ObjectMapper instance */
    @Autowired 
    private ObjectMapper mapper;
    
    /**
     * This method returns all the users from db.
     *
     * @throws Exception
     */
    @Test
    public void testFetchAllUsers() throws Exception {

    	Mockito.when(dataService.findAll()).thenReturn(populateUsersDetails());
        mockMvc.perform(MockMvcRequestBuilders.get("/com/chandra/microservice/v1/users")
            .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("x-messageId", "234324")
                .header("x-appCorrelationId", "123424")
                .header("x-organisationId", "CHANDRA")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
    
    /**
     * This method stores new user into db.
     *
     * @throws Exception
     */
    @Test
    public void testSaveUser() throws Exception {

    	UserDetails user = new UserDetails("1", "chandra", "male", "Techie");
    	Mockito.when(dataService.save(user)).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/com/chandra/microservice/v1/addUser")
    			.header("Content-Type", MediaType.APPLICATION_JSON)
                .header("x-messageId", "234324")
                .header("x-appCorrelationId", "123424")
                .header("x-organisationId", "CHANDRA")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
	
	
    /**
     * This method returns list of user details used to mock response.
     * @return List<UserDetails>
     */
    private List<UserDetails> populateUsersDetails(){
    	List<UserDetails> userDetails = new ArrayList<UserDetails>();
    	UserDetails user = new UserDetails();
    	user.setUserid("1");
    	user.setUsername("chandra");
    	user.setOccupation("Techie");
    	user.setGender("male");
    	userDetails.add(user);
        return userDetails;
    }
    
}

