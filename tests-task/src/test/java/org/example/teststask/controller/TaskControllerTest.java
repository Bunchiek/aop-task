package org.example.teststask.controller;

import org.example.teststask.dto.TaskDto;
import org.example.teststask.entity.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class TaskControllerTest extends AbstractTest {

    @Test
    @DisplayName("Интеграционный тест получения Task")
    void getTask_shouldReturnTaskDto_whenTaskExists() throws Exception {
        mockMvc.perform(get("/api/task/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Test Task 1"))
                .andExpect(jsonPath("$.description").value("Test Description 1"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }

    @Test
    @DisplayName("Интеграционный тест ошибки отсутствия Task при получении")
    void getTask_shouldThrowException_whenTaskNotFound() throws Exception {
        mockMvc.perform(get("/api/task/3"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("Задача с ID 3 не найдена!", result.getResolvedException().getMessage()));
    }

    @Test
    @DisplayName("Интеграционный тест получения списка Task")
    void getTasks_shouldReturnListOfTaskDto_whenTasksExist() throws Exception {
        mockMvc.perform(get("/api/task"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("Интеграционный тест создания Task")
    void createTask_shouldReturnCreatedTaskDto() throws Exception {
        TaskDto task3 = new TaskDto();
        task3.setTitle("Test Task 3");
        task3.setDescription("Test Description 3");
        task3.setStatus(Status.COMPLETED);

        mockMvc.perform(post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(task3)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Test Task 3"));

    }

    @Test
    @DisplayName("Интеграционный тест обновления Task")
    void updateTask_shouldReturnUpdatedTaskDto_whenTaskExists() throws Exception {
        TaskDto updatedTask = new TaskDto();
        updatedTask.setTitle("Updated Task");

        mockMvc.perform(put("/api/task/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedTask)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Updated Task"));
    }

    @Test
    @DisplayName("Интеграционный тест ошибки отсутствия Task при обновлении")
    void updateTask_shouldThrowException_whenTaskNotFound() throws Exception {
        TaskDto updatedTask = new TaskDto();
        updatedTask.setTitle("Updated Task");
        mockMvc.perform(put("/api/task/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedTask)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("Задача с ID 3 не найдена!", result.getResolvedException().getMessage()));
    }

    @Test
    @DisplayName("Интеграционный тест удаления Task")
    void deleteTask_shouldDeleteTask_whenTaskExists() throws Exception {

        mockMvc.perform(delete("/api/task/2"))
                .andExpect(status().isNoContent())
                .andExpect(result -> assertEquals(1, taskRepository.count()));
    }

    @Test
    @DisplayName("Интеграционный тест ошибки отсутствия Task при удалении")
    void deleteTask_shouldThrowException_whenTaskNotFound() throws Exception {
        mockMvc.perform(delete("/api/task/3"))
                .andExpect(result -> assertEquals("Задача с ID 3 не найдена!", result.getResolvedException().getMessage()))
                .andExpect(result -> assertEquals(2, taskRepository.count()));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}