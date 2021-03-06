package com.axsos.exambuilder.controllers;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import javax.servlet.http.HttpServletRequest;
import com.axsos.exambuilder.models.Exam;
import com.axsos.exambuilder.services.ExamService;

import com.axsos.exambuilder.models.AllRoles;
import com.axsos.exambuilder.models.User;
import com.axsos.exambuilder.services.RoleService;
import com.axsos.exambuilder.services.StudentService;
import com.axsos.exambuilder.services.UserService;
import com.axsos.exambuilder.validator.UserValidator;

@Controller
public class UsersController {

    private UserService userService;
    private ExamService examService;
    private RoleService roleService;
    private StudentService studentService;

    // NEW
    private UserValidator userValidator;

    // NEW
    public UsersController(UserService userService,ExamService examService,
    		StudentService studentService,RoleService roleService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
        this.studentService=studentService;
        this.examService=examService;

    }
    @RequestMapping("/registration")
    public String registerForm(@Valid @ModelAttribute("user") User user ,
    		Model model,ModelMap modelMap) {
            model.addAttribute("allRoles", AllRoles.Roles);

            modelMap.addAttribute("page","/WEB-INF/registrationPage.jsp");
            return "template.jsp";

    }



    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session,
    		ModelMap modelMap) {
        userValidator.validate(user, result);

            if (result.hasErrors()) {
	            modelMap.addAttribute("page","/WEB-INF/registrationPage.jsp");
	            return "template.jsp";
               }


            if (user.getSelected().equals("ROLE_ADMIN"))
        userService.saveUserWithAdminRole(user);

    else if (user.getSelected().equals("ROLE_INSTRUCTOR"))
        userService.saveWithInstructorRole(user);

    else if (user.getSelected().equals("ROLE_STUDENT"))
        userService.saveWithStudentRole(user);


        return "redirect:/login";
    }

    @RequestMapping("/admin")
    public String adminPage(Principal principal, Model model) {

        String username = principal.getName();

        model.addAttribute("currentUser", userService.findByUsername(username));

        return "redirect:/admin/instructors";
    }

    @RequestMapping("/login")
    public String login(@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout,
    		Model model,ModelMap modelMap) {
        if(error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        }

        if(logout != null) {
            model.addAttribute("logoutMessage", "Logout Successfully!");
        }


        // List<Object[]> students = studentService.top5();
        // Object[] student = students.get(0);
        // Object name = student[0];
        // Object mark = student[1];
        // System.out.println(name);
        // System.out.println(mark);


        return "loginPage.jsp";

    }

    @RequestMapping("/chart")
    public String chart(Model model) {

        List<Object[]> students = studentService.top5();

        Object[] student = students.get(0);
//
//        Object name = student[0];
//        Object mark = student[1];
//
//        model.addAttribute("name",name);
//        model.addAttribute("mark",mark);

        model.addAttribute("students",students);

        return "chart.jsp";
    }


    @RequestMapping(value = { "/","/home"})
    public String home(Principal principal, ModelMap model,ModelMap modelMap) {
        if(principal==null)
            return "redirect:/login";
        String username = principal.getName();
        User user =userService.findByUsername(username);
        System.out.println(user.getRoles().get(0).getName());
        if (user.getRoles().contains(roleService.findByName("ROLE_ADMIN").get(0)))
      //  return "redirect:/admin";
        {
            modelMap.addAttribute("nav","/WEB-INF/admin/nav.jsp");

        }
        if (user.getRoles().contains(roleService.findByName("ROLE_INSTRUCTOR").get(0))) {
            modelMap.addAttribute("nav", "/WEB-INF/instructor/nav.jsp");

        }
        if (user.getRoles().contains(roleService.findByName("ROLE_STUDENT").get(0))) {
            modelMap.addAttribute("nav", "/WEB-INF/student/nav.jsp");

        }
        return "template.jsp";


    }

    @RequestMapping("/students")
    public String viewAllStudents(Model model,  Principal principal,ModelMap modelMap)
    {

        // User user= studentService.findByUsername(principal.getName());
        // model.addAttribute("user_id",user.getId());
        model.addAttribute("users", studentService.findAllByRole("ROLE_STUDENT"));
        modelMap.addAttribute("page","/WEB-INF/showUsers.jsp");
        return "template.jsp";


    }
    @RequestMapping("/instructors")
    public String viewAllInstructors(Model model,  Principal principal,ModelMap modelMap)
    {

        // User user= studentService.findByUsername(principal.getName());
        // model.addAttribute("user_id",user.getId());
        model.addAttribute("users", studentService.findAllByRole("ROLE_INSTRUCTOR"));
        modelMap.addAttribute("page","/WEB-INF/showUsers.jsp");
        return "template.jsp";


    }
    @RequestMapping("/admins")
    public String viewAllAdmins(Model model,  Principal principal,ModelMap modelMap)
    {

        User user= userService.findByUsername(principal.getName());
        // model.addAttribute("user_id",user.getId());
        model.addAttribute("users", studentService.findAllByRole("ROLE_ADMIN").stream().filter(admin->admin.getId()!=user.getId()).collect(Collectors.toList()));
        modelMap.addAttribute("page","/WEB-INF/showUsers.jsp");
        return "template.jsp";


    }
    @RequestMapping(value = "/extras")
    public String viewAllExtraExams(
    		Principal principal,HttpServletRequest request,
    		Model model,ModelMap modelMap)
    {
		List<Exam> allExtraExams=this.examService.getAllExtraExams();
		User user =this.userService.findByUsername(principal.getName());
		if(request.isUserInRole("ROLE_STUDENT")){
			allExtraExams=allExtraExams
			.stream()
			.filter(exam->!user.getStudentExams().stream()
			.anyMatch(studentExam->studentExam.getExam().getId()==exam.getId()))
			.collect(Collectors.toList());
		}
		model.addAttribute("extraExams",allExtraExams);
		modelMap.addAttribute("page","/WEB-INF/extras.jsp");
		return "template.jsp";
    }

    @ResponseBody
    @RequestMapping(value = "/getName")
    public String getName(Principal principal)    {
		User user =this.userService.findByUsername(principal.getName());
		return user.getFirstName()+" "+user.getLastName();
    }



}
