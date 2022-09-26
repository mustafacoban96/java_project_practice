package com.ilkdeneme.demo.student;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


// add service , restController
// because tihs repository is responsible for data access.
// We want to use this interface inside the Service class.
//So we create first Interface (Student Repository) then autowired inside the Service class.
@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{ // the Long for id.
	
	
	// SELECT * FROM student WHERE email = ?
	@Query("SELECT s FROM Student s WHERE s.email = ?1")
	Optional<Student> findStudentByEmail(String email);
	
}
