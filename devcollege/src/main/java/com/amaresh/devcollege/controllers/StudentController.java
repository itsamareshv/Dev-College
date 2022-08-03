package com.amaresh.devcollege.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amaresh.devcollege.payloads.StudentDto;
import com.amaresh.devcollege.services.StudentService;

@Controller
@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentService studentService;

	// ADD STUDENT
	@PostMapping("/addstudent")
	public ResponseEntity<Map<String, String>> addStudent(@Valid @RequestBody StudentDto studentDto) {
		StudentDto addStudent = this.studentService.addStudent(studentDto);
		Map<String, String> message = new HashMap<String, String>();
		message.put("Successfully Added Student details for", addStudent.getStudentId());
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}

	// UPDATE STUDENT BY STUDENT ID
	@PutMapping("/updatestudent/{studentId}")
	public ResponseEntity<Map<String, String>> updateStudent(@Valid @RequestBody StudentDto studentDto,
			@PathVariable String studentId) {
		StudentDto updateStudent = this.studentService.updateStudent(studentDto, studentId);
		Map<String, String> message = new HashMap<String, String>();
		message.put("Successfully Updated Student details for", updateStudent.getStudentId());
		return new ResponseEntity<>(message, HttpStatus.OK);

	}

	// DELETE STUDENT BY STUDENT ID
	@DeleteMapping("/deletestudent/{studentId}")
	public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable String studentId) {
		Map<String, String> deleteStudent = this.studentService.deleteStudent(studentId);
//		Map<String, String> message = new HashMap<String, String>();
//		message.put("Successfully Deleted Student details for Student ID = ", studentId);
		return new ResponseEntity<Map<String, String>>(deleteStudent, HttpStatus.OK);
	}

	// GET ALL STUDENTS
	@GetMapping("/getall")
	public ResponseEntity<List<StudentDto>> getStudentList() {
		List<StudentDto> studentList = this.studentService.getStudentList();
		return ResponseEntity.ok(studentList);

	}

	// GET BY STUDENT ID
	@GetMapping("/{studentId}")
	public ResponseEntity<StudentDto> getStudentById(@PathVariable String studentId) {
		StudentDto studentById = this.studentService.getStudentById(studentId);
		return new ResponseEntity<StudentDto>(studentById, HttpStatus.OK);
	}

	// GET WALLET DETAILS
	@GetMapping("/wallet/{studentId}")
	public ResponseEntity<Map<String, String>> getWalletDetails(@PathVariable String studentId) {
		StudentDto studentById = this.studentService.getStudentById(studentId);
		String walletAmount = "" + studentById.getWalletAmount();
		String id = studentById.getStudentId();
		Map<String, String> message = new HashMap<String, String>();
		message.put("StudentID =", id);
		message.put("Wallet Amount", walletAmount);
		return new ResponseEntity<Map<String, String>>(message, HttpStatus.OK);
	}

	// ADD WALLET AMOUNT
	@PutMapping("/walletupdate/{studentId}")
	public ResponseEntity<Map<String, String>> updateStudentWalletAmount(@RequestBody StudentDto studentDto,
			@PathVariable String studentId) {
		Map<String, String> updateStudentWalletAmount = this.studentService.updateStudentWalletAmount(studentDto, studentId);
		return new ResponseEntity<>(updateStudentWalletAmount, HttpStatus.OK);

	}

}
