package com.todo.todobackend.controller;

import com.todo.todobackend.model.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {
    @GetMapping
    public String testController() {
        return "Hello World!";
    }

    @GetMapping("/{id}")
    public String testControllerWithPathVariables(@PathVariable(required = false) int id) {
        return "Hello World! ID " + id;
    }

    @GetMapping("/testResponseBody")
    public ResponseDTO<String> testControllerResponseBody() {
        List<String> list = new ArrayList<>();
        list.add("Hello Wordl! I'm ResponseDTO");
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        /*
            {
                "error": null,
                "data": [
                    "Hello Wordl! I'm ResponseDTO"
                ]
            }
        */
        return response;
    }

    @GetMapping("/testResponseEntity")
    public ResponseEntity<?> testControllerResponseEntity() {
        List<String> list = new ArrayList<>();
        list.add("Hello Wordl! I'm ResponseEntity. And you got 400!");
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        /*
            400 Bad Request
            {
                "error": null,
                "data": [
                    "Hello Wordl! I'm ResponseEntity. And you got 400!"
                ]
            }
        */
        return ResponseEntity.badRequest().body(response);
    }
}
