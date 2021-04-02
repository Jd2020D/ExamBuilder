package com.axsos.exambuilder.controllers;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.axsos.exambuilder.models.Exam;
import com.axsos.exambuilder.models.User;
import com.axsos.exambuilder.services.ExamService;
import com.axsos.exambuilder.services.UserService;


@RestController 
@RequestMapping("/instructor/api")
public class ExamApiController {
	private final ExamService examService;
	private final UserService userService;
	public ExamApiController(ExamService examService, UserService userService) {
		this.examService = examService;
		this.userService = userService;
	}
	
	public boolean isIdForTheCurrentUser(User user,Long id) {
		return user.getId()==this.userService.findById(id).getId();
	}
//
	@RequestMapping(value = "/{id}/exams",method=RequestMethod.POST)
    public Exam createExamForm(@PathVariable Long id ,@RequestParam(name = "title") String title ,
    		@RequestParam(name = "markFrom") Integer markFrom,
    		@RequestParam(name = "examDay") Date examDay,
    		@RequestParam(name = "duration") Integer duration,
    		@RequestParam(name = "isExtra") Boolean isExtra,
    		@RequestParam(name = "examHour") Date examHour,
    		Principal principal)
    {
		User instructor =this.userService.findByUsername(principal.getName());
		if(isIdForTheCurrentUser(instructor, id))
    		return this.examService.createExamForm(new Exam(title, markFrom, examDay, examHour, duration, isExtra), instructor);	
		return null;
    }
	@RequestMapping("/2/exams/1/test/test")
	public long testets() {
		Date d =new Date();
//		return d;
//        long minutes  = TimeUnit.MILLISECONDS.toMinutes(d.getTime()-this.examService.getExamById(5L,2L).getExamDay().getTime()); 
		return this.examService.getExamById(5L,2L).getExamDay().compareTo(d);
//        return minutes;
	}



//	@RequestMapping("/{id}/exams")
//	public List<Exam> instructorExams(@PathVariable Long id) {
//		
//		User instructor =this.userService.findById(id);
//		if(instructor==null)
//			return null;
//		List<Exam> exam =this.examService.allInstructorExams(instructor);
//		
//	}

	
}
