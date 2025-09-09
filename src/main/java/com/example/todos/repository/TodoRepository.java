package com.example.todos.repository;

import com.example.todos.entity.Todo;
import com.example.todos.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {
    @Query("SELECT t FROM Todo t WHERE t.owner.email = :email")
    List<Todo> findByOwner(String email);
}
