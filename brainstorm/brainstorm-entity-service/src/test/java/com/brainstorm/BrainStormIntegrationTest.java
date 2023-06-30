
package com.brainstorm;

import com.brainstorm.controller.UserController;
import com.brainstorm.entity.User;
import com.brainstorm.repository.UserRepository;
import org.apache.commons.text.RandomStringGenerator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BrainStormIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;


    private User createRandomUser() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstName(StringUtils.RandomTextGenerator.generateRandomText(6));
        user.setLastName(StringUtils.RandomTextGenerator.generateRandomText(6));
        user.setEmail(StringUtils.RandomTextGenerator.generateRandomEmail());
        user.setPassword(StringUtils.RandomTextGenerator.generateRandomText(8));
        return user;
    }

    @Test
    public void testGetUserById() throws Exception {
        User user = createRandomUser();
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
        User user = createRandomUser();

        String userJson = "{\"firstName\":\"" + user.getFirstName() + "\",\"lastName\":\"" + user.getLastName() + "\",\"email\":\"" + user.getEmail() + "\",\"password\":\"" + user.getPassword() + "\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = createRandomUser();
        UUID userId = user.getId();
        String updatedLastName = StringUtils.RandomTextGenerator.generateRandomText(6);
        String updatedEmail = StringUtils.RandomTextGenerator.generateRandomEmail();
        String updatedPassword = StringUtils.RandomTextGenerator.generateRandomText(8);

        String userJson = "{\"lastName\":\"" + updatedLastName + "\",\"email\":\"" + updatedEmail + "\",\"password\":\"" + updatedPassword + "\"}";

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(updatedLastName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(updatedEmail))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDeleteUser() throws Exception {
        User user = createRandomUser();
        UUID userId = user.getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
