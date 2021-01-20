package com.axsos.exambuilder.repositories;

import com.axsos.exambuilder.models.StudentAnswer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentAnswerRepository extends CrudRepository<StudentAnswer, Long> {
}
