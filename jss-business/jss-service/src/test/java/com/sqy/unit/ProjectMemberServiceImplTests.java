package com.sqy.unit;

import com.sqy.domain.employee.Employee;
import com.sqy.domain.project.Project;
import com.sqy.domain.projectmember.ProjectMember;
import com.sqy.domain.projectmember.ProjectMemberRole;
import com.sqy.dto.projectmember.ProjectMemberDto;
import com.sqy.repository.ProjectMemberRepository;
import com.sqy.service.ProjectMemberServiceImpl;
import com.sqy.service.interfaces.ProjectMemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = ProjectMemberServiceImpl.class)
public class ProjectMemberServiceImplTests {

    @MockBean
    private ProjectMemberRepository projectMemberRepository;

    @Autowired
    private ProjectMemberService projectMemberService;

    @Test
    public void getAll_ReturnsListOfProjectMemberDto() {
        List<ProjectMemberDto> expectedDtoList = List.of(
                ProjectMemberDto.builder().id(1L).projectId(1L).employeeId(1L).projectMemberRole(ProjectMemberRole.LEAD).build(),
                ProjectMemberDto.builder().id(2L).projectId(2L).employeeId(2L).projectMemberRole(ProjectMemberRole.ANALYST).build(),
                ProjectMemberDto.builder().id(3L).projectId(3L).employeeId(3L).projectMemberRole(ProjectMemberRole.TESTER).build()
        );

        when(projectMemberRepository.findAll()).thenReturn(List.of(
                ProjectMember.builder()
                        .projectMemberId(1L)
                        .project(Project.builder().projectId(1L).build())
                        .employee(Employee.builder().employeeId(1L).build())
                        .projectMemberRole(ProjectMemberRole.LEAD)
                        .build(),

                ProjectMember.builder()
                        .projectMemberId(2L)
                        .project(Project.builder().projectId(2L).build())
                        .employee(Employee.builder().employeeId(2L).build())
                        .projectMemberRole(ProjectMemberRole.ANALYST)
                        .build(),

                ProjectMember.builder()
                        .projectMemberId(3L)
                        .project(Project.builder().projectId(3L).build())
                        .employee(Employee.builder().employeeId(3L).build())
                        .projectMemberRole(ProjectMemberRole.TESTER)
                        .build()
        ));
        List<ProjectMemberDto> result = projectMemberService.getAll();

        assertEquals(expectedDtoList, result);
        verify(projectMemberRepository, times(1)).findAll();
    }

    @Test
    public void getById_ReturnsProjectMemberDto() {
        when(projectMemberRepository.findById(anyLong())).thenReturn(Optional.of(
                ProjectMember.builder()
                        .projectMemberId(1L)
                        .project(Project.builder().projectId(1L).build())
                        .employee(Employee.builder().employeeId(1L).build())
                        .projectMemberRole(ProjectMemberRole.TESTER)
                        .build()
        ));

        ProjectMemberDto result = ProjectMemberDto.builder()
                .id(1L)
                .projectId(1L)
                .employeeId(1L)
                .projectMemberRole(ProjectMemberRole.TESTER)
                .build();

        assertEquals(result, projectMemberService.getById(1L));
        verify(projectMemberRepository, times(1)).findById(anyLong());
    }

    @Test
    public void getById_ReturnsNull() {
        when(projectMemberRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertNull(projectMemberService.getById(2L));
        verify(projectMemberRepository, times(1)).findById(anyLong());
    }

    @Test
    public void getAllByProjectId_ReturnsListOfProjectMemberDto() {
        List<ProjectMemberDto> expectedDtoList = List.of(
                ProjectMemberDto.builder().id(1L).projectId(1L).employeeId(1L).projectMemberRole(ProjectMemberRole.LEAD).build(),
                ProjectMemberDto.builder().id(2L).projectId(1L).employeeId(2L).projectMemberRole(ProjectMemberRole.ANALYST).build(),
                ProjectMemberDto.builder().id(3L).projectId(1L).employeeId(3L).projectMemberRole(ProjectMemberRole.TESTER).build()
        );

        when(projectMemberRepository.findProjectMemberByProjectProjectId(anyLong())).thenReturn(List.of(
                ProjectMember.builder()
                        .projectMemberId(1L)
                        .project(Project.builder().projectId(1L).build())
                        .employee(Employee.builder().employeeId(1L).build())
                        .projectMemberRole(ProjectMemberRole.LEAD)
                        .build(),

                ProjectMember.builder()
                        .projectMemberId(2L)
                        .project(Project.builder().projectId(1L).build())
                        .employee(Employee.builder().employeeId(2L).build())
                        .projectMemberRole(ProjectMemberRole.ANALYST)
                        .build(),

                ProjectMember.builder()
                        .projectMemberId(3L)
                        .project(Project.builder().projectId(1L).build())
                        .employee(Employee.builder().employeeId(3L).build())
                        .projectMemberRole(ProjectMemberRole.TESTER)
                        .build()
        ));
        List<ProjectMemberDto> result = projectMemberService.getAllByProjectId(1L);

        assertEquals(expectedDtoList, result);
        verify(projectMemberRepository, times(1)).findProjectMemberByProjectProjectId(anyLong());
    }

    @Test
    public void save_ReturnsSavedEntityId() {
        ProjectMemberDto input =
                ProjectMemberDto.builder().projectMemberRole(ProjectMemberRole.LEAD).projectId(1L).employeeId(1L).build();

        ProjectMember savedEntity = ProjectMember.builder().projectMemberId(1L).build();
        when(projectMemberRepository.save(any(ProjectMember.class))).thenReturn(savedEntity);

        Long result = projectMemberService.save(input);

        assertNotNull(result);
        assertEquals(savedEntity.getProjectMemberId(), result);
        verify(projectMemberRepository, times(1)).save(any(ProjectMember.class));
    }

    @Test
    public void save_WithException_ReturnsNull() {
        ProjectMemberDto input =
                ProjectMemberDto.builder().projectMemberRole(ProjectMemberRole.LEAD).projectId(1L).employeeId(1L).build();

        when(projectMemberRepository.save(any(ProjectMember.class))).thenThrow(DataIntegrityViolationException.class);
        assertNull(projectMemberService.save(input));
        verify(projectMemberRepository, times(1)).save(any(ProjectMember.class));
    }

    @Test
    public void delete_WithExistingId_ReturnsTrue() {
        when(projectMemberRepository.existsById(1L)).thenReturn(true);
        doNothing().when(projectMemberRepository).deleteById(anyLong());
        assertTrue(projectMemberService.delete(1L));
        verify(projectMemberRepository, times(1)).existsById(anyLong());
        verify(projectMemberRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void delete_WithExistingId_ReturnsFalse() {
        when(projectMemberRepository.existsById(1L)).thenReturn(false);
        assertFalse(projectMemberService.delete(1L));
        verify(projectMemberRepository, times(1)).existsById(anyLong());
        verify(projectMemberRepository, never()).deleteById(anyLong());
    }

}
