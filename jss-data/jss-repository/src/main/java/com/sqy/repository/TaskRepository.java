package com.sqy.repository;

import com.sqy.domain.task.Task;
import com.sqy.domain.task.TaskEmailInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    @Query("SELECT NEW com.sqy.domain.task.TaskEmailInfo" +
            "(e.email, p.employee.firstName, p.employee.lastName, t.name, p.project.name, t.deadline, a.employee.firstName, a.employee.lastName) " +
            "FROM Task t " +
            "JOIN ProjectMember p ON t.performer.projectMemberId = p.projectMemberId " +
            "JOIN ProjectMember a ON t.author.projectMemberId = a.projectMemberId " +
            "JOIN Employee e ON p.employee.employeeId = e.employeeId " +
            "WHERE t.taskId = :taskId")
    TaskEmailInfo findTaskEmailInfoByTaskId(Long taskId);

}
