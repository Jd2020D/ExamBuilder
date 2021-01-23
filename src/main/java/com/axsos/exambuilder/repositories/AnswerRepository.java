package com.axsos.exambuilder.repositories;

import com.axsos.exambuilder.models.Answer;
import com.axsos.exambuilder.models.Exam;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, Long> {
    @Query("SELECT d FROM Answer d WHERE id = ?1 and question_id = ?2")
	Answer findByIdAndQuestionId(Long answerId,Long questionId);

    @Query("SELECT d FROM Answer d WHERE question_id = ?1 and isCorrect = ?2")
	List<Answer> findByQuestionIdAndIsCorrect(Long questionId, Boolean isCorrect);
}
