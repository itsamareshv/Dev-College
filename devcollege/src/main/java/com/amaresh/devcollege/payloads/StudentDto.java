package com.amaresh.devcollege.payloads;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

	private String studentId;

	@NotEmpty
	@Size(min = 4, message = "Student Name Must Be minimum 4 Characters")
	@Pattern(regexp="^[A-Za-z]+[A-Za-z ]*$",message = "Input Should Be Alphabets")
	private String studentName;

	@NotEmpty
	@Size(min = 2, message = "Student Qualification Must Be minimum 2 Characters")
	private String highestQualification;

	@NotEmpty
	@Pattern(regexp="(^$|[0-9]{10})",message = "Input Should Be Digits and size should be 10 digits")
	//|	Find a match for any one of the patterns separated by | as in: cat|dog|fish
	//^	Finds a match as the beginning of a string as in: ^Hello
	//$	Finds a match at the end of the string as in: World$
	private String contactNo;

	
	@Min(value=1,message="Wallet Amount Should be atleast 1 Rs")
	@Max(value=20000,message="Cant add greater than 20000 Rs")
	private float walletAmount;
}
