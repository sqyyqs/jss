package com.sqy.repository;

import com.sqy.domain.projectmember.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    List<ProjectMember> findProjectMemberByProjectProjectId(long projectId);

}
