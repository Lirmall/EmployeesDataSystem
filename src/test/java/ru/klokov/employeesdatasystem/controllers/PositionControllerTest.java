package ru.klokov.employeesdatasystem.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@PropertySource("classpath:application-springBootTest.properties")
@ActiveProfiles("springBootTest")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class PositionControllerTest {
    private static final String URL_TEMPLATE = "/api/v1/admin/common/positions";

    @Autowired
    private MockMvc mockMvc;

    private String token;

    @BeforeEach
    void setUp() throws Exception {
        String body = "{\n" +
                "    \"username\": \"user-head-pers-dept\",\n" +
                "    \"password\": \"user-head-pers-dept\"\n" +
                "}";
        token = mockMvc.perform(post("/login").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andReturn()
                .getResponse().getHeader("Authorization");
    }

    @Test
    void findAllTest() throws Exception {
        mockMvc.perform(get(URL_TEMPLATE).header("Authorization", token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].name").value("Mechanic"))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].name").value("Master mechanic"))
                .andExpect(jsonPath("$.[2].id").value(3))
                .andExpect(jsonPath("$.[2].name").value("Engineer"))
                .andExpect(jsonPath("$.[3].id").value(4))
                .andExpect(jsonPath("$.[3].name").value("Chief engineer"))
                .andExpect(jsonPath("$.[4].id").value(5))
                .andExpect(jsonPath("$.[4].name").value("Technical director"));
    }

    @Test
    void getByIdTest() throws Exception {

        mockMvc.perform(get(URL_TEMPLATE + "/2").header("Authorization", token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Master mechanic"));
    }

    @Test
    void getPositionsByFilterTest() throws Exception {
        String searchModel = "{\n" +
                "  \"specifications\": [\n" +
                "    {\n" +
                "      \"fieldName\": \"salary\",\n" +
                "      \"operation\": \"<\",\n" +
                "      \"fieldValue\": 22000,\n" +
                "      \"orPredicate\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"fieldName\": \"worktypeId\",\n" +
                "      \"operation\": \":\",\n" +
                "      \"fieldValue\": 1,\n" +
                "      \"orPredicate\": false\n" +
                "    }\n" +
                "  ],\n" +
                "  \"pageSettings\": {\n" +
                "    \"pages\": 0,\n" +
                "    \"limit\": 5,\n" +
                "    \"sortColumn\": \"-id\"\n" +
                "  }\n" +
                "}";

        mockMvc.perform(post(URL_TEMPLATE + "/filter")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(searchModel))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.[0].id").value(3))
                .andExpect(jsonPath("$.items.[0].name").value("Engineer"))
                .andExpect(jsonPath("$.items.[1].id").value(2))
                .andExpect(jsonPath("$.items.[1].name").value("Master mechanic"));
    }

    @Test
    void addTest() throws Exception {
        String body = "{\n" +
                "  \"id\": 0,\n" +
                "  \"name\": \"Test position\",\n" +
                "  \"worktype\": \"Salary\",\n" +
                "  \"salary\": 10000.00\n" +
                "}";

        mockMvc.perform(post(URL_TEMPLATE + "/new")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.name").value("Test position"))
                .andExpect(jsonPath("$.worktype.id").value(1))
                .andExpect(jsonPath("$.worktype.name").value("Salary"))
                .andExpect(jsonPath("$.salary").value(10000.0));
    }

    @Test
    void patchUpdateByIdTest() throws Exception {
        String body = "{\n" +
                "  \"id\": 3,\n" +
                "  \"name\": \"Engineer(edited)\",\n" +
                "  \"salary\": 25000.0,\n" +
                "  \"worktype\": {\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Salary\"\n" +
                "  }\n" +
                "}";

        mockMvc.perform(get(URL_TEMPLATE + "/3").header("Authorization", token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("Engineer"))
                .andExpect(jsonPath("$.salary").value(20000.0));

        mockMvc.perform(patch(URL_TEMPLATE + "/new")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("Engineer(edited)"))
                .andExpect(jsonPath("$.salary").value(25000.0));
    }

    @Test
    void deletePositionByIdTest() throws Exception {
        String body = "{\n" +
                "  \"id\": 0,\n" +
                "  \"name\": \"Test position\",\n" +
                "  \"worktype\": \"Salary\",\n" +
                "  \"salary\": 10000.00\n" +
                "}";

        mockMvc.perform(post(URL_TEMPLATE + "/new")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.name").value("Test position"))
                .andExpect(jsonPath("$.worktype.id").value(1))
                .andExpect(jsonPath("$.worktype.name").value("Salary"))
                .andExpect(jsonPath("$.salary").value(10000.0));

        mockMvc.perform(get(URL_TEMPLATE + "/6").header("Authorization", token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.name").value("Test position"));

        mockMvc.perform(delete(URL_TEMPLATE + "/6").header("Authorization", token))
                .andDo(print())
                .andExpect(status().isOk());

        assertThrows(NestedServletException.class,
                () -> mockMvc.perform(get(URL_TEMPLATE + "/6").header("Authorization", token))
                        .andDo(print())
                        .andExpect(status().isOk()));


    }
}