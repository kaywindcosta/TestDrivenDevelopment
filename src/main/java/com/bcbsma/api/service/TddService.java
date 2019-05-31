package com.bcbsma.api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bcbsma.api.model.Patient;
import com.bcbsma.api.model.PatientResp;

public interface TddService {

	ResponseEntity<String> createPatient(String firstName, String lastName, String gender);

	ResponseEntity<List<PatientResp>> listAllPatient();

	ResponseEntity<String> deletePatient(String firstName, String lastName, String gender);

	Patient findPatient(String patId);

	ResponseEntity<PatientResp> findPatientA(String valueOf);

}
