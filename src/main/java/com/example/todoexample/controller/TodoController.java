package com.example.todoexample.controller;

import com.example.todoexample.dto.RequestBodyDTO;
import com.example.todoexample.dto.ResponseDTO;
import com.example.todoexample.dto.TodoDTO;
import com.example.todoexample.model.TodoEntity;
import com.example.todoexample.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping("/insert")
    public ResponseEntity<?> getMyTodo(@RequestBody TodoDTO dto){

        try {
            String tempUserId = "temp-user";

            // (1) TodoEntity로 변환
            TodoEntity entity = TodoDTO.toEntity(dto);

            // (2) id를 null로 초기화
            // entity.setId(null);

            // (3) 임시 사용자 아이디 설정. (인증 구현 뒤 수정)
            entity.setUserId(tempUserId);

            // (4) 서비스를 이용해 TodoEntity 생성
            List<TodoEntity> entities = todoService.createTodo(entity);

            // (5) 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            // (6) 변환된 TodoDTO 리스트를 이용해 ResponseDTO 초기화
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);

        } catch(Exception e){
            String error = e.getMessage();
            log.warn(error);
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
