package org.example.teststask.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.teststask.dto.TaskDto;
import org.example.teststask.entity.Task;
import org.example.teststask.repository.TaskRepository;
import org.example.teststask.service.TaskService;
import org.example.teststask.util.TaskMapper;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskDto getTask(Long id) {
        return TaskMapper.toTaskDto(taskRepository
                .findById(id).orElseThrow(()-> new EntityNotFoundException(
                        MessageFormat.format("Задача с ID {0} не найдена!", id))));
    }

    @Override
    public List<TaskDto> getTasks() {
        return taskRepository.findAll().stream()
                .map(TaskMapper::toTaskDto)
                .toList();
    }

    @Override
    public TaskDto createTask(TaskDto task) {
        return TaskMapper.toTaskDto(taskRepository.save(TaskMapper.toTask(task)));
    }

    @Override
    public TaskDto updateTask(Long taskId, TaskDto task) {
        Task existedTask = taskRepository.findById(taskId).orElseThrow(()-> new EntityNotFoundException(
                MessageFormat.format("Задача с ID {0} не найдена!", taskId)));
        existedTask.setTitle(task.getTitle());
        existedTask.setDescription(task.getDescription());
        existedTask.setStatus(task.getStatus());
        return TaskMapper.toTaskDto(taskRepository.save(existedTask));
    }

    @Override
    public void deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(MessageFormat.format("Задача с ID {0} не найдена!", id));
        }
    }
}
