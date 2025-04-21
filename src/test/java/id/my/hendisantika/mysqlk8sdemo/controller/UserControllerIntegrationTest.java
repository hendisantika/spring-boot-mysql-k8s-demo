package id.my.hendisantika.mysqlk8sdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.my.hendisantika.mysqlk8sdemo.config.AbstractContainerBaseTest;
import id.my.hendisantika.mysqlk8sdemo.model.User;
import id.my.hendisantika.mysqlk8sdemo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.junit.jupiter.Testcontainers;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class UserControllerIntegrationTest extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    void shouldCreateUser() throws Exception {
        // Given
        User user = new User();
        user.setName("Itadori Yuji");
        user.setCountry("JPN");

        // When
        ResultActions response = mockMvc.perform(post("/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isString())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        // Given
        User user1 = new User();
        user1.setName("Itadori Yuji");
        user1.setCountry("JPN");

        User user2 = new User();
        user2.setName("Satoru Gojo");
        user2.setCountry("JPN");

        userRepository.save(user1);
        userRepository.save(user2);

        // When
        ResultActions response = mockMvc.perform(get("/users"));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)));
    }

    @Test
    void shouldGetUserById() throws Exception {
        // Given
        User user = new User();
        user.setName("Itadori Yuji");
        user.setCountry("JPN");
        User savedUser = userRepository.save(user);

        // When
        ResultActions response = mockMvc.perform(get("/findUser/{id}", savedUser.getId()));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Itadori Yuji")))
                .andExpect(jsonPath("$.country", is("JPN")));
    }


    @Test
    void shouldDeleteUserById() throws Exception {
        // Given
        User user = new User();
        user.setName("Itadori Yuji");
        user.setCountry("JPN");
        User savedUser = userRepository.save(user);

        // When
        ResultActions response = mockMvc.perform(get("/deleteUser/{id}", savedUser.getId()));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Deleted User Successfully::" + savedUser.getId())));

        // Verify user is deleted
        mockMvc.perform(get("/findUser/{id}", savedUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
}