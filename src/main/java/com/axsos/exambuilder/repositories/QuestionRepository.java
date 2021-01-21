package com.axsos.exambuilder.repositories;

import com.axsos.exambuilder.models.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {
    List<Question> findAll();

	List<Question> findByExamId(Long examId);
}
