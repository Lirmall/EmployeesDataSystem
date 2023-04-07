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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
class SalaryControllerTest {
    private static final String URL_TEMPLATE = "/api/v1/admin/common/salary";

    @Autowired
    private MockMvc mockMvc;

    private String token;

    @BeforeEach
    void setUp() throws Exception {
        String body = "{\n" +
                "    \"username\": \"user-buhhalter\",\n" +
                "    \"password\": \"user-buhhalter\"\n" +
                "}";
        token = mockMvc.perform(post("/login").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andReturn()
                .getResponse().getHeader("Authorization");
    }
    @Test
    void getAverageSalaryByWorktypeTest() throws Exception {
        String resultContent = "{\"nameOfSalary\":\"Salary by \\\"Salary\\\" worktype per month\",\"salary\":21600.0}";

        String body = "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"Salary\"\n" +
                "}";

        MockHttpServletResponse response = mockMvc.perform(post(URL_TEMPLATE + "/averageSalaryByWorktype")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse();

        assertEquals(resultContent, response.getContentAsString());
    }

    @Test
    void getMonthSalaryByEmployeeTest() throws Exception {
        String resultContent =
                "{\"nameOfSalary\":\"Month salary of an employee  Sidorov Alexander Sidorovich per working day\",\"salary\":130.0}";

        String body = "{\n" +
                "  \"id\": 3,\n" +
                "  \"secondName\": \"Sidorov\",\n" +
                "  \"firstName\": \"Alexander\",\n" +
                "  \"thirdName\": \"Sidorovich\",\n" +
                "  \"gender\": {\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Male\"\n" +
                "  },\n" +
                "  \"birthday\": \"1982-03-17\",\n" +
                "  \"worktype\": {\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"Hourly\"\n" +
                "  },\n" +
                "  \"salary\": 130.00,\n" +
                "  \"workstartDate\": \"2001-04-16\",\n" +
                "  \"dismissed\": false,\n" +
                "  \"dismissedDate\": null\n" +
                "}";

        MockHttpServletResponse response = mockMvc.perform(post(URL_TEMPLATE + "/monthSalaryByEmployee")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse();

        assertEquals(resultContent, response.getContentAsString());
    }

    @Test
    void salaryOnPeriodByEmployeeTest() throws Exception {
        String resultContent =
                "{\"nameOfSalary\":\"Salary of an employee  Minaeva Elena Anatolyevna on period 2022-05-01 - 2022-05-31\",\"salary\":20000.0}";

        String body = "{\n" +
                "  \"secondName\": \"Minaeva\",\n" +
                "  \"firstName\": \"Elena\",\n" +
                "  \"thirdName\": \"Anatolyevna\",\n" +
                "  \"birthdayDate\": \"1990-08-15\",\n" +
                "  \"periodStart\": \"2022-05-01\",\n" +
                "  \"periodEnd\": \"2022-05-31\"\n" +
                "}";

        MockHttpServletResponse response = mockMvc.perform(post(URL_TEMPLATE + "/salaryOnPeriodByEmployee")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse();

        assertEquals(resultContent, response.getContentAsString());
    }
}