package com.bcbsma.api;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.bcbsma.api.config.H2Config;
import com.bcbsma.api.h2.H2Application;
import com.bcbsma.api.model.Patient;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringBootApp.class})
@Import({ H2Application.class ,H2Config.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.DEFAULT)
public class RestAssuredIntegrationTest {

	private static final Logger log = LoggerFactory.getLogger(RestAssuredIntegrationTest.class);

	private final String SERVER_URL = "http://localhost:";

	private URI uri = null;

	@LocalServerPort
	protected int port;

	@Test
	public void firstTestCreatePatient() {

		try {
			uri = new URI(SERVER_URL + port + "/createPatient");
		} catch (URISyntaxException e) {

		}

		Patient pat = new Patient();
		pat.setFirstName("Kaywin");
		pat.setLastName("Dcosta");
		pat.setGender("Male");

		given().contentType("application/json").body(pat).when().post(uri).then().statusCode(200);
		
	}

	@Test
	public void secondTestmakeSureListAllWorksFine() {

		try {
			uri = new URI(SERVER_URL + port + "/listAllPatient");
		} catch (URISyntaxException e) {
			log.error(e.getMessage());
		}

		given().when().get(uri).then().statusCode(200);
	}

	@Test
	public void thirdTestdeletePatient() {

		try {
			uri = new URI(SERVER_URL + port + "/deletePatient");
		} catch (URISyntaxException e) {

		}

		Patient pat = new Patient();
		pat.setFirstName("Kaywin");
		pat.setLastName("Dcosta");
		pat.setGender("Male");

		given().contentType("application/json").body(pat).when().post(uri).then().statusCode(200);
	}

	@Test
	public void whenGetAllPatients_thenOK() {

		try {
			uri = new URI(SERVER_URL + port + "/listAllPatient");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		Response response = RestAssured.get(uri);

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	}


}
