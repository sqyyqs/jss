package com.sqy.controller;

import com.sqy.dto.TaskDto;
import com.sqy.service.TaskService;

import java.util.List;

public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    public List<TaskDto> getAll() {
        return taskService.getAll();
    }

    public TaskDto getById(Long id) {
        return taskService.getById(id);
    }

    public void save(TaskDto taskDto) {
        taskService.save(taskDto);
    }

    public void update(TaskDto taskDto) {
        taskService.update(taskDto);
    }

    public void delete(Long id) {
        taskService.delete(id);
    }

    public List<TaskDto> search() {
        return taskService.search();
    }
}
