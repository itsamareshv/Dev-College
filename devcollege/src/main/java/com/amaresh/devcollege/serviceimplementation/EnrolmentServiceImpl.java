package com.amaresh.devcollege.serviceimplementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amaresh.devcollege.exceptions.ResourceNotFoundException;
import com.amaresh.devcollege.models.Course;
import com.amaresh.devcollege.models.Enrolment;
import com.amaresh.devcollege.models.Student;
import com.amaresh.devcollege.payloads.EnrolmentResponse;
import com.amaresh.devcollege.repository.CourseRepo;
import com.amaresh.devcollege.repository.EnrolmentRepo;
import com.amaresh.devcollege.repository.StudentRepo;
import com.amaresh.devcollege.services.EnrolmentService;

@Service
public class EnrolmentServiceImpl implements EnrolmentService {

	@Autowired
	private EnrolmentRepo enrolmentRepo;

	@Autowired
	private StudentRepo studentRepo;

	@Autowired
	private CourseRepo courseRepo;

	// ADD ENROLMENT
	@Override
	public Map<String, String> addEnrolment(Enrolment enrolment) {
		Course course = courseRepo.findById(enrolment.getCourse_id())
				.orElseThrow(() -> new ResourceNotFoundException("courseId", "courseId", enrolment.getCourse_id()));
		Student student = studentRepo.findById(enrolment.getStudent_id())
				.orElseThrow(() -> new ResourceNotFoundException("studentId", "studentId", enrolment.getStudent_id()));
		if (course.getNoOfRegistrations() >= 1) {
			course.setNoOfRegistrations(course.getNoOfRegistrations() - 1);
		} else {
			Map<String, String> message = new HashMap<String, String>();
			String noOfRegistrations = "" + course.getNoOfRegistrations();
			message.put("Registrations Closed For This Course", noOfRegistrations);
			return message;
		}

		if (student.getWalletAmount() >= course.getCourseFees()) {
			student.setWalletAmount(student.getWalletAmount() - course.getCourseFees());
			enrolment.setStatus("Allocated");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(enrolment.getCourseStartDT());
			calendar.add(Calendar.MINUTE, course.getCourseDuration());
			enrolment.setCuurseEndDT(calendar.getTime());
			System.out.println(calendar.getTime());
		} else {
			Map<String, String> message = new HashMap<String, String>();
			message.put("Cant Opt For Courses Due To Low Wallet Amount",""+ student.getWalletAmount());
			return message;
		}

		List<Enrolment> allEnrolmentsByStudentID = this.enrolmentRepo
				.getAllEnrolmentsByStudentID(enrolment.getStudent_id());
		ArrayList<Date> endDateData = new ArrayList<Date>();
		ArrayList<Date> startDateData = new ArrayList<Date>();
		
		for (Enrolment enrolls : allEnrolmentsByStudentID) {
			endDateData.add(enrolls.getCuurseEndDT());
			startDateData.add(enrolls.getCourseStartDT());
			System.out.println(startDateData);
			System.out.println(endDateData);

		}
		
		for (int i = 0; i < endDateData.size() && i < startDateData.size(); i++) {
			if (enrolment.getCourseStartDT().before(endDateData.get(i))
					&& enrolment.getCourseStartDT().after(startDateData.get(i))) {

				Map<String, String> message1 = new HashMap<String, String>();
				message1.put("Failed To Enroll For This Course ", "You have taken course in same duration");
				return message1;
			}
		}

		enrolmentRepo.save(enrolment);
		Map<String, String> message = new HashMap<String, String>();

		message.put(student.getStudentName() + " Successfully Enrolled in Course = " + course.getCourseName()
				+ " and Enrolment ID =", enrolment.getEnrolmentId());

		return message;
	}

	// GET ENROLLMENT BY ENROLMENT ID
	@Override
	public EnrolmentResponse getEnrolmentById(String enrolmentId) {
		Enrolment enrolment = this.enrolmentRepo.findById(enrolmentId)
				.orElseThrow(() -> new ResourceNotFoundException("enrolmentId", "enrolmentId", enrolmentId));
		Student student = this.studentRepo.findById(enrolment.getStudent_id()).orElseThrow();
		Course course = this.courseRepo.findById(enrolment.getCourse_id()).orElseThrow();
		EnrolmentResponse enrolmentResponse = new EnrolmentResponse();
		enrolmentResponse.setEnrolmentId(enrolment.getEnrolmentId());
		enrolmentResponse.setStudent_id(enrolment.getStudent_id());
		enrolmentResponse.setStudentName(student.getStudentName());
		enrolmentResponse.setCourse_id(enrolment.getCourse_id());
		enrolmentResponse.setCourseName(course.getCourseName());
		enrolmentResponse.setStatus(enrolment.getStatus());
		enrolmentResponse.setCourseStartDT(enrolment.getCourseStartDT());
		enrolmentResponse.setCuurseEndDT(enrolment.getCuurseEndDT());
		enrolmentResponse.setCourseLink("http://localhost:8080/course/get/" + course.getCourseId());
		enrolmentResponse.setStudentLink("http://localhost:8080/student/" + student.getStudentId());
		return enrolmentResponse;
	}

	// GET ENROLMENT BY STUDENT ID
	@Override
	public List<?> getAllEnrolmentsByStudentId(String studentId) {

		this.studentRepo.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("studentId", "studentId", studentId));
		List<Enrolment> allEnrols = this.enrolmentRepo.getAllEnrolmentsByStudentID(studentId);

		List<EnrolmentResponse> enrolementeResponseList = new ArrayList<>();
		for (Enrolment enrols : allEnrols) {
			EnrolmentResponse enrolmentById = getEnrolmentById(enrols.getEnrolmentId());
			enrolementeResponseList.add(enrolmentById);
		}
		return enrolementeResponseList;
	}

	// CHANGE STATUS BY ALLOCATED-PROGRESS-CANCELLED
	@Override
	public Map<String, String> changeStatus(Enrolment enrolment, String enrolmentId) {
		Enrolment enrolment2 = this.enrolmentRepo.findById(enrolmentId)
				.orElseThrow(() -> new ResourceNotFoundException("enrolmentId", "enrolmentId", enrolmentId));
		Student student = studentRepo.findById(enrolment2.getStudent_id()).orElseThrow();
		Course course = courseRepo.findById(enrolment2.getCourse_id()).orElseThrow();
		if (enrolment2.getStatus().equalsIgnoreCase("Cancelled")) {
			if (enrolment.getStatus().equalsIgnoreCase("Progress")) {
				Map<String, String> message = new HashMap<String, String>();
				message.put("Cant be Progressed Because Course is already in Cancelled", "Thank You");
				return message;
			} else if (enrolment.getStatus().equalsIgnoreCase("Completed")) {
				Map<String, String> message = new HashMap<String, String>();
				message.put("Cant be Completed Beacuse Course is already Cancelled", "All The Best");
				return message;
			} else if (enrolment.getStatus().equalsIgnoreCase("Cancelled")) {
				Map<String, String> message = new HashMap<String, String>();
				message.put("Cant be Cancelled Beacuse Course is already Cancelled", "All The Best");
				return message;
			}
		}
		if (enrolment2.getStatus().equalsIgnoreCase("Allocated")) {
			if (enrolment.getStatus().equalsIgnoreCase("Cancelled")) {
				Date courseStartDate = enrolment2.getCourseStartDT();

				LocalDateTime courseCanceledDate = LocalDateTime.now();

				int noOfDaysBeforeCancelled = courseStartDate.getDate() - courseCanceledDate.getDayOfMonth();

				if (noOfDaysBeforeCancelled >= 2) {
					student.setWalletAmount(student.getWalletAmount() + course.getCourseFees());
					course.setNoOfRegistrations(course.getNoOfRegistrations() + 1);
				} else if (noOfDaysBeforeCancelled >= 1) {
					student.setWalletAmount(student.getWalletAmount() + ((course.getCourseFees() * 70) / 100));
					course.setNoOfRegistrations(course.getNoOfRegistrations() + 1);
				} else if (noOfDaysBeforeCancelled <= 1) {
					student.setWalletAmount(student.getWalletAmount() + (course.getCourseFees() / 2));
					course.setNoOfRegistrations(course.getNoOfRegistrations() + 1);
				}
			} else if (enrolment.getStatus().equalsIgnoreCase("Progress")) {
				enrolment2.setStatus(enrolment.getStatus());
			}
		}
		enrolment2.setStatus(enrolment.getStatus());
		enrolmentRepo.save(enrolment2);
		Map<String, String> message = new HashMap<String, String>();
		message.put("Successfully changed the status For Enrol ID = ", enrolmentId);
		return message;
	}

	// CHECK COURSE AVAILABILTY
	@Override
	public Map<String, String> checkAvailability(Enrolment enrolment, String courseId) {
		Course course = this.courseRepo.findById(courseId)
				.orElseThrow(() -> new ResourceNotFoundException("courseId", "courseId", courseId));
		Map<String, String> message = new HashMap<String, String>();
		message.put("Course Id = " + courseId + " Course Name =" + course.getCourseName() + " available for enrolment",
				" No of Registrations Avialiable=" + course.getNoOfRegistrations());
		return message;
	}

	// GET COURSE SUGESTION LIST
	@Override
	public Map<String, String> courseSuggestion(Enrolment enrolment, String studentId) {
		Student student = this.studentRepo.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("studentId", "studentId", studentId));
		List<Course> course = courseRepo.findAll();
		Map<String, String> suggestion = new HashMap<String, String>();
		boolean flag = false;
		for (Course allCourse : course) {
			if (courseRepo.getCourseTagByCourseId(allCourse.getCourseId())
					.contains(student.getHighestQualification())) {
				suggestion.put(allCourse.getCourseId(), allCourse.getCourseName());
				flag = true;
			}
		}
		return suggestion;

	}

	// GET ALL ENROLMENTS WITH LINK
	@Override
	public List<?> getAllEnrolment() {
		List<Enrolment> enrolment = enrolmentRepo.findAll();
		List<EnrolmentResponse> enrolementeResponseList = new ArrayList<>();
		for (Enrolment enrol : enrolment) {
			EnrolmentResponse enrolmentResponse = getEnrolmentById(enrol.getEnrolmentId());
			enrolementeResponseList.add(enrolmentResponse);
		}
		return enrolementeResponseList;
	}

}
