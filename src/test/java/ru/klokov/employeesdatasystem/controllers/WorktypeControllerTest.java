package ru.klokov.employeesdatasystem.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.klokov.employeesdatasystem.specifications.worktypesSpecification.WorktypeSearchModel;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class WorktypeControllerTest {
    private static final String URL_TEMPLATE = "/api/v1/admin/common/worktypes";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAllTest() throws Exception {
        mockMvc.perform(get(URL_TEMPLATE))
                .andExpect(status().isOk());
    }

    @Test
    void getByIdTest() throws Exception {
        mockMvc.perform(get(URL_TEMPLATE + "/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Salary"));
    }

    @Test
    void getWorktypesTest() throws Exception {
        String searchModel = "{\n" +
                "  \"ids\": [\n" +
                "    1, 2\n" +
                "  ],\n" +
                "  \"limit\": 0,\n" +
                "  \"names\": [\n" +
                "  ],\n" +
                "  \"pages\": 0,\n" +
                "  \"sortColumn\": \"-id\"\n" +
                "}";

        String resultContent =
                "{\"items\":" +
                        "[" +
                        "{\"id\":2," +
                        "\"name\":\"Hourly\"}," +
                        "{\"id\":1," +
                        "\"name\":\"Salary\"}" +
                        "]," +
                        "\"totalItems\":2," +
                        "\"filteredCount\":2" +
                        "}";

        MockHttpServletResponse response = mockMvc.perform(post(URL_TEMPLATE + "/filter").contentType(MediaType.APPLICATION_JSON)
                        .content(searchModel))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse();

        assertEquals(resultContent, response.getContentAsString());
    }
}