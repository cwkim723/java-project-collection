package com.todo.todobackend.model.dto;

import com.todo.todobackend.model.entity.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TodoDTO {
    private String id;
    private String title;
    private boolean done;

    public TodoDTO(final TodoEntity todoEntity) {
        this.id = todoEntity.getId();
        this.title = todoEntity.getTitle();
        this.done = todoEntity.isDone();
    }
}
