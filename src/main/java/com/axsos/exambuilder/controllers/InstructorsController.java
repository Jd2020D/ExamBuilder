package com.axsos.exambuilder.controllers;

import com.axsos.exambuilder.models.AllRoles;
import com.axsos.exambuilder.models.User;
import com.axsos.exambuilder.services.AdminService;
import com.axsos.exambuilder.services.InstructorService;
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
public class InstructorsController {

    // NEW
    private final UserValidator userValidator;
    private  final InstructorService instructorService;
    // NEW
    public InstructorsController( InstructorService instructorService, UserValidator userValidator) {
        this.instructorService = instructorService;
        this.userValidator = userValidator;
    }







    @RequestMapping("/instructor/editUser")
    public String editUserForm(@ModelAttribute("user") User user , Model model, ModelMap modelMap, Principal principal) {

        User logged_user= instructorService.findByUsername(principal.getName());
        model.addAttribute("user",logged_user);

        model.addAttribute("allRoles", AllRoles.Roles[2]);

        modelMap.addAttribute("page","/WEB-INF/instructor/editUser.jsp");
        modelMap.addAttribute("nav","/WEB-INF/instructor/nav.jsp");


        return "template.jsp";

    }


    @PutMapping("/instructor/editUser")
    public String editUser(@Valid @ModelAttribute("user") User user, BindingResult result,ModelMap modelMap) {
        userValidator.validate(user, result);

        if (result.hasErrors()) {
            modelMap.addAttribute("nav","/WEB-INF/instructor/nav.jsp");
            modelMap.addAttribute("page","/WEB-INF/instructor/editUser.jsp");

            return "template.jsp";
        }

        instructorService.saveWithInstructorRole(user);

        return "redirect:/instructor/editUser";

    }


    @RequestMapping("/instructor/{users}")
    public String viewAll(Model model, @PathVariable("users")String users,ModelMap modelMap, Principal principal)
    {

           User user= instructorService.findByUsername(principal.getName());
            model.addAttribute("user_id",user.getId());


        if (users.equals("instructors")) {
            model.addAttribute("users", instructorService.findAllByRole("ROLE_INSTRUCTOR"));

        }

        if (users.equals("students")) {
            model.addAttribute("users", instructorService.findAllByRole("ROLE_STUDENT"));
        }
        modelMap.addAttribute("page","/WEB-INF/instructor/showUsers.jsp");
        modelMap.addAttribute("nav","/WEB-INF/instructor/nav.jsp");

        return "template.jsp";

    }












    }















