package com.brainstorm;

import java.util.UUID;

import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.brainstorm.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class UserTestUtils {

    @Autowired
    private RandomStringGenerator randomStringGenerator;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    public User createRandomUser() {
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

    public User saveUser(User user) throws Exception {
        String userJson = objectMapper.writeValueAsString(user);
        String newUserResponse = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        User newUser = objectMapper.readValue(newUserResponse, User.class);
        return newUser;
    }

    public User createAndSaveRandomUser() throws Exception {
        User user = createRandomUser();
        return saveUser(user);
    }


}
