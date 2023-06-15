package com.sqy.mapper;

import com.sqy.domain.project.Project;
import com.sqy.domain.project.ProjectFile;
import com.sqy.dto.project.ProjectFileDto;
import jakarta.annotation.Nullable;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Log4j2
public class ProjectFileMapper {
    @Nullable
    public static ProjectFile getFromMultipartFile(@Nullable MultipartFile file, long projectId) {
        log.info("Invoke getFromMultipartFile({}, {}).", file, projectId);
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            return ProjectFile.builder().file(file.getBytes()).fileContentType(file.getContentType())
                    .project(Project.builder().projectId(projectId).build()).build();
        } catch (IOException e) {
            log.error("Invoke getFromMultipartFile({}, {}) with exception.", file, projectId, e);
        }
        return null;
    }

    @Nullable
    public static ProjectFileDto getDtoFromModel(@Nullable ProjectFile projectFile) {
        log.info("Invoke getDtoFromModel({}).", projectFile);
        if (projectFile == null) {
            return null;
        }
        return new ProjectFileDto(projectFile.getFile(), projectFile.getFileContentType());
    }
}
