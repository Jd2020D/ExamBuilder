package com.axsos.exambuilder.validator;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.axsos.exambuilder.models.Exam;
import com.axsos.exambuilder.models.Question;
import com.axsos.exambuilder.services.ExamService;


@Component
public class PublishExamValidator implements Validator {
	private final ExamService examService;
	PublishExamValidator(ExamService examService){
		this.examService=examService;
	}
    @Override
    public boolean supports(Class<?> clazz) {
        return Exam.class.equals(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        Exam exam = (Exam) object;
        boolean first=false,sec=false,third=false;

        List<Question> questions=exam.getQuestions();
        if(questions.size()<5)
    		errors.rejectValue("isPublished", "LessThanFive");
        for(int i=0;i<questions.size();i++) {
        	if(!first&&this.examService.getRandomAnswers(questions.get(i).getId(), true, 1).size()<1)
        		{
        			first=true;
        			errors.rejectValue("isPublished", "OneCorrect");
        		}
        	if(!sec&&this.examService.getRandomAnswers(questions.get(i).getId(), false, 1).size()<1)
        		{
        			sec=true;
        			errors.rejectValue("isPublished", "OneWrong");
        		}
        	if(questions.get(i).getAnswers().size()>2) {
            	if(!third&&this.examService.getRandomAnswers(questions.get(i).getId(), false, 3).size()<3)
            		{
            			third=true;
            			errors.rejectValue("isPublished", "ThreeWrongInMany");
            		}
        	}
        	
        }	
    }
}
