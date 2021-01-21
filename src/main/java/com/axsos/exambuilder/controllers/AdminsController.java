package com.axsos.exambuilder.controllers;

import com.axsos.exambuilder.models.AllRoles;
import com.axsos.exambuilder.models.User;
import com.axsos.exambuilder.services.AdminService;
import com.axsos.exambuilder.validator.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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


    @RequestMapping("/{users}")
    public String viewAll(Model model, @PathVariable("users")String users) {
        if (users.equals("admins")) {
            model.addAttribute("users", adminService.findAllByRole("ROLE_ADMIN"));
            return "admin/showUsers.jsp";
        }
        if (users.equals("instructors")) {
            model.addAttribute("users", adminService.findAllByRole("ROLE_INSTRUCTOR"));
            return "admin/showUsers.jsp";
        }
        if (users.equals("students")) {
            model.addAttribute("users", adminService.findAllByRole("ROLE_STUDENT"));
            return "admin/showUsers.jsp";
        }

        return "admin/showUsers.jsp";
    }



    @RequestMapping(value = "/deleteUser/{id}" )
    public String deleteShow(@PathVariable("id") Long user_id, HttpSession session)

    {

        adminService.deleteUser(user_id);
        return "admin/mainPage.jsp";
    }
    @RequestMapping("/editUser/{id}")
    public String editUserForm(@ModelAttribute("user") User user , Model model, @PathVariable("id") Long user_id) {
        model.addAttribute("allRoles", AllRoles.Roles);
        model.addAttribute("user", adminService.findById(user_id));

        return "admin/editUser.jsp";


    }


    @PutMapping("/editUser")
    public String editUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
        userValidator.validate(user, result);

        if (result.hasErrors()) {
            return "admin/editUser.jsp";
        }
        if (user.getSelected().equals("ROLE_ADMIN"))
            adminService.saveUserWithAdminRole(user);

        else if (user.getSelected().equals("ROLE_INSTRUCTOR"))
            adminService.saveWithInstructorRole(user);

        else if (user.getSelected().equals("ROLE_STUDENT"))
            adminService.saveWithStudentRole(user);


        return "admin/mainPage.jsp";

    }




    @RequestMapping("/insertUser")
    public String insertUserForm(@ModelAttribute("user") User user , Model model) {
        model.addAttribute("allRoles", AllRoles.Roles);

        return "admin/insertUser.jsp";
    }




    @PostMapping("/insertUser")
    public String insertUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
        userValidator.validate(user, result);

        if (result.hasErrors()) {
            return "admin/insertUser.jsp";
        }
        if (user.getSelected().equals("ROLE_ADMIN"))
            adminService.saveUserWithAdminRole(user);

        else if (user.getSelected().equals("ROLE_INSTRUCTOR"))
            adminService.saveWithInstructorRole(user);

        else if (user.getSelected().equals("ROLE_STUDENT"))
            adminService.saveWithStudentRole(user);


        return "admin/mainPage.jsp";

    }



}
