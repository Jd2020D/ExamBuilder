package com.axsos.exambuilder.controllers;


import java.security.Principal;
import java.util.List;
import java.util.Optional;

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

import com.axsos.exambuilder.models.Answer;
import com.axsos.exambuilder.models.Exam;
import com.axsos.exambuilder.models.Question;
import com.axsos.exambuilder.models.StudentExam;
import com.axsos.exambuilder.models.User;
import com.axsos.exambuilder.services.ExamService;
import com.axsos.exambuilder.services.UserService;
import com.axsos.exambuilder.validator.EditExamValidator;
import com.axsos.exambuilder.validator.PublishExamValidator;

@RequestMapping("/instructor")
@Controller
public class ExamController {
	private static int counter=0;
	private final ExamService examService;
	private final UserService userService;
	private final EditExamValidator editExamValidator;
	private final PublishExamValidator publishExamValidator;
	public ExamController(ExamService examService, UserService userService, PublishExamValidator publishExamValidator, EditExamValidator editExamValidator) {
		this.examService = examService;
		this.userService = userService;
		this.editExamValidator = editExamValidator;
		this.publishExamValidator = publishExamValidator;
	}
	public boolean isIdForTheCurrentUser(User user,Long id) {
		return user.getId()==this.userService.findById(id).getId();
	}
//	@RequestMapping(value = "/{id}/exams/test")
//	public String Test(@PathVariable Long id,
//    		Principal principal) throws ParseException {
//		User instructor =this.userService.findByUsername(principal.getName());
//			Exam exam=this.examService.createExamForm(new Exam("calculas", 40,new SimpleDateFormat("yyyy-MM-dd").parse("2021-04-12"),new SimpleDateFormat("yyyy-MM-dd").parse("2021-04-12"), 20, false), instructor);
//		return "";
//	}
//	@RequestMapping(value = "/{id}/students/{studentId}/exams/{examId}")
//	public int Test(@PathVariable Long id,@PathVariable Long studentId,@PathVariable Long examId,
//    		Principal principal) throws ParseException {
//		User instructor =this.userService.findByUsername(principal.getName());
//		User student=this.userService.findById(studentId);
//		Exam exam=this.examService.getExamById(examId,id);
////		StudentExam studentExam=this.examService.createStudentExam(exam, student, 3);
////		StudentExam studentExam=this.examService.getStudentExamById(1L);
//		StudentQuestion studentQuestion = this.examService.getStudentQuestion(studentExam.getId(),studentExam.getQuestions().get(2).getId());
////		this.examService.createRandomAnswers(studentExam.getStudentQuestions().get(1), 4);
//		return studentExam.getStudentQuestions().get(1).getAnswers().size();
////		return this.examService.allAnswers(studentExam.getQuestions().get(1).getId(),false).size();
	//		return studentQuestion.getId();
		//		System.out.println(exam.getQuestions().toString());
		
//			return 		studentExam.getStudentQuestions().get(1).getQuestion().getAnswers();
//	}
	
	
	@RequestMapping(value = "/exams")
	public String redirectToExams(Principal principal) {
		User current =this.userService.findByUsername(principal.getName());
		return "redirect:/instructor/"+current.getId()+"/exams";
	}
	//done
	@RequestMapping(value = "/{id}/exams")
    public String createExamFormView(@PathVariable Long id,
    		Principal principal,HttpServletRequest request,
    		@ModelAttribute("exam") Exam exam,Model model,
    		ModelMap modelMap)
    {
		User instructor = this.userService.findById(id);
		if(instructor==null)return redirectToExams(principal);
		User current =this.userService.findByUsername(principal.getName());
		if(!isIdForTheCurrentUser(current, id) &&!(request.isUserInRole("ROLE_ADMIN")))
			return redirectToExams(principal);
		model.addAttribute("instructor",instructor);
		modelMap.addAttribute("page","/WEB-INF/instructor/exams.jsp");
		modelMap.addAttribute("nav","/WEB-INF/nav.jsp");
		model.addAttribute("examsPage","active");
		return "template.jsp";
    }
	
	@RequestMapping(value = "/{id}/exams",method=RequestMethod.POST)
    public String createExamForm( @PathVariable Long id,
    		Principal principal,Model model,HttpServletRequest request,
    		@Valid @ModelAttribute("exam") Exam exam,BindingResult result,
    		ModelMap modelMap)
    {
		User instructor = this.userService.findById(id);
		if(instructor==null)return redirectToExams(principal);
		User current =this.userService.findByUsername(principal.getName());
		if(!isIdForTheCurrentUser(current, id) &&!(request.isUserInRole("ROLE_ADMIN")))
			return redirectToExams(principal);
		model.addAttribute("instructor",instructor);

		if(result.hasErrors()) {
			modelMap.addAttribute("page","/WEB-INF/instructor/exams.jsp");
			return "template.jsp";
		}
    	this.examService.createExamForm(exam, instructor);
		
		return "redirect:/instructor/"+instructor.getId()+"/exams";
    }
	
	@RequestMapping(value = "/{id}/exams/{examId}")
    public String viewExam(@PathVariable Long id,@PathVariable Long examId,
    		Principal principal,@ModelAttribute("exam") Exam update,@ModelAttribute("publishExam") Exam publish,
    		@ModelAttribute("question") Question question,
    		HttpServletRequest request, @ModelAttribute("answer") Answer answer,Model model,
    		ModelMap modelMap)
    {
		User instructor = this.userService.findById(id);
		if(instructor==null)return redirectToExams(principal);
		User current =this.userService.findByUsername(principal.getName());
		Exam exam =this.examService.getExamById(examId, id);
		if(exam==null)
			return "redirect:/instructor/exams";
		if(!isIdForTheCurrentUser(current, id) &&!(request.isUserInRole("ROLE_ADMIN")))
			return redirectToExams(principal);
		model.addAttribute("instructor",instructor);
		model.addAttribute("thisExam",exam);
		model.addAttribute("exam",exam);
		model.addAttribute("publishExam",exam);
		modelMap.addAttribute("page","/WEB-INF/instructor/viewExam.jsp");
		return "template.jsp";
    }
	@RequestMapping(value = "/{id}/exams/{examId}/students")
    public String viewExamStudents(@PathVariable Long id,@PathVariable Long examId,
    		HttpServletRequest request,Principal principal,Model model,ModelMap modelMap)
    {
		User instructor = this.userService.findById(id);
		if(instructor==null)return redirectToExams(principal);
		User current =this.userService.findByUsername(principal.getName());
		Exam exam =this.examService.getExamById(examId, id);
		if(exam==null)
			return "redirect:/instructor/exams";
		if(!isIdForTheCurrentUser(current, id) &&!(request.isUserInRole("ROLE_ADMIN")))
			return redirectToExams(principal);
		model.addAttribute("instructor",instructor);
		model.addAttribute("thisExam",exam);
		List<User> OtherStudents=this.userService.allRoleUsers("ROLE_STUDENT");
		OtherStudents.removeAll(this.userService.examStudents(examId));
		model.addAttribute("allStudents",OtherStudents);
		modelMap.addAttribute("page","/WEB-INF/instructor/students.jsp");
		modelMap.addAttribute("nav","/WEB-INF/instructor/nav.jsp");
		return "template.jsp";
    }
	
	@RequestMapping(value = "/{id}/exams/{examId}/students/{studentId}",method=RequestMethod.POST)
    public String addStudentExam(@PathVariable Long id,@PathVariable Long examId,
    		@PathVariable Long studentId,HttpServletRequest request,
			// @Valid @ModelAttribute("publishExam") Exam publish,BindingResult publishExamResult,
    		Principal principal,Model model,ModelMap modelMap)
    {
		User instructor = this.userService.findById(id);
		if(instructor==null)return redirectToExams(principal);
		User current =this.userService.findByUsername(principal.getName());
		Exam exam =this.examService.getExamById(examId, id);
		if(exam==null)
			return "redirect:/instructor/exams";
		if(!isIdForTheCurrentUser(current, id) &&!(request.isUserInRole("ROLE_ADMIN")))
			return redirectToExams(principal);
		User student=this.userService.findById(studentId);
		if(student==null)
			return "redirect:/instructor/"+id+"/exams/"+examId+"/students";
		if(this.userService.examStudents(examId).indexOf(student)>=0)
			return "redirect:/instructor/"+id+"/exams/"+examId+"/students";
		// editExamValidator.validate(exam, publishExamResult);
		// if(publishExamResult.hasFieldErrors("isPublished")) 
		// 		{	
		// 		modelMap.addAttribute("page","/WEB-INF/instructor/students.jsp");
		// 		modelMap.addAttribute("nav","/WEB-INF/instructor/nav.jsp");
		// 		return "template.jsp";
		// 		}
		StudentExam studentExam=this.examService.createStudentExam(exam, student);
		return "redirect:/instructor/"+id+"/exams/"+examId+"/students";
    }
	@RequestMapping(value = "/{id}/exams/{examId}/students/{studentId}",method=RequestMethod.DELETE)
    public String deleteStudentExam(@PathVariable Long id,@PathVariable Long examId,
    		@PathVariable Long studentId,HttpServletRequest request,
    		Principal principal,Model model)
    {
		User instructor = this.userService.findById(id);
		if(instructor==null)return redirectToExams(principal);
		User current =this.userService.findByUsername(principal.getName());
		Exam exam =this.examService.getExamById(examId, id);
		if(exam==null)
			return "redirect:/instructor/exams";
		if(!isIdForTheCurrentUser(current, id) &&!(request.isUserInRole("ROLE_ADMIN")))
			return redirectToExams(principal);
		User student=this.userService.findById(studentId);
		if(student==null)
			return "redirect:/instructor/"+id+"/exams/"+examId+"/students";
		StudentExam studentExam=this.examService.getStudentExamByIds(examId, studentId);
		if(studentExam==null)
			return "redirect:/instructor/"+id+"/exams/"+examId+"/students";
		this.examService.deleteStudentExam(studentExam);
		return "redirect:/instructor/"+id+"/exams/"+examId+"/students";
    }


//	@RequestMapping(value = "/{id}/exams/{examId}/publish",method=RequestMethod.POST)
//    public String publishExam(@PathVariable Long id,@PathVariable Long examId,
//    		Principal principal,@Valid @ModelAttribute("exam") Exam update,BindingResult result1,@Valid @ModelAttribute("publishExam") Exam publish,
//    		BindingResult result,@ModelAttribute("question") Question question,@ModelAttribute("answer") Answer answer,Model model)
//    {
//		User instructor =this.userService.findByUsername(principal.getName());
//		if(!isIdForTheCurrentUser(instructor, id))
//			return "redirect:/";
//		Exam exam =this.examService.getExamById(examId,id);
//		if(exam==null)
//			return "redirect:/instructor/exams";
//		model.addAttribute("instructor",instructor);
//		model.addAttribute("exam",update);
//		model.addAttribute("thisExam",exam);
//		model.addAttribute("publishExam",publish);
//        publishExamValidator.validate(exam, result);
//        if(result.hasErrors()) {
////        	System.out.println(result.getFieldErrors().get(1).getField());
//    		return "instructor/viewExam.jsp";
//    		
//        }
//        this.examService.changePublish(exam);
//		return "redirect:/instructor/"+instructor.getId()+"/exams/"+exam.getId();
//
//    }
//	@RequestMapping(value ="/{id}/exams/{examId}/update",method=RequestMethod.POST)
//    public String update(@PathVariable Long id,@PathVariable Long examId,@ModelAttribute("publishExam") Exam publish,
//    		@ModelAttribute("question") Question question,@Valid @ModelAttribute("exam") Exam update,BindingResult result,Model model,
//    		Principal principal,HttpServletRequest request) {
//		User instructor = this.userService.findById(id);
//		if(instructor==null)return redirectToExams(principal);
//		User current =this.userService.findByUsername(principal.getName());
//		Exam exam =this.examService.getExamById(examId,id);
//		if(exam==null)
//			return "redirect:/instructor/exams";
//		if(!isIdForTheCurrentUser(current, id) &&!(request.isUserInRole("ROLE_ADMIN")))
//			return redirectToExams(principal);
//		model.addAttribute("instructor",instructor);
//		model.addAttribute("exam",update);
//		model.addAttribute("thisExam",exam);
//		model.addAttribute("publishExam",publish);
//		if(result.hasErrors())
//		return "instructor/viewExam.jsp";
//			this.examService.updateExam(exam,update);
//			return "redirect:/instructor/"+instructor.getId()+"/exams/"+exam.getId();
//
//
//	}

	@RequestMapping(value ="/{id}/exams/{examId}",method=RequestMethod.DELETE)
    public String deleteExam(@PathVariable Long id,@PathVariable Long examId,
    		Principal principal,HttpServletRequest request) {
		User instructor = this.userService.findById(id);
		if(instructor==null)return redirectToExams(principal);
		User current =this.userService.findByUsername(principal.getName());
		Exam exam =this.examService.getExamById(examId,id);
		if(exam==null)
			return "redirect:/instructor/exams";
		if(!isIdForTheCurrentUser(current, id) &&!(request.isUserInRole("ROLE_ADMIN")))
			return redirectToExams(principal);
		this.examService.deleteExam(exam);

		return "redirect:/instructor/"+instructor.getId()+"/exams";

	}
//	@RequestMapping(value = "/{id}/exams/{examId}/addq",method=RequestMethod.POST)
//	public String addQuestionToExam(@PathVariable Long id,@PathVariable Long examId,
//			@Valid @ModelAttribute("question") Question question,BindingResult result,@ModelAttribute("exam") Exam update,Model model,
//			@ModelAttribute("answer") Answer answer,Principal principal,HttpServletRequest request){
//		User instructor = this.userService.findById(id);
//		if(instructor==null)return redirectToExams(principal);
//		User current =this.userService.findByUsername(principal.getName());
//		Exam exam =this.examService.getExamById(examId,id);
//		if(exam==null)
//			return "redirect:/instructor/exams";
//		if(!isIdForTheCurrentUser(current, id) &&!(request.isUserInRole("ROLE_ADMIN")))
//			return redirectToExams(principal);
//		model.addAttribute("instructor",instructor);
//		model.addAttribute("exam",exam);
//
//		if(result.hasErrors())
//		return "instructor/viewExam.jsp";
//
//		question=this.examService.addQuestion(question, exam);
//		return "redirect:/instructor/"+instructor.getId()+"/exams/"+exam.getId()+"/#question_"+question.getId();
//	}
	@RequestMapping(path="/{id}/exams/{examId}/**")
	public String anything(@PathVariable Long id,@PathVariable Long examId,
			
			Principal principal,HttpServletRequest request) {
		User instructor = this.userService.findById(id);
		if(instructor==null)return redirectToExams(principal);
		User current =this.userService.findByUsername(principal.getName());
		Exam exam =this.examService.getExamById(examId, id);
		if(exam==null)
			return "redirect:/instructor/exams";
		return "redirect:/instructor/"+id+"/exams/"+exam.getId();
	}
	
	@RequestMapping(
			path={
			"/{id}/exams/{examId}/update",
			"/{id}/exams/{examId}/publish",
			"/{id}/exams/{examId}/addq",
			"/{id}/exams/{examId}/questions/{questionId}/update",
			"/{id}/exams/{examId}/questions/{questionId}/adda",
			"/{id}/exams/{examId}/questions/{questionId}/answers/{answerId}/update"
			},
			method = RequestMethod.POST)
	public String updates(HttpServletRequest request ,
			@PathVariable Long id,
			@PathVariable Long examId,
			@PathVariable Optional <Long> questionId,
			@PathVariable Optional <Long>  answerId,
			@Valid @ModelAttribute("exam") Exam update,BindingResult updateExamResult,
			@Valid @ModelAttribute("publishExam") Exam publish,BindingResult publishExamResult,
			@Valid @ModelAttribute("question") Question questionUpdate,BindingResult updateQuestionResult,
			@Valid @ModelAttribute("answer") Answer answerUpdate,BindingResult updateAnswerResult,
    		BindingResult result,
    		Model model,RedirectAttributes redirectAttributes,
			Principal principal,ModelMap modelMap) {
			User instructor = this.userService.findById(id);
		if(instructor==null)return redirectToExams(principal);
		User current =this.userService.findByUsername(principal.getName());
		Exam exam =this.examService.getExamById(examId, id);
		if(exam==null)
			return "redirect:/instructor/exams";
		if(!isIdForTheCurrentUser(current, id) &&!(request.isUserInRole("ROLE_ADMIN")))
			return redirectToExams(principal);
		model.addAttribute("instructor",instructor);
		model.addAttribute("exam",update);
		model.addAttribute("thisExam",exam);
		model.addAttribute("publishExam",publish);
		modelMap.addAttribute("page","/WEB-INF/instructor/viewExam.jsp");
		modelMap.addAttribute("nav","/WEB-INF/instructor/nav.jsp");
		boolean publishFlag=false;
		Question question =this.examService.getQuestionById(questionId.orElse(null), examId);
		Answer answer=this.examService.getAnswerById(answerId.orElse(null), questionId.orElse(null));
		//here
		if(exam.getIsPublished()) {
			editExamValidator.validate(exam, publishExamResult);
			model.addAttribute("exam",exam);
			if(publishExamResult.hasFieldErrors("isPublished")) {
				return "template.jsp";
			}
			this.examService.changePublish(exam);
			publishFlag=true;
			redirectAttributes.addFlashAttribute("examUnPublish","exam is not published any more");

		}
			
		if(request.getRequestURI().indexOf("questions")<0 ) 
			{
				if(request.getRequestURI().endsWith("addq"))
				{
					model.addAttribute("exam",exam);
					if(updateQuestionResult.hasErrors())
						return "template.jsp";
					question=this.examService.addQuestion(questionUpdate, exam);
					return "redirect:/instructor/"+instructor.getId()+"/exams/"+exam.getId()+"/#question_"+question.getId();
				}

				if(request.getRequestURI().endsWith("update"))
				{
					if(updateExamResult.hasErrors())
						return "template.jsp";
					this.examService.updateExam(exam,update);
				}
				else if(request.getRequestURI().endsWith("publish")&&!publishFlag)
				{
					publishExamValidator.validate(exam, publishExamResult);
			        if(publishExamResult.hasErrors()) {
						publish.setExamDay(exam.getExamDay());
						model.addAttribute("exam", exam);
						return "template.jsp";

					}
			        this.examService.changePublish(exam);
				}
				 
				return "redirect:/instructor/"+id+"/exams/"+exam.getId();

			}
			else if (question ==null)
				return "redirect:/instructor/"+id+"/exams/"+exam.getId();

			else if(request.getRequestURI().indexOf("answers")<0)
			{
				model.addAttribute("exam",exam);
				if(request.getRequestURI().endsWith("update"))
				{
					model.addAttribute("erroredQuestion",question);
					if(updateQuestionResult.hasErrors())
						return "template.jsp";
					this.examService.updateQuestion(question, questionUpdate);
					return "redirect:/instructor/"+instructor.getId()+"/exams/"+exam.getId()+"/#question_"+questionId.get();
				}
				else if(request.getRequestURI().endsWith("adda"))
				{
					model.addAttribute("answerQuestion",question);
					if(updateAnswerResult.hasErrors())
						return "template.jsp";
					answer=this.examService.addAnswer(answerUpdate,question);
					return "redirect:/instructor/"+instructor.getId()+"/exams/"+exam.getId()+"/#answer_"+answer.getId();
				}
			}
			else if(answer==null)
				return "redirect:/instructor/"+instructor.getId()+"/exams/"+exam.getId()+"/#question_"+questionId.get();
			else if(request.getRequestURI().indexOf("answers")>-1 && request.getRequestURI().endsWith("update") )
			{
				model.addAttribute("exam",exam);
				model.addAttribute("erroredAnswer",answer);
				if(result.hasErrors())
					return "template.jsp";
				this.examService.updateAnswer(answer, answerUpdate);
				return "redirect:/instructor/"+instructor.getId()+"/exams/"+exam.getId()+"/#answer_"+answerId.get();
			}
//

		return "redirect:/instructor/"+id+"/exams/"+exam.getId();
	}
//	@RequestMapping( value = "/{id}/exams/{examId}/questions/{questionId}/update",method=RequestMethod.POST)
//	public String updateQuestion(@PathVariable Long id,@PathVariable Long examId,
//			@PathVariable Long questionId,Principal principal,HttpServletRequest request,
//			@ModelAttribute("exam") Exam update,@Valid @ModelAttribute("question") Question questionUpdate,BindingResult result,
//			 @ModelAttribute("answer") Answer answerUpdate,
//			Model model){
//		User instructor = this.userService.findById(id);
//		if(instructor==null)return redirectToExams(principal);
//		User current =this.userService.findByUsername(principal.getName());
//		Exam exam =this.examService.getExamById(examId, id);
//		if(exam==null)
//			return "redirect:/instructor/exams";
//		Question question=this.examService.getQuestionById(questionId,examId);
//		if(question==null)
//			return "redirect:/instructor/exams/"+exam.getId();
//		if(!isIdForTheCurrentUser(current, id) &&!(request.isUserInRole("ROLE_ADMIN")))
//			return redirectToExams(principal);
//		model.addAttribute("instructor",instructor);
//		model.addAttribute("exam",exam);
//		model.addAttribute("erroredQuestion",question);
//
//		if(result.hasErrors())
//		return "instructor/viewExam.jsp";
//
//		this.examService.updateQuestion(question, questionUpdate);
//		return "redirect:/instructor/"+instructor.getId()+"/exams/"+exam.getId()+"/#question_"+questionId;
//
//	}

	@ResponseBody
	@RequestMapping(value = "/{id}/exams/{examId}/questions/{questionId}",method=RequestMethod.DELETE)
	public String deleteQuestionFromExam(@PathVariable Long id,@PathVariable Long examId,
			@PathVariable Long questionId,Principal principal,HttpServletRequest request){
		User instructor = this.userService.findById(id);
		if(instructor==null)
		return null;//redirectToExams(principal);
		User current =this.userService.findByUsername(principal.getName());
		Exam exam =this.examService.getExamById(examId,id);
		if(exam==null)
			return null;//"redirect:/instructor/exams";
		Question question=this.examService.getQuestionById(questionId,examId);
		if(question==null)
			return null;// "redirect:/instructor/exams/"+exam.getId();
		if(!isIdForTheCurrentUser(current, id) &&!(request.isUserInRole("ROLE_ADMIN")))
			return null;//redirectToExams(principal);

		this.examService.deleteQuestion(question);
		return null;//"redirect:/instructor/"+instructor.getId()+"/exams/"+exam.getId();
	}
	@ResponseBody
	@RequestMapping(value = "/{id}/exams/{examId}/questions/{questionId}/answers/{answerId}",method=RequestMethod.DELETE)
	public String deleteAnswerFromExam(@PathVariable Long id,@PathVariable Long examId,
			@PathVariable Long questionId,@PathVariable Long answerId,Principal principal,HttpServletRequest request){
		User instructor = this.userService.findById(id);
		if(instructor==null)
			return null; //redirectToExams(principal);
		User current =this.userService.findByUsername(principal.getName());
		Exam exam =this.examService.getExamById(examId, id);
		if(exam==null)
			return null;// "redirect:/instructor/exams";
		Question question=this.examService.getQuestionById(questionId,examId);
		if(question==null)
			return null;//"redirect:/instructor/exams/"+exam.getId();
		Answer answer=this.examService.getAnswerById(answerId,questionId);
		if(answer==null)
			return null; //"redirect:/instructor/exams/"+exam.getId()+"/#question_"+questionId;
		if(!isIdForTheCurrentUser(current, id) &&!(request.isUserInRole("ROLE_ADMIN")))
			return null;// redirectToExams(principal);
		this.examService.deleteAnswer(answer);
		//return "redirect:/instructor/"+instructor.getId()+"/exams/"+exam.getId()+"/#question_"+questionId;
		return String.valueOf(answer.getId());
	}
//	@RequestMapping(value = "/{id}/exams/{examId}/questions/{questionId}/answers/adda",method=RequestMethod.POST)
//	public String addAnswer(@PathVariable Long id,@PathVariable Long examId,
//			@PathVariable Long questionId,Principal principal,HttpServletRequest request,
//			@ModelAttribute("exam") Exam update,@ModelAttribute("question") Question questionUpdate,
//			@Valid @ModelAttribute("answer") Answer answerUpdate,BindingResult result,
//			Model model){
//		User instructor = this.userService.findById(id);
//		if(instructor==null)return redirectToExams(principal);
//		User current =this.userService.findByUsername(principal.getName());
//		Exam exam =this.examService.getExamById(examId, id);
//		if(exam==null)
//			return "redirect:/instructor/exams";
//		Question question=this.examService.getQuestionById(questionId,examId);
//		if(question==null)
//			return "redirect:/instructor/exams/"+exam.getId();
//		if(!isIdForTheCurrentUser(current, id) &&!(request.isUserInRole("ROLE_ADMIN")))
//			return redirectToExams(principal);			
//		model.addAttribute("instructor",instructor);
//		model.addAttribute("exam",exam);
//		model.addAttribute("answerQuestion",question);
//		if(result.hasErrors())
//		return "instructor/viewExam.jsp";
//		this.examService.addAnswer(answerUpdate,question);
//		return "redirect:/instructor/"+instructor.getId()+"/exams/"+exam.getId()+"/#answer_"+answerUpdate.getId();
//
//	}

//	@RequestMapping(value = "/{id}/exams/{examId}/questions/{questionId}/answers/{answerId}/update",method=RequestMethod.POST)
//	public String updateAnswer(@PathVariable Long id,@PathVariable Long examId,
//			@PathVariable Long questionId,@PathVariable Long answerId,Principal principal,HttpServletRequest request,
//			@ModelAttribute("exam") Exam update,@ModelAttribute("question") Question questionUpdate,
//			@Valid @ModelAttribute("answer") Answer answerUpdate,BindingResult result,
//			Model model){
//		User instructor = this.userService.findById(id);
//		if(instructor==null)return redirectToExams(principal);
//		User current =this.userService.findByUsername(principal.getName());
//		Exam exam =this.examService.getExamById(examId, id);
//		if(exam==null)
//			return "redirect:/instructor/exams";
//		Question question=this.examService.getQuestionById(questionId,examId);
//		if(question==null)
//			return "redirect:/instructor/exams/"+exam.getId();
//		Answer answer=this.examService.getAnswerById(answerId,questionId);
//		if(answer==null)
//			return "redirect:/instructor/exams/"+exam.getId()+"/#question_"+questionId;
//		if(!isIdForTheCurrentUser(current, id) &&!(request.isUserInRole("ROLE_ADMIN")))
//			return redirectToExams(principal);
//		model.addAttribute("instructor",instructor);
//		model.addAttribute("exam",exam);
//		model.addAttribute("erroredAnswer",answer);
//		if(result.hasErrors())
//		return "instructor/viewExam.jsp";
//
//		this.examService.updateAnswer(answer, answerUpdate);
//		return "redirect:/instructor/"+instructor.getId()+"/exams/"+exam.getId()+"/#answer_"+answerId;
//
//	}



}
