package com.sqy.mapper;

import com.sqy.domain.task.Task;
import com.sqy.domain.task.TaskFile;
import com.sqy.dto.task.TaskFileDto;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
public class TaskFileMapper {
    @Nullable
    public static TaskFile getFromMultipartFile(@Nullable MultipartFile file, long taskId) {
        log.info("Invoke getFromMultipartFile({}, {}).", file, taskId);
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            return TaskFile.builder().fileContentType(file.getContentType())
                    .task(Task.builder().taskId(taskId).build())
                    .file(file.getBytes())
                    .build();
        } catch (IOException e) {
            log.error("Invoke getFromMultipartFile({}, {}) with exception.", file, taskId, e);
        }
        return null;
    }

    @Nullable
    public static TaskFileDto getDtoFromModel(@Nullable TaskFile taskFile) {
        log.info("Invoke getDtoFromModel({}).", taskFile);
        if (taskFile == null) {
            return null;
        }
        return new TaskFileDto(taskFile.getFile(), taskFile.getFileContentType());
    }
}
