package com.axsos.exambuilder.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.axsos.exambuilder.models.Answer;
import com.axsos.exambuilder.models.StudentExam;
import com.axsos.exambuilder.models.User;

@Repository
public interface StudentExamRepository extends CrudRepository<StudentExam, Long> {
//    @Query("SELECT d FROM (SELECT s FROM User s JOIN s.roles r where r.name = 'ROLE_STUDENT') User d WHERE d.user_id NOT IN (SELECT e FROM StudentExam e WHERE exam_id = ?2 )")
//	List<User> findOtherStudents(Long examId);
	
    @Query("SELECT d FROM StudentExam d WHERE exam_id = ?1 and user_id = ?2")
	StudentExam findStudentExamByIds(Long examId,Long studentId);
    @Query("SELECT d FROM StudentExam d WHERE user_id = ?1 and submitted = ?2")
	List<StudentExam> findStudentExamByIdAnsSubmitValue(Long studentId,Boolean submitted);
    @Query(value = "select username , total_marks from users u, student_exams WHERE user_id =u.id and exam_id =1 ORDER by total_marks desc LIMIT 3" , nativeQuery = true)
    List<Object[]> top5();


}
