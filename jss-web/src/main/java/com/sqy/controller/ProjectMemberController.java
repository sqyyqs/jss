package com.sqy.controller;

import com.sqy.dto.ProjectMemberDto;
import com.sqy.service.ProjectMemberService;
import org.springframework.lang.Nullable;

import java.util.List;

public class ProjectMemberController {
    private final ProjectMemberService projectMemberService;

    public ProjectMemberController(ProjectMemberService projectMemberService) {
        this.projectMemberService = projectMemberService;
    }

    public List<ProjectMemberDto> getAll() {
        return projectMemberService.getAll();
    }

    @Nullable
    public ProjectMemberDto getById(Long id) {
        return projectMemberService.getById(id);
    }

    public void save(ProjectMemberDto projectMemberDto) {
        projectMemberService.save(projectMemberDto);
    }

    public void update(ProjectMemberDto projectMemberDto) {
        projectMemberService.update(projectMemberDto);
    }

    public void delete(Long id) {
        projectMemberService.delete(id);
    }

    public List<ProjectMemberDto> search() {
        return projectMemberService.search();
    }
}
