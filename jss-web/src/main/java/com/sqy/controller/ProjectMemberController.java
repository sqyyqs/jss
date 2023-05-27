package com.sqy.controller;

import com.sqy.dto.ProjectMemberDto;
import com.sqy.service.interfaces.ProjectMemberService;
import com.sqy.util.MappingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/api/v1/project-member")
public class ProjectMemberController {
    private static final Logger logger = LoggerFactory.getLogger(ProjectMemberController.class);
    private final ProjectMemberService projectMemberService;

    public ProjectMemberController(ProjectMemberService projectMemberService) {
        this.projectMemberService = projectMemberService;
    }

    @GetMapping
    public ResponseEntity<List<ProjectMemberDto>> getAllProjectMembers() {
        logger.info("Invoke getAllProjectMembers().");
        return ResponseEntity.ok(projectMemberService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectMemberDto> getByProjectMemberId(@PathVariable("id") Long id) {
        logger.info("Invoke getByProjectMemberId({}).", id);
        ProjectMemberDto result = projectMemberService.getById(id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/project/{project-id}")
    public ResponseEntity<List<ProjectMemberDto>> getAllByProjectId(@PathVariable("project-id") Long projectId) {
        logger.info("Invoke getByProjectMemberId({}).", projectId);
        return ResponseEntity.ok(projectMemberService.getAllByProjectId(projectId));
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody ProjectMemberDto projectMemberDto) {
        logger.info("Invoke save({}).", projectMemberDto);
        boolean status = projectMemberService.save(projectMemberDto);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        logger.info("Invoke delete({}).", id);
        boolean status = projectMemberService.delete(id);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return ResponseEntity.notFound().build();
    }

}
