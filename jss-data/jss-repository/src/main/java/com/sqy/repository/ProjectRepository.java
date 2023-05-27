package com.sqy.repository;

import com.sqy.domain.project.Project;
import com.sqy.domain.project.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT e FROM Project e "
            + "WHERE (e.projectStatus IN :statuses) AND ("
            + " e.description  LIKE %:value% OR  "
            + " e.name         LIKE %:value% OR  "
            + " e.code         LIKE %:value%)")
    List<Project> findByFieldsContainingWithStatuses(@Param("value") String value, @Param("statuses") Set<ProjectStatus> statuses);

    boolean existsByCode(String code);
}
