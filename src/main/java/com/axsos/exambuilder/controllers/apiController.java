//package com.axsos.exambuilder.controllers;
//
//import com.axsos.exambuilder.models.User;
//import com.axsos.exambuilder.services.UserService;
//import com.axsos.exambuilder.validator.UserValidator;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//
//public class apiController {
//
//
//    private UserService userService;
//
//    // NEW
//    private UserValidator userValidator;
//
//
//
//
//    public apiController(UserService userService, UserValidator userValidator) {
//        this.userService = userService;
//        this.userValidator = userValidator;
//    }
//    @RequestMapping("")
//    public String rateShow() {
//
//        return "noor";
//    }
//    @RequestMapping("/all")
//    public List<User> l() {
//
//        return userService.all();
//    }
//
//
//
//    @PostMapping(value = "/createUser")
//    public User createUser(@RequestParam(name = "name") String name ,@RequestParam(name = "password") String password,@RequestParam(name = "selected") String selected) {
//        User user = new User();
//        user.setPassword(password);
//        user.setSelected(selected);
//        user.setUsername(name);
//        System.out.println(user.getSelected());
//        System.out.println(user.getId());
//        if (user.getSelected().equals("ROLE_ADMIN"))
//            return userService.saveUserWithAdminRole(user);
//
//        else if (user.getSelected().equals("ROLE_INSTRUCTOR"))
//            return userService.saveWithInstructorRole(user);
//
//        else if (user.getSelected().equals("ROLE_STUDENT"))
//           return userService.saveWithStudentRole(user);
//
//        return user;
//    }
//
//
//}
