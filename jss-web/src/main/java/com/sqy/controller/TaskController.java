package com.sqy.controller;

import com.sqy.dto.task.TaskDto;
import com.sqy.dto.task.TaskFilterDto;
import com.sqy.dto.task.TaskNewStatusDto;
import com.sqy.service.interfaces.TaskService;
import com.sqy.util.MappingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class TaskController {
    /**
     * Операции над задачами
     * <p>
     * Создание задачи. При создании задачи должны заполняться все обязательные поля, и проходить соответствующие проверки. Также задача автоматически создается в статусе Новая.
     * Изменение задачи. Могут редактироваться поля задачи, но не может меняться поле статус и генерируемые значения. Автор задачи в таком случае должен быть изменен.
     * Поиск задач - задачи должны искать по текстовому значению (по полям Наименование задачи)
     * и с применением фильтров (по статусам задачи, по исполнителю, по автору задачи, по периоду крайнего срока задачи, по периоду создания задачи).
     * Фильтры все не обязательны, как и текстовое поле. Результат должен быть отсортирован по дате создания задачи в обратном порядке (сначала свежие задачи).
     * Изменение статуса задачи. При этом действии задача переводится в новый статус, который поступил на вход. Переход по статусам возможен согласно диаграмме ниже.
     */
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<TaskDto>> searchByFilters(@RequestBody TaskFilterDto taskFilterDto) {
        logger.info("Invoke searchByFilters({}).", taskFilterDto);
        return ResponseEntity.ok(taskService.searchByFilters(taskFilterDto));
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody TaskDto taskDto) {
        logger.info("Invoke save({}).", taskDto);
        boolean status = taskService.save(taskDto);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody TaskDto taskDto) {
        logger.info("Invoke update({}).", taskDto);
        boolean status = taskService.update(taskDto);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return ResponseEntity.ok().build();

    }

    @PutMapping("/update-status")
    public ResponseEntity<String> updateStatus(@RequestBody TaskNewStatusDto taskNewStatusDto) {
        logger.info("Invoke updateStatus({}).", taskNewStatusDto);
        boolean status = taskService.updateStatus(taskNewStatusDto);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return ResponseEntity.ok().build();
    }


}
