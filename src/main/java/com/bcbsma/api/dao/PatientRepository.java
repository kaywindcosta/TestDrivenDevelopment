package com.bcbsma.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bcbsma.api.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, String> {

	List<Patient> findByLastName(@Param("name") String lName);

	List<Patient> findByFirstName(@Param("name") String fName);

	List<Patient> findByGender(@Param("name") String gender);
	//, nativeQuery = true

	@Query(value = "select p from Patient p WHERE p.firstName= :firstName AND p.lastName= :lastName AND p.gender= :gender")
	List<Patient> findByFirstNamelastNameAndgender(@Param("firstName") String firstName, @Param("lastName") String lastName,@Param("gender") String gender);
}
