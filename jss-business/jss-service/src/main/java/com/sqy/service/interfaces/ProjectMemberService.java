package com.sqy.service.interfaces;

import com.sqy.dto.ProjectMemberDto;

import java.util.List;

public interface ProjectMemberService {
    List<ProjectMemberDto> getAll();

    ProjectMemberDto getById(Long id);

    List<ProjectMemberDto> getAllByProjectId(Long id);

    boolean save(ProjectMemberDto projectMemberDto);

    boolean delete(Long id);

}
