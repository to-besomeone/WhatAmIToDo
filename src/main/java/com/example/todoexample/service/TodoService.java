package com.example.todoexample.service;

import com.example.todoexample.dto.TodoDTO;
import com.example.todoexample.model.TodoEntity;
import com.example.todoexample.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<TodoEntity> getTodo(String userId){
        return repository.findByUserId(userId);
    }

    public List<TodoEntity> updateTodo(final TodoEntity entity){
        validate(entity);

        final Optional<TodoEntity> original = Optional.ofNullable(repository.getById(entity.getId()));

        original.ifPresent(todo -> {
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());

            repository.save(todo);
        });

        return getTodo(entity.getUserid());
    }

    public List<TodoEntity> deleteTodo(final TodoEntity entity){
        validate(entity);

        try{
            repository.delete(entity);

        } catch (Exception e){
            log.error("error during deleting entity ", entity.getId(), e);
            throw new RuntimeException("error during deleting entity "+entity.getId());
        }

        return getTodo(entity.getUserid());

    }
}
