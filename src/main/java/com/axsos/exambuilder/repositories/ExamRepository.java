package com.axsos.exambuilder.repositories;

import com.axsos.exambuilder.models.Exam;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends CrudRepository<Exam, Long> {
}
