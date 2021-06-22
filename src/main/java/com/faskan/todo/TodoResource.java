package com.faskan.todo;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

import java.util.List;

@Controller("/api/todos")
public class TodoResource {
    private final TodoRepository todoRepository;

    public TodoResource(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    @Get
    public List<Todo> getAllTodos(){
        return todoRepository.getAll();
    }

    @Post
    public void saveTodo(@Body Todo todo) {
        todoRepository.save(todo);
    }

}
