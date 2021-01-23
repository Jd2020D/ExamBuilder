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
@Entity
@Table(name = "studentExams")
public class StudentExam {
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
    public StudentExam() {}
    public StudentExam( Exam exam,User user) {
		this.user = user;
		this.exam = exam;
	}

	private Double totalMarks;
    private Boolean submitted=false;
    public Boolean getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Boolean submitted) {
		this.submitted = submitted;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user; //student
    
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

	public Double getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(Double totalMarks) {
		this.totalMarks = totalMarks;
	}


	public User getStudent() {
		return user;
	}

	public void setStudent(User user) {
		this.user = user;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public List<StudentQuestion> getStudentQuestions() {
		return studentQuestions;
	}

	public void setStudentQuestions(List<StudentQuestion> studentQuestions) {
		this.studentQuestions = studentQuestions;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="exam_id")
    private Exam exam;
    
    @OneToMany(mappedBy="studentExam", fetch = FetchType.LAZY)
    private List<StudentQuestion> studentQuestions;
	
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "studentQuestions", 
        joinColumns = @JoinColumn(name = "studentExam_id"), 
        inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private List<Question> questions;
	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

    
//    private User teacher;
}
