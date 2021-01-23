package com.axsos.exambuilder.services;

import com.axsos.exambuilder.models.User;
import com.axsos.exambuilder.repositories.RoleRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.axsos.exambuilder.repositories.ExamRepository;
import com.axsos.exambuilder.repositories.UserRepository;

import java.util.List;

@Service
	public class InstructorService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public InstructorService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder)     {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
