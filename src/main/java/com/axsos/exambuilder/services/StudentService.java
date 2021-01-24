package com.axsos.exambuilder.services;

import com.axsos.exambuilder.models.User;
import com.axsos.exambuilder.repositories.RoleRepository;
import com.axsos.exambuilder.repositories.StudentExamRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.axsos.exambuilder.repositories.UserRepository;

import java.util.List;

@Service
public class StudentService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private  final StudentExamRepository studentExamRepository;
	public StudentService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, StudentExamRepository studentExamRepository)
	{
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.studentExamRepository = studentExamRepository;
	}

	public List<User> findAllByRole(String role){
		return userRepository.findAllByRole(role);
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


	public List<Object[]> top5(){

		return studentExamRepository.top5();
	}

}
