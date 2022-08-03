package com.amaresh.devcollege.models;

import java.util.Date;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.amaresh.devcollege.util.StringPrefixedSequenceIdGenerator;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "enrolments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Enrolment {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enrolmentseq_id")
	@GenericGenerator(name = "enrolmentseq_id", strategy = "com.amaresh.devcollege.util.StringPrefixedSequenceIdGenerator", parameters = {
			@Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
			@Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "EN"),
			@Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d") })
	private String enrolmentId;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SS")
	private Date courseStartDT;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SS")
	private Date cuurseEndDT;
	private String status;

	private String course_id;
	private String student_id;


}
