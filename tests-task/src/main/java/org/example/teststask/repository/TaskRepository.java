package org.example.teststask.repository;

import org.example.teststask.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public interface TaskRepository extends JpaRepository<Task, Long> {

}
