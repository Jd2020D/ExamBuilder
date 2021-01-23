package com.axsos.exambuilder.repositories;

import com.axsos.exambuilder.models.Exam;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends CrudRepository<Exam, Long> {
    @Query("SELECT d FROM Exam d WHERE id = ?1 and user_id = ?2")
	Exam findByIdAndUserId(Long examId,Long instructorId);

}
