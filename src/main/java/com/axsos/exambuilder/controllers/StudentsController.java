package com.axsos.exambuilder.controllers;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Comparator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.axsos.exambuilder.models.AllRoles;
import com.axsos.exambuilder.models.StudentAnswer;
import com.axsos.exambuilder.models.StudentExam;
import com.axsos.exambuilder.models.User;
import com.axsos.exambuilder.models.Exam;
import com.axsos.exambuilder.services.ExamService;
import com.axsos.exambuilder.services.StudentService;
import com.axsos.exambuilder.services.UserService;
import com.axsos.exambuilder.validator.UserValidator;
import com.axsos.exambuilder.validator.StudentExamValidator;
@RequestMapping("/student")
@Controller
public class StudentsController {


    private final UserValidator userValidator;
    private  final StudentService studentService;
	private final UserService userService;
	private final ExamService examService;
	private final StudentExamValidator studentExamValidator;

    public StudentsController( StudentService studentService, UserValidator userValidator,
	 UserService userService, ExamService examService,
	 StudentExamValidator studentExamValidator) {
        this.studentService= studentService;
        this.userValidator = userValidator;
		this.userService = userService;
		this.examService = examService;
		this.studentExamValidator = studentExamValidator;
    }
	public boolean isIdForTheCurrentUser(User user,Long id) {
		return user.getId()==this.userService.findById(id).getId();
	}





    
    


	@RequestMapping(value = "/exams")
	public String redirectToExams(Principal principal) {
		User current =this.userService.findByUsername(principal.getName());
		return "redirect:/student/"+current.getId()+"/exams";
	}
	@RequestMapping(value = "/{id}/exams")
    public String viewStudentExams(@PathVariable Long id,
    		Principal principal,HttpServletRequest request,
    		Model model,ModelMap modelMap)
    {
		User student = this.userService.findById(id);
		if(student==null)return redirectToExams(principal);
		User current =this.userService.findByUsername(principal.getName());
		if(!isIdForTheCurrentUser(current, id) &&!(request.isUserInRole("ROLE_ADMIN")))
			return redirectToExams(principal);
		model.addAttribute("student",student);
		model.addAttribute("nonSubmittedExams",this.examService.getExamsBySubmit(id, false));
		model.addAttribute("submittedExams",this.examService.getExamsBySubmit(id, true));
		// Date ExamFinishDate =new Date(this.examService.getExamsBySubmit(id, false).get(0).getExam().getExamDay().getTime() + (this.examService.getExamsBySubmit(id, false).get(0).getExam().getDuration() * 60000));
		// model.addAttribute("ExamFinishDate", ExamFinishDate);
		// model.addAttribute("ExamStartDate", this.examService.getExamsBySubmit(id, false).get(0).getExam().getExamDay() );
		modelMap.addAttribute("page","/WEB-INF/student/exams.jsp");
		return "template.jsp";
    }

	@RequestMapping(value = "/{id}/exams/{examId}")
    public String viewExam(@PathVariable Long id,@PathVariable Long examId,
    		Principal principal,ModelMap modelMap,
    		HttpServletRequest request,Model model,
			@Valid @ModelAttribute("JoinExamForm") StudentExam joinExam
			,BindingResult joinExamErrors,
			RedirectAttributes redirectMessage)
    {
		User student = this.userService.findById(id);
		if(student==null)return redirectToExams(principal);
		User current =this.userService.findByUsername(principal.getName());
		StudentExam studentExam=this.examService.getStudentExamByIds(examId, id);
		if(studentExam==null)
			return "redirect:/student/exams";
		if(!isIdForTheCurrentUser(current, id))
			return redirectToExams(principal);
		studentExamValidator.validate(studentExam, joinExamErrors);
		if(joinExamErrors.hasFieldErrors("submitted")){
			
			switch(joinExamErrors.getFieldErrors().get(0).getCode()){
				case "unPublishedExam":
					break;
				case "LatelyJoin":
					redirectMessage.addFlashAttribute("JoinErrorMessage", "Exam timeout!");
					break;
				case "EearlyJoin":
					redirectMessage.addFlashAttribute("JoinErrorMessage", "Exam didn't start yet!");
					break;
				case "SubmittedJoin":
					redirectMessage.addFlashAttribute("JoinErrorMessage", "You can't Join after submitted the exam !");
					break;
				
			}
			return "redirect:/student/"+student.getId()+"/exams";
	}
		if(studentExam.getQuestions().size()<=0) {
			this.examService.createStudentQuestions(studentExam.getExam(), studentExam, 30);
			this.examService.addRandomAnswersToExam(studentExam, 4);
		}
		model.addAttribute("theExam",studentExam);
		// model.addAttribute("isAnswered",this.examService.isAllQuestionsAnswerd(studentExam));
		modelMap.addAttribute("page","/WEB-INF/student/viewExam.jsp");
		return "template.jsp";
    }


	@RequestMapping(value = "/{id}/exams/{examId}",method=RequestMethod.PUT)
    public String submitExam(@PathVariable Long id,@PathVariable Long examId,
			@Valid @ModelAttribute("JoinExamForm") StudentExam joinExam
			,BindingResult joinExamErrors,
    		Principal principal,
    		HttpServletRequest request,Model model)
    {
		User student = this.userService.findById(id);
		if(student==null)return redirectToExams(principal);
		User current =this.userService.findByUsername(principal.getName());
		StudentExam studentExam=this.examService.getStudentExamByIds(examId, id);
		if(studentExam==null)
			return "redirect:/student/exams";
		if(!isIdForTheCurrentUser(current, id))
			return redirectToExams(principal);
		if(studentExam.getSubmitted())
			return "redirect:/student/exams";
		studentExamValidator.validate(studentExam, joinExamErrors);
		if(joinExamErrors.hasFieldErrors("submitted")){
			if(joinExamErrors.getFieldErrors().get(0).getCode().equals("LatelyJoin")){
						this.examService.setExamMarks(studentExam);					
			}
			return "redirect:/student/exams";
		}
	
		if(!this.examService.isAllQuestionsAnswerd(studentExam))
			return "redirect:/student/"+id+"/exams/"+examId;

		this.examService.setExamMarks(studentExam);
		return "redirect:/student/exams";
    }


	@RequestMapping(value = "/{id}/exams/{examId}",method=RequestMethod.DELETE)
    public String deleteStudentExam(@PathVariable Long id,@PathVariable Long examId,
    		HttpServletRequest request,
    		Principal principal,Model model)
    {
		User student = this.userService.findById(id);
		if(student==null)return redirectToExams(principal);
		User current =this.userService.findByUsername(principal.getName());
		StudentExam studentExam=this.examService.getStudentExamByIds(examId, id);
		if(studentExam==null)
			return "redirect:/student/exams";
		if(!isIdForTheCurrentUser(current, id) &&!(request.isUserInRole("ROLE_ADMIN")))
			return redirectToExams(principal);
		if(studentExam.getExam().getIsExtra())
			this.examService.deleteStudentExam(studentExam);
		return "redirect:/student/"+id+"/exams/";
    }

	@ResponseBody
	@RequestMapping(value = "/{id}/answers/{answerId}",method=RequestMethod.POST)
    public String choseAnswer(@PathVariable Long id,@PathVariable Long answerId,
    		HttpServletRequest request,			
			@Valid @ModelAttribute("JoinExamForm") StudentExam joinExam
			,BindingResult joinExamErrors,

    		Principal principal,Model model)
    {

		User student = this.userService.findById(id);
		if(student==null)return null;//return redirectToExams(principal);
		User current =this.userService.findByUsername(principal.getName());
		if(!isIdForTheCurrentUser(current, id) )
			return null;//redirectToExams(principal);
		StudentAnswer studentAnswer =this.examService.getById(answerId);
		if(studentAnswer!=null)
		{
			
			StudentExam studentExam = studentAnswer.getStudentQuestion().getStudentExam();
			if(studentExam.getStudent().getId()!=student.getId())
				return null;// "redirect:/student/exams"; 
				studentExamValidator.validate(studentExam, joinExamErrors);
				if(joinExamErrors.hasFieldErrors("submitted"))
					return null;
		
			this.examService.choseAnswer(studentAnswer.getStudentQuestion(), answerId);
			return studentAnswer.getId().toString();
		}
		return null;//"redirect:/student/"+id+"/exams/";
    }



    @RequestMapping("/{users}")
    public String viewAll(Model model, @PathVariable("users")String users, Principal principal,ModelMap modelMap)
    {

        User user= studentService.findByUsername(principal.getName());
        model.addAttribute("user_id",user.getId());



        if (users.equals("students")) {
            model.addAttribute("users", studentService.findAllByRole("ROLE_STUDENT"));
        }

        modelMap.addAttribute("nav","/WEB-INF/student/nav.jsp");
        modelMap.addAttribute("page","/WEB-INF/student/showUsers.jsp");

        return "template.jsp";


    }




	@RequestMapping(value = "/extras/{examId}",method=RequestMethod.POST)
    public String joinExtraExam(
		@PathVariable Long examId,
    	HttpServletRequest request,
    	Principal principal,Model model,ModelMap modelMap)
    {
		if(!request.isUserInRole("ROLE_STUDENT"))
			return "redirect:/extras";
		Exam exam =this.examService.getExamByItsId(examId);
		if(exam==null)
			return "redirect:/extras";
		User student =this.userService.findByUsername(principal.getName());
		if(student==null)
			return "redirect:/extras";
		if(this.userService.examStudents(examId).indexOf(student)>=0)
			return "redirect:/student/exams";
		this.examService.createStudentExam(exam, student);
		return "redirect:/extras";
    }








}









