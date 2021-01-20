package com.axsos.exambuilder.repositories;

import com.axsos.exambuilder.models.StudentExam;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentExamRepository extends CrudRepository<StudentExam, Long> {
}
