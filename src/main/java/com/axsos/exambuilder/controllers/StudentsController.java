package com.axsos.exambuilder.controllers;

import com.axsos.exambuilder.models.AllRoles;
import com.axsos.exambuilder.models.User;
import com.axsos.exambuilder.services.AdminService;
import com.axsos.exambuilder.services.InstructorService;
import com.axsos.exambuilder.services.StudentService;
import com.axsos.exambuilder.validator.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class StudentsController {


    private final UserValidator userValidator;
    private  final StudentService studentService;

    public StudentsController( StudentService studentService, UserValidator userValidator) {
        this.studentService= studentService;
        this.userValidator = userValidator;
    }








    @RequestMapping("/student/editUser")
    public String editUserForm(@ModelAttribute("user") User user , Model model, ModelMap modelMap,Principal principal) {

        User logged_user= studentService.findByUsername(principal.getName());
        model.addAttribute("user",logged_user);

        model.addAttribute("allRoles", AllRoles.Roles[1]);

        modelMap.addAttribute("page","/WEB-INF/student/editUser.jsp");
        modelMap.addAttribute("nav","/WEB-INF/student/nav.jsp");

        return "template.jsp";

    }


    @PutMapping("/student/editUser")
    public String editUser(@Valid @ModelAttribute("user") User user, BindingResult result,ModelMap modelMap) {
        userValidator.validate(user, result);

        if (result.hasErrors()) {
            modelMap.addAttribute("nav","/WEB-INF/student/nav.jsp");
            modelMap.addAttribute("page","/WEB-INF/student/editUser.jsp");

            return "template.jsp";
        }

        studentService.saveWithStudentRole(user);

        return "redirect:/student/editUser";

    }


    @RequestMapping("/student/{users}")
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












}















