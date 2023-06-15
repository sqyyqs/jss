package com.sqy.domain.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "task_file")
public class TaskFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_file_id")
    private Long taskFileId;

    @OneToOne
    @JoinColumn(name = "task_id", referencedColumnName = "task_id", nullable = false, unique = true)
    private Task task;

    @Column(name = "file", nullable = false)
    private byte[] file;

    @Column(name = "file_content_type", nullable = false)
    private String fileContentType;

    @Override
    public String toString() {
        return "TaskFile{" +
                "taskFileId=" + taskFileId +
                ", file=" + Arrays.toString(file).substring(0, 10) + "...]" +
                ", fileContentType='" + fileContentType + '\'' +
                '}';
    }
}
