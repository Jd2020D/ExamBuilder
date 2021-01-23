package com.axsos.exambuilder.repositories;

import com.axsos.exambuilder.models.Exam;
import com.axsos.exambuilder.models.Question;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {
    List<Question> findAll();
    
    @Query("SELECT d FROM Question d WHERE id = ?1 and exam_id = ?2")
	Question findByIdAndExamId(Long questionId,Long examId);

	List<Question> findByExamId(Long examId);
}
