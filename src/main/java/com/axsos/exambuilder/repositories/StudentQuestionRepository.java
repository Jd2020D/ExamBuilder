package com.axsos.exambuilder.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.axsos.exambuilder.models.StudentQuestion;

@Repository
public interface StudentQuestionRepository extends CrudRepository<StudentQuestion, Long> {
    @Query("SELECT d FROM StudentQuestion d WHERE student_exam_id = ?1 and question_id = ?2")
	StudentQuestion findByExamAndQuestionIds(Long studentExamId,Long questionId);
}
