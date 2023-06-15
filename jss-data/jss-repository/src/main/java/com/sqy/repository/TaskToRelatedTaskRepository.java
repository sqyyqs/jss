package com.sqy.repository;

import com.sqy.domain.task.TaskToRelatedTask;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskToRelatedTaskRepository extends JpaRepository<TaskToRelatedTask, Long> {
    List<TaskToRelatedTask> findAllByTask_TaskId(long taskId);

    @Transactional
    void deleteAllByTask_TaskId(long taskId);
}
