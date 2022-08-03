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
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.amaresh.devcollege.util.StringPrefixedSequenceIdGenerator;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_idseq")
	@GenericGenerator(name = "course_idseq", strategy = "com.amaresh.devcollege.util.StringPrefixedSequenceIdGenerator", parameters = {
			@Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
			@Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "CRS"),
			@Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d") })
	@Column(name = "course_id")
	private String courseId;

	@Column(length = 100, nullable = false)
	private String courseName;

	@Column(length = 500, nullable = false)
	private String courseDescription;

	@Column(length = 2, nullable = false)
	private int noOfRegistrations;

	private float courseFees;

	@Column(length = 2, nullable = false)
	private int courseDuration;

	private String courseTag;

	@OneToMany(targetEntity = Enrolment.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "course_id", referencedColumnName = "course_id")
	@JsonBackReference
	private List<Enrolment> enrolment;

}
