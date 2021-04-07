package com.axsos.exambuilder.validator;

import java.util.regex.Pattern;

import com.axsos.exambuilder.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.axsos.exambuilder.services.UserService;


@Component
public class UserValidator implements Validator {
    static final Pattern EMAIL_REGEX = Pattern.compile("^[a-zA-Z0-9.+_-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z]+$", Pattern.CASE_INSENSITIVE);

    @Autowired
    private  UserService userService;
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        User user = (User) object;
        User userToEdit =userService.findByUsername(user.getUsername());
        if(userToEdit!=null)
            if(user.getId()!=userToEdit.getId())
                errors.rejectValue("username", "Unique");

        if(!EMAIL_REGEX.matcher(user.getUsername()).find())
            errors.rejectValue("username", "Format");
        if (!user.getPasswordConfirmation().equals(user.getPassword())) {
            // 3
            errors.rejectValue("passwordConfirmation", "Match");
        }
    }
}
