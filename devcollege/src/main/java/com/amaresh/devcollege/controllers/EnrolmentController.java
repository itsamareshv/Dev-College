package com.amaresh.devcollege.controllers;

import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amaresh.devcollege.models.Enrolment;
import com.amaresh.devcollege.payloads.EnrolmentDto;
import com.amaresh.devcollege.payloads.EnrolmentResponse;
import com.amaresh.devcollege.services.EnrolmentService;

@Controller
@RestController
@RequestMapping("/enrolment")
public class EnrolmentController {

	@Autowired
	private EnrolmentService enrolmentService;

	@Autowired
	private ModelMapper modelMapper;

	// ADD ENROLMENT
	@PostMapping("/addenrolment")
	public Map<String, String> adden(@RequestBody EnrolmentDto enrolmentDto) {
		Enrolment createEnrolment = modelMapper.map(enrolmentDto, Enrolment.class);
		return enrolmentService.addEnrolment(createEnrolment);
	}

	// UPDATE ENROLMENT BY ENROLMENT ID
	@PutMapping("update/status/{enrolmentId}")
	public ResponseEntity<Map<String, String>> updateStatus(@RequestBody Enrolment enrolment,
			@PathVariable String enrolmentId) {
		Map<String, String> changeStatus = this.enrolmentService.changeStatus(enrolment, enrolmentId);
		return new ResponseEntity<Map<String, String>>(changeStatus, HttpStatus.OK);
	}

	// GET ENROLMENT BY ENROLMENT ID
	@GetMapping("get/{enrolmentId}")
	public ResponseEntity<Map<String, String>> getEnrolmentById(@PathVariable String enrolmentId) {
		EnrolmentResponse enrolmentById = this.enrolmentService.getEnrolmentById(enrolmentId);
		return new ResponseEntity(enrolmentById, HttpStatus.OK);
	}

	// GET ALL ENROLMENTS
	@GetMapping("/getAll")
	public ResponseEntity<?> getAllEnrolments() {
		List<?> allEnrolment = this.enrolmentService.getAllEnrolment();
		return ResponseEntity.ok(allEnrolment);
	}

	// GET ENROLLMENTS OF PARTICULAR STUDENT ID
	@GetMapping("/getstudent/{studentId}")
	public ResponseEntity<?> getAllEnrolmentsByStudnetId(@PathVariable String studentId) {
		List<?> allEnrolments = this.enrolmentService.getAllEnrolmentsByStudentId(studentId);
		return ResponseEntity.ok(allEnrolments);
	}

//GET COURSE AVAILIABILTY BY COURSES ID
	@GetMapping("/availability/{courseId}")
	public ResponseEntity<Map<String, String>> getAvailabiltyByCourseId(Enrolment enrolment,
			@PathVariable String courseId) {
		Map<String, String> checkAvailability = this.enrolmentService.checkAvailability(enrolment, courseId);
		return new ResponseEntity<Map<String, String>>(checkAvailability, HttpStatus.OK);

	}

//GET COURSE SUGESSTION LIST FOR STUDENT ID
	@GetMapping("/sugestion/{studentId}")
	public ResponseEntity<Map<String, String>> getSugestionByStudentId(Enrolment enrolment,
			@PathVariable String studentId) {
		Map<String, String> getSugestion = this.enrolmentService.courseSuggestion(enrolment, studentId);
		return new ResponseEntity<Map<String, String>>(getSugestion, HttpStatus.OK);

	}

}
