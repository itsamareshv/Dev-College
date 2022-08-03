package com.amaresh.devcollege.services;

import java.util.List;
import java.util.Map;

import com.amaresh.devcollege.models.Student;
import com.amaresh.devcollege.payloads.StudentDto;

public interface StudentService {

	StudentDto addStudent(StudentDto studentDto);
	StudentDto updateStudent(StudentDto studentDto,String studentId);
	Map<String, String> deleteStudent(String studentId);
	List<StudentDto> getStudentList();
	StudentDto getStudentById(String studentId);
	StudentDto updateStudentWalletAmount(StudentDto studentDto,String studentId);
	Student getWalletDetails(String studentId);
	
	
}
