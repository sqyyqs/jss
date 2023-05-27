package com.sqy.controller;

import com.sqy.dto.project.ProjectDto;
import com.sqy.dto.project.ProjectNewStatusDto;
import com.sqy.dto.project.ProjectSearchDto;
import com.sqy.service.interfaces.ProjectService;
import com.sqy.util.MappingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {
    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAll() {
        logger.info("Invoke getAll().");
        return ResponseEntity.ok(projectService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getById(@PathVariable Long id) {
        logger.info("Invoke getById({}).", id);
        ProjectDto result = projectService.getById(id);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody ProjectDto projectDto) {
        logger.info("Invoke save({}).", projectDto);
        boolean status = projectService.save(projectDto);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody ProjectDto projectDto) {
        logger.info("Invoke update({}).", projectDto);
        boolean status = projectService.update(projectDto);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();

    }

    @PutMapping("/update-state")
    public ResponseEntity<String> updateState(@RequestBody ProjectNewStatusDto projectNewStatusDto) {
        logger.info("Invoke updateState({}).", projectNewStatusDto);
        boolean status = projectService.updateState(projectNewStatusDto);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProjectDto>> search(@RequestBody ProjectSearchDto projectSearchDto) {
        logger.info("Invoke search({}).", projectSearchDto);
        return ResponseEntity.ok(projectService.search(projectSearchDto));
    }
}
