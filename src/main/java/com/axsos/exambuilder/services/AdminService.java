package com.axsos.exambuilder.services;


import com.axsos.exambuilder.models.Exam;
import com.axsos.exambuilder.models.StudentExam;
import com.axsos.exambuilder.models.User;
import com.axsos.exambuilder.repositories.RoleRepository;
import com.axsos.exambuilder.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ExamService examService;

    public AdminService(ExamService examService,UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder)     {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.examService = examService;

    }

    public List<User> findAllByRole(String role){
      return userRepository.findAllByRole(role);
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
        return userRepository.getById(id);
    }
    public void deleteUser(User userToDelete) {
        for(Exam exam :userToDelete.getExams())
            examService.deleteExam(exam);
        for(StudentExam studentExam : userToDelete.getStudentExams() )
            examService.deleteStudentExam(studentExam);
        userRepository.delete(userToDelete);
    }

    public List<User> all() {
        return userRepository.findAll();
    }
}
