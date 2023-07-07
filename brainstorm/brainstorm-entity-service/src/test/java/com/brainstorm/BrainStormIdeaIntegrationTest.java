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

import com.brainstorm.entity.Idea;
import com.brainstorm.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = { BrainstormEntityServiceApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("secrets")
public class BrainStormIdeaIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IdeaTestUtils ideaTestUtils;

    @Autowired
    private UserTestUtils userTestUtils;

    @Autowired
    private RandomStringGenerator randomStringGenerator;

    @BeforeEach
    public void setup() {
        // Setup any necessary configurations before each test
    }

    @Test
    public void testGetIdea() throws Exception {

        // Create a random user
        User user = userTestUtils.createAndSaveRandomUser();
        // Create and save a random idea
        Idea idea = ideaTestUtils.createAndSaveRandomIdea(user);
        UUID ideaId = idea.getId();

        mockMvc.perform(MockMvcRequestBuilders.get("/ideas/{id}", ideaId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(ideaId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(idea.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(idea.getDescription()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testCreateIdea() throws Exception {
        // Create a random user
        User user = userTestUtils.createAndSaveRandomUser();

        // Create a random idea
        Idea idea = ideaTestUtils.createRandomIdea();
        idea.setCreator(user);

        String ideaJson = objectMapper.writeValueAsString(idea);

        mockMvc.perform(MockMvcRequestBuilders.post("/ideas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ideaJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(idea.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(idea.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.creator.id").value(user.getId().toString()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testUpdateIdea() throws Exception {
               // Create a random user
        User user = userTestUtils.createAndSaveRandomUser();

        // Create and save a random idea
        Idea idea = ideaTestUtils.createAndSaveRandomIdea(user);
        UUID ideaId = idea.getId();

        // Generate updated data for the idea
        String updatedTitle = randomStringGenerator.generate(10);
        String updatedDescription = randomStringGenerator.generate(20);

        // Update the idea object with the generated data
        idea.setTitle(updatedTitle);
        idea.setDescription(updatedDescription);

        String ideaJson = objectMapper.writeValueAsString(idea);

        mockMvc.perform(MockMvcRequestBuilders.put("/ideas/{id}", ideaId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ideaJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(ideaId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(updatedTitle))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(updatedDescription))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDeleteIdea() throws Exception {
               // Create a random user
        User user = userTestUtils.createAndSaveRandomUser();

        // Create and save a random idea
        Idea idea = ideaTestUtils.createAndSaveRandomIdea(user);
        UUID ideaId = idea.getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/ideas/{id}", ideaId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
