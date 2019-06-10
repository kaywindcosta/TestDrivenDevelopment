package com.bcbsma.api;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.net.URISyntaxException;

import org.aspectj.lang.annotation.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.bcbsma.api.config.H2Config;
import com.bcbsma.api.constant.TddConstant;
import com.bcbsma.api.h2.H2Application;
import com.bcbsma.api.model.Patient;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { SpringBootApp.class })
@Import({ H2Application.class, H2Config.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.DEFAULT)
@ActiveProfiles("test")
//@Category(Test.class)
public class RestAssuredIntegrationTest {

	private static final Logger log = LoggerFactory.getLogger(RestAssuredIntegrationTest.class);

	private final String SERVER_URL = "http://localhost:";

	@Value("${server.url}")
	private String serverUrl;

	private URI uri = null;

	@LocalServerPort
	protected int port;

	@Before(value = "")
	public void before() {

	}

	@Test
	public void firstTestCreatePatientTest() {

		try {
			uri = new URI(serverUrl + "/createPatient");
		} catch (URISyntaxException e) {

		}
		log.info("#############################Final URL:"+uri);
		Patient pat = new Patient();
		pat.setFirstName("Sam");
		pat.setLastName("Simson");
		pat.setGender("Male");

		given().contentType("application/json").body(pat).when().post(uri).then().statusCode(406);

	}

	@Test
	public void firstTestCreatePatient() {

		try {
			uri = new URI(SERVER_URL + port + "/createPatient");
		} catch (URISyntaxException e) {

		}

		Patient pat = new Patient();
		pat.setFirstName("Sam");
		pat.setLastName("Simson");
		pat.setGender("Male");

		given().contentType("application/json").body(pat).when().post(uri).then().statusCode(200);

	}

	@Test
	public void secondTestmakeSureListAllWorksFine() {
		Patient pat = new Patient();
		pat.setFirstName("TDDDaemon");
		pat.setLastName("Development");
		pat.setGender("Male");

		String createresponse = given().contentType("application/json").body(pat).when()
				.post(SERVER_URL + port + "/createPatient").peek() // Use peek() to print the ouput
				.then().statusCode(200) // check http status code
				.extract().asString();
		assertNotNull(createresponse);
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
		Patient pat = new Patient();
		pat.setFirstName("TDD");
		pat.setLastName("Development");
		pat.setGender("Male");

		String createresponse = given().contentType("application/json").body(pat).when()
				.post(SERVER_URL + port + "/createPatient").peek() // Use peek() to print the ouput
				.then().statusCode(200) // check http status code
				.extract().asString();
		assertNotNull(createresponse);
		try {
			uri = new URI(SERVER_URL + port + "/listAllPatient");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		Response response = RestAssured.get(uri);

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	}

	@Test
	public void createPatientValidateResponseTest() {
		Patient pat = new Patient();
		pat.setFirstName("Kaywin");
		pat.setLastName("Dcosta");
		pat.setGender("Male");

		String response = given().contentType("application/json").body(pat).when()
				.post(SERVER_URL + port + "/createPatient").peek() // Use peek() to print the ouput
				.then().statusCode(200) // check http status code
				.extract().asString();
		assertNotNull(response);
		assertEquals(TddConstant.RECORD_CREATED, response.toString());
	}

	@Test
	public void createAndDeletePatientValidateResponseTest() {
		Patient pat = new Patient();
		pat.setFirstName("Adolf");
		pat.setLastName("Hitler");
		pat.setGender("Male");
		String response = given().contentType("application/json").body(pat).when()
				.post(SERVER_URL + port + "/createPatient").peek().then().statusCode(200).extract().asString();
		assertNotNull(response);
		String delresponse = given().contentType("application/json").body(pat).when()
				.post(SERVER_URL + port + "/deletePatient").peek().then().statusCode(200).extract().asString();
		assertNotNull(delresponse);
		assertEquals(TddConstant.DELETE_SUCCESSFUL, delresponse.toString());
	}

	@Test
	public void createAndDuplicateTest() {
		Patient pat = new Patient();
		pat.setFirstName("Adolf");
		pat.setLastName("Hitler");
		pat.setGender("Male");
		String response = given().contentType("application/json").body(pat).when()
				.post(SERVER_URL + port + "/createPatient").peek().then().statusCode(200).extract().asString();
		assertNotNull(response);
		String delresponse = given().contentType("application/json").body(pat).when()
				.post(SERVER_URL + port + "/createPatient").peek().then().statusCode(406).extract().asString();
		assertNotNull(delresponse);
		assertEquals(TddConstant.RECORD_ALREADY_EXISTS, delresponse.toString());
	}

}
