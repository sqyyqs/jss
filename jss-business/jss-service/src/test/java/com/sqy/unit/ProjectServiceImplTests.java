package com.sqy.unit;

import com.sqy.domain.project.Project;
import com.sqy.domain.project.ProjectFile;
import com.sqy.domain.project.ProjectStatus;
import com.sqy.dto.project.ProjectDto;
import com.sqy.dto.project.ProjectFileDto;
import com.sqy.dto.project.ProjectNewStatusDto;
import com.sqy.dto.project.ProjectSearchDto;
import com.sqy.repository.ProjectFileRepository;
import com.sqy.repository.ProjectRepository;
import com.sqy.service.ProjectServiceImpl;
import com.sqy.service.interfaces.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ProjectServiceImpl.class)
public class ProjectServiceImplTests {

    @MockBean
    private ProjectRepository projectRepository;

    @MockBean
    private ProjectFileRepository projectFileRepository;

    @Autowired
    private ProjectService projectService;


    @Test
    public void getAll_ReturnsListOfProjects() {
        List<ProjectDto> expected = List.of(
                ProjectDto.builder().id(1L).code("FIRST_code").name("FIRST_name").build(),
                ProjectDto.builder().id(2L).code("SECOND_code").name("SECOND_name").build(),
                ProjectDto.builder().id(3L).code("THIRD_code").name("THIRD_name").build()
        );

        when(projectRepository.findAll()).thenReturn(List.of(
                Project.builder().projectId(1L).code("FIRST_code").name("FIRST_name").build(),
                Project.builder().projectId(2L).code("SECOND_code").name("SECOND_name").build(),
                Project.builder().projectId(3L).code("THIRD_code").name("THIRD_name").build()
        ));

        List<ProjectDto> result = projectService.getAll();

        assertEquals(expected, result);
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    public void getById_ReturnsProjectDto() {
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(
                Project.builder().projectId(1L).code("FIRST_code").name("FIRST_name").build()
        ));

        ProjectDto result = ProjectDto.builder().id(1L).code("FIRST_code").name("FIRST_name").build();

        assertEquals(result, projectService.getById(1L));
        verify(projectRepository, times(1)).findById(anyLong());
    }

    @Test
    public void getById_ReturnsNull() {
        when(projectRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertNull(projectService.getById(2L));
        verify(projectRepository, times(1)).findById(anyLong());
    }

    @Test
    public void save_ReturnsNull_CodeExisting() {
        ProjectDto input =
                ProjectDto.builder().id(1L).code("FIRST_code").name("FIRST_name").build();

        when(projectRepository.existsByCode(anyString())).thenReturn(true);

        assertNull(projectService.save(input));

        verify(projectRepository, times(1)).existsByCode(anyString());
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    public void save_ReturnsSavedEntityId_CodeNotExisting() {
        ProjectDto input =
                ProjectDto.builder().id(1L).code("FIRST_code").name("FIRST_name").build();

        Project savedEntity =
                Project.builder().projectId(1L).code("FIRST_code").name("FIRST_name").projectStatus(ProjectStatus.DRAFT).build();

        when(projectRepository.existsByCode(anyString())).thenReturn(false);
        when(projectRepository.save(any(Project.class))).thenReturn(savedEntity);

        Long result = projectService.save(input);

        assertNotNull(result);
        assertEquals(savedEntity.getProjectId(), result);
        verify(projectRepository, times(1)).existsByCode(anyString());
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    public void update_ReturnsFalse_NullId() {
        ProjectDto input =
                ProjectDto.builder().code("FIRST_code").name("FIRST_name").build();

        assertFalse(projectService.update(input));
        verify(projectRepository, never()).existsByCodeAndProjectIdNot(anyString(), anyLong());
        verify(projectRepository, never()).findById(anyLong());
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    public void update_ReturnsFalse_NotExistingById() {
        ProjectDto input =
                ProjectDto.builder().id(1L).code("null").name("FIRST_name").build();

        when(projectRepository.existsByCodeAndProjectIdNot(anyString(), anyLong())).thenReturn(false);
        when(projectRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertFalse(projectService.update(input));
        verify(projectRepository, times(1)).existsByCodeAndProjectIdNot(anyString(), anyLong());
        verify(projectRepository, times(1)).findById(anyLong());
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    public void update_ReturnsTrue() {
        ProjectDto input =
                ProjectDto.builder().id(1L).code("null").name("FIRST_name").build();

        when(projectRepository.existsByCodeAndProjectIdNot(anyString(), anyLong())).thenReturn(false);
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(
                Project.builder().projectId(1L).code("another").name("someName").projectStatus(ProjectStatus.COMPLETED).build()
        ));

        assertTrue(projectService.update(input));
        verify(projectRepository, times(1)).existsByCodeAndProjectIdNot(anyString(), anyLong());
        verify(projectRepository, times(1)).findById(anyLong());
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    public void updateState_ReturnsFalse_NotExistingById() {
        ProjectNewStatusDto input = new ProjectNewStatusDto(1L, ProjectStatus.IN_TESTING);

        when(projectRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertFalse(projectService.updateState(input));
        verify(projectRepository, times(1)).findById(anyLong());
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    public void updateState_ReturnsFalse_CompletedStatus() {
        ProjectNewStatusDto input = new ProjectNewStatusDto(1L, ProjectStatus.COMPLETED);

        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(
                Project.builder().projectId(1L).code("another").name("someName").projectStatus(ProjectStatus.COMPLETED).build()
        ));

        assertFalse(projectService.updateState(input));
        verify(projectRepository, times(1)).findById(anyLong());
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    public void updateState_ReturnsTrue() {
        ProjectNewStatusDto input = new ProjectNewStatusDto(1L, ProjectStatus.COMPLETED);

        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(
                Project.builder().projectId(1L).code("another").name("someName").projectStatus(ProjectStatus.IN_TESTING).build()
        ));

        assertTrue(projectService.updateState(input));
        verify(projectRepository, times(1)).findById(anyLong());
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    public void search() {
        ProjectSearchDto input = new ProjectSearchDto("vvv", Set.of(ProjectStatus.IN_TESTING, ProjectStatus.COMPLETED));

        List<ProjectDto> expected = List.of(
                ProjectDto.builder().id(1L).code("FIRST_codevvv").name("FIRST_name").projectStatus(ProjectStatus.IN_TESTING).build(),
                ProjectDto.builder().id(2L).code("SECOND_code").name("SECONDvvv_name").projectStatus(ProjectStatus.IN_TESTING).build(),
                ProjectDto.builder().id(3L).code("THIRD_codvvve").name("THIRD_name").projectStatus(ProjectStatus.COMPLETED).build()
        );

        when(projectRepository.findByFieldsContainingWithStatuses(anyString(), anySet())).thenReturn(List.of(
                Project.builder().projectId(1L).code("FIRST_codevvv").name("FIRST_name").projectStatus(ProjectStatus.IN_TESTING).build(),
                Project.builder().projectId(2L).code("SECOND_code").name("SECONDvvv_name").projectStatus(ProjectStatus.IN_TESTING).build(),
                Project.builder().projectId(3L).code("THIRD_codvvve").name("THIRD_name").projectStatus(ProjectStatus.COMPLETED).build()
        ));

        assertEquals(expected, projectService.search(input));
        verify(projectRepository, times(1)).findByFieldsContainingWithStatuses(anyString(), anySet());
    }

    @Test
    public void uploadFileToProjectId() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "File content".getBytes());
        when(projectFileRepository.save(any(ProjectFile.class))).thenReturn(
                ProjectFile.builder().projectFileId(1L)
                        .file(file.getBytes())
                        .fileContentType(file.getContentType())
                        .build()
        );
        assertTrue(projectService.uploadFileToProjectId(file, 1L));
        verify(projectFileRepository, times(1)).save(any(ProjectFile.class));
    }

    @Test
    public void uploadFileToProjectId_ThrowsException() {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "File content".getBytes());
        when(projectFileRepository.save(any(ProjectFile.class))).thenThrow(DataIntegrityViolationException.class);
        assertFalse(projectService.uploadFileToProjectId(file, 1L));
        verify(projectFileRepository, times(1)).save(any(ProjectFile.class));
    }

    @Test
    public void getFileFromRelatedProject() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "File content".getBytes());
        when(projectFileRepository.getProjectFileByProject_ProjectId(anyLong())).thenReturn(
                ProjectFile.builder().projectFileId(1L)
                        .file(file.getBytes())
                        .fileContentType(file.getContentType())
                        .build()
        );
        ProjectFileDto expected = new ProjectFileDto(file.getBytes(), file.getContentType());

        assertEquals(expected, projectService.getFileFromRelatedProject(1L));
        verify(projectFileRepository, times(1)).getProjectFileByProject_ProjectId(anyLong());

    }

    @Test
    public void getFileFromRelatedProject_NotFound() {
        when(projectFileRepository.existsByProject_ProjectId(anyLong())).thenReturn(false);
        assertNull(projectService.getFileFromRelatedProject(1L));
        verify(projectFileRepository, times(1)).getProjectFileByProject_ProjectId(anyLong());
    }

    @Test
    public void deleteFileFromRelatedProject() {
        doNothing().when(projectFileRepository).deleteProjectFileByProject_ProjectId(anyLong());
        when(projectFileRepository.existsByProject_ProjectId(anyLong())).thenReturn(true);
        assertTrue(projectService.deleteFileFromRelatedProject(1L));
        verify(projectFileRepository, times(1)).deleteProjectFileByProject_ProjectId(anyLong());
    }

    @Test
    public void deleteFileFromRelatedProject_NotFound() {
        when(projectFileRepository.existsByProject_ProjectId(anyLong())).thenReturn(false);
        assertFalse(projectService.deleteFileFromRelatedProject(1L));
        verify(projectFileRepository, times(1)).existsByProject_ProjectId(anyLong());
        verify(projectFileRepository, never()).deleteProjectFileByProject_ProjectId(anyLong());
    }
}
