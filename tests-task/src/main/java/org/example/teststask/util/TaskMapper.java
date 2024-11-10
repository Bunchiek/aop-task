package org.example.teststask.util;

import org.example.teststask.dto.TaskDto;
import org.example.teststask.entity.Task;

public class TaskMapper {

    public static TaskDto toTaskDto(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setStatus(task.getStatus());
        return taskDto;
    }

    public static Task toTask(TaskDto taskDto) {
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus());
        return task;
    }
}
