package com.bcbsma.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bcbsma.api.constant.TddConstant;
import com.bcbsma.api.dao.PatientRepository;
import com.bcbsma.api.model.Patient;
import com.bcbsma.api.model.PatientResp;
import com.bcbsma.api.util.TddUtil;

@Service("tddService")
public class TddServiceImpl implements TddService {

	private static final Logger log = LoggerFactory.getLogger(TddServiceImpl.class);

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private TddUtil tddUtil;

	@Override
	public ResponseEntity<String> createPatient(String firstName, String lastName, String gender) {
		String result = "";
		HttpStatus status = null;

		List<Patient> pats = patientRepository.findByFirstNamelastNameAndgender(firstName, lastName, gender);
		if (pats.size() > 0) {
			result = TddConstant.RECORD_ALREADY_EXISTS;
			status = HttpStatus.NOT_ACCEPTABLE;
			log.info(TddConstant.RECORD_ALREADY_EXISTS);
		} else {
			Patient patient = new Patient();
			if (!StringUtils.isEmpty(firstName)) {
				patient.setFirstName(firstName);
			}
			if (!StringUtils.isEmpty(lastName)) {
				patient.setLastName(lastName);
			}
			if (!StringUtils.isEmpty(gender)) {
				patient.setGender(gender);
			}
			Patient patientCreated = patientRepository.save(patient);
			if (patientCreated != null) {
				result = TddConstant.RECORD_CREATED;
				status = HttpStatus.OK;
				log.info(TddConstant.RECORD_CREATED);
			} else {
				result = TddConstant.RECORD_CREATION_UNSUCCESSFULL;
				status = HttpStatus.EXPECTATION_FAILED;
				log.info(TddConstant.RECORD_CREATION_UNSUCCESSFULL);
			}
		}
		return new ResponseEntity<String>(result, status);
	}

	@Override
	public ResponseEntity<List<PatientResp>> listAllPatient() {
		List<PatientResp> result = patientRepository.findAll().stream().map(tddUtil.transformdata)
				.collect(Collectors.<PatientResp>toList());

		ResponseEntity<List<PatientResp>> resp = null;
		try {
			resp = new ResponseEntity<List<PatientResp>>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
		// return result;
	}

	@Override
	public ResponseEntity<String> deletePatient(String firstName, String lastName, String gender) {
		List<Patient> pats = patientRepository.findByFirstNamelastNameAndgender(firstName, lastName, gender);
		String result = "";

		if (pats.stream().count() < 1) {
			result = TddConstant.NOTHING_TO_DELETE;
			log.info(TddConstant.NOTHING_TO_DELETE);

		}
		for (Patient patient : pats) {
			try {
				patientRepository.delete(patient);
				result = TddConstant.DELETE_SUCCESSFUL;
				log.info(TddConstant.DELETE_SUCCESSFUL);
			} catch (Exception e) {
				result = "Exception on Delete " + e.getMessage();
			}
		}
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	@Override
	public Patient findPatient(String patientId) {
		
		Optional<Patient> pat=patientRepository.findById(patientId);

		return pat.get();

	}

	@Override
	public ResponseEntity<PatientResp> findPatientA(String patId) {
		Optional<Patient> result = patientRepository.findById(patId);

		return new ResponseEntity<PatientResp>(tddUtil.transformdata.apply(result.get()), HttpStatus.OK);
	
	}

}
