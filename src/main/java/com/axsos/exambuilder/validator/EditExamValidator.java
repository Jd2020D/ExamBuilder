package com.axsos.exambuilder.validator;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.axsos.exambuilder.models.Exam;
import com.axsos.exambuilder.models.Question;
import com.axsos.exambuilder.repositories.ExamRepository;
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
//        if(exam.getIsPublished()&& (exam.getStudentExam().size()>0))
//        		if(exam.getStudentExam().get(0).getQuestions().size()<1)
//        		{
//        			this.examService.changePublish(exam);
//            		errors.rejectValue("isPublished", "unPublished");
//
//        		}
//        if(exam.getIsPublished()&& (exam.getStudentExam().size()<1))
//    		{
//				this.examService.changePublish(exam);
//        		errors.rejectValue("isPublished", "unPublished");
//
//    		}

//        			
        if(exam.getIsPublished()&&exam.getStudentExam().size()>0)
        	if(exam.getStudentExam().get(0).getQuestions().size()>0) {
        		errors.rejectValue("isPublished", "ExamStarted");
        	}

       	
    }
}
