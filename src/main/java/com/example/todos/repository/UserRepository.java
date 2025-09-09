package com.example.todos.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.todos.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
