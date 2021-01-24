package com.axsos.exambuilder.repositories;

import com.axsos.exambuilder.models.StudentExam;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentExamRepository extends CrudRepository<StudentExam, Long> {

@Query(value = "select username , total_marks from users u, student_exams WHERE user_id =u.id and exam_id =1 ORDER by total_marks desc LIMIT 3" , nativeQuery = true)
List<Object[]> top5();

}
