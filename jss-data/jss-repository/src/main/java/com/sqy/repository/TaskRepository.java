package com.sqy.repository;

import com.sqy.domain.task.Task;
import com.sqy.domain.task.TaskEmailInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    @Query("SELECT new com.sqy.domain.task.TaskEmailInfo(" +
            "emp.email, emp.firstName, emp.lastName, " +
            "t.name, pj.name, t.deadline, author.firstName, author.lastName) " +
            "FROM Task t " +
            "JOIN Employee emp ON t.performer.employeeId = emp.employeeId " +
            "JOIN ProjectMember pm ON t.author.projectMemberId = pm.projectMemberId " +
            "JOIN Employee author ON pm.employee.employeeId = author.employeeId " +
            "JOIN Project pj ON pm.project.projectId = pj.projectId " +
            "WHERE t.taskId = :taskId ")
    TaskEmailInfo getTaskEmailInfoByTaskId(@Param("taskId") Long taskId);
}
