package com.amaresh.devcollege.serviceimplementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
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

	private Course course;

	private Student student;

	private String studentName;

	private String courseName;

	private float courseFees;

	private float walletAmount;

	// add enrollment
	@Override
	public Map<String, String> addEnrolment(Enrolment enrolment) {
		String student_id = enrolment.getStudent_id();
		String course_id = enrolment.getCourse_id();
		course = courseRepo.findById(course_id)
				.orElseThrow(() -> new ResourceNotFoundException("course_id", "student_id", course_id));
		courseName = course.getCourseName();
		courseFees = course.getCourseFees();
		System.out.println(courseFees);
		System.out.println(courseName);
		student = studentRepo.findById(student_id)
				.orElseThrow(() -> new ResourceNotFoundException("student_id", "student_id", student_id));
		studentName = student.getStudentName();
		walletAmount = student.getWalletAmount();
		if (course.getNoOfRegistrations() >= 1) {
			course.setNoOfRegistrations(course.getNoOfRegistrations() - 1);
		} else {
			Map<String, String> message = new HashMap<String, String>();
			String noOfRegistrations = "" + course.getNoOfRegistrations();
			message.put("Registrations Closed For This Course", noOfRegistrations);
			return message;
		}

		if (walletAmount >= courseFees) {
			student.setWalletAmount(walletAmount - courseFees);
			enrolment.setStatus("Allocated");
			Calendar calendar = Calendar.getInstance();
			Date stratdate = enrolment.getCourseStartDT();
			//timeInSecs=date.getTimeInMillis();
			calendar.setTime(stratdate);
			//long minutes = 60000;
			int hours = course.getCourseDuration();
			//Date oldDate = date.
			//System.out.println(oldDate.getTime());
			
		//	Date newDate = new Date(oldDate.getMinutes() + hours);
			//Date newDate=new Date()
			calendar.add(Calendar.MINUTE, hours);
			System.out.println(calendar.getTime());
			enrolment.setCuurseEndDT(calendar.getTime());
		} else {
			Map<String, String> message = new HashMap<String, String>();
			String lowBalance = "" + walletAmount;
			message.put("Cant Opt For Courses Due To Low Wallet Amount", lowBalance);
			return message;
		}

		enrolmentRepo.save(enrolment);
		Map<String, String> message = new HashMap<String, String>();

		message.put(studentName + " Successfully Enrolled in Course = " + courseName + " and Enrolment ID =",
				enrolment.getEnrolmentId());

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
		student = studentRepo.findById(enrolment2.getStudent_id()).orElseThrow();
		course = courseRepo.findById(enrolment2.getCourse_id()).orElseThrow();
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
				System.out.println("Course Start Date =" + courseStartDate);
				LocalDateTime courseCanceledDate = LocalDateTime.now();
				System.out.println("Course Canceled Date " + courseCanceledDate);
				int courseStartDay = courseStartDate.getDate();
				int cancelDay = courseCanceledDate.getDayOfMonth();
				int noOfDaysBeforeCancelled = courseStartDay - cancelDay;
				System.out.println("difference" + noOfDaysBeforeCancelled);
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
		message.put("Successfully change the status from old status to new status = " + enrolment2.getStatus()
				+ "For Enrol ID", enrolmentId);
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
		String highestQualification = student.getHighestQualification();
		if (highestQualification.equalsIgnoreCase("bca") || highestQualification.equalsIgnoreCase("bsc")) {
			Map<String, String> suggestionList1 = new HashMap<String, String>();
			suggestionList1.put(" -->CourseName =", "BCA");
			suggestionList1.put(" -->CourseName=", "MBA");
			suggestionList1.put(" -->CourseName=", "MSc");
			return suggestionList1;
		} else if (highestQualification.equalsIgnoreCase("Bcom") || highestQualification.equalsIgnoreCase("BBA")) {
			Map<String, String> suggestionList2 = new HashMap<String, String>();
			suggestionList2.put(" -->CourseName =", "MBA");
			suggestionList2.put(" -->CourseName =", "Mcom");
			return suggestionList2;
		}
		return null;

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
