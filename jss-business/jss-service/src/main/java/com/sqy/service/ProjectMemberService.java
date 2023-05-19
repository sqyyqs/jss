package com.sqy.service;

import com.sqy.dto.ProjectMemberDto;

import java.util.List;

public interface ProjectMemberService {
    List<ProjectMemberDto> getAll();

    ProjectMemberDto getById(Long id);

    void save(ProjectMemberDto projectMemberDto);

    void update(ProjectMemberDto projectMemberDto);

    void delete(Long id);

    List<ProjectMemberDto> search();
}
