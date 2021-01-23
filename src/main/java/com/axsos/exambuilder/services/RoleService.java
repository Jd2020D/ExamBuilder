package com.axsos.exambuilder.services;

import com.axsos.exambuilder.models.Role;
import com.axsos.exambuilder.repositories.RoleRepository;
import com.axsos.exambuilder.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;


    }

    public List<Role> findByName(String name){
        return roleRepository.findByName(name);

    }




    public List<Role>getAll(){

        return roleRepository.findAll();
    }
}
