package com.amaresh.devcollege.serviceimplementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amaresh.devcollege.exceptions.ResourceNotFoundException;
import com.amaresh.devcollege.models.Enrolment;
import com.amaresh.devcollege.models.Student;
import com.amaresh.devcollege.payloads.EnrolmentResponse;
import com.amaresh.devcollege.payloads.StudentDto;
import com.amaresh.devcollege.repository.EnrolmentRepo;
import com.amaresh.devcollege.repository.StudentRepo;
import com.amaresh.devcollege.services.EnrolmentService;
import com.amaresh.devcollege.services.StudentService;

@Service
public class StudentServiceImplementation implements StudentService {

	@Autowired
	private StudentRepo studentRepo;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private EnrolmentRepo enrolmentRepo;
	
	@Autowired
	private EnrolmentService enrolmentService;

	// ADD STUDENT
	@Override
	public StudentDto addStudent(StudentDto studentDto) {
		Student student = this.modelMapper.map(studentDto, Student.class);
		Student saveStudent = this.studentRepo.save(student);
		return this.modelMapper.map(saveStudent, StudentDto.class);
	}

	// UPDATE STUDENT
	@Override
	public StudentDto updateStudent(StudentDto studentDto, String studentId) {
		Student id = this.studentRepo.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("studentId", "studentId", studentId));
		id.setStudentName(studentDto.getStudentName());
		id.setHighestQualification(studentDto.getHighestQualification());
		id.setContactNo(studentDto.getContactNo());
		id.setWalletAmount(id.getWalletAmount());
		Student updatedStudentDetails = this.studentRepo.save(id);
		return this.modelMapper.map(updatedStudentDetails, StudentDto.class);
	}

	// DELETE STUDENT
	@Override
	public Map<String, String> deleteStudent(String studentId) {
		Student delStudent = this.studentRepo.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("studentId", "studentId", studentId));
		List<Enrolment> enrolements = enrolmentRepo.findAll();
		ArrayList<String> idData = new ArrayList<String>();
		for (Enrolment enrol : enrolements) {
			String studentIdTemp = enrol.getStudent_id();
			idData.add(studentIdTemp);
		}
		System.out.println(idData);
		boolean flag1 = false;
		for (int i = 0; i < idData.size(); i++) {
			
			List<Enrolment> allEnrols = this.enrolmentRepo.getAllEnrolmentsByStudentID(idData.get(i));
			List<EnrolmentResponse> enrolementeResponseList = new ArrayList<>();
			for (Enrolment enrols : allEnrols) {
				//EnrolmentResponse enrolmentById = ();
				EnrolmentResponse response = this.enrolmentService.getEnrolmentById(enrols.getEnrolmentId());
				response.setStatus("Cancelled");
				//enrolmentRepo.save(response.setStatus("Cancelled"));
				//enrolmentRepo.
				//this.studentRepo.delete(delStudent);
				Map<String, String> message = new HashMap<String, String>();
				message.put("Enrolement Status Changed and Deleetd Sucessfully", studentId);
				return message;
			}
			}
		return null;
		
		
		}
		
		

//	}

	// GET STUDENT LIST
	@Override
	public List<StudentDto> getStudentList() {

		List<Student> findAll = this.studentRepo.findAll();
		List<StudentDto> collectStudents = findAll.stream()
				.map((student) -> this.modelMapper.map(student, StudentDto.class)).collect(Collectors.toList());
		return collectStudents;

	}

	// GET STUDENT BY ID
	@Override
	public StudentDto getStudentById(String studentId) {
		Student student = this.studentRepo.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("studentId", "studentId", studentId));
		return this.modelMapper.map(student, StudentDto.class);
	}

	// UPDATE WALLET AMOUNT BY OLD AMOUNT
	@Override
	public StudentDto updateStudentWalletAmount(StudentDto studentDto, String studentId) {
		Student id = this.studentRepo.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("studentId", "studentId", studentId));
		id.setWalletAmount(id.getWalletAmount() + studentDto.getWalletAmount());
		Student save = this.studentRepo.save(id);
		return this.modelMapper.map(save, StudentDto.class);
	}

	// GET WALLET DETAILS 
	@Override
	public Student getWalletDetails(String studentId) {
		Student id = this.studentRepo.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("studentId", "studentId", studentId));
		return this.modelMapper.map(id.getWalletAmount(), Student.class);
	}
}
