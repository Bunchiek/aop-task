package org.example.teststask.controller;

import org.example.teststask.dto.TaskDto;
import org.example.teststask.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> findById(@PathVariable("id") Long taskId) {
        return ResponseEntity.ok(taskService.getTask(taskId));
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> findAll() {
        return ResponseEntity.ok(taskService.getTasks());
    }

    @PostMapping
    public ResponseEntity<TaskDto> create(@RequestBody TaskDto taskDto) {
        return ResponseEntity.status(201).body(taskService.createTask(taskDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> update(@PathVariable("id") Long taskId, @RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.updateTask(taskId, taskDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }


}
