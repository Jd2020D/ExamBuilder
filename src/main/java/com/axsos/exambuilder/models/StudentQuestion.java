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
    
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="student_answer_id")
	private StudentAnswer chosedAnswer;
	 ///    private User teacher;
}
