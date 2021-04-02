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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
@Entity
@Table(name = "studentQuestions")
public class StudentQuestion {
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="studentExam_id")
    private StudentExam studentExam;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="question_id")
    private Question question;

    @OneToMany(mappedBy="studentQuestion", fetch = FetchType.LAZY)
    private List<StudentAnswer> studentsAnswers;
    
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

	public StudentExam getStudentExam() {
		return studentExam;
	}

	public void setStudentExam(StudentExam studentExam) {
		this.studentExam = studentExam;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public List<StudentAnswer> getStudentsAnswers() {
		return studentsAnswers;
	}

	public void setStudentsAnswers(List<StudentAnswer> studentsAnswers) {
		this.studentsAnswers = studentsAnswers;
	}

	public Long getChosedAnswerId() {
		return chosedAnswerId;
	}

	public void setChosedAnswerId(Long chosedAnswerId) {
		this.chosedAnswerId = chosedAnswerId;
	}
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "studentAnswers", 
        joinColumns = @JoinColumn(name = "studentQuestion_id"), 
        inverseJoinColumns = @JoinColumn(name = "answer_id")
    )
    private List<Answer> answers;

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	private Long chosedAnswerId=null;
	 ///    private User teacher;
	

}
