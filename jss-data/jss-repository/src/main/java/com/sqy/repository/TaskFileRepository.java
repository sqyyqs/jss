package com.sqy.repository;

import com.sqy.domain.task.TaskFile;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskFileRepository extends JpaRepository<TaskFile, Long> {
    TaskFile getTaskFileByTask_TaskId(long taskId);

    @Transactional
    void deleteByTask_TaskId(long taskId);
}
