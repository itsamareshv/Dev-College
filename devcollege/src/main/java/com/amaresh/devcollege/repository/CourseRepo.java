package com.amaresh.devcollege.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amaresh.devcollege.models.Course;

public interface CourseRepo extends JpaRepository<Course, String> {

	
}
