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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@PropertySource("classpath:application-springBootTest.properties")
@ActiveProfiles("springBootTest")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class RangeControllerTest {
    private static final String URL_TEMPLATE = "/api/v1/admin/common/ranges";

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
    void findAllTest() throws Exception {

        mockMvc.perform(get(URL_TEMPLATE).header("Authorization", token))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getByIdTest() throws Exception {
        mockMvc.perform(get(URL_TEMPLATE + "/1")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("1 range"))
                .andExpect(jsonPath("$.bonus").value(1.3));
    }

    @Test
    void getRangesByFilterTest() throws Exception {
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
                        "\"name\":\"2 range\"," +
                        "\"bonus\":1.2}," +
                        "{\"id\":1," +
                        "\"name\":\"1 range\"," +
                        "\"bonus\":1.3}" +
                        "]," +
                        "\"totalItems\":4," +
                        "\"filteredCount\":2" +
                        "}";

        MockHttpServletResponse response = mockMvc.perform(post(URL_TEMPLATE + "/filter")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(searchModel))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse();

        assertEquals(resultContent, response.getContentAsString());
    }
}