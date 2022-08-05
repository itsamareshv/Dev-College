package com.amaresh.devcollege.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.amaresh.devcollege.models.Enrolment;
import com.amaresh.devcollege.payloads.EnrolmentResponse;

public interface EnrolmentRepo extends JpaRepository<Enrolment, String>, CrudRepository<Enrolment, String> {
	@Query(nativeQuery = true, value = "SELECT * FROM enrolments where student_id=:studentId")
	public List<Enrolment> getAllEnrolmentsByStudentID(@Param("studentId") String studentId);

}
