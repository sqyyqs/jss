package com.sqy.repository;

import com.sqy.domain.task.TaskFile;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskFileRepository extends JpaRepository<TaskFile, Long> {
    TaskFile getTaskFileByTaskTaskId(long taskId);

    @Transactional
    void deleteByTaskTaskId(long taskId);
}
