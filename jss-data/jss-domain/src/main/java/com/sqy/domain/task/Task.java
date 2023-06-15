package com.sqy.domain.task;

import com.sqy.domain.projectmember.ProjectMember;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    @Nullable
    private String description;

    @ManyToOne
    @JoinColumn(name = "performer_id", referencedColumnName = "project_member_id")
    @Nullable
    private ProjectMember performer;

    @Column(name = "estimated_hours", nullable = false)
    private Long estimatedHours;

    @Column(name = "deadline", nullable = false)
    private LocalDateTime deadline;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "project_member_id")
    private ProjectMember author;

    @Column(name = "creation_date", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime creationDate;

    @Column(name = "last_update_date", nullable = false)
    @CreationTimestamp
    private LocalDateTime lastUpdateDate;

    @OneToOne(mappedBy = "task")
    private TaskFile taskFile;

    @OneToMany(mappedBy = "task")
    private Set<TaskToRelatedTask> taskRelationships;

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", estimatedHours=" + estimatedHours +
                ", deadline=" + deadline +
                ", status=" + status +
                ", creationDate=" + creationDate +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }
}
