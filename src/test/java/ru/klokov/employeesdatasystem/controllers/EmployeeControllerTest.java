package ru.klokov.employeesdatasystem.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("springBootTest")
@PropertySource("classpath:application-springBootTest.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class EmployeeControllerTest {
    private static final String URL_TEMPLATE = "/api/v1/admin/common/employees";

    @Autowired
    private MockMvc mockMvc;

    private String token;

    @BeforeEach
    void setUp() throws Exception {
        String body = "{\n" +
                "    \"username\": \"user\",\n" +
                "    \"password\": \"123\"\n" +
                "}";
        token = mockMvc.perform(post("/login").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andReturn()
                .getResponse().getHeader("Authorization");
    }

    @Test
    void addTest() throws Exception {
        String body = "{\n" +
                "  \"birthdayDate\": \"2000-03-15\",\n" +
                "  \"firstName\": \"Test\",\n" +
                "  \"genderDTO\": {\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Male\"\n" +
                "  },\n" +
                "  \"positionDTO\": {\n" +
                "    \"id\": 3,\n" +
                "    \"name\": \"Engineer\",\n" +
                "    \"salary\": 20000.00,\n" +
                "    \"worktype\": {\n" +
                "      \"id\": 1,\n" +
                "      \"name\": \"Salary\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"rangeDTO\": {\n" +
                "    \"bonus\": 1.0,\n" +
                "    \"id\": 4,\n" +
                "    \"name\": \"Non-range\"\n" +
                "  },\n" +
                "  \"secondName\": \"Testov\",\n" +
                "  \"thirdName\": \"Testovich\",\n" +
                "  \"workstartDate\": \"2020-05-15\"\n" +
                "}";

        mockMvc.perform(post(URL_TEMPLATE)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.secondName").value("Testov"))
                .andExpect(jsonPath("$.firstName").value("Test"))
                .andExpect(jsonPath("$.thirdName").value("Testovich"))
                .andExpect(jsonPath("$.gender.id").value(1))
                .andExpect(jsonPath("$.gender.name").value("Male"))
                .andExpect(jsonPath("$.birthday").value("2000-03-15"))
                .andExpect(jsonPath("$.worktype.id").value(1))
                .andExpect(jsonPath("$.worktype.name").value("Salary"))
                .andExpect(jsonPath("$.salary").value(20000.0))
                .andExpect(jsonPath("$.workstartDate").value("2020-05-15"))
                .andExpect(jsonPath("$.dismissed").value(false));
    }

    @Test
    void findAllTest() throws Exception {
        mockMvc.perform(get(URL_TEMPLATE)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    void getByIdTest() throws Exception {
        mockMvc.perform(get(URL_TEMPLATE + "/1")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.secondName").value("Ivanov"))
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.thirdName").value("Ivanovich"))
                .andExpect(jsonPath("$.gender.id").value(1))
                .andExpect(jsonPath("$.gender.name").value("Male"))
                .andExpect(jsonPath("$.birthday").value("1980-01-15"))
                .andExpect(jsonPath("$.worktype.id").value(2))
                .andExpect(jsonPath("$.worktype.name").value("Hourly"))
                .andExpect(jsonPath("$.salary").value(120.0))
                .andExpect(jsonPath("$.workstartDate").value("2000-05-14"))
                .andExpect(jsonPath("$.dismissed").value(false));
    }

    @Test
    void getEmployeesByFilterTest() throws Exception {
        String body = "{\n" +
                "  \"birthdayDates\": [\n" +
                "    " +
                "  ],\n" +
                "  \"dismissed\": [\n" +
                "    " +
                "  ],\n" +
                "  \"dismissedDates\": [\n" +
                "    " +
                "  ],\n" +
                "  \"firstNames\": [\n" +
                "    " +
                "  ],\n" +
                "  \"genderIds\": [\n" +
                "    " +
                "  ],\n" +
                "  \"ids\": [\n" +
                "    " +
                "  ],\n" +
                "  \"limit\": 5,\n" +
                "  \"pages\": 0,\n" +
                "  \"salaries\": [\n" +
                "    " +
                "  ],\n" +
                "  \"secondNames\": [\n" +
                "    \"Ivanov\"" +
                "  ],\n" +
                "  \"sortColumn\": \"id\",\n" +
                "  \"thirdNames\": [\n" +
                "    " +
                "  ],\n" +
                "  \"workstartDates\": [\n" +
                "    " +
                "  ],\n" +
                "  \"worktypeIds\": [\n" +
                "    " +
                "  ]\n" +
                "}";

        mockMvc.perform(post(URL_TEMPLATE + "/filter")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.items.[0].id").value(1))
                .andExpect(jsonPath("$.items.[0].secondName").value("Ivanov"))
                .andExpect(jsonPath("$.items.[0].firstName").value("Ivan"))
                .andExpect(jsonPath("$.items.[0].thirdName").value("Ivanovich"))
                .andExpect(jsonPath("$.items.[0].gender.id").value(1))
                .andExpect(jsonPath("$.items.[0].gender.name").value("Male"))
                .andExpect(jsonPath("$.items.[0].birthday").value("1980-01-15"))
                .andExpect(jsonPath("$.items.[0].worktype.id").value(2))
                .andExpect(jsonPath("$.items.[0].worktype.name").value("Hourly"))
                .andExpect(jsonPath("$.items.[0].salary").value(120.0))
                .andExpect(jsonPath("$.items.[0].workstartDate").value("2000-05-14"))
                .andExpect(jsonPath("$.items.[0].dismissed").value(false));
    }

    @Test
    void dismissEmployeeTest() throws Exception {
        String dismissEmployee = "{\n" +
                "  \"birthdayDate\": \"1980-01-15\",\n" +
                "  \"secondName\": \"Ivanov\",\n" +
                "  \"firstName\": \"Ivan\",\n" +
                "  \"thirdName\": \"Ivanovich\",\n" +
                "  \"workstartDate\": \"2000-05-14\",\n" +
                "  \"dismissDate\": \"2022-08-30\"\n" +
                "}";

        mockMvc.perform(post(URL_TEMPLATE + "/dismiss")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dismissEmployee))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void updateEmployeeTest() throws Exception {
        String updateEmployee = "{\n" +
                "  \"birthdayDate\": \"1980-01-15\",\n" +
                "  \"firstName\": \"Ivan\",\n" +
                "  \"positionDTO\": {\n" +
                "    \"id\": 3,\n" +
                "    \"name\": \"Engineer\",\n" +
                "    \"salary\": 0,\n" +
                "    \"worktype\": {\n" +
                "      \"id\": 1,\n" +
                "      \"name\": \"Salary\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"rangeDTO\": {\n" +
                "    \"bonus\": 0,\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"3 range\"\n" +
                "  },\n" +
                "  \"secondName\": \"Ivanov\",\n" +
                "  \"thirdName\": \"Ivanovich\",\n" +
                "  \"updateDate\": \"2022-08-15\"\n" +
                "}";

        mockMvc.perform(post(URL_TEMPLATE + "/update")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateEmployee))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.secondName").value("Ivanov"))
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.thirdName").value("Ivanovich"))
                .andExpect(jsonPath("$.gender.id").value(1))
                .andExpect(jsonPath("$.gender.name").value("Male"))
                .andExpect(jsonPath("$.birthday").value("1980-01-15"))
                .andExpect(jsonPath("$.worktype.id").value(1))
                .andExpect(jsonPath("$.worktype.name").value("Salary"))
                .andExpect(jsonPath("$.salary").value(20000.0))
                .andExpect(jsonPath("$.workstartDate").value("2000-05-14"))
                .andExpect(jsonPath("$.dismissed").value(false));
    }
}