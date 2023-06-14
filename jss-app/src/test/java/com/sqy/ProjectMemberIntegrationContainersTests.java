package com.sqy;

import com.sqy.dto.projectmember.ProjectMemberDto;
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

import static com.sqy.domain.projectmember.ProjectMemberRole.DEVELOPER;
import static com.sqy.domain.projectmember.ProjectMemberRole.TESTER;
import static com.sqy.util.MappingUtils.convertObjectToJson;
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
public class ProjectMemberIntegrationContainersTests {

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
    public void testGetAllProjectMembersFirstTime() throws Exception {
        mockMvc.perform(get("/api/v1/project-member"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].project_id", is(1)))
                .andExpect(jsonPath("$[0].employee_id", is(1)))
                .andExpect(jsonPath("$[0].project_member_role", is(DEVELOPER.name())));
    }

    @Test
    @Order(2)
    public void testSaveProjectMember() throws Exception {
        ProjectMemberDto projectMemberDto =
                ProjectMemberDto.builder().id(1L).projectId(1L).employeeId(1L).projectMemberRole(TESTER).build();


        mockMvc.perform(post("/api/v1/project-member/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(projectMemberDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @Order(3)
    public void testGetAllProjectMembersAfterSave() throws Exception {
        mockMvc.perform(get("/api/v1/project-member"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(7)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].project_id", is(1)))
                .andExpect(jsonPath("$[0].employee_id", is(1)))
                .andExpect(jsonPath("$[0].project_member_role", is(DEVELOPER.name())))
                .andExpect(jsonPath("$[6].id", is(7)))
                .andExpect(jsonPath("$[6].project_id", is(1)))
                .andExpect(jsonPath("$[6].employee_id", is(1)))
                .andExpect(jsonPath("$[6].project_member_role", is(TESTER.name())));
    }

    @Test
    @Order(4)
    public void testGetByProjectMemberId() throws Exception {
        mockMvc.perform(get("/api/v1/project-member/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    @Order(5)
    public void testGetByProjectMemberIdSecondTime() throws Exception {
        mockMvc.perform(get("/api/v1/project-member/{id}", 2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)));
    }


    @Test
    @Order(6)
    public void testGetAllByProjectId() throws Exception {
        mockMvc.perform(get("/api/v1/project-member/project/{project-id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].project_id", is(1)))
                .andExpect(jsonPath("$[0].employee_id", is(1)))
                .andExpect(jsonPath("$[0].project_member_role", is(DEVELOPER.name())))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].project_id", is(1)))
                .andExpect(jsonPath("$[1].employee_id", is(2)))
                .andExpect(jsonPath("$[1].project_member_role", is(TESTER.name())));
    }

    @Test
    @Order(7)
    public void testDeleteProjectMember() throws Exception {
        mockMvc.perform(delete("/api/v1/project-member/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Order(8)
    public void testGetAllProjectMembersAfterDelete() throws Exception {
        mockMvc.perform(get("/api/v1/project-member"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].project_id", is(1)))
                .andExpect(jsonPath("$[0].employee_id", is(2)))
                .andExpect(jsonPath("$[0].project_member_role", is(TESTER.name())));
    }

    @Test
    @Order(9)
    public void testGetByProjectMemberIdThirdTime_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/project-member/{id}", 999))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(10)
    public void testDeleteByProjectMemberIdSecondTime_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/project-member/{id}", 999))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(11)
    public void testSaveProjectMember_ViolatingConstraints() throws Exception {
        ProjectMemberDto projectMemberDto =
                ProjectMemberDto.builder().id(1L).projectId(999L).employeeId(1999L).projectMemberRole(TESTER).build();


        mockMvc.perform(post("/api/v1/project-member/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(projectMemberDto)))
                .andExpect(status().isConflict());
    }
}
