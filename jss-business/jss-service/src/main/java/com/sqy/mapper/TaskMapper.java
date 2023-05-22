package com.sqy.mapper;

import com.sqy.domain.task.Task;
import com.sqy.dto.TaskDto;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper implements Mapper<TaskDto, Task> {
    @Override
    public Task getModelFromDto(TaskDto taskDto) {
        Task task = new Task();
        task.setId(taskDto.id());
        task.setName(taskDto.name());
        task.setDescription(taskDto.description());
        task.setPerformer(taskDto.performer());
        task.setEstimatedHours(taskDto.estimatedHours());
        task.setDeadline(taskDto.deadline());
        task.setStatus(taskDto.status());
        task.setAuthor(taskDto.author());
        task.setCreationDate(taskDto.creationDate());
        task.setLastUpdateDate(taskDto.lastUpdateDate());
        return task;
    }

    @Override
    public TaskDto getDtoFromModel(Task task) {
        return new TaskDto(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getPerformer(),
                task.getEstimatedHours(),
                task.getDeadline(),
                task.getStatus(),
                task.getAuthor(),
                task.getCreationDate(),
                task.getLastUpdateDate()
        );
    }
}
