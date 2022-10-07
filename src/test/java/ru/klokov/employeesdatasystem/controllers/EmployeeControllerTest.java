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
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class EmployeeControllerTest {
    private static final String URL_TEMPLATE = "/api/v1/admin/common/employees";

    @Autowired
    private MockMvc mockMvc;
    @Test
    void add() {
    }

    @Test
    void findAll() {
    }

    @Test
    void getById() {
    }

    @Test
    void getEmployeesByFilter() {
    }

    @Test
    void dismissEmployee() throws Exception {
        String dismissEmployee = "{\n" +
                "  \"birthdayDate\": \"1980-01-15\",\n" +
                "  \"secondName\": \"Ivanov\",\n" +
                "  \"firstName\": \"Ivan\",\n" +
                "  \"thirdName\": \"Ivanovich\",\n" +
                "  \"workstartDate\": \"2000-05-14\",\n" +
                "  \"dismissDate\": \"2022-08-30\"\n" +
                "}";

        mockMvc.perform(post(URL_TEMPLATE + "/dismiss").contentType(MediaType.APPLICATION_JSON)
                        .content(dismissEmployee))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void updateEmployee() throws Exception {
        String dismissEmployee = "{\n" +
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

        mockMvc.perform(post(URL_TEMPLATE + "/update").contentType(MediaType.APPLICATION_JSON)
                        .content(dismissEmployee))
                .andExpect(status().isOk())
                .andDo(print());
    }
}