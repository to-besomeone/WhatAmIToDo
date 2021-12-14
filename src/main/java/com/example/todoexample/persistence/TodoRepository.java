package com.example.todoexample.persistence;

import com.example.todoexample.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {
//    JpaRepository<T, Id>
//    T : 테이블에 매핑될 엔티티 클래스;
//    Id : 엔티티의 기본 키의 타입;
    @Query(value="select * from Todo where userId = ?1", nativeQuery=true)
    List<TodoEntity> findByUserId(String userId);
}
