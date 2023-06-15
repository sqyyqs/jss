package com.sqy.controller;

import com.sqy.dto.task.TaskDto;
import com.sqy.dto.task.TaskFileDto;
import com.sqy.dto.task.TaskFilterDto;
import com.sqy.dto.task.TaskNewStatusDto;
import com.sqy.service.interfaces.TaskService;
import com.sqy.util.MappingUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
@Slf4j
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/search")
    public ResponseEntity<List<TaskDto>> searchByFilters(@RequestBody TaskFilterDto taskFilterDto) {
        log.info("Invoke searchByFilters({}).", taskFilterDto);
        return ResponseEntity.ok(taskService.searchByFilters(taskFilterDto));
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody TaskDto taskDto) {
        log.info("Invoke save({}).", taskDto);
        Long resultId = taskService.save(taskDto);
        if (resultId == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok("{\"id\": " + resultId + "}");
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody TaskDto taskDto) {
        log.info("Invoke update({}).", taskDto);
        boolean status = taskService.update(taskDto);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();

    }

    @PutMapping("/update-status")
    public ResponseEntity<String> updateStatus(@RequestBody TaskNewStatusDto taskNewStatusDto) {
        log.info("Invoke updateStatus({}).", taskNewStatusDto);
        boolean status = taskService.updateStatus(taskNewStatusDto);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PostMapping("/upload-file/{task-id}")
    public ResponseEntity<String> uploadTaskFile(@RequestBody MultipartFile file,
                                                 @PathVariable("task-id") long taskId) {
        log.info("Invoke uploadTaskFile({}, {}).", file, taskId);
        boolean status = taskService.uploadFileToTaskId(file, taskId);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return ResponseEntity.badRequest().body("{\"message\": \"error while uploading file\"}");
    }

    @GetMapping("/download-file/{task-id}")
    public ResponseEntity<?> downloadFileFromRelatedTask(@PathVariable("task-id") long taskId) {
        log.info("Invoke downloadFileFromRelatedTask({}).", taskId);
        TaskFileDto taskFile = taskService.getFileFromRelatedTask(taskId);
        ByteArrayResource file = new ByteArrayResource(taskFile.file());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(taskFile.fileContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"task_file\"")
                .body(file);
    }

    @DeleteMapping("/file/{task-id}")
    public ResponseEntity<?> deleteFileFromRelatedTask(@PathVariable("task-id") long taskId) {
        log.info("Invoke deleteFileFromRelatedTask({}).", taskId);
        boolean status = taskService.deleteFileFromRelatedTask(taskId);
        if (status) {
            return ResponseEntity.ok().body("");
        }
        return ResponseEntity.badRequest().body("{\"message\": \"error while deleting file\"}");
    }

}
