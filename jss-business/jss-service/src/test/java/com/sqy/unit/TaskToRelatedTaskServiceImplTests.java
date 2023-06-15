package com.sqy.unit;

import com.sqy.domain.task.Task;
import com.sqy.domain.task.TaskToRelatedTask;
import com.sqy.dto.task.TaskToRelatedTaskDto;
import com.sqy.repository.TaskToRelatedTaskRepository;
import com.sqy.service.TaskToRelatedTaskServiceImpl;
import com.sqy.service.interfaces.TaskToRelatedTaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TaskToRelatedTaskServiceImpl.class)
public class TaskToRelatedTaskServiceImplTests {

    @MockBean
    private TaskToRelatedTaskRepository taskToRelatedTaskRepository;

    @Autowired
    private TaskToRelatedTaskService taskToRelatedTaskService;

    @Test
    public void getById() {
        TaskToRelatedTaskDto expected = new TaskToRelatedTaskDto(1L, 1L, 2L);
        when(taskToRelatedTaskRepository.findById(anyLong())).thenReturn(Optional.of(
                TaskToRelatedTask.builder().relationshipId(1L)
                        .task(Task.builder().taskId(1L).build())
                        .relatedTask(Task.builder().taskId(2L).build())
                        .build()
        ));

        assertEquals(expected, taskToRelatedTaskService.getById(1L));
        verify(taskToRelatedTaskRepository, times(1)).findById(anyLong());
    }

    @Test
    public void getById_ReturnsNull() {
        when(taskToRelatedTaskRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertNull(taskToRelatedTaskService.getById(1L));
        verify(taskToRelatedTaskRepository, times(1)).findById(anyLong());
    }

    @Test
    public void getAllRelationshipsForTask() {
        List<TaskToRelatedTaskDto> expected = List.of(
                TaskToRelatedTaskDto.builder().relationshipId(45L).relatedTaskId(46L).taskId(45L).build(),
                TaskToRelatedTaskDto.builder().relationshipId(46L).relatedTaskId(47L).taskId(45L).build(),
                TaskToRelatedTaskDto.builder().relationshipId(129L).relatedTaskId(130L).taskId(45L).build()
        );

        when(taskToRelatedTaskRepository.findAllByTask_TaskId(anyLong())).thenReturn(List.of(
                TaskToRelatedTask.builder()
                        .relationshipId(45L)
                        .relatedTask(Task.builder().taskId(46L).build())
                        .task(Task.builder().taskId(45L).build())
                        .build(),

                TaskToRelatedTask.builder()
                        .relationshipId(46L)
                        .relatedTask(Task.builder().taskId(47L).build())
                        .task(Task.builder().taskId(45L).build())
                        .build(),

                TaskToRelatedTask.builder()
                        .relationshipId(129L)
                        .relatedTask(Task.builder().taskId(130L).build())
                        .task(Task.builder().taskId(45L).build())
                        .build()
        ));

        List<TaskToRelatedTaskDto> result = taskToRelatedTaskService.getAllRelationshipsForTask(45L);

        assertEquals(expected, result);
        verify(taskToRelatedTaskRepository, times(1)).findAllByTask_TaskId(anyLong());
    }

    @Test
    public void addRelatedTask() {
        TaskToRelatedTaskDto input =
                TaskToRelatedTaskDto.builder().relationshipId(45L).relatedTaskId(46L).taskId(45L).build();

        when(taskToRelatedTaskRepository.save(any(TaskToRelatedTask.class))).thenReturn(
                TaskToRelatedTask.builder()
                        .relationshipId(45L)
                        .relatedTask(Task.builder().taskId(46L).build())
                        .task(Task.builder().taskId(45L).build())
                        .build()
        );

        assertEquals(45L, taskToRelatedTaskService.addRelatedTask(input));
        verify(taskToRelatedTaskRepository, times(1)).save(any(TaskToRelatedTask.class));
    }

    @Test
    public void addRelatedTask_ThrowsException() {
        TaskToRelatedTaskDto input =
                TaskToRelatedTaskDto.builder().relationshipId(45L).relatedTaskId(46L).taskId(45L).build();

        when(taskToRelatedTaskRepository.save(any(TaskToRelatedTask.class)))
                .thenThrow(DataIntegrityViolationException.class);

        assertNull(taskToRelatedTaskService.addRelatedTask(input));
        verify(taskToRelatedTaskRepository, times(1)).save(any(TaskToRelatedTask.class));
    }

    @Test
    public void removeRelationshipById() {
        doNothing().when(taskToRelatedTaskRepository).deleteById(anyLong());
        taskToRelatedTaskService.removeRelationshipById(1L);
        verify(taskToRelatedTaskRepository, times(1)).deleteById(anyLong());
    }


    @Test
    public void removeAllRelationshipsForTask() {
        doNothing().when(taskToRelatedTaskRepository).deleteAllByTask_TaskId(anyLong());
        taskToRelatedTaskService.removeAllRelationshipsForTask(1L);
        verify(taskToRelatedTaskRepository, times(1)).deleteAllByTask_TaskId(anyLong());
    }


}
