package com.axsos.exambuilder.validator;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.axsos.exambuilder.models.Exam;
import com.axsos.exambuilder.services.ExamService;


@Component
public class EditExamValidator implements Validator {
	private final ExamService examService;
	EditExamValidator(ExamService examService){
		this.examService=examService;
	}
    @Override
    public boolean supports(Class<?> clazz) {
        return Exam.class.equals(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {

       Exam exam = (Exam) object;
       Date now =new Date();

    //    if(exam.getExamDay().compareTo(now) <=0 )
    //    {
   	// 	errors.rejectValue("isPublished", "ExamStarted");       	
    //    }

    }
}
