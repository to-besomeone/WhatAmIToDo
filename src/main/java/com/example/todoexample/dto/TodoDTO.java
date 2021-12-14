package com.example.todoexample.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import com.example.todoexample.model.TodoEntity;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {
    private String id;
    private String userId;
    private String title;
    @Nullable
    private Date from;
    @Nullable
    private Date to;
    private boolean done;

    public TodoDTO(final TodoEntity entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.from = entity.getFromdt();
        this.to = entity.getTodt();
        this.done = entity.isDone();
    }

    public static TodoEntity toEntity(final TodoDTO dto){
        return TodoEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .todt(dto.getTo())
                .fromdt(dto.getFrom())
                .done(dto.isDone())
                .build();
    }


}