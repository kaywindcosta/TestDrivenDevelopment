package com.bcbsma.api.handler;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import com.bcbsma.api.model.Patient;
import com.bcbsma.api.model.PatientReq;
import com.bcbsma.api.model.PatientResp;
import com.bcbsma.api.service.TddService;

import io.swagger.annotations.ApiParam;

@RestController
public class Controller implements CreatePatientApi, ListAllPatientApi, DeletePatientApi {

	@Autowired
	private TddService tddService;

	@Override
	public ResponseEntity<String> createPatientUsingPOST(
			@ApiParam(value = "patient", required = true) @Valid @RequestBody PatientReq patientReq) {
		return tddService.createPatient(patientReq.getFirstName(), patientReq.getLastName(), patientReq.getGender());
	}

	@Override
	@RequestMapping(value = "/listAllPatient", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<List<PatientResp>> listAllPatientUsingGET() {
//		getRequest().ifPresent(request -> {
//			for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
//				if (mediaType.isCompatibleWith(MediaType.valueOf("*/*"))) {
//					ApiUtil.setExampleResponse(request, "*/*",
//							"{  \"firstName\" : \"firstName\",  \"lastName\" : \"lastName\",  \"gender\" : \"gender\",  \"id\" : \"id\"}");
//					break;
//				}
//			}
//		});
		return tddService.listAllPatient();
	}

	@Override
	public ResponseEntity<String> deletePatientUsingPOST(
			@ApiParam(value = "patient", required = true) @Valid @RequestBody PatientReq patientReq) {
		return tddService.deletePatient(patientReq.getFirstName(), patientReq.getLastName(), patientReq.getGender());
	}

	@Override
	public Optional<NativeWebRequest> getRequest() {
		return CreatePatientApi.super.getRequest();
	}

	@RequestMapping(value = "/findPatientA", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<PatientResp> findPatientUsingGETA(@RequestParam String patientId) {

		return tddService.findPatientA(patientId);
	}
	
	@GetMapping(value = "/findPatient", produces = MediaType.APPLICATION_JSON_VALUE)
	public Patient findPatientUsingGET(@RequestParam String patientId) {

		return tddService.findPatient(patientId);
	}

}
