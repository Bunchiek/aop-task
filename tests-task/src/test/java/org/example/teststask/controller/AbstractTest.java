package org.example.teststask.controller;

import jakarta.transaction.Transactional;
import org.example.teststask.dto.TaskDto;
import org.example.teststask.entity.Status;
import org.example.teststask.entity.Task;
import org.example.teststask.repository.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
abstract class AbstractTest {

    protected static PostgreSQLContainer<?> postgreSQLContainer;

    static {
        DockerImageName postgres = DockerImageName.parse("postgres:12.3");
        postgreSQLContainer = new PostgreSQLContainer<>(postgres).withReuse(true);
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    public static void registerProperties(DynamicPropertyRegistry registry) {
        String jdbcUrl = postgreSQLContainer.getJdbcUrl();
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.url", () -> jdbcUrl);
    }

    @Autowired
    protected TaskRepository taskRepository;

    @Autowired
    protected MockMvc mockMvc;

    protected Task task1 = new Task();
    protected Task task2 = new Task();
    protected TaskDto taskDto1 = new TaskDto();
    protected TaskDto taskDto2 = new TaskDto();

    @BeforeEach
    public void setup() {

        task1 = new Task(1L, "Test Task 1", "Test Description 1", Status.IN_PROGRESS);
        task2 = new Task(2L, "Test Task 2", "Test Description 2", Status.COMPLETED);
        taskDto1 = new TaskDto(1L, "Test Task 1", "Test Description 1", Status.IN_PROGRESS);
        taskDto2 = new TaskDto(2L, "Test Task 2", "Test Description 2", Status.COMPLETED);

        taskRepository.save(task1);
        taskRepository.save(task2);

    }

    @AfterEach
    public void afterEach() {
        taskRepository.deleteAll();
    }
}
