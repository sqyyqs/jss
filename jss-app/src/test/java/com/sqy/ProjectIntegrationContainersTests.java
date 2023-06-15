package com.sqy;

import com.sqy.dto.project.ProjectDto;
import com.sqy.dto.project.ProjectNewStatusDto;
import com.sqy.dto.project.ProjectSearchDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Set;

import static com.sqy.domain.project.ProjectStatus.COMPLETED;
import static com.sqy.domain.project.ProjectStatus.DRAFT;
import static com.sqy.domain.project.ProjectStatus.IN_PROGRESS;
import static com.sqy.util.MappingUtils.convertObjectToJson;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {"spring.config.location=classpath:application-properties.yml"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class ProjectIntegrationContainersTests {

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
    public void testGetAllProjects() throws Exception {
        mockMvc.perform(get("/api/v1/project"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].code", is("P001")))
                .andExpect(jsonPath("$[0].name", is("Project 1")))
                .andExpect(jsonPath("$[0].description", is("This is project 1")))
                .andExpect(jsonPath("$[0].project_status", is(DRAFT.name())))
                .andExpect(jsonPath("$[3].id", is(4)))
                .andExpect(jsonPath("$[3].code", is("P004")))
                .andExpect(jsonPath("$[3].name", is("Project 4")))
                .andExpect(jsonPath("$[3].description", is("This project has a very long description...")))
                .andExpect(jsonPath("$[3].project_status", is(COMPLETED.name())));
    }

    @Test
    @Order(2)
    public void testGetProjectByIdFirstTime() throws Exception {
        mockMvc.perform(get("/api/v1/project/{id}", 5))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", aMapWithSize(5)))
                .andExpect(jsonPath("$.id", is(5)))
                .andExpect(jsonPath("$.code", is("P005")))
                .andExpect(jsonPath("$.name", is("Project 5")))
                .andExpect(jsonPath("$.description", is("This is project 5")))
                .andExpect(jsonPath("$.project_status", is(IN_PROGRESS.name())));
    }

    @Test
    @Order(3)
    public void testGetProjectByIdSecondTime_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/project/{id}", 999))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(4)
    public void testSaveProject_ViolatingUniqueConstraint() throws Exception {
        ProjectDto input = ProjectDto.builder().code("P005").name("Project 6").build();

        mockMvc.perform(post("/api/v1/project/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(input)))
                .andExpect(status().isConflict());
    }

    @Test
    @Order(5)
    public void testSaveProject() throws Exception {
        ProjectDto input = ProjectDto.builder().code("P006").name("Project 6").build();

        mockMvc.perform(post("/api/v1/project/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @Order(6)
    public void testUpdateProject() throws Exception {
        ProjectDto input = ProjectDto.builder().id(3L).code("P003").name("Project 3")
                .description("Absolutely new description, amazing!").build();

        mockMvc.perform(put("/api/v1/project/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Order(7)
    public void testGetProjectByIdThirdTime() throws Exception {
        mockMvc.perform(get("/api/v1/project/{id}", 3))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Absolutely new description, amazing!")));
    }

    @Test
    @Order(8)
    public void testUpdateProject_ViolatingUniqueConstraint() throws Exception {
        ProjectDto input = ProjectDto.builder().id(3L).code("P001").name("Project 3")
                .description("Absolutely same description, amazing!(actually not)").build();

        mockMvc.perform(put("/api/v1/project/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(input)))
                .andExpect(status().isConflict());
    }

    @Test
    @Order(9)
    public void testUpdateProject_NonExistingId() throws Exception {
        ProjectDto input = ProjectDto.builder().id(999L).code("P001").name("Project 3")
                .description("Absolutely same description, amazing!(actually not)").build();

        mockMvc.perform(put("/api/v1/project/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(input)))
                .andExpect(status().isConflict());
    }


    @Test
    @Order(10)
    public void testUpdateProjectState() throws Exception {
        ProjectNewStatusDto input = new ProjectNewStatusDto(3L, COMPLETED);

        mockMvc.perform(put("/api/v1/project/update-state")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Order(11)
    public void testGetProjectByIdFourthTime() throws Exception {
        mockMvc.perform(get("/api/v1/project/{id}", 3))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project_status", is(COMPLETED.name())));
    }

    @Test
    @Order(12)
    public void testUpdateProjectState_IncorrectStatus() throws Exception {
        ProjectNewStatusDto input = new ProjectNewStatusDto(2L, COMPLETED);

        mockMvc.perform(put("/api/v1/project/update-state")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(input)))
                .andExpect(status().isConflict());
    }

    @Test
    @Order(13)
    public void testSearch() throws Exception {
        ProjectSearchDto input = new ProjectSearchDto("00", Set.of(COMPLETED, DRAFT));

        mockMvc.perform(get("/api/v1/project/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    @Order(14)
    public void testUploadFile() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "testtext".getBytes());
        mockMvc.perform(multipart("/api/v1/project/file/{project-id}", 1L)
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Order(15)
    public void testUploadFile_NonExistingId() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "testtext".getBytes());
        mockMvc.perform(multipart("/api/v1/project/file/{project-id}", 999L)
                        .file(file))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(16)
    public void testDownloadFile() throws Exception {
        byte[] expected = "testtext".getBytes();
        mockMvc.perform(get("/api/v1/project/file/{project-id}", 1L))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"project_file\""))
                .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(result -> {
                    byte[] contentAsByteArray = result.getResponse().getContentAsByteArray();
                    assertArrayEquals(expected, contentAsByteArray);
                });
    }

    @Test
    @Order(17)
    public void testDownloadFile_NonExistingId() throws Exception {
        mockMvc.perform(get("/api/v1/project/file/{project-id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(18)
    public void testUploadFile_AlreadyExist() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "testtext".getBytes());
        mockMvc.perform(multipart("/api/v1/project/file/{project-id}", 1L)
                        .file(file))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(19)
    public void deleteFileFromRelatedTask() throws Exception {
        mockMvc.perform(delete("/api/v1/project/file/{project-id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Order(20)
    public void deleteFileFromRelatedTask_NonExistingId() throws Exception {
        mockMvc.perform(delete("/api/v1/project/file/{project-id}", 1L))
                .andExpect(status().isBadRequest());
    }
}
