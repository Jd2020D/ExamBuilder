package com.axsos.exambuilder.validator;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.axsos.exambuilder.models.StudentExam;
import com.axsos.exambuilder.services.ExamService;


@Component
public class StudentExamValidator implements Validator {
    static final long ONE_MINUTE_IN_MILLIS=60000;//millisecs

	private final ExamService examService;
	StudentExamValidator(ExamService examService){
		this.examService=examService;
	}
    @Override
    public boolean supports(Class<?> clazz) {
        return StudentExam.class.equals(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {

        StudentExam studentExam = (StudentExam) object;
        Date now =new Date();
        Date examStartDate = studentExam.getExam().getExamDay();
        Integer examDuration = studentExam.getExam().getDuration();
        Date examFinishDate =new Date(examStartDate.getTime() + (examDuration * ONE_MINUTE_IN_MILLIS));
        if(!studentExam.getExam().getIsPublished())
            errors.rejectValue("submitted", "unPublishedExam"); 

       if(examStartDate.compareTo(now) >0)
       {
   		errors.rejectValue("submitted", "EearlyJoin"); 
       }
       else if(studentExam.getSubmitted())
       {
   		errors.rejectValue("submitted", "SubmittedJoin"); 
       }
    
       else if(examFinishDate.compareTo(now)<0)
       {
   		errors.rejectValue("submitted", "LatelyJoin");
       }

    }
}
