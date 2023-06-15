package com.sqy.domain.project;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "project_file")
public class ProjectFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_file_id")
    private Long projectFileId;

    @OneToOne
    @JoinColumn(name = "project_id", referencedColumnName = "project_id", nullable = false, unique = true)
    private Project project;

    @Column(name = "file", nullable = false)
    private byte[] file;

    @Column(name = "file_content_type", nullable = false)
    private String fileContentType;

    @Override
    public String toString() {
        return "ProjectFile{" +
                "projectFileId=" + projectFileId +
                ", file=byte[" + file.length + ']' +
                ", fileContentType='" + fileContentType + '\'' +
                '}';
    }
}
