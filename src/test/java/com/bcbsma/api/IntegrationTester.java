package com.bcbsma.api;

import static com.jayway.restassured.RestAssured.given;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.bcbsma.api.model.Patient;

@RunWith(SpringRunner.class)
@ActiveProfiles("INTEGRATION-TEST-TESTENVIRONMENT")
public class IntegrationTester {

	private static final Logger log = LoggerFactory.getLogger(IntegrationTester.class);

	private String serverUrl = "http://testdrivendevelopment-test.cfapps.io";

	private URI uri = null;
	
	@Value("${server.url}")
	private String serverUrlProp;

	@Test
	@Order(1)
	public void firstTestCreatePatient() {

		try {
			uri = new URI(serverUrl + "/createPatient");
			log.info(uri.toString());
		} catch (URISyntaxException e) {

		}

		Patient pat = new Patient();
		pat.setFirstName("Sam");
		pat.setLastName("Simson");
		pat.setGender("Male");

		given().contentType("application/json").body(pat).when().post(uri).then().statusCode(200);
		//duplicate check after creation
		given().contentType("application/json").body(pat).when().post(uri).then().statusCode(406);

	}

//	@Test
//	@Order(2)
//	public void duplicateCheck() {
//
//		try {
//			uri = new URI(serverUrl + "/createPatient");
//			log.info(uri.toString());
//		} catch (URISyntaxException e) {
//
//		}
//
//		Patient pat = new Patient();
//		pat.setFirstName("Sam");
//		pat.setLastName("Simson");
//		pat.setGender("Male");
//
//		//given().contentType("application/json").body(pat).when().post(uri).then().statusCode(200);
//		//duplicate check after creation
//		given().contentType("application/json").body(pat).when().post(uri).then().statusCode(406);
//
//	}
	
	@Test
	@Order(3)
	public void secondTestmakeSureListAllWorksFine() {

		try {
			uri = new URI(serverUrl + "/listAllPatient");
		} catch (URISyntaxException e) {
			log.error(e.getMessage());
		}

		given().when().get(uri).then().statusCode(200);
		
		
	}

	@Test
	@Order(4)
	public void thirdTestdeletePatient() {

		try {
			uri = new URI(serverUrl + "/deletePatient");
			log.info(uri.toString());
		} catch (URISyntaxException e) {

		}

		Patient pat = new Patient();
		pat.setFirstName("Sam");
		pat.setLastName("Simson");
		pat.setGender("Male");

		given().contentType("application/json").body(pat).when().post(uri).then().statusCode(200);
	}

}
