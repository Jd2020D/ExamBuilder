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

import com.sun.istack.NotNull;

@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false)
    private Date createdAt;
    private Date updatedAt;
    public Long getId() {
		return id;
	}

	public Answer(Answer answer) {
		this.text = answer.text;
		this.isCorrect = answer.isCorrect;
		this.question = answer.question;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

//	public List<StudentAnswer> getStudentsAnswers() {
//		return studentsAnswers;
//	}
//
//	public void setStudentsAnswers(List<StudentAnswer> studentsAnswers) {
//		this.studentsAnswers = studentsAnswers;
//	}

	@PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
    
    private String text;
    @NotNull
    private Boolean isCorrect;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="question_id")
    private Question question;
    
//    @OneToMany(mappedBy="answer", fetch = FetchType.LAZY)
//    private List<StudentAnswer> studentsAnswers;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "studentAnswers", 
        joinColumns = @JoinColumn(name = "answer_id"), 
        inverseJoinColumns = @JoinColumn(name = "studentQuestion_id")
    )
    private List<Answer> answers;

//    private User teacher;
}
