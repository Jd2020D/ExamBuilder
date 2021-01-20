package com.axsos.exambuilder.repositories;

import com.axsos.exambuilder.models.StudentQuestion;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentQuestionRepository extends CrudRepository<StudentQuestion, Long> {
}
