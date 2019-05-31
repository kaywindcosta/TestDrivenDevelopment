package com.bcbsma.api.util;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.bcbsma.api.model.Patient;
import com.bcbsma.api.model.PatientResp;

@Component
public class TddUtil {

	public Function<Patient, PatientResp> transformdata = new Function<Patient, PatientResp>() {

		@Override
		public PatientResp apply(Patient t) {
			PatientResp result = new PatientResp();
			result.setId(t.getId().toString());
			result.setFirstName(t.getFirstName());
			result.setLastName(t.getLastName());
			result.setGender(t.getGender());
			return result;
		}

	};
}
