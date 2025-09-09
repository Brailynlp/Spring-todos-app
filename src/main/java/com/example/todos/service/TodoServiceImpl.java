package com.example.todos.service;
import com.example.todos.entity.Todo;
import com.example.todos.entity.User;
import com.example.todos.repository.TodoRepository;
import com.example.todos.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class TodoServiceImpl {
    UserRepository userRepository;
    TodoRepository todoRepository;

    public TodoServiceImpl(UserRepository userRepository, TodoRepository todoRepository) {
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
    }

    public User getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        return userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found " + email));
    }

    public Todo createTodo(Todo todo) {
        todo.setOwner(getCurrentUser());
        return todoRepository.save(todo);
    }

    public Todo getTodoById(Long id){
        Todo todo = todoRepository.findById(id).orElseThrow(()-> new RuntimeException("No existe el todo con este id: "));
        if (!todo.getOwner().equals(getCurrentUser())) {
            throw new RuntimeException("No está autorizado");
        }
        return todo;
    }

    public Todo updateTodo(Long id, Todo updatedTodo) {
        Todo todo = getTodoById(id);
        todo.setTitle(updatedTodo.getTitle());
        todo.setDescription(updatedTodo.getDescription());
        todo.setCompleted(updatedTodo.getCompleted());
        return todoRepository.save(todo);
    }

    public void deleteTodo(Long id) {
        Todo todo = getTodoById(id);
        todoRepository.delete(todo);
    }

    public List<Todo> getMyTodos() {
        return todoRepository.findByOwner(getCurrentUser().getEmail());
    }

}
