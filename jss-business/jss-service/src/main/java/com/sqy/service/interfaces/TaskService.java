package com.sqy.service.interfaces;

import com.sqy.dto.task.TaskDto;
import com.sqy.dto.task.TaskFileDto;
import com.sqy.dto.task.TaskFilterDto;
import com.sqy.dto.task.TaskNewStatusDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TaskService {
    Long save(TaskDto taskDto);

    boolean update(TaskDto taskDto);

    boolean updateStatus(TaskNewStatusDto taskNewStatusDto);

    List<TaskDto> searchByFilters(TaskFilterDto taskFilterDto);

    boolean uploadFileToTaskId(MultipartFile file, long id);

    TaskFileDto getFileFromRelatedTask(long taskId);

    boolean deleteFileFromRelatedTask(long taskId);
}
