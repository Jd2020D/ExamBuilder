package com.axsos.exambuilder.controllers;

import com.axsos.exambuilder.models.AllRoles;
import com.axsos.exambuilder.models.User;
import com.axsos.exambuilder.services.AdminService;
import com.axsos.exambuilder.validator.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
public class AdminsController {

    private final AdminService adminService;

    // NEW
    private final UserValidator userValidator;

    // NEW
    public AdminsController(AdminService adminService, UserValidator userValidator) {
        this.adminService =adminService;
        this.userValidator = userValidator;
    }


    @RequestMapping("/admin/{users}")
    public String viewAll(ModelMap modelMap,Model model, @PathVariable("users")String users) {
        if (users.equals("admins")) {
            model.addAttribute("users", adminService.findAllByRole("ROLE_ADMIN"));
        }
        if (users.equals("instructors")) {
            model.addAttribute("users", adminService.findAllByRole("ROLE_INSTRUCTOR"));

        }
        if (users.equals("students")) {
            model.addAttribute("users", adminService.findAllByRole("ROLE_STUDENT"));
        }

        modelMap.addAttribute("page","/WEB-INF/admin/showUsers.jsp");
        modelMap.addAttribute("nav","/WEB-INF/admin/nav.jsp");

        return "template.jsp";



    }











    @RequestMapping(value = "/admin/deleteUser/{id}" )
    public String deleteShow(@PathVariable("id") Long user_id, HttpSession session,ModelMap modelMap)

    {

        adminService.deleteUser(user_id);
        modelMap.addAttribute("nav","/WEB-INF/admin/nav.jsp");

        return "template.jsp";
    }
    @RequestMapping("/admin/editUser/{id}")
    public String editUserForm(@ModelAttribute("user") User user , ModelMap modelMap,Model model, @PathVariable("id") Long user_id) {
        model.addAttribute("allRoles", AllRoles.Roles);
        model.addAttribute("user", adminService.findById(user_id));
        modelMap.addAttribute("page","/WEB-INF/admin/editUser.jsp");
        modelMap.addAttribute("nav","/WEB-INF/admin/nav.jsp");

        return "template.jsp";


    }


    @PutMapping("/admin/editUser")
    public String editUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model,ModelMap modelMap ) {
        userValidator.validate(user, result);
        modelMap.addAttribute("nav","/WEB-INF/admin/nav.jsp");

        if (result.hasErrors()) {

            modelMap.addAttribute("page","/WEB-INF/admin/editUser.jsp");

               return "template.jsp";
        }
        if (user.getSelected().equals("ROLE_ADMIN"))
            adminService.saveUserWithAdminRole(user);

        else if (user.getSelected().equals("ROLE_INSTRUCTOR"))
            adminService.saveWithInstructorRole(user);

        else if (user.getSelected().equals("ROLE_STUDENT"))
            adminService.saveWithStudentRole(user);

        return "template.jsp";

    }




    @RequestMapping("/admin/insertUser")
    public String insertUserForm(@ModelAttribute("user") User user , Model model,ModelMap modelMap) {
        model.addAttribute("allRoles", AllRoles.Roles);
        modelMap.addAttribute("page","/WEB-INF/admin/insertUser.jsp");
        modelMap.addAttribute("nav","/WEB-INF/admin/nav.jsp");

        return "template.jsp";
    }




    @PostMapping("/admin/insertUser")
    public String insertUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session,ModelMap modelMap) {
        userValidator.validate(user, result);
        modelMap.addAttribute("nav","/WEB-INF/admin/nav.jsp");

        if (result.hasErrors()) {
            modelMap.addAttribute("page","/WEB-INF/admin/insertUser.jsp");

            return "template.jsp";
        }
        if (user.getSelected().equals("ROLE_ADMIN"))
            adminService.saveUserWithAdminRole(user);

        else if (user.getSelected().equals("ROLE_INSTRUCTOR"))
            adminService.saveWithInstructorRole(user);

        else if (user.getSelected().equals("ROLE_STUDENT"))
            adminService.saveWithStudentRole(user);


        return "template.jsp";

    }



}
