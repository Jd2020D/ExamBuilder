package com.axsos.exambuilder.controllers;

import com.axsos.exambuilder.models.AllRoles;
import com.axsos.exambuilder.models.User;
import com.axsos.exambuilder.services.RoleService;
import com.axsos.exambuilder.services.UserService;
import com.axsos.exambuilder.validator.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UsersController {

    private UserService userService;

    private RoleService roleService;

    // NEW
    private UserValidator userValidator;

    // NEW
    public UsersController(UserService userService, RoleService roleService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }
    @RequestMapping("/registration")
    public String registerForm(@Valid @ModelAttribute("user") User user ,Model model) {
            model.addAttribute("allRoles", AllRoles.Roles);

        return "registrationPage.jsp";

    }



    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
        userValidator.validate(user, result);

            if (result.hasErrors()) {
            return "registrationPage.jsp";
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
    public String login(@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model) {
        if(error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        }

        if(logout != null) {
            model.addAttribute("logoutMessage", "Logout Successful!");
        }


        return "loginPage.jsp";
    }



    @RequestMapping(value = { "/","/home"})
    public String home(Principal principal, ModelMap model,ModelMap modelMap) {

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


}
