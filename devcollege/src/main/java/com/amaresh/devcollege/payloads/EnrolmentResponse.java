package com.amaresh.devcollege.payloads;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrolmentResponse {

	private String enrolmentId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SS")
	private Date courseStartDT;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SS")
	private Date cuurseEndDT;
	private String status;
	private String course_id;
	private String student_id;
	private String studentName;
	private String courseName;
	private String courseLink;
	private String studentLink;

}
