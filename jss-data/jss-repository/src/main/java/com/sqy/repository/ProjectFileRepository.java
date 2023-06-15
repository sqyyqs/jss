package com.sqy.repository;

import com.sqy.domain.project.ProjectFile;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectFileRepository extends JpaRepository<ProjectFile, Long> {
    ProjectFile getProjectFileByProject_ProjectId(long projectId);

    @Transactional
    void deleteProjectFileByProject_ProjectId(long projectId);

    boolean existsByProject_ProjectId(long projectId);
}
