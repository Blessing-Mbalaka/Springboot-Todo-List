package com.example.todoapp.controller;

import com.example.todoapp.model.Todo;
import com.example.todoapp.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("todos", todoService.getAllTodos());
        return "index";
    }

    @GetMapping("/todos/create")
    public String createForm(Model model) {
        model.addAttribute("todo", new Todo());
        return "create";
    }

    @PostMapping("/todos")
    public String createTodo(Todo todo) {
        todoService.saveTodo(todo);
        return "redirect:/";
    }




    @GetMapping("/todos/{id}/edit")
public String editForm(@org.springframework.web.bind.annotation.PathVariable Long id, Model model) {

    Todo todo = todoService.getTodoById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid todo ID: " + id));

    model.addAttribute("todo", todo);

    return "edit";
}

@PostMapping("/todos/{id}")
public String updateTodo(
        @org.springframework.web.bind.annotation.PathVariable Long id,
        Todo todo) {

    todo.setId(id);

    todoService.saveTodo(todo);

    return "redirect:/";
}

@PostMapping("/todos/{id}/delete")
public String deleteTodo(
        @org.springframework.web.bind.annotation.PathVariable Long id) {

    todoService.deleteTodo(id);

    return "redirect:/";
}
}