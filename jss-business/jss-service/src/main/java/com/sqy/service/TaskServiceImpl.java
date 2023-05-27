package com.sqy.service;

import com.sqy.domain.task.Task;
import com.sqy.domain.task.TaskStatus;
import com.sqy.dto.task.TaskDto;
import com.sqy.dto.task.TaskFilterDto;
import com.sqy.dto.task.TaskNewStatusDto;
import com.sqy.mapper.TaskMapper;
import com.sqy.repository.TaskRepository;
import com.sqy.service.interfaces.TaskService;
import com.sqy.util.TaskSpecificationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sqy.mapper.TaskMapper.getModelFromDto;

@Service
public class TaskServiceImpl implements TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public boolean save(TaskDto taskDto) {
        logger.info("Invoke save({}).", taskDto);
        if (taskDto.getId() != null) {
            taskDto.setId(null);
        }
        try {
            taskRepository.save(getModelFromDto(taskDto));
            return true;
        } catch (DataIntegrityViolationException ex) {
            logger.info("Invoke save({}) with exception.", taskDto, ex);
        }
        return false;
    }

    @Override
    public boolean update(TaskDto taskDto) {
        logger.info("Invoke update({}).", taskDto);
        if (taskDto.getId() == null || !taskRepository.existsById(taskDto.getId())) {
            return false;
        }
        try {
            taskRepository.save(getModelFromDto(taskDto));
            return true;
        } catch (DataIntegrityViolationException ex) {
            logger.info("Invoke update({}) with exception.", taskDto, ex);
        }
        return false;
    }

    @Override
    public boolean updateStatus(TaskNewStatusDto taskNewStatusDto) {
        logger.info("Invoke updateStatus({}).", taskNewStatusDto);
        Task task = taskRepository.findById(taskNewStatusDto.id()).orElse(null);
        if (task == null) {
            return false;
        }
        switch (task.getStatus()) {
            case NEW -> {
                if (taskNewStatusDto.newTaskStatus() != TaskStatus.IN_PROGRESS) {
                    return false;
                }
            }
            case IN_PROGRESS -> {
                if (taskNewStatusDto.newTaskStatus() != TaskStatus.COMPLETED) {
                    return false;
                }
            }
            case COMPLETED -> {
                if (taskNewStatusDto.newTaskStatus() != TaskStatus.CLOSED) {
                    return false;
                }
            }
            case CLOSED -> {
                return false;
            }
        }
        task.setStatus(taskNewStatusDto.newTaskStatus());
        taskRepository.save(task);
        return false;
    }

    @Override
    public List<TaskDto> searchByFilters(TaskFilterDto taskFilterDto) {
        logger.info("Invoke searchByFilters({}).", taskFilterDto);
        Specification<Task> specification = TaskSpecificationBuilder.buildSpecification(taskFilterDto);
        return taskRepository.findAll(specification, Sort.by(Sort.Direction.DESC, "creationDate"))
                .stream()
                .map(TaskMapper::getDtoFromModel)
                .toList();
    }
}
