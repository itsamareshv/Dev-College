package com.amaresh.devcollege.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amaresh.devcollege.models.Course;
import com.amaresh.devcollege.payloads.CourseDto;
import com.amaresh.devcollege.services.CourseService;

@RestController
@RequestMapping("/course")
public class CourseController {

	@Autowired
	private CourseService courseService;

	private String courseId;

	// ADDING COURSE
	@PostMapping("/add")
	public ResponseEntity<Map<String, String>> createCourse(@Valid @RequestBody CourseDto courseDto) {
		CourseDto createCourse = this.courseService.createCourse(courseDto);
		courseId = createCourse.getCourseId();
		Map<String, String> message = new HashMap<String, String>();
		message.put("Successfully Added Course details for =", courseId);
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}

	// UPDATING COURSE
	@PutMapping("/update/{courseId}")
	public ResponseEntity<Map<String, String>> updateCourse(@Valid @RequestBody Course course,
			@PathVariable("courseId") String courseId) {
		Map<String, String> updateCourse = this.courseService.updateCourse(course, courseId);
		return new ResponseEntity<>(updateCourse, HttpStatus.OK);
	}

	// GET ALL COURSES
	@GetMapping("/getAll")
	public ResponseEntity<List<CourseDto>> getCourseList() {
		List<CourseDto> courseList = this.courseService.getCourseList();
		return ResponseEntity.ok(courseList);
	}

	// GET COURSE BY COURSE ID
	@GetMapping("/get/{courseId}")
	public ResponseEntity<CourseDto> getCourseDetail(@PathVariable String courseId) {
		CourseDto courseDto = this.courseService.getCourseDetail(courseId);
		return new ResponseEntity<CourseDto>(courseDto, HttpStatus.OK);
	}

	// DELETE COURSE BY ID
	@DeleteMapping("/delete/{courseId}")
	public ResponseEntity<Map<String, String>> deleteCourse(@PathVariable String courseId) {
		Map<String, String> deleteCourse = this.courseService.deleteCourse(courseId);
		return new ResponseEntity<>(deleteCourse, HttpStatus.OK);
	}

}
