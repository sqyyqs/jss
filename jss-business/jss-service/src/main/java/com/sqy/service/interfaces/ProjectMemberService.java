package com.sqy.service.interfaces;

import com.sqy.dto.projectmember.ProjectMemberDto;

import java.util.List;

public interface ProjectMemberService {
    List<ProjectMemberDto> getAll();

    ProjectMemberDto getById(Long id);

    List<ProjectMemberDto> getAllByProjectId(Long id);

    Long save(ProjectMemberDto projectMemberDto);

    boolean delete(Long id);

}
