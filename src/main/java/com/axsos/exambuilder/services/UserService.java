package com.axsos.exambuilder.services;


import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.axsos.exambuilder.models.Role;
import com.axsos.exambuilder.models.User;
import com.axsos.exambuilder.repositories.RoleRepository;
import com.axsos.exambuilder.repositories.UserRepository;
import com.axsos.exambuilder.repositories.UserRoleRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, UserRoleRepository userRoleRepository)     {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
		this.userRoleRepository = userRoleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    // 1
    public User saveWithInstructorRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_INSTRUCTOR"));
        return userRepository.save(user);
    }


    public User saveWithStudentRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_STUDENT"));
        return userRepository.save(user);
    }

    // 2
    public User saveUserWithAdminRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_ADMIN"));
        return userRepository.save(user);
    }




    // 3
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public User findById(Long id) {
    	return userRepository.findById(id).orElse(null);
    }
    public List<User> all() {
        return userRepository.findAll();
    }
}
