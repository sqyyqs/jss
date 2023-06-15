package com.sqy.unit;

import com.sqy.domain.employee.Employee;
import com.sqy.domain.projectmember.ProjectMember;
import com.sqy.domain.task.Task;
import com.sqy.domain.task.TaskFile;
import com.sqy.domain.task.TaskStatus;
import com.sqy.dto.task.TaskDto;
import com.sqy.dto.task.TaskFileDto;
import com.sqy.dto.task.TaskFilterDto;
import com.sqy.dto.task.TaskNewStatusDto;
import com.sqy.rabbitmq.RabbitMqProducer;
import com.sqy.repository.TaskFileRepository;
import com.sqy.repository.TaskRepository;
import com.sqy.service.TaskServiceImpl;
import com.sqy.service.interfaces.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TaskServiceImpl.class)
public class TaskServiceImplTests {
    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private RabbitMqProducer rabbitMqProducer;

    @MockBean
    private TaskFileRepository taskFileRepository;

    @Autowired
    private TaskService taskService;

    @Test
    void save_WithException_ReturnsNull() {
        TaskDto input = TaskDto.builder().id(1L).name("first_project_name").performerId(1L).authorId(1L)
                .estimatedHours(168L).deadline(LocalDateTime.now().plusYears(1)).build();
        when(taskRepository.save(any(Task.class))).thenThrow(DataIntegrityViolationException.class);
        doNothing().when(rabbitMqProducer).sendMessage(anyString(), any());
        assertNull(taskService.save(input));
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void save_ReturnsSavedEntityId() {
        TaskDto input = TaskDto.builder().id(1L).name("first_project_name").performerId(1L).authorId(1L)
                .estimatedHours(168L).deadline(LocalDateTime.now().plusYears(1)).build();

        when(taskRepository.save(any(Task.class))).thenReturn(
                Task.builder().taskId(1L)
                        .performer(Employee.builder().employeeId(1L).build())
                        .author(ProjectMember.builder().projectMemberId(1L).build())
                        .build()
        );
        doNothing().when(rabbitMqProducer).sendMessage(anyString(), any());
        Long result = taskService.save(input);

        assertNotNull(result);
        assertEquals(1L, result);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void update_ReturnsFalse_NullId() {
        TaskDto input = TaskDto.builder().name("first_project_name").performerId(1L).authorId(1L)
                .estimatedHours(168L).deadline(LocalDateTime.now().plusYears(1)).build();
        assertFalse(taskService.update(input));
        verify(taskRepository, never()).existsById(anyLong());
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void update_ReturnsFalse_NotExistingById() {
        TaskDto input = TaskDto.builder().id(1L).name("first_project_name").performerId(1L).authorId(1L)
                .estimatedHours(168L).deadline(LocalDateTime.now().plusYears(1)).build();
        when(taskRepository.existsById(anyLong())).thenReturn(false);
        assertFalse(taskService.update(input));
        verify(taskRepository, times(1)).existsById(anyLong());
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void update_ReturnsFalse_ThrowsException() {
        TaskDto input = TaskDto.builder().id(1L).name("first_project_name").performerId(1L).authorId(1L)
                .estimatedHours(168L).deadline(LocalDateTime.now().plusYears(1)).build();

        when(taskRepository.existsById(anyLong())).thenReturn(true);
        when(taskRepository.save(any(Task.class))).thenThrow(DataIntegrityViolationException.class);

        assertFalse(taskService.update(input));
        verify(taskRepository, times(1)).existsById(anyLong());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void update_ReturnsTrue() {
        TaskDto input = TaskDto.builder().id(1L).name("first_project_name").performerId(1L).authorId(1L)
                .estimatedHours(168L).deadline(LocalDateTime.now().plusYears(1)).build();

        when(taskRepository.existsById(anyLong())).thenReturn(true);

        assertTrue(taskService.update(input));
        verify(taskRepository, times(1)).existsById(anyLong());
        verify(taskRepository, times(1)).save(any(Task.class));
    }


    @Test
    void updateStatus_ReturnsFalse_NotExistingById() {
        TaskNewStatusDto input = new TaskNewStatusDto(1L, TaskStatus.COMPLETED);

        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertFalse(taskService.updateStatus(input));
        verify(taskRepository, times(1)).findById(anyLong());
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void updateStatus_ReturnsFalse_IncorrectStatus() {
        TaskNewStatusDto input = new TaskNewStatusDto(1L, TaskStatus.COMPLETED);

        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(
                Task.builder().status(TaskStatus.NEW).build()
        ));

        assertFalse(taskService.updateStatus(input));
        verify(taskRepository, times(1)).findById(anyLong());
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void updateStatus_ReturnsFalse_CompletedStatus() {
        TaskNewStatusDto input = new TaskNewStatusDto(1L, TaskStatus.COMPLETED);

        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(
                Task.builder().status(TaskStatus.COMPLETED).build()
        ));

        assertFalse(taskService.updateStatus(input));
        verify(taskRepository, times(1)).findById(anyLong());
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void updateStatus_ReturnsTrue() {
        TaskNewStatusDto input = new TaskNewStatusDto(1L, TaskStatus.COMPLETED);

        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(
                Task.builder().status(TaskStatus.IN_PROGRESS).build()
        ));

        assertTrue(taskService.updateStatus(input));
        verify(taskRepository, times(1)).findById(anyLong());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void searchByFilters_returnsList() {
        TaskFilterDto qwe = new TaskFilterDto("qwe", null, null, null, null, null);
        LocalDateTime someDate = LocalDateTime.now().plusYears(1);

        List<TaskDto> expected = List.of(
                TaskDto.builder().id(1L).name("first_project_name").performerId(1L).authorId(1L)
                        .estimatedHours(168L).deadline(someDate).build(),
                TaskDto.builder().id(2L).name("second_project_name").performerId(2L).authorId(2L)
                        .estimatedHours(168L).deadline(someDate).build(),
                TaskDto.builder().id(3L).name("third_project_name").performerId(3L).authorId(3L)
                        .estimatedHours(168L).deadline(someDate).build()
        );

        when(taskRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(List.of(
                Task.builder().taskId(1L).name("first_project_name")
                        .author(ProjectMember.builder().projectMemberId(1L).build())
                        .performer(Employee.builder().employeeId(1L).build())
                        .estimatedHours(168L).deadline(someDate)
                        .build(),

                Task.builder().taskId(2L).name("second_project_name")
                        .author(ProjectMember.builder().projectMemberId(2L).build())
                        .performer(Employee.builder().employeeId(2L).build())
                        .estimatedHours(168L).deadline(someDate)
                        .build(),

                Task.builder().taskId(3L).name("third_project_name")
                        .author(ProjectMember.builder().projectMemberId(3L).build())
                        .performer(Employee.builder().employeeId(3L).build())
                        .estimatedHours(168L).deadline(someDate)
                        .build()
        ));

        assertEquals(expected, taskService.searchByFilters(qwe));
        verify(taskRepository, times(1)).findAll(any(Specification.class), any(Sort.class));
    }

    @Test
    void uploadFileToTaskId() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "File content".getBytes());
        when(taskFileRepository.save(any(TaskFile.class))).thenReturn(
                TaskFile.builder().taskFileId(1L)
                        .file(file.getBytes())
                        .fileContentType(file.getContentType())
                        .build()
        );
        assertTrue(taskService.uploadFileToTaskId(file, 1L));
        verify(taskFileRepository, times(1)).save(any(TaskFile.class));
    }

    @Test
    void uploadFileToTaskId_ThrowsException() {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "File content".getBytes());
        when(taskFileRepository.save(any(TaskFile.class))).thenThrow(DataIntegrityViolationException.class);
        assertFalse(taskService.uploadFileToTaskId(file, 1L));
        verify(taskFileRepository, times(1)).save(any(TaskFile.class));
    }

    @Test
    void getFileFromRelatedTask() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "File content".getBytes());
        when(taskFileRepository.getTaskFileByTask_TaskId(anyLong())).thenReturn(
                TaskFile.builder().taskFileId(1L)
                        .file(file.getBytes())
                        .fileContentType(file.getContentType())
                        .build()
        );
        TaskFileDto expected = new TaskFileDto(file.getBytes(), file.getContentType());

        assertEquals(expected, taskService.getFileFromRelatedTask(1L));
        verify(taskFileRepository, times(1)).getTaskFileByTask_TaskId(anyLong());
    }

    @Test
    void deleteFileFromRelatedTask() {
        doNothing().when(taskFileRepository).deleteByTask_TaskId(anyLong());
        assertTrue(taskService.deleteFileFromRelatedTask(1L));
        verify(taskFileRepository, times(1)).deleteByTask_TaskId(anyLong());
    }

}
