package com.sqy.controller;

import com.sqy.dto.projectmember.ProjectMemberDto;
import com.sqy.service.interfaces.ProjectMemberService;
import com.sqy.util.MappingUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
@RequiredArgsConstructor
@Log4j2
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    @GetMapping
    @Operation(summary = "Получение всех участников проекта.")
    public ResponseEntity<List<ProjectMemberDto>> getAllProjectMembers() {
        log.info("Invoke getAllProjectMembers().");
        return ResponseEntity.ok(projectMemberService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение участника проекта по id.")
    public ResponseEntity<ProjectMemberDto> getByProjectMemberId(@PathVariable("id") Long id) {
        log.info("Invoke getByProjectMemberId({}).", id);
        ProjectMemberDto result = projectMemberService.getById(id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/project/{project-id}")
    @Operation(summary = "Получение всех участников одного проекта по project-id.")
    public ResponseEntity<List<ProjectMemberDto>> getAllByProjectId(@PathVariable("project-id") Long projectId) {
        log.info("Invoke getByProjectMemberId({}).", projectId);
        return ResponseEntity.ok(projectMemberService.getAllByProjectId(projectId));
    }

    @PostMapping("/save")
    @Operation(summary = "Сохранение участника проекта, id игнорируется.")
    public ResponseEntity<String> save(@RequestBody ProjectMemberDto projectMemberDto) {
        log.info("Invoke save({}).", projectMemberDto);
        Long resultId = projectMemberService.save(projectMemberDto);
        if (resultId == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok("{\"id\": " + resultId + "}");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление участника проекта по id.")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.info("Invoke delete({}).", id);
        boolean status = projectMemberService.delete(id);
        if (status) {
            return ResponseEntity.ok(MappingUtils.EMPTY_JSON);
        }
        return ResponseEntity.notFound().build();
    }
}
