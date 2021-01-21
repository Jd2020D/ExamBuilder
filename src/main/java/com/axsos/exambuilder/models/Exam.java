package com.axsos.exambuilder.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "exams")
public class Exam {
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
    public Exam(Exam exam) {
		this.title = exam.title;
		this.markFrom = exam.markFrom;
		this.examDay = exam.examDay;
		this.duration = exam.duration;
		this.isPublished = exam.isPublished;
		this.isExtra = exam.isExtra;
		this.user = exam.user;
	}
    @Size(min=2,max=50)
	private String title;
    private Integer markFrom;
    @FutureOrPresent
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date examDay;
    private String examHour;
    public String getExamHour() {
		return examHour;
	}

	public void setExamHour(String examHour) {
		this.examHour = examHour;
	}
	@Min(5)@Max(120)
    private Integer duration;
    private Boolean isPublished=false;
    private Boolean isExtra=false;
    
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getMarkFrom() {
		return markFrom;
	}

	public void setMarkFrom(Integer markFrom) {
		this.markFrom = markFrom;
	}

	public Date getExamDay() {
		return examDay;
	}

	public void setExamDay(Date startTime) {
		this.examDay = startTime;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Boolean getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}

	public Boolean getIsExtra() {
		return isExtra;
	}

	public void setIsExtra(Boolean isExtra) {
		this.isExtra = isExtra;
	}

	public User getInstructor() {
		return user;
	}

	public void setInstructor(User user) {
		this.user = user;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public List<StudentExam> getStudentExam() {
		return studentExam;
	}

	public void setStudentExam(List<StudentExam> studentExam) {
		this.studentExam = studentExam;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user; // instructor

    @OneToMany(mappedBy="exam", fetch = FetchType.LAZY)
    private List<Question> questions;
    
    @OneToMany(mappedBy="exam", fetch = FetchType.LAZY)
    private List<StudentExam> studentExam;

    public List<Question> getRandomQuestions(int numOfQuestions) {
    	List<Question> temp = this.getQuestions();
    	Collections.shuffle(temp);
    	if(temp.size()<numOfQuestions)
    		return temp.subList(0, temp.size());
    	return temp.subList(0, numOfQuestions);
    }
//    private User teacher;
}
