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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;
import com.axsos.exambuilder.services.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
public class AdminsController {

    private final AdminService adminService;
	private final UserService userService;
    private final UserValidator userValidator;

    public AdminsController(AdminService adminService, UserValidator userValidator,UserService userService) {
        this.adminService =adminService;
        this.userValidator = userValidator;
        this.userService=userService;
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










    @RequestMapping(value = "/admin/deleteUser/{id}",method=RequestMethod.DELETE )
    public String deleteShow(@PathVariable("id") Long user_id, HttpSession session,Principal principal,ModelMap modelMap)

    {
        User userToDelete=this.userService.findById(user_id);
        if(userToDelete==null)
            return null;
        User current =this.userService.findByUsername(principal.getName());
        if(current.getId()==user_id)
            return null;
        adminService.deleteUser(userToDelete);
        switch(userToDelete.getRoles().get(0).getName()){
            case "ROLE_INSTRUCTOR":
                return "redirect:/instructors";
            case "ROLE_ADMIN":
                return "redirect:/admins";
            case "ROLE_STUDENT":
                return "redirect:/students";
        }
        return "redirect:/";
    }

    @RequestMapping("/admin/editUser/{id}")
    public String editUserForm(@ModelAttribute("user") User user , ModelMap modelMap,Model model,Principal principal, @PathVariable("id") Long user_id) {
        User current =this.userService.findByUsername(principal.getName());
        if(current.getId()==user_id)
            return "redirect:/admins";

        model.addAttribute("allRoles", AllRoles.Roles);
        model.addAttribute("user", adminService.findById(user_id));
        modelMap.addAttribute("page","/WEB-INF/admin/editUser.jsp");
        modelMap.addAttribute("nav","/WEB-INF/admin/nav.jsp");

        return "template.jsp";


    }


    @RequestMapping(value="/admin/editUser/",method=RequestMethod.PUT)
    public String editUser(
                            @Valid @ModelAttribute("user") User user,
                            BindingResult result,
                            Model model,ModelMap modelMap,
                            RedirectAttributes redirectAttributes,
                            Principal principal) {
        User userToEdit =this.userService.findById(user.getId());
        if(userToEdit==null)
            return "redirect:/";
        userValidator.validate(user, result);
        User current =this.userService.findByUsername(principal.getName());
        if(current.getId()==user.getId())
            return "redirect:/admins";
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors",result.getFieldErrors());
            return "redirect:/admin/editUser/"+user.getId();
        }
        if (user.getSelected().equals("ROLE_ADMIN")){
            adminService.saveUserWithAdminRole(user);
            return "redirect:/admins";
        }

        else if (user.getSelected().equals("ROLE_INSTRUCTOR"))
{            adminService.saveWithInstructorRole(user);
                return "redirect:/instructors";

}
        else if (user.getSelected().equals("ROLE_STUDENT"))
{            adminService.saveWithStudentRole(user);
                return "redirect:/students";

}
return "template.jsp";

    }




    @RequestMapping("/addUser")
    public String insertUserForm(@ModelAttribute("user") User user , Model model,ModelMap modelMap) {
        model.addAttribute("allRoles", AllRoles.Roles);
        modelMap.addAttribute("page","/WEB-INF/admin/insertUser.jsp");
        return "template.jsp";
    }




    @PostMapping("/addUser")
    public String insertUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session,ModelMap modelMap) {
        userValidator.validate(user, result);
        model.addAttribute("allRoles", AllRoles.Roles);


        if (result.hasErrors()) {
            modelMap.addAttribute("page","/WEB-INF/admin/insertUser.jsp");

            return "template.jsp";
        }
        if (user.getSelected().equals("ROLE_ADMIN")){
            adminService.saveUserWithAdminRole(user);
            return "redirect:/admins";
        }

        else if (user.getSelected().equals("ROLE_INSTRUCTOR"))
{            adminService.saveWithInstructorRole(user);
                return "redirect:/instructors";

}
        else if (user.getSelected().equals("ROLE_STUDENT"))
{            adminService.saveWithStudentRole(user);
                return "redirect:/students";

}


        return "template.jsp";

    }



}
