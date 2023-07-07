package com.brainstorm;

import java.util.UUID;

import org.apache.commons.text.RandomStringGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.brainstorm.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = { BrainstormEntityServiceApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("secrets")
public class BrainStormUserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserTestUtils userTestUtils;

    @Autowired
    private RandomStringGenerator randomStringGenerator;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        // No additional setup required
    }

    @Test
    public void testGetUser() throws Exception {
        User user = userTestUtils.createAndSaveRandomUser();
        UUID userId = user.getId();

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = userTestUtils.createRandomUser();

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = userTestUtils.createAndSaveRandomUser();
        UUID userId = user.getId();

        String updatedLastName = randomStringGenerator.generate(8);
        String updatedEmail = randomStringGenerator.generate(8) + "@example.com";
        String updatedPassword = randomStringGenerator.generate(10);
        String updatedFirstName = randomStringGenerator.generate(6);

        user.setFirstName(updatedFirstName);
        user.setLastName(updatedLastName);
        user.setEmail(updatedEmail);
        user.setPassword(updatedPassword);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(updatedFirstName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(updatedLastName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(updatedEmail))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDeleteUser() throws Exception {
        User user = userTestUtils.createAndSaveRandomUser();
        UUID userId = user.getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
