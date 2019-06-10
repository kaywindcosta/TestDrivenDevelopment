package com.bcbsma.api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bcbsma.api.constant.TddConstant;
import com.bcbsma.api.handler.Controller;
import com.bcbsma.api.model.Patient;
import com.bcbsma.api.model.PatientReq;
import com.bcbsma.api.model.PatientResp;
import com.bcbsma.api.service.TddService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = Controller.class, secure = false)
@ContextConfiguration(classes = SpringBootApp.class)
//@ActiveProfiles("dev")
public class ControllerJunit {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TddService tddService;

	Patient result = new Patient("1", "Kaywin", "Dcosta", "Male");

	ResponseEntity<PatientResp> mockPatientResp = getResp();

	public ResponseEntity<PatientResp> getResp() {

		PatientResp resp = new PatientResp();

		resp.setId("1");
		resp.setFirstName("Kaywin");
		resp.setLastName("Dcosta");

		ResponseEntity<PatientResp> result = new ResponseEntity<>(resp, HttpStatus.OK);
		return result;

	}

	String examplePatientJson = "{\"firstName\":\"Sam\",\"lastName\":\"Simson\"}";

	@Test
	public void retrievePatientResponseEntity() throws Exception {

		Mockito.when(tddService.findPatientA(Mockito.anyString())).thenReturn(mockPatientResp);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/findPatientA?patientId=1")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = "{firstName : Kaywin, lastName : Dcosta}";// id : 1,

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void retrievePatient() throws Exception {

		Mockito.when(tddService.findPatient(Mockito.anyString())).thenReturn(result);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/findPatient?patientId=1")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = "{firstName : Kaywin, lastName : Dcosta}";// id : 1,

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void createPatient() throws Exception {

		String result = TddConstant.RECORD_CREATED;
		new ResponseEntity<String>(result, HttpStatus.OK);
		Mockito.when(tddService.createPatient(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(new ResponseEntity<String>(result, HttpStatus.OK));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/createPatient").accept(MediaType.APPLICATION_JSON)
				.content(examplePatientJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcresult = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = mvcresult.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

}
