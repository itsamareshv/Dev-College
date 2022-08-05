package com.amaresh.devcollege.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.amaresh.devcollege.models.Course;

public interface CourseRepo extends JpaRepository<Course, String> {

	@Query(nativeQuery = true, value = "SELECT status FROM enrolments where course_id=:courseId")
	public String getStatusByCourseId(@Param("courseId") String courseId);

	@Query(nativeQuery = true, value = "SELECT course_tag FROM courses where course_id=:courseId")
	public String getCourseTagByCourseId(@Param("courseId") String courseId);
}
