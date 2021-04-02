package com.axsos.exambuilder.services;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.axsos.exambuilder.models.User;
import com.axsos.exambuilder.repositories.RoleRepository;
import com.axsos.exambuilder.repositories.StudentExamRepository;
import com.axsos.exambuilder.repositories.UserRepository;

@Service
public class StudentService {
	private  final StudentExamRepository studentExamRepository;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public StudentService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, StudentExamRepository studentExamRepository)
	{
		this.studentExamRepository = studentExamRepository;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public List<User> findAllByRole(String role){
		return userRepository.findAllByRole(role);
	}



	public List<Object[]> top5(){

		return studentExamRepository.top5();
	}

	public User saveWithStudentRole(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(roleRepository.findByName("ROLE_STUDENT"));
		return userRepository.save(user);
	}





	// 3
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}


	public User findById(Long id) {
		return userRepository.getById(id);
	}
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	public List<User> all() {
		return userRepository.findAll();
	}

}
