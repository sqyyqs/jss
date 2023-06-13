package com.sqy.controller;

import com.sqy.dto.task.TaskDto;
import com.sqy.dto.task.TaskFilterDto;
import com.sqy.dto.task.TaskNewStatusDto;
import com.sqy.service.interfaces.TaskService;
import com.sqy.util.MappingUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
