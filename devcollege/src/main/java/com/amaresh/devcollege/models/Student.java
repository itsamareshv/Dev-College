package com.amaresh.devcollege.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.amaresh.devcollege.util.StringPrefixedSequenceIdGenerator;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="students")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "studentid_seq")
    @GenericGenerator(
            name = "studentid_seq",
            strategy = "com.amaresh.devcollege.util.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "STD"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d")
            })
	
	@Column(name = "student_id")
	private String studentId;
	
	@Column(length = 25,nullable = false)
	private String studentName;
	
	@Column(length = 25,nullable = false)
	private String highestQualification;
	
	@Column(length = 10,nullable = false)
	private String contactNo;
	
	
	private float walletAmount;

	@OneToMany(targetEntity = Enrolment.class, cascade = CascadeType.ALL ,fetch =FetchType.EAGER )
	@JoinColumn(name = "student_id", referencedColumnName = "student_id")
	@JsonBackReference
	private List<Enrolment> enrolment;
	
}



















