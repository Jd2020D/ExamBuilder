package com.axsos.exambuilder.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.axsos.exambuilder.models.Role;
import com.axsos.exambuilder.models.UserRole;


@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
//    @Query("SELECT roles FROM User d JOIN d.roles WHERE user_id = ?1 and name = ?2")
//    List<Object[]>findUserRole(Long userId,String name);
}
