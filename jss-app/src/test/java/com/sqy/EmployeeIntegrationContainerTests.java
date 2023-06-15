package com.sqy;

import com.sqy.dto.employee.EmployeeDto;
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

import static com.sqy.domain.employee.EmployeeStatus.ACTIVE;
import static com.sqy.domain.employee.EmployeeStatus.DELETED;
import static com.sqy.util.MappingUtils.convertObjectToJson;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
public class EmployeeIntegrationContainerTests {

    @Autowired
    private MockMvc mockMvc;

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("employee")
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
    public void testGetAllEmployees() throws Exception {
        mockMvc.perform(get("/api/v1/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(7)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].first_name", is("John")))
                .andExpect(jsonPath("$[0].last_name", is("Doe")))
                .andExpect(jsonPath("$[0].middle_name", is("Michael")))
                .andExpect(jsonPath("$[0].position", is("Manager")))
                .andExpect(jsonPath("$[0].account", is("jdoe123")))
                .andExpect(jsonPath("$[0].email", is("jdoe@example.com")))
                .andExpect(jsonPath("$[0].employee_status", is(ACTIVE.name())))
                .andExpect(jsonPath("$[4].id", is(5)))
                .andExpect(jsonPath("$[4].first_name", is("David")))
                .andExpect(jsonPath("$[4].last_name", is("Wilson")))
                .andExpect(jsonPath("$[4].middle_name", is("Thomas")))
                .andExpect(jsonPath("$[4].position", is("Clerk")))
                .andExpect(jsonPath("$[4].account", is("dwilson123")))
                .andExpect(jsonPath("$[4].email", is("dwilson@example.com")))
                .andExpect(jsonPath("$[4].employee_status", is(DELETED.name())));
    }

    @Test
    @Order(2)
    public void testGetEmployeeById() throws Exception {
        mockMvc.perform(get("/api/v1/employee/{id}", 2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.first_name", is("Jane")))
                .andExpect(jsonPath("$.last_name", is("Smith")))
                .andExpect(jsonPath("$.middle_name", is(nullValue())))
                .andExpect(jsonPath("$.position", is("Assistant")))
                .andExpect(jsonPath("$.account", is("jsmith456")))
                .andExpect(jsonPath("$.email", is("jsmith@example.com")))
                .andExpect(jsonPath("$.employee_status", is(ACTIVE.name())));
    }

    @Test
    @Order(3)
    public void testGetEmployeeByIdSecondTime() throws Exception {
        mockMvc.perform(get("/api/v1/employee/{id}", 4))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.first_name", is("dmitriy")))
                .andExpect(jsonPath("$.last_name", is("medvedev")))
                .andExpect(jsonPath("$.middle_name", is("anatol'evich")))
                .andExpect(jsonPath("$.position", is("smth")))
                .andExpect(jsonPath("$.account", is(nullValue())))
                .andExpect(jsonPath("$.email", is("ttl1de@ya.ru")))
                .andExpect(jsonPath("$.employee_status", is(ACTIVE.name())));
    }

    @Test
    @Order(4)
    public void testSaveEmployee() throws Exception {
        EmployeeDto input = EmployeeDto.builder().firstName("test").lastName("famili9").build();

        mockMvc.perform(post("/api/v1/employee/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @Order(5)
    public void testGetEmployeeByIdThirdTime() throws Exception {
        mockMvc.perform(get("/api/v1/employee/{id}", 8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(8)))
                .andExpect(jsonPath("$.first_name", is("test")))
                .andExpect(jsonPath("$.last_name", is("famili9")))
                .andExpect(jsonPath("$.employee_status", is(ACTIVE.name())));
    }

    @Test
    @Order(6)
    public void testUpdateEmployee() throws Exception {
        EmployeeDto input = EmployeeDto.builder().id(8L).firstName("test_changed").lastName("famili9").build();

        mockMvc.perform(put("/api/v1/employee/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Order(7)
    public void testGetEmployeeByIdFourthTime() throws Exception {
        mockMvc.perform(get("/api/v1/employee/{id}", 8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(8)))
                .andExpect(jsonPath("$.first_name", is("test_changed")))
                .andExpect(jsonPath("$.last_name", is("famili9")))
                .andExpect(jsonPath("$.employee_status", is(ACTIVE.name())));
    }

    @Test
    @Order(8)
    public void testDeleteEmployee() throws Exception {
        mockMvc.perform(delete("/api/v1/employee/{id}", 8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Order(9)
    public void testGetEmployeeByIdFifthTime() throws Exception {
        mockMvc.perform(get("/api/v1/employee/{id}", 8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(8)))
                .andExpect(jsonPath("$.first_name", is("test_changed")))
                .andExpect(jsonPath("$.last_name", is("famili9")))
                .andExpect(jsonPath("$.employee_status", is(DELETED.name())));
    }


    @Test
    @Order(10)
    public void testSearchEmployees() throws Exception {
        String value = "mi";
        mockMvc.perform(get("/api/v1/employee/search/{value}", value))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @Order(11)
    public void testGetEmployeeByIdSixthTime_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/employee/{id}", 999))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(12)
    public void testDeleteEmployeeByIdSecondTime_NotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/employee/{id}", 999))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(13)
    public void testUpdateEmployee_NotExistingID() throws Exception {
        EmployeeDto input = EmployeeDto.builder().id(999L).firstName("test_changed").lastName("famili9").build();

        mockMvc.perform(put("/api/v1/employee/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(input)))
                .andExpect(status().isConflict());
    }
}
