package com.brainstorm;

import java.util.UUID;

import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.brainstorm.entity.Idea;
import com.brainstorm.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class IdeaTestUtils {

    @Autowired
    private RandomStringGenerator randomStringGenerator;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    public Idea createRandomIdea() {
        String title = randomStringGenerator.generate(10);
        String description = randomStringGenerator.generate(20);

        Idea idea = new Idea();
        idea.setId(UUID.randomUUID());
        idea.setTitle(title);
        idea.setDescription(description);
        return idea;
    }

    public Idea saveIdea(Idea idea) throws Exception {
        String ideaJson = objectMapper.writeValueAsString(idea);
        String newIdeaResponse = mockMvc.perform(MockMvcRequestBuilders.post("/ideas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ideaJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        Idea newIdea = objectMapper.readValue(newIdeaResponse, Idea.class);
        return newIdea;
    }

    public Idea createAndSaveRandomIdea(User user) throws Exception {
        Idea idea = createRandomIdea();
        idea.setCreator(user);
        return saveIdea(idea);
    }
}
