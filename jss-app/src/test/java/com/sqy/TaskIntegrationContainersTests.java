package com.sqy;

import com.sqy.dto.task.TaskDto;
import com.sqy.dto.task.TaskFilterDto;
import com.sqy.dto.task.TaskNewStatusDto;
import org.junit.jupiter.api.BeforeAll;
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDateTime;

import static com.sqy.domain.task.TaskStatus.COMPLETED;
import static com.sqy.domain.task.TaskStatus.IN_PROGRESS;
import static com.sqy.domain.task.TaskStatus.NEW;
import static com.sqy.util.MappingUtils.convertObjectToJson;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {"spring.config.location=classpath:application-properties.yml"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class TaskIntegrationContainersTests {

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

    @BeforeAll
    public static void addTrigger() throws Exception {
        String jdbcUrl = postgreSQLContainer.getJdbcUrl();
        String username = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement()) {
            statement.execute("""
                        CREATE OR REPLACE FUNCTION update_last_modified()
                        RETURNS TRIGGER AS
                    $$
                    BEGIN
                        NEW.last_update_date = NOW();
                        RETURN NEW;
                    END;
                    $$ LANGUAGE plpgsql;


                    CREATE TRIGGER task_last_modified
                        BEFORE UPDATE
                        ON task
                        FOR EACH ROW
                    EXECUTE FUNCTION update_last_modified();
                    """);
        }
    }

    @Test
    @Order(1)
    public void testSearchAllTasks() throws Exception {
        TaskFilterDto taskFilterDto = new TaskFilterDto("", null, null, null, null, null);

        mockMvc.perform(get("/api/v1/task/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(taskFilterDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Task 1")))
                .andExpect(jsonPath("$[0].description", is("Description for Task 1")))
                .andExpect(jsonPath("$[0].performer_id", is(1)))
                .andExpect(jsonPath("$[0].estimated_hours", is(8)))
                .andExpect(jsonPath("$[0].status", is(NEW.name())))
                .andExpect(jsonPath("$[0].author_id", is(2)));
    }

    @Test
    @Order(2)
    public void testSaveTask() throws Exception {
        TaskDto taskDto = TaskDto.builder().performerId(4L).estimatedHours(168L).name("created task")
                .deadline(LocalDateTime.now().plusYears(1)).status(IN_PROGRESS).authorId(2L).build();

        mockMvc.perform(post("/api/v1/task/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(taskDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @Order(3)
    public void testSaveTask_ViolatingConstraints() throws Exception {
        TaskDto taskDto = TaskDto.builder().performerId(999L).estimatedHours(168L).name("created task")
                .deadline(LocalDateTime.now().plusYears(1)).status(IN_PROGRESS).authorId(2L).build();

        mockMvc.perform(post("/api/v1/task/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(taskDto)))
                .andExpect(status().isConflict());
    }

    @Test
    @Order(4)
    public void testSearchSecondTime() throws Exception {
        TaskFilterDto input = new TaskFilterDto("created task", null, null, null, null, null);

        mockMvc.perform(get("/api/v1/task/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(4)))
                .andExpect(jsonPath("$[0].name", is("created task")))
                .andExpect(jsonPath("$[0].description", is(nullValue())))
                .andExpect(jsonPath("$[0].performer_id", is(4)))
                .andExpect(jsonPath("$[0].estimated_hours", is(168)))
                .andExpect(jsonPath("$[0].status", is(IN_PROGRESS.name())))
                .andExpect(jsonPath("$[0].author_id", is(2)));
    }


    @Test
    @Order(5)
    public void testUpdateStatus() throws Exception {
        TaskNewStatusDto input = new TaskNewStatusDto(4L, COMPLETED);

        mockMvc.perform(put("/api/v1/task/update-status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Order(6)
    public void testSearchThirdTime() throws Exception {
        TaskFilterDto input = new TaskFilterDto("created task", null, null, null, null, null);

        mockMvc.perform(get("/api/v1/task/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(4)))
                .andExpect(jsonPath("$[0].name", is("created task")))
                .andExpect(jsonPath("$[0].description", is(nullValue())))
                .andExpect(jsonPath("$[0].performer_id", is(4)))
                .andExpect(jsonPath("$[0].estimated_hours", is(168)))
                .andExpect(jsonPath("$[0].status", is(COMPLETED.name())))
                .andExpect(jsonPath("$[0].author_id", is(2)));
    }

    @Test
    @Order(7)
    public void testUpdateStatus_IncorrectStatus() throws Exception {
        TaskNewStatusDto input = new TaskNewStatusDto(4L, NEW);

        mockMvc.perform(put("/api/v1/task/update-status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(input)))
                .andExpect(status().isConflict());
    }

}
