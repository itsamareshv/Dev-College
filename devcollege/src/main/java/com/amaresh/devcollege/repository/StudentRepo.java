package com.amaresh.devcollege.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.amaresh.devcollege.models.Student;

public interface StudentRepo extends JpaRepository<Student, String> {

	@Query(nativeQuery = true, value = "SELECT wallet_amount from students where student_id= :studentId")
	Student findWalletAmountById(String studentId);

}
