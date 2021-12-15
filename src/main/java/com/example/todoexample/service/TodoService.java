package com.example.todoexample.service;

import com.example.todoexample.dto.TodoDTO;
import com.example.todoexample.model.TodoEntity;
import com.example.todoexample.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TodoService {
    @Autowired
    private TodoRepository repository;

    public String testService(){
        TodoEntity entity = TodoEntity.builder().title("My first todo item").build();

        repository.save(entity);
        TodoEntity savedEntity = repository.findById(entity.getId()).get(); // findById returns abstract class, get returns real class
        return savedEntity.getTitle();
    }

    public void validate(TodoEntity entity){
        if (entity == null){
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if (entity.getUserid() == null){
            log.warn("Unknown user.");
            throw new RuntimeException("UserId cannot be null");
        }
    }
    public List<TodoEntity> createTodo(final TodoEntity entity){
        // Validation
        validate(entity);

        repository.save(entity);

        log.info("Entity Id: {} is saved.", entity.getId());
        return repository.findByUserId(entity.getUserid());
    }
}
