package com.amaresh.devcollege.services;

import java.util.List;
import java.util.Map;

import com.amaresh.devcollege.models.Enrolment;
import com.amaresh.devcollege.payloads.EnrolmentResponse;

public interface EnrolmentService {

	public Map<String, String> addEnrolment(Enrolment enrolment);

	EnrolmentResponse getEnrolmentById(String enrolmentId);

	List<?> getAllEnrolment();

	List<?> getAllEnrolmentsByStudentId(String studentId);

	Map<String, String> changeStatus(Enrolment enrolment, String enrolmentId);

	Map<String, String> checkAvailability(Enrolment enrolment, String courseId);

	Map<String, String> courseSuggestion(Enrolment enrolment, String studentId);

}
