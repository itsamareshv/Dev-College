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
import com.amaresh.devcollege.models.Course;
import com.amaresh.devcollege.models.Enrolment;
import com.amaresh.devcollege.payloads.CourseDto;
import com.amaresh.devcollege.repository.CourseRepo;
import com.amaresh.devcollege.repository.EnrolmentRepo;
import com.amaresh.devcollege.services.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseRepo courseRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private EnrolmentRepo enrolmentRepo;

	// CREATE COURSE
	@Override
	public CourseDto createCourse(CourseDto courseDto) {
		Course course = this.modelMapper.map(courseDto, Course.class);
		Course savedCourse = this.courseRepo.save(course);
		return this.modelMapper.map(savedCourse, CourseDto.class);
	}

	// UPDATE COURSE
	@Override
	public Map<String, String> updateCourse(Course course, String courseId) {
		Course course1 = this.courseRepo.findById(courseId)
				.orElseThrow(() -> new ResourceNotFoundException("courseId", "courseId", courseId));
		List<Enrolment> enrolements = enrolmentRepo.findAll();
		ArrayList<String> idData = new ArrayList<String>();
		for (Enrolment enrol : enrolements) {
			idData.add(enrol.getCourse_id());
		}

		if (courseRepo.getStatusByCourseId(courseId) != null) {
			boolean flag = false;

			if (courseRepo.getStatusByCourseId(courseId).equalsIgnoreCase("Allocated")) {
				flag = true;
				if (flag = true) {
					course1.setCourseName(course.getCourseName());
					course1.setCourseDescription(course.getCourseDescription());
					if (course.getNoOfRegistrations() > 0) {
						course1.setNoOfRegistrations(course1.getNoOfRegistrations() + course.getNoOfRegistrations());
					} else {
						Map<String, String> error = new HashMap<String, String>();
						error.put("No of Registrations Cant be Negative Value", "" + course.getNoOfRegistrations());
						return error;
					}
					course1.setCourseFees(course1.getCourseFees());
					course1.setCourseDuration(course1.getCourseDuration());
					course1.setCourseTag(course1.getCourseTag());
					this.courseRepo.save(course1);
					Map<String, String> message = new HashMap<String, String>();
					message.put("Course Name :" + " Course Discription :" + " No of Registrations :"
							+ " Updated For Course ID =", courseId);
					return message;
				}
			}
		}

		course1.setCourseName(course.getCourseName());
		course1.setCourseDescription(course.getCourseDescription());
		course1.setCourseDuration(course.getCourseDuration());
		course1.setCourseFees(course.getCourseFees());
		if (course.getNoOfRegistrations() > 0) {
			course1.setNoOfRegistrations(course1.getNoOfRegistrations() + course.getNoOfRegistrations());
		} else {
			Map<String, String> error = new HashMap<String, String>();
			error.put("No of Registrations Cant be Negative Value", "" + course.getNoOfRegistrations());
			return error;
		}

		course1.setCourseTag(course.getCourseTag());
		this.courseRepo.save(course1);
		Map<String, String> message = new HashMap<String, String>();
		message.put("Successfully Updated Course details", courseId);
		return message;
	}

	// GET ALL COURSES
	@Override
	public List<CourseDto> getCourseList() {
		List<Course> allCourses = this.courseRepo.findAll();
		List<CourseDto> categoryDtos = allCourses.stream()
				.map((course) -> this.modelMapper.map(course, CourseDto.class)).collect(Collectors.toList());
		return categoryDtos;
	}

	// GET SINGLE COURSE DETAIL BY COURSE ID
	@Override
	public CourseDto getCourseDetail(String courseId) {
		Course course = this.courseRepo.findById(courseId)
				.orElseThrow(() -> new ResourceNotFoundException("courseId", "courseId", courseId));
		return this.modelMapper.map(course, CourseDto.class);
	}

	// DELETE COURSE BY ID
	@Override
	public Map<String, String> deleteCourse(String courseId) {
		Course course = this.courseRepo.findById(courseId)
				.orElseThrow(() -> new ResourceNotFoundException("courseId", "courseId", courseId));

		String statusByCourseId = courseRepo.getStatusByCourseId(courseId);

		if (statusByCourseId != null) {
			if (courseRepo.getStatusByCourseId(courseId).equalsIgnoreCase("Allocated")
					|| courseRepo.getStatusByCourseId(courseId).equalsIgnoreCase("Inprogress")) {
				// flag1 = true;
				Map<String, String> message = new HashMap<String, String>();
				message.put("Failed to delete course details", courseId);
				return message;

			}

			if (courseRepo.getStatusByCourseId(courseId).equalsIgnoreCase("Completed")
					|| courseRepo.getStatusByCourseId(courseId).equalsIgnoreCase("Cancelled")) {
				this.courseRepo.delete(course);
				Map<String, String> message1 = new HashMap<String, String>();
				message1.put("Successfully Deleted Course details for ID =", courseId);
				return message1;
			}

		}
		this.courseRepo.delete(course);
		Map<String, String> message1 = new HashMap<String, String>();
		message1.put("Successfully Deleted Course details for ID =", courseId);
		return message1;

	}
}