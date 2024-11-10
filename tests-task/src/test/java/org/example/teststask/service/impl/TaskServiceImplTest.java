package org.example.teststask.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.teststask.dto.TaskDto;
import org.example.teststask.entity.Status;
import org.example.teststask.entity.Task;
import org.example.teststask.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskServiceImpl;

    private Task task1;
    private Task task2;
    private TaskDto taskDto1;
    private TaskDto taskDto2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        task1 = new Task(1L, "Test Task 1", "Test Description 1", Status.IN_PROGRESS);
        task2 = new Task(2L, "Test Task 2", "Test Description 2", Status.COMPLETED);
        taskDto1 = new TaskDto(1L, "Test Task 1", "Test Description 1", Status.IN_PROGRESS);
        taskDto2 = new TaskDto(2L, "Test Task 2", "Test Description 2", Status.COMPLETED);

    }

    @Test
    @DisplayName("Модульный тест получения Task")
    void getTask_shouldReturnTaskDto_whenTaskExists() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));

        TaskDto result = taskServiceImpl.getTask(1L);

        assertNotNull(result);
        assertEquals(taskDto1.getId(), result.getId());
        assertEquals(taskDto1.getTitle(), result.getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Модульный тест ошибки отсутствия Task при получении")
    void getTask_shouldThrowException_whenTaskNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            taskServiceImpl.getTask(1L);
        });
        assertEquals("Задача с ID 1 не найдена!", exception.getMessage());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Модульный тест получения списка Task")
    void getTasks_shouldReturnListOfTaskDto_whenTasksExist() {
        List<Task> tasks = List.of(task1, task2);
        when(taskRepository.findAll()).thenReturn(tasks);

        List<TaskDto> result = taskServiceImpl.getTasks();

        assertEquals(2, result.size());
        assertEquals(taskDto1.getId(), result.get(0).getId());
        assertEquals(taskDto2.getId(), result.get(1).getId());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Модульный тест создания Task")
    void createTask_shouldReturnCreatedTaskDto() {
        when(taskRepository.save(any(Task.class))).thenReturn(task1);

        TaskDto result = taskServiceImpl.createTask(taskDto1);

        assertNotNull(result);
        assertEquals(taskDto1.getId(), result.getId());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Модульный тест обновления Task")
    void updateTask_shouldReturnUpdatedTaskDto_whenTaskExists() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));
        when(taskRepository.save(any(Task.class))).thenReturn(task1);

        TaskDto result = taskServiceImpl.updateTask(1L, taskDto1);

        assertNotNull(result);
        assertEquals(taskDto1.getTitle(), result.getTitle());
        assertEquals(taskDto1.getDescription(), result.getDescription());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Модульный тест ошибки отсутствия Task при обновлении")
    void updateTask_shouldThrowException_whenTaskNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            taskServiceImpl.updateTask(1L, taskDto1);
        });
        assertEquals("Задача с ID 1 не найдена!", exception.getMessage());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Модульный тест удаления Task")
    void deleteTask_shouldDeleteTask_whenTaskExists() {
        when(taskRepository.existsById(1L)).thenReturn(true);

        taskServiceImpl.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Модульный тест ошибки отсутствия Task при удалении")
    void deleteTask_shouldThrowException_whenTaskNotFound() {
        when(taskRepository.existsById(1L)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            taskServiceImpl.deleteTask(1L);
        });
        assertEquals("Задача с ID 1 не найдена!", exception.getMessage());
        verify(taskRepository, times(1)).existsById(1L);
    }
}