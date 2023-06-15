package com.sqy;


import com.sqy.dto.task.TaskToRelatedTaskDto;
import com.sqy.util.MappingUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {"spring.config.location=classpath:application-properties.yml"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class TaskToRelatedTaskIntegrationContainerTests {

    @Autowired
    private MockMvc mockMvc;

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withInitScript("init_script.sql")
            .withDatabaseName("project_member")
            .withUsername("postgres")
            .withPassword("test");

    @DynamicPropertySource
    private static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    @Order(1)
    public void testGetById() throws Exception {
        mockMvc.perform(get("/api/v1/task-related/{relationship-id}", 2L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", aMapWithSize(3)))
                .andExpect(jsonPath("$.relationship_id", is(2)))
                .andExpect(jsonPath("$.task_id", is(1)))
                .andExpect(jsonPath("$.related_task_id", is(3)));

    }

    @Test
    @Order(2)
    public void testGetByTaskId() throws Exception {
        mockMvc.perform(get("/api/v1/task-related/by-task-id/{task-id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))

                .andExpect(jsonPath("$[0].relationship_id", is(1)))
                .andExpect(jsonPath("$[0].task_id", is(1)))
                .andExpect(jsonPath("$[0].related_task_id", is(2)))

                .andExpect(jsonPath("$[1].relationship_id", is(2)))
                .andExpect(jsonPath("$[1].task_id", is(1)))
                .andExpect(jsonPath("$[1].related_task_id", is(3)))

                .andExpect(jsonPath("$[2].relationship_id", is(3)))
                .andExpect(jsonPath("$[2].task_id", is(1)))
                .andExpect(jsonPath("$[2].related_task_id", is(4)));
    }

    @Test
    @Order(3)
    public void testSaveRelationship() throws Exception {
        TaskToRelatedTaskDto input = TaskToRelatedTaskDto.builder().taskId(2).relatedTaskId(1).build();
        mockMvc.perform(post("/api/v1/task-related/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MappingUtils.convertObjectToJson(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());

    }

    @Test
    @Order(4)
    public void testGetByIdSecondTime() throws Exception {
        mockMvc.perform(get("/api/v1/task-related/{relationship-id}", 5L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", aMapWithSize(3)))
                .andExpect(jsonPath("$.relationship_id", is(5)))
                .andExpect(jsonPath("$.task_id", is(2)))
                .andExpect(jsonPath("$.related_task_id", is(1)));

    }

    @Test
    @Order(5)
    public void deleteRelationshipById() throws Exception {
        mockMvc.perform(delete("/api/v1/task-related/{relationship-id}", 5L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Order(6)
    public void testGetByIdThirdTime() throws Exception {
        mockMvc.perform(get("/api/v1/task-related/{relationship-id}", 5L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(7)
    public void deleteRelationshipByIdSecondTime() throws Exception {
        mockMvc.perform(delete("/api/v1/task-related/{relationship-id}", 5L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(8)
    public void deleteRelationshipsByTaskId() throws Exception {
        mockMvc.perform(delete("/api/v1/task-related/by-task-id/{task-id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Order(9)
    public void testGetByTaskIdSecondTime() throws Exception {
        mockMvc.perform(get("/api/v1/task-related/by-task-id/{task-id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", empty()));
    }

    @Test
    @Order(10)
    public void deleteRelationshipsByTaskIdSecondTime() throws Exception {
        mockMvc.perform(delete("/api/v1/task-related/by-task-id/{task-id}", 1L))
                .andExpect(status().isNotFound());
    }
}
