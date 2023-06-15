package com.sqy.controller;

import com.sqy.dto.task.TaskToRelatedTaskDto;
import com.sqy.service.interfaces.TaskToRelatedTaskService;
import com.sqy.util.MappingUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task-related")
@RequiredArgsConstructor
@Slf4j
public class TaskToRelatedTaskController {
    private final TaskToRelatedTaskService taskToRelatedTaskService;

    @GetMapping("/{relationship-id}")
    public ResponseEntity<TaskToRelatedTaskDto> getById(@PathVariable("relationship-id") long relationshipId) {
        log.info("Invoke getById({}).", relationshipId);
        TaskToRelatedTaskDto result = taskToRelatedTaskService.getById(relationshipId);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/by-task-id/{task-id}")
    public ResponseEntity<List<TaskToRelatedTaskDto>> getAllByTaskId(@PathVariable("task-id") long taskId) {
        log.info("Invoke getAllByTaskId({}).", taskId);
        return ResponseEntity.ok(taskToRelatedTaskService.getAllRelationshipsForTask(taskId));
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveRelationship(@RequestBody TaskToRelatedTaskDto taskToRelatedTaskDto) {
        log.info("Invoke addRelatedTask({}).", taskToRelatedTaskDto);
        Long resultId = taskToRelatedTaskService.addRelatedTask(taskToRelatedTaskDto);
        if (resultId == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok("{\"id\": " + resultId + "}");
    }

    @DeleteMapping("/{relationship-id}")
    public ResponseEntity<String> deleteRelationshipById(@PathVariable("relationship-id") long relationshipId) {
        log.info("Invoke deleteRelationship({}).", relationshipId);
        boolean status = taskToRelatedTaskService.removeRelationshipById(relationshipId);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/by-task-id/{task-id}")
    public ResponseEntity<String> deleteAllRelationshipsByTaskId(@PathVariable("task-id") long taskId) {
        log.info("Invoke deleteAllRelationshipsByTaskId({}).", taskId);
        boolean status = taskToRelatedTaskService.removeAllRelationshipsForTask(taskId);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return ResponseEntity.notFound().build();
    }
}
