package com.sqy.mapper;

import com.sqy.domain.project.Project;
import com.sqy.domain.project.ProjectFile;
import com.sqy.dto.project.ProjectFileDto;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
public class ProjectFileMapper {
    @Nullable
    public static ProjectFile getFromMultipartFile(MultipartFile file, long projectId) {
        log.info("Invoke getFromMultipartFile({}, {}).", file, projectId);
        try {
            return ProjectFile.builder().file(file.getBytes()).fileContentType(file.getContentType())
                    .project(Project.builder().projectId(projectId).build()).build();
        } catch (IOException e) {
            log.error("Invoke getFromMultipartFile({}, {}) with exception.", file, projectId, e);
        }
        return null;
    }

    public static ProjectFileDto getDtoFromModel(ProjectFile projectFile) {
        return new ProjectFileDto(projectFile.getFile(), projectFile.getFileContentType());
    }
}
