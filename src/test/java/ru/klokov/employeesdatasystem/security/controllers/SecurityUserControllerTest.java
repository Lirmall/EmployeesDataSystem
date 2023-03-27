package ru.klokov.employeesdatasystem.security.controllers;

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
@PropertySource("classpath:application-springBootTest.properties")
@ActiveProfiles("springBootTest")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class SecurityUserControllerTest {
    private static final String URL_TEMPLATE = "/api/v1/admin/common/security";

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
                .andExpect(jsonPath("$.id").value(1));

    }

    @Test
    void createUserTest() throws Exception {
        //TODO Упростить запрос

        String createUser = "{\n" +
                "\"username\":\"testUser\",\n" +
                "\"password\":\"123\",\n" +
                "\"roles\":\n" +
                " [\n" +
                "   {\"id\":1,\n" +
                "   \"name\":\"ROLE_EMPLOYEE\",\n" +
                "   \"authorities\":\n" +
                "    [\n" +
                "    {\"id\":1,\n" +
                "     \"name\":\"genders.read\",\n" +
                "     \"authority\":\"genders.read\"\n" +
                "    }\n" +
                "   ]\n" +
                "  }\n" +
                " ]\n" +
                "}\n";


        mockMvc.perform(post(URL_TEMPLATE)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createUser))
                .andExpect(status().isOk())
                .andDo(print());
    }
}