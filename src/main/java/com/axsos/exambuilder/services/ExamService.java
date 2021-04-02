package com.axsos.exambuilder.services;

import java.util.Collections;
import java.util.List;
import java.util.Comparator;

import org.springframework.stereotype.Service;

import com.axsos.exambuilder.models.Answer;
import com.axsos.exambuilder.models.Exam;
import com.axsos.exambuilder.models.Question;
import com.axsos.exambuilder.models.StudentAnswer;
import com.axsos.exambuilder.models.StudentExam;
import com.axsos.exambuilder.models.StudentQuestion;
import com.axsos.exambuilder.models.User;
import com.axsos.exambuilder.repositories.AnswerRepository;
import com.axsos.exambuilder.repositories.ExamRepository;
import com.axsos.exambuilder.repositories.QuestionRepository;
import com.axsos.exambuilder.repositories.StudentAnswerRepository;
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
	    private final StudentAnswerRepository studentAnswerRepository;
	    
	    public ExamService(UserRepository userRepository, ExamRepository examRepository, QuestionRepository questionRepository, AnswerRepository answerRepository, StudentExamRepository studentExamRepository, StudentQuestionRepository studentQuestionRepository, StudentAnswerRepository studentAnswerRepository) {
			this.userRepository = userRepository;
			this.examRepository = examRepository;
			this.questionRepository = questionRepository;
			this.answerRepository = answerRepository;
			this.studentExamRepository = studentExamRepository;
			this.studentQuestionRepository = studentQuestionRepository;
			this.studentAnswerRepository = studentAnswerRepository;
		}
	    public void setExamMarks(StudentExam studentExam) {
	    	Double mark=Double.valueOf(studentExam.getExam().getMarkFrom());
	    	List<StudentQuestion> studentQuestions=studentExam.getStudentQuestions();
	    	Double questionMark=mark/studentQuestions.size();

	    	for(int i=0;i<studentQuestions.size();i++) {
				StudentAnswer studentAnswer = null ;
				try{
					studentAnswer=this.studentAnswerRepository.findById(studentQuestions.get(i).getChosedAnswerId()).orElse(null);
				}catch(Exception e){

				}
	    		if(studentAnswer!=null){
					if(!studentAnswer.getAnswer().getIsCorrect())
					mark-=questionMark;
				}
				else
					mark-=questionMark;

					
	    	}
	    	studentExam.setTotalMarks(mark);
			studentExam.setSubmitted(true);
	    	this.studentExamRepository.save(studentExam);
	    }
	    public boolean  isAllQuestionsAnswerd(StudentExam studentExam) {
	    	List<StudentQuestion> studentQuestions=studentExam.getStudentQuestions();
	    	for(int i=0;i<studentQuestions.size();i++) {
	    		if(studentQuestions.get(i).getChosedAnswerId()==null)
	    			return false;
	    	}
	    	return true;
	    }
	    public void choseAnswer(StudentQuestion studentQuestion,Long chosedAnswerId) {
			if(this.studentAnswerRepository.existsById(chosedAnswerId)){	    	
				studentQuestion.setChosedAnswerId(chosedAnswerId);
				this.studentQuestionRepository.save(studentQuestion);
			}	    
		}
	    public StudentAnswer getById(Long id) {
	    	return this.studentAnswerRepository.findById(id).orElse(null);
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
	    public void deleteStudentExam(StudentExam studentExam) {
	    	this.studentQuestionRepository.deleteAll(studentExam.getStudentQuestions());
	    	this.studentExamRepository.delete(studentExam);
	    }
	    public void deleteQuestion(Question question) {
	    	this.studentQuestionRepository.deleteAll(question.getStudentQuestions());
	    	this.answerRepository.deleteAll(question.getAnswers());
	    	this.questionRepository.delete(question);
	    }
	    public void deleteExam(Exam exam) {
			for( Question question : exam.getQuestions() )
				this.deleteQuestion(question);
	    	// this.questionRepository.deleteAll(exam.getQuestions());
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
	    public StudentExam getStudentExamByIds(Long examId,Long studentId) {
	    	return this.studentExamRepository.findStudentExamByIds(examId, studentId);
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
		
		public StudentExam createStudentExam(Exam exam,User student) {
			StudentExam studentExam= new StudentExam(exam, student);
			return this.studentExamRepository.save(studentExam);
		}
		public StudentExam createStudentQuestions(Exam exam,StudentExam studentExam, int numOfQuestions) {
			studentExam.setQuestions(exam.getRandomQuestions(numOfQuestions));
			return this.studentExamRepository.save(studentExam);
		}
		public void addRandomAnswersToExam(StudentExam studentExam,int numOfAnswers) {
			List<StudentQuestion> questions  = studentExam.getStudentQuestions();
			for (int i=0;i<questions.size();i++) {
				this.createRandomAnswers(questions.get(i), numOfAnswers);
			}
			
		
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

		public List<StudentExam> getExamsBySubmit(Long studentId,Boolean submit){
			List <StudentExam> studentExams=this.studentExamRepository.findStudentExamByIdAnsSubmitValue(studentId, submit) ;		  
			Collections.sort(studentExams, new Comparator<StudentExam>() {
				public int compare(StudentExam o1, StudentExam o2) {
					return o2.getExam().getExamDay().compareTo(o1.getExam().getExamDay());
				}
			  });
	
			return studentExams ;
		}
		public List<Exam> getAllExtraExams(){
			return this.examRepository.allExtras(true);
		}
		
}
