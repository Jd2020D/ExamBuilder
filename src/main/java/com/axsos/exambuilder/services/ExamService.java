package com.axsos.exambuilder.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.axsos.exambuilder.models.Answer;
import com.axsos.exambuilder.models.Exam;
import com.axsos.exambuilder.models.Question;
import com.axsos.exambuilder.models.StudentExam;
import com.axsos.exambuilder.models.User;
import com.axsos.exambuilder.repositories.AnswerRepository;
import com.axsos.exambuilder.repositories.ExamRepository;
import com.axsos.exambuilder.repositories.QuestionRepository;
import com.axsos.exambuilder.repositories.StudentExamRepository;
import com.axsos.exambuilder.repositories.UserRepository;

@Service
public class ExamService {
	    private final UserRepository userRepository;
	    private final ExamRepository examRepository;
	    private final QuestionRepository questionRepository;
	    private final AnswerRepository answerRepository;
	    private final StudentExamRepository studentExamRepository;
	    
	    public ExamService(UserRepository userRepository, ExamRepository examRepository, QuestionRepository questionRepository, AnswerRepository answerRepository, StudentExamRepository studentExamRepository) {
			this.userRepository = userRepository;
			this.examRepository = examRepository;
			this.questionRepository = questionRepository;
			this.answerRepository = answerRepository;
			this.studentExamRepository = studentExamRepository;
		}

		public Exam createExamForm(Exam exam,User instructor) {
			exam.setInstructor(instructor);
	    	return this.examRepository.save(new Exam(exam));
	    }
		public Exam publishExam(Exam exam) {
			if (exam.getIsPublished())
				return exam;
			exam.setIsPublished(true);
			return this.examRepository.save(exam);
		}
		
		public Question addQuestion(Question question , Exam exam) {
			question.setExam(exam);
			return this.questionRepository.save(new Question(question));
		}
		public List<Question> allExamQuestions(Long examId){
			return this.questionRepository.findByExamId(examId);
		}
		
		public Answer addAnswer(Answer answer,Question question) {
			answer.setQuestion(question);
			return this.answerRepository.save(new Answer(answer));
		}
		
		public List<Answer> allAnswers(Long questionId,Boolean isCorrect){
			return this.answerRepository.findByQuestionIdAndIsCorrect(questionId,isCorrect);
		}
		
		public StudentExam createStudentExam(Exam exam,User student,int numOfQuestions) {
			StudentExam studentExam= new StudentExam(exam, student);
			studentExam.setQuestions(exam.getRandomQuestions(numOfQuestions));
			return this.studentExamRepository.save(studentExam);
		}
//		public List<Question> createRandomAnswers(List<Question> questions){
//			questions.get(0).
//		}
		
}
