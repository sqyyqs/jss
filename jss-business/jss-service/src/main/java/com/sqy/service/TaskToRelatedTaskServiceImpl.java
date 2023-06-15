package com.sqy.service;

import com.sqy.dto.task.TaskToRelatedTaskDto;
import com.sqy.mapper.TaskToRelatedTaskMapper;
import com.sqy.repository.TaskToRelatedTaskRepository;
import com.sqy.service.interfaces.TaskToRelatedTaskService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sqy.mapper.TaskToRelatedTaskMapper.getModelFromDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskToRelatedTaskServiceImpl implements TaskToRelatedTaskService {

    private final TaskToRelatedTaskRepository taskToRelatedTaskRepository;

    @Override
    @Nullable
    public TaskToRelatedTaskDto getById(long relationshipId) {
        log.info("Invoke getById({}).", relationshipId);
        return taskToRelatedTaskRepository.findById(relationshipId)
                .map(TaskToRelatedTaskMapper::getDtoFromModel)
                .orElse(null);
    }

    @Override
    public List<TaskToRelatedTaskDto> getAllRelationshipsForTask(long taskId) {
        log.info("Invoke getAllRelationshipsForTask({}).", taskId);
        return taskToRelatedTaskRepository.findAllByTask_TaskId(taskId)
                .stream()
                .map(TaskToRelatedTaskMapper::getDtoFromModel)
                .toList();
    }

    @Override
    public Long addRelatedTask(TaskToRelatedTaskDto taskToRelatedTaskDto) {
        log.info("Invoke addRelatedTask({}).", taskToRelatedTaskDto);
        if (taskToRelatedTaskDto.getRelationshipId() != null) {
            taskToRelatedTaskDto.setRelationshipId(null);
        }
        try {
            return taskToRelatedTaskRepository.save(getModelFromDto(taskToRelatedTaskDto)).getRelationshipId();
        } catch (DataIntegrityViolationException e) {
            log.info("Invoke addRelatedTask({}) with exception.", taskToRelatedTaskDto, e);
        }
        return null;
    }

    @Override
    public boolean removeRelationshipById(long relationshipId) {
        log.info("Invoke removeRelationshipById({}).", relationshipId);
        if (taskToRelatedTaskRepository.existsById(relationshipId)) {
            taskToRelatedTaskRepository.deleteById(relationshipId);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAllRelationshipsForTask(long taskId) {
        log.info("Invoke removeAllRelationshipsForTask({}).", taskId);
        if (taskToRelatedTaskRepository.existsByTask_TaskId(taskId)) {
            taskToRelatedTaskRepository.deleteAllByTask_TaskId(taskId);
            return true;
        }
        return false;
    }
}
