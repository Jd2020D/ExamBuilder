package com.axsos.exambuilder.controllers;

import com.axsos.exambuilder.models.AllRoles;
import com.axsos.exambuilder.models.User;
import com.axsos.exambuilder.services.RoleService;
import com.axsos.exambuilder.services.StudentService;
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
import java.util.List;

@Controller
public class UsersController {

    private UserService userService;

    private RoleService roleService;

    // NEW
    private UserValidator userValidator;
    private StudentService studentService;
    // NEW
    public UsersController(UserService userService, RoleService roleService, UserValidator userValidator,StudentService studentService) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
        this.studentService=studentService;
    }
    @RequestMapping("/registration")
    public String registerForm(@Valid @ModelAttribute("user") User user ,Model model,ModelMap modelMap) {
            model.addAttribute("allRoles", AllRoles.Roles);


        modelMap.addAttribute("page","/WEB-INF/registrationPage.jsp");
            return "template.jsp";

    }



    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session,ModelMap modelMap) {
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
    public String login(@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model,ModelMap modelMap) {
        if(error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        }

        if(logout != null) {
            model.addAttribute("logoutMessage", "Logout Successful!");
        }

        List<Object[]> students = studentService.top5();
        Object[] student = students.get(0);
        Object name = student[0];
        Object mark = student[1];
        System.out.println(name);
        System.out.println(mark);

        modelMap.addAttribute("page","/WEB-INF/loginPage.jsp");
        return "template.jsp";
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


}
