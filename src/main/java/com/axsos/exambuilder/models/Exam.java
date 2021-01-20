package com.axsos.exambuilder.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

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
    
    private String title;
    private Integer markFrom;
    private Date startTime;
    @Min(5)@Max(120)
    private Integer duration;
    private Boolean isPublished=false;
    private Boolean isExtra=false;
    
    @OneToMany(mappedBy="exam", fetch = FetchType.LAZY)
    private List<Question> questions;
    
    @OneToMany(mappedBy="exam", fetch = FetchType.LAZY)
    private List<StudentExam> studentExam;

//    private User teacher;
}
