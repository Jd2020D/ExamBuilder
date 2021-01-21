package com.axsos.exambuilder.repositories;

import com.axsos.exambuilder.models.Answer;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, Long> {


	List<Answer> findByQuestionIdAndIsCorrect(Long questionId, Boolean isCorrect);
}
