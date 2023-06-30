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

/**
 * Integration tests for the BrainstormEntityService.
 */
@SpringBootTest(classes = { BrainstormEntityServiceApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("secrets")
public class BrainStormIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private RandomStringGenerator randomStringGenerator;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        randomStringGenerator = new RandomStringGenerator.Builder()
                .withinRange('a', 'z')
                .build();
    }

    /**
     * Tests the GET endpoint for retrieving a user.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testGetUser() throws Exception {
        // Create and save a random user
        User user = createAndSaveRandomUser();
        UUID userId = user.getId();

        // Perform GET request to retrieve the user by ID
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Tests the POST endpoint for creating a user.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testCreateUser() throws Exception {
        // Create and save a random user
        User user = createAndSaveRandomUser();
        String userJson = "{\"firstName\":\"" + user.getFirstName() + "\",\"lastName\":\"" + user.getLastName()
                + "\",\"email\":\"" + user.getEmail() + "\",\"password\":\"" + user.getPassword() + "\"}";

        // Perform POST request to create a new user
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Tests the PUT endpoint for updating a user.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testUpdateUser() throws Exception {
        // Create and save a random user
        User user = createAndSaveRandomUser();
        UUID userId = user.getId();

        // Generate updated data for the user
        String updatedLastName = randomStringGenerator.generate(8);
        String updatedEmail = randomStringGenerator.generate(8) + "@example.com";
        String updatedPassword = randomStringGenerator.generate(10);
        String updatedFirstName = randomStringGenerator.generate(6);

        // Update the user object with the generated data
        user.setFirstName(updatedFirstName);
        user.setLastName(updatedLastName);
        user.setEmail(updatedEmail);
        user.setPassword(updatedPassword);

        // Convert the user object to JSON
        String userJson = objectMapper.writeValueAsString(user);

        // Perform PUT request to update the user by ID
        mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(updatedFirstName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(updatedLastName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(updatedEmail))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Tests the DELETE endpoint for deleting a user.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testDeleteUser() throws Exception {
        // Create and save a random user
        User user = createAndSaveRandomUser();
        UUID userId = user.getId();

        // Perform DELETE request to delete the user by ID
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    private User createRandomUser() {
        String firstName = randomStringGenerator.generate(6);
        String lastName = randomStringGenerator.generate(8);
        String email = randomStringGenerator.generate(10) + "@example.com";
        String password = randomStringGenerator.generate(12);

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);

        return user;
    }

    private User saveUser(User user) throws Exception {
        String userJson = "{\"firstName\":\"" + user.getFirstName() + "\",\"lastName\":\"" + user.getLastName()
                + "\",\"email\":\"" + user.getEmail() + "\",\"password\":\"" + user.getPassword() + "\"}";

        // Perform POST request to create a new user
        String newUserResponse = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        // Deserialize the response JSON to User object
        User newUser = objectMapper.readValue(newUserResponse, User.class);
        return newUser;
    }

    private User createAndSaveRandomUser() throws Exception {
        // Create a random user
        User user = createRandomUser();

        // Save the user by performing a POST request
        return saveUser(user);
    }
}
