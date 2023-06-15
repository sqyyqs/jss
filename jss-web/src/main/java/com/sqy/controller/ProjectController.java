package com.sqy.controller;

import com.sqy.dto.project.ProjectDto;
import com.sqy.dto.project.ProjectFileDto;
import com.sqy.dto.project.ProjectNewStatusDto;
import com.sqy.dto.project.ProjectSearchDto;
import com.sqy.service.interfaces.ProjectService;
import com.sqy.util.MappingUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
@Log4j2
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    @Operation(summary = "Получение всех проектов.")
    public ResponseEntity<List<ProjectDto>> getAll() {
        log.info("Invoke getAll().");
        return ResponseEntity.ok(projectService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение проекта по id.")
    public ResponseEntity<ProjectDto> getById(@PathVariable Long id) {
        log.info("Invoke getById({}).", id);
        ProjectDto result = projectService.getById(id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/save")
    @Operation(summary = "Добавление проекта в базу данных, id и status игнорируются(status при создании будет DRAFT).")
    public ResponseEntity<String> save(@RequestBody ProjectDto projectDto) {
        log.info("Invoke save({}).", projectDto);
        Long resultId = projectService.save(projectDto);
        if (resultId == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok("{\"id\": " + resultId + "}");
    }

    @PutMapping("/update")
    @Operation(summary = "Обновление проекта по id, status игнорируется. Обновлять статус в /update-state.")
    public ResponseEntity<String> update(@RequestBody ProjectDto projectDto) {
        log.info("Invoke update({}).", projectDto);
        boolean status = projectService.update(projectDto);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PutMapping("/update-state")
    @Operation(summary = "Обновление статуса проекта по id. Допустимые обновления: DRAFT -> IN_PROGRESS -> IN_TESTING -> COMPLETED.")
    public ResponseEntity<String> updateState(@RequestBody ProjectNewStatusDto projectNewStatusDto) {
        log.info("Invoke updateState({}).", projectNewStatusDto);
        boolean status = projectService.updateState(projectNewStatusDto);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping("/search")
    @Operation(summary = "Поиск по name, code и description среди проектов с выбранным статусом.")
    public ResponseEntity<List<ProjectDto>> search(@RequestBody ProjectSearchDto projectSearchDto) {
        log.info("Invoke search({}).", projectSearchDto);
        return ResponseEntity.ok(projectService.search(projectSearchDto));
    }

    @PostMapping("/file/{project-id}")
    @Operation(summary = "Загрузка файла в проект по id, у проекта может быть только 1 файл.")
    public ResponseEntity<String> uploadProjectFile(@RequestBody MultipartFile file,
                                                    @PathVariable("project-id") long projectId) {
        log.info("Invoke uploadProjectFile({}, {}).", file, projectId);
        boolean status = projectService.uploadFileToProjectId(file, projectId);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/file/{project-id}")
    @Operation(summary = "Загрузка файла с проекта по id.")
    public ResponseEntity<?> downloadFileFromRelatedProject(@PathVariable("project-id") long projectId) {
        log.info("Invoke downloadFileFromRelatedTask({}).", projectId);
        ProjectFileDto projectFile = projectService.getFileFromRelatedProject(projectId);
        if (projectFile == null) {
            return ResponseEntity.notFound().build();
        }
        ByteArrayResource file = new ByteArrayResource(projectFile.file());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(projectFile.fileContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"project_file\"")
                .body(file);
    }

    @DeleteMapping("/file/{project-id}")
    @Operation(summary = "Удаление файла с проекта по id.")
    public ResponseEntity<?> deleteFileFromRelatedProject(@PathVariable("project-id") long projectId) {
        log.info("Invoke deleteFileFromRelatedProject({}).", projectId);
        boolean status = projectService.deleteFileFromRelatedProject(projectId);
        if (status) {
            return ResponseEntity.ok().body(MappingUtils.EMPTY_JSON);
        }
        return ResponseEntity.badRequest().build();
    }
}
