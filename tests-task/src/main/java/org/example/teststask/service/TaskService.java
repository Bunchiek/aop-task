package org.example.teststask.service;

import org.example.teststask.dto.TaskDto;

import java.util.List;

public interface TaskService {


    TaskDto getTask(Long id);

    List<TaskDto> getTasks();

    TaskDto createTask(TaskDto task);

    TaskDto updateTask(Long taskId, TaskDto task);

    void deleteTask(Long id);


}
