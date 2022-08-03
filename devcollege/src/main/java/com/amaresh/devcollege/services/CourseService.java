package com.amaresh.devcollege.services;

import java.util.List;
import java.util.Map;

import com.amaresh.devcollege.models.Course;
import com.amaresh.devcollege.payloads.CourseDto;

public interface CourseService {
	CourseDto createCourse(CourseDto courseDto);
	Map<String, String> updateCourse(Course course ,String courseId);
	CourseDto getCourseDetail(String courseId);
	Map<String, String> deleteCourse(String courseId);
	List<CourseDto> getCourseList();
	
}
