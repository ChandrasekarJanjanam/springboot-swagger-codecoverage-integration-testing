package com.chandra.microservice.v1.api;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author L083239
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = SpringbootApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TestConfig {
	

}
