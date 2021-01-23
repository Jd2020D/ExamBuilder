package com.axsos.exambuilder.models;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
import javax.persistence.Transient;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
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
    public Exam() {}
    public Exam(String title, Integer markFrom,Date examDay, Date examHour,
    			Integer duration, Boolean isExtra) {
		this.title = title;
		this.markFrom = markFrom;
		this.examDay = examDay;
		this.examDay.setHours(examHour.getHours());
		this.examDay.setMinutes(examHour.getMinutes());
		this.duration = duration;
		this.isExtra = isExtra;
	}

	public Exam(Exam exam) {
		this.title = exam.title;
		this.markFrom = exam.markFrom;
		this.examDay = exam.examDay;
		this.examDay.setHours(exam.examHour.getHours());
		this.examDay.setMinutes(exam.examHour.getMinutes());
		this.duration = exam.duration;
		this.isExtra = exam.isExtra;
		this.user = exam.user;
	}
    @Size(min=2,max=50)
	private String title;
	@Min(5)@Max(100)@NotNull
    private Integer markFrom;
    @FutureOrPresent@NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date examDay;
    @DateTimeFormat(pattern="HH:mm")
    @Transient	
    private Date examHour;
    public Date getExamHour() {
		return examDay;
	}

	public void setExamHour(Date examHour) {
		this.examHour = examHour;
	}
	@Range(min=5,max=120)
	@NotNull
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
//		startTime.setHours(this.examHour.getHours());
//		startTime.setMinutes(this.examHour.getMinutes());
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
    public String getExamTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm aa");  

    	return formatter.format(this.examDay);
    	
   }
    public Date getExamTimeAsDate() {

    	return this.examDay;
    	
   }

    public String getExamDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");  

    	return formatter.format(this.examDay);
    	
   }
//    private User teacher;
}
