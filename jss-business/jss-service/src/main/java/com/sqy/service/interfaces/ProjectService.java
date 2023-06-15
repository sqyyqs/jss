package com.sqy.service.interfaces;

import com.sqy.dto.project.ProjectDto;
import com.sqy.dto.project.ProjectFileDto;
import com.sqy.dto.project.ProjectNewStatusDto;
import com.sqy.dto.project.ProjectSearchDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProjectService {
    List<ProjectDto> getAll();

    ProjectDto getById(Long id);

    Long save(ProjectDto projectDto);

    boolean update(ProjectDto projectDto);

    boolean updateState(ProjectNewStatusDto projectNewStatusDto);

    List<ProjectDto> search(ProjectSearchDto projectSearchDto);

    boolean uploadFileToProjectId(MultipartFile file, long projectId);

    ProjectFileDto getFileFromRelatedProject(long projectId);

    boolean deleteFileFromRelatedProject(long projectId);
}
