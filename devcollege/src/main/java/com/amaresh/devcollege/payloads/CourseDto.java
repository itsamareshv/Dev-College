package com.amaresh.devcollege.payloads;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {

	private String courseId;

	@NotEmpty
	@Size(min = 2, message = "Course Name Must Be minimum 2 Characters")
	private String courseName;

	@NotEmpty
	@Size(min = 5, message = "Course Description Must Be minimum 5 Characters")
	private String courseDescription;

	@Min(value = 5, message = "No Of Registrations Should be atleast 5 ")
	@Max(value = 50, message = "No Of Registrations Should be less than 50 ")
	//@Positive(message = "Value Cant Be Negative")
	//@Digits(message="Invalid number.", fraction = 0, integer = 0)
	private int noOfRegistrations;

	@Min(value = 100, message = "Course Fees Should be  greater than 100 Rs")
	@Positive(message = "Value Cant Be Negative")
	private float courseFees;

	@Min(value = 1, message = "Course Duration  Should be atleast 1 Hour ")
	@Max(value = 100, message = "Course Duration Should be less than 100 Hours ")
	@Positive(message = "Value Cant Be Negative")
	private int courseDuration;

	@NotEmpty
	@Size(min = 2, message = "Course Tag Must Be minimum 5 Characters")
	private String courseTag;
}
