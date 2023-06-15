package com.sqy.service;

import com.sqy.domain.email.Email;
import com.sqy.domain.email.EmailTemplate;
import com.sqy.domain.task.Task;
import com.sqy.domain.task.TaskEmailInfo;
import com.sqy.domain.task.TaskFile;
import com.sqy.domain.task.TaskStatus;
import com.sqy.dto.task.TaskDto;
import com.sqy.dto.task.TaskFileDto;
import com.sqy.dto.task.TaskFilterDto;
import com.sqy.dto.task.TaskNewStatusDto;
import com.sqy.mapper.TaskFileMapper;
import com.sqy.mapper.TaskMapper;
import com.sqy.rabbitmq.RabbitMqProducer;
import com.sqy.repository.TaskFileRepository;
import com.sqy.repository.TaskRepository;
import com.sqy.service.interfaces.TaskService;
import com.sqy.util.EmailTemplateProcessor;
import com.sqy.util.TaskSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static com.sqy.mapper.TaskMapper.getModelFromDto;

@Service
@RequiredArgsConstructor
@Log4j2
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskFileRepository taskFileRepository;
    private final RabbitMqProducer rabbitMqProducer;

    @Override
    @Nullable
    public Long save(TaskDto taskDto) {
        log.info("Invoke save({}).", taskDto);
        if (taskDto.getId() != null) {
            taskDto.setId(null);
        }
        try {
            Long savedId = taskRepository.save(getModelFromDto(taskDto)).getTaskId();
            TaskEmailInfo info = taskRepository.getTaskEmailInfoByTaskId(savedId);
            if (info == null || info.getTo() == null) {
                return savedId;
            }
            Email email = Email.builder().recipient(info.getTo())
                    .subject("У вас новая задача!")
                    .html(EmailTemplateProcessor.prepareMessage(EmailTemplate.EMPLOYEE_EMAIL, Map.of(
                            "performer.first.name", info.getPerformerFirstName(),
                            "performer.last.name", info.getPerformerLastName(),
                            "project.name", info.getProjectName(),
                            "task.name", info.getTaskName(),
                            "task.deadline", info.getTaskDeadline().format(DateTimeFormatter.ISO_LOCAL_DATE),
                            "author.first.name", info.getAuthorFirstName(),
                            "author.last.name", info.getAuthorLastName()
                    ))).build();
            rabbitMqProducer.sendMessage("emailQueue", email);
            return savedId;
        } catch (DataIntegrityViolationException ex) {
            log.info("Invoke save({}) with exception.", taskDto, ex);
        }
        return null;
    }

    @Override
    public boolean update(TaskDto taskDto) {
        log.info("Invoke update({}).", taskDto);
        if (taskDto.getId() == null || !taskRepository.existsById(taskDto.getId())) {
            return false;
        }
        try {
            taskRepository.save(getModelFromDto(taskDto));
            return true;
        } catch (DataIntegrityViolationException ex) {
            log.info("Invoke update({}) with exception.", taskDto, ex);
        }
        return false;
    }

    @Override
    public boolean updateStatus(TaskNewStatusDto taskNewStatusDto) {
        log.info("Invoke updateStatus({}).", taskNewStatusDto);
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
        return true;
    }

    @Override
    public List<TaskDto> searchByFilters(TaskFilterDto taskFilterDto) {
        log.info("Invoke searchByFilters({}).", taskFilterDto);
        Specification<Task> specification = TaskSpecificationBuilder.buildSpecification(taskFilterDto);
        return taskRepository.findAll(specification, Sort.by(Sort.Direction.DESC, "creationDate"))
                .stream()
                .map(TaskMapper::getDtoFromModel)
                .toList();
    }

    @Override
    public boolean uploadFileToTaskId(MultipartFile file, long taskId) {
        log.info("Invoke uploadFileToTaskId({}, {}).", file, taskId);
        TaskFile result = TaskFileMapper.getFromMultipartFile(file, taskId);
        if (result == null) {
            return false;
        }
        try {
            taskFileRepository.save(result);
            return true;
        } catch (DataIntegrityViolationException e) {
            log.info("Invoke uploadFileToTaskId({}, {}) with exception.", file, taskId, e);
        }
        return false;
    }

    @Override
    @Nullable
    public TaskFileDto getFileFromRelatedTask(long taskId) {
        log.info("Invoke getFileFromRelatedTask({}).", taskId);
        return TaskFileMapper.getDtoFromModel(taskFileRepository.getTaskFileByTask_TaskId(taskId));
    }

    @Override
    public boolean deleteFileFromRelatedTask(long taskId) {
        log.info("Invoke deleteFileFromRelatedTask({}).", taskId);
        if (taskFileRepository.existsByTask_TaskId(taskId)) {
            taskFileRepository.deleteByTask_TaskId(taskId);
            return true;
        }
        return false;
    }
}
