package com.axsos.exambuilder.services;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.axsos.exambuilder.models.Answer;
import com.axsos.exambuilder.models.Exam;
import com.axsos.exambuilder.models.Question;
import com.axsos.exambuilder.models.StudentExam;
import com.axsos.exambuilder.models.StudentQuestion;
import com.axsos.exambuilder.models.User;
import com.axsos.exambuilder.repositories.AnswerRepository;
import com.axsos.exambuilder.repositories.ExamRepository;
import com.axsos.exambuilder.repositories.QuestionRepository;
import com.axsos.exambuilder.repositories.StudentExamRepository;
import com.axsos.exambuilder.repositories.StudentQuestionRepository;
import com.axsos.exambuilder.repositories.UserRepository;

@Service
public class ExamService {
	    private final UserRepository userRepository;
	    private final ExamRepository examRepository;
	    private final QuestionRepository questionRepository;
	    private final AnswerRepository answerRepository;
	    private final StudentExamRepository studentExamRepository;
	    private final StudentQuestionRepository studentQuestionRepository;
	    
	    public ExamService(UserRepository userRepository, ExamRepository examRepository, QuestionRepository questionRepository, AnswerRepository answerRepository, StudentExamRepository studentExamRepository, StudentQuestionRepository studentQuestionRepository) {
			this.userRepository = userRepository;
			this.examRepository = examRepository;
			this.questionRepository = questionRepository;
			this.answerRepository = answerRepository;
			this.studentExamRepository = studentExamRepository;
			this.studentQuestionRepository = studentQuestionRepository;
		}
	    public Exam changePublish(Exam exam) {
	    	exam.setIsPublished(!exam.getIsPublished());
	    	return this.examRepository.save(exam);
	    }
	    public Question updateQuestion(Question question,Question update) {
	    	question.setQuestionText(update.getQuestionText());
	    	return this.questionRepository.save(question);
	    }
	    public Answer updateAnswer(Answer answer,Answer update) {
	    	update=new Answer(update);
	    	answer.setIsCorrect(update.getIsCorrect());
	    	answer.setText(update.getText());
	    	return this.answerRepository.save(answer);
	    }
	    public void deleteAnswer(Answer answer) {
	    	this.answerRepository.delete(answer);
	    }
	    public Answer getAnswerById(Long id,Long questionId) {
	    	return this.answerRepository.findByIdAndQuestionId(id, questionId);
	    }
	    public void deleteQuestion(Question question) {
	    	this.studentQuestionRepository.deleteAll(question.getStudentQuestions());
	    	this.answerRepository.deleteAll(question.getAnswers());
	    	this.questionRepository.delete(question);
	    }
	    public void deleteExam(Exam exam) {
	    	this.questionRepository.deleteAll(exam.getQuestions());
	    	this.studentExamRepository.deleteAll(exam.getStudentExam());
	    	this.examRepository.delete(exam);
	    }
	    public Exam updateExam(Exam exam,Exam update) {
	    	update=new Exam(update);
	    	exam.setTitle(update.getTitle());
	    	exam.setExamDay(update.getExamDay());
	    	exam.setMarkFrom(update.getMarkFrom());
	    	exam.setDuration(update.getDuration());
	    	exam.setIsExtra(update.getIsExtra());
	    	return this.examRepository.save(exam);
	    }
	    public Exam getExamByItsId(Long id) {
	    	return this.examRepository.findById(id).orElse(null);
	    }
	    public Exam getExamById(Long id,Long instructorId) {
	    	return this.examRepository.findByIdAndUserId(id, instructorId);
	    }
	    public StudentExam getStudentExamById(Long id) {
	    	return this.studentExamRepository.findById(id).orElse(null);
	    }
	    
	    public Question getQuestionById(Long id,Long examId) {
	    	return this.questionRepository.findByIdAndExamId(id,examId);
	    }
	    public StudentQuestion getStudentQuestion(Long studentExamId,Long questionId) {
	    	return this.studentQuestionRepository.findByExamAndQuestionIds(studentExamId,questionId);
	    }
	    public List<Exam> allInstructorExams(User instructor){
	    	return (List<Exam>) this.examRepository.findAll();
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
		
		public StudentQuestion createRandomAnswers(StudentQuestion question,int numOfAnswers){
				List<Answer> RandomAnswers =this.getRandomAnswers(question
						.getQuestion()
						.getId(),
						true, 1);
				RandomAnswers.addAll(this.getRandomAnswers(question
						.getQuestion()
						.getId(), 
						false, numOfAnswers-1));

				question.setAnswers(RandomAnswers);
			
			return this.studentQuestionRepository.save(question);
		}
		public List<Answer> getRandomAnswers(Long questionId,Boolean isCorrect,int numOfAnswers) {
	    	List<Answer> answers = this.allAnswers(questionId, isCorrect);
	    	Collections.shuffle(answers);
	    	if(answers.size()<numOfAnswers)
	    		return answers.subList(0, answers.size());
	    	return answers.subList(0, numOfAnswers);
	    }

		
}
