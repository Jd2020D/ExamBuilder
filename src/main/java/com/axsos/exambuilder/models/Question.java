package com.axsos.exambuilder.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false)
    private Date createdAt;
    private Date updatedAt;
    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
    public Question() {}
    
    public Question(Question question) {
		this.questionText = question.questionText;
		this.exam = question.exam;
	}
    
    @Size(min=3,max=250)
	private String questionText;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="exam_id")
    private Exam exam;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String text) {
		this.questionText = text;
	}
	
	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
    @OneToMany(mappedBy="question", fetch = FetchType.LAZY)
    private List<StudentQuestion> studentQuestions;

	public List<StudentQuestion> getStudentQuestions() {
		return studentQuestions;
	}

	public void setStudentQuestions(List<StudentQuestion> studentQuestions) {
		this.studentQuestions = studentQuestions;
	}

	@OneToMany(mappedBy="question", fetch = FetchType.LAZY)
    private List<Answer> answers;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "studentQuestions", 
        joinColumns = @JoinColumn(name = "question_id"), 
        inverseJoinColumns = @JoinColumn(name = "studentExam_id")
    )
    private List<StudentExam> studentExams;
	public List<StudentExam> getStudentExams() {
		return studentExams;
	}

	public void setStudentExams(List<StudentExam> studentExams) {
		this.studentExams = studentExams;
	}
	


    
//    private User teacher;
}
