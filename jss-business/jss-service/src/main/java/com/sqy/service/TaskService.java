package com.sqy.service;

import com.sqy.domain.task.Task;
import com.sqy.dto.TaskDto;
import com.sqy.mapper.Mapper;
import com.sqy.repository.TaskRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final Mapper<TaskDto, Task> taskMapper;

    public TaskService(TaskRepository taskRepository, Mapper<TaskDto, Task> taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public List<TaskDto> getAll() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::getDtoFromModel)
                .toList();
    }

    @Nullable
    public TaskDto getById(Long id) {
        return taskRepository.findById(id)
                .stream()
                .map(taskMapper::getDtoFromModel)
                .findAny()
                .orElse(null);
    }

    public void save(TaskDto TaskDto) {
        if (taskRepository.existsById(TaskDto.id())) {
            throw new IllegalArgumentException();
        }
        taskRepository.save(taskMapper.getModelFromDto(TaskDto));
    }

    public void update(TaskDto TaskDto) {
        if (!taskRepository.existsById(TaskDto.id())) {
            throw new IllegalArgumentException();
        }
        taskRepository.save(taskMapper.getModelFromDto(TaskDto));
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    public List<TaskDto> search() {
        return Collections.emptyList();
        // TODO: 17.05.2023
    }
}
