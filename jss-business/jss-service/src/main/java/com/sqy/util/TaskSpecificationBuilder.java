package com.sqy.util;

import com.sqy.domain.employee.Employee;
import com.sqy.domain.projectmember.ProjectMember;
import com.sqy.domain.task.Task;
import com.sqy.dto.task.TaskFilterDto;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TaskSpecificationBuilder {
    public static Specification<Task> buildSpecification(TaskFilterDto filterDTO) {
        List<Specification<Task>> specifications = new ArrayList<>();

        if (filterDTO.value() != null) {
            specifications.add((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("name"), "%" + filterDTO.value() + "%"));
        }

        if (filterDTO.permissibleStatuses() != null) {
            specifications.add((root, query, criteriaBuilder) ->
                    root.get("status").in(filterDTO.permissibleStatuses()));
        }

        if (filterDTO.startOfPeriod() != null) {
            specifications.add((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThan(root.get("creationDate"), filterDTO.startOfPeriod()));
        }

        if (filterDTO.endOfPeriod() != null) {
            specifications.add((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThan(root.get("lastUpdateDate"), filterDTO.endOfPeriod()));
        }

        if (filterDTO.performerId() != null) {
            specifications.add((root, query, criteriaBuilder) -> {
                Join<Task, Employee> performerJoin = root.join("performer");
                return criteriaBuilder.equal(performerJoin.get("employeeId"), filterDTO.performerId());
            });
        }

        if (filterDTO.authorId() != null) {
            specifications.add((root, query, criteriaBuilder) -> {
                Join<Task, ProjectMember> performerJoin = root.join("author");
                return criteriaBuilder.equal(performerJoin.get("projectMemberId"), filterDTO.authorId());
            });
        }
        return Specification.allOf(specifications);
    }
}
