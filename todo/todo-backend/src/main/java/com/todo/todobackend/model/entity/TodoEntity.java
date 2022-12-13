package com.todo.todobackend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TodoEntity {
    private String id;
    private String userId;
    private String title;
    private boolean done;
}
