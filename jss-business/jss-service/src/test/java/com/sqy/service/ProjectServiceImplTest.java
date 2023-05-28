package com.sqy.service;

import com.sqy.domain.project.Project;
import com.sqy.dto.project.ProjectDto;
import com.sqy.repository.ProjectRepository;
import com.sqy.service.interfaces.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
//1.12.06

@SpringBootTest
class ProjectServiceImplTest {
    @MockBean
    private ProjectRepository projectRepository;

    private ProjectService projectService;

    @Test
    void getAll() {
        List<Project> projects = new ArrayList<>();
//        projects.add(new Project(1L, "code1", "projectName1", ProjectStatus.IN_PROGRESS));
//        projects.add(new Project(2L, "code2", "projectName2", ProjectStatus.COMPLETED));
        when(projectRepository.findAll()).thenReturn(projects);

        // Call the service method
        List<ProjectDto> result = projectService.getAll();

        // Verify the results
        assertEquals(2, result.size());
        assertEquals("Project 1", result.get(0).getName());
        assertEquals("Project 2", result.get(1).getName());
    }

    @Test
    void getById() {
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void updateState() {
    }

    @Test
    void search() {
    }
}