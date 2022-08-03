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
public class EnrolmentDto {

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date courseStartDT;

	private String student_id;

	private String course_id;

}
