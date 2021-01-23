package com.axsos.exambuilder.repositories;


import com.axsos.exambuilder.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    List<User> findAll();

    @Query(value = "select users.* from users  ,users_roles ,roles where users.id = users_roles.user_id and roles.id =users_roles.role_id and roles.name= :role",nativeQuery = true)
    List<User> findAllByRole(String role);

    User getById(Long id);


}