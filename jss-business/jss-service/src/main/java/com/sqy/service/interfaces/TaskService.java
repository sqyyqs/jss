package com.sqy.service.interfaces;

import com.sqy.dto.TaskDto;

import java.util.List;

public interface TaskService {
    List<TaskDto> getAll();

    TaskDto getById(Long id);

    void save(TaskDto taskDto);

    void update(TaskDto taskDto);

    void delete(Long id);

    List<TaskDto> search();
}
