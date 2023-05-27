package com.sqy.service.interfaces;

import com.sqy.dto.task.TaskDto;
import com.sqy.dto.task.TaskFilterDto;
import com.sqy.dto.task.TaskNewStatusDto;

import java.util.List;

public interface TaskService {
    boolean save(TaskDto taskDto);

    boolean update(TaskDto taskDto);

    boolean updateStatus(TaskNewStatusDto taskNewStatusDto);

    List<TaskDto> searchByFilters(TaskFilterDto taskFilterDto);
}
