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
import com.brainstorm.entity.Idea;
import com.brainstorm.entity.Vote;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Integration tests for the BrainstormEntityService.
 */
@SpringBootTest(classes = { BrainstormEntityServiceApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("secrets")
public class BrainStormVoteIntegrationTest {

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
     * Tests the GET endpoint for retrieving a vote.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testGetVote() throws Exception {
        // Create and save a random user
        User user = createAndSaveRandomUser();
        UUID userId = user.getId();

        // Create and save a random idea
        Idea idea = createAndSaveRandomIdea();
        UUID ideaId = idea.getId();

        // Create and save a random vote with the created user and idea
        Vote vote = createAndSaveRandomVote(user, idea);
        UUID voteId = vote.getId();

        // Perform GET request to retrieve the vote by ID
        mockMvc.perform(MockMvcRequestBuilders.get("/votes/{id}", voteId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(voteId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.id").value(userId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idea.id").value(ideaId.toString()))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Tests the POST endpoint for creating a vote.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testCreateVote() throws Exception {
        // Create and save a random user
        User user = createAndSaveRandomUser();

        // Create and save a random idea
        Idea idea = createAndSaveRandomIdea();

        // Create a vote with the created user and idea
        Vote vote = createRandomVote(user, idea);
        String voteJson = objectMapper.writeValueAsString(vote);

        // Perform POST request to create a new vote
        mockMvc.perform(MockMvcRequestBuilders.post("/votes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(voteJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.id").value(user.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idea.id").value(idea.getId().toString()))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Tests the DELETE endpoint for deleting a vote.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testDeleteVote() throws Exception {
        // Create and save a random user
        User user = createAndSaveRandomUser();

        // Create and save a random idea
        Idea idea = createAndSaveRandomIdea();

        // Create and save a random vote with the created user and idea
        Vote vote = createAndSaveRandomVote(user, idea);
        UUID voteId = vote.getId();

        // Perform DELETE request to delete the vote by ID
        mockMvc.perform(MockMvcRequestBuilders.delete("/votes/{id}", voteId)
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

    private Idea createRandomIdea() {
        String title = randomStringGenerator.generate(10);
        String description = randomStringGenerator.generate(20);

        Idea idea = new Idea();
        idea.setId(UUID.randomUUID());
        idea.setTitle(title);
        idea.setDescription(description);

        return idea;
    }

    private User createAndSaveRandomUser() throws Exception {
        User user = createRandomUser();
        return saveUser(user);
    }

    private Idea createAndSaveRandomIdea() throws Exception {
        Idea idea = createRandomIdea();
        return saveIdea(idea);
    }

    private Vote createRandomVote(User user, Idea idea) {
        Vote vote = new Vote();
        vote.setId(UUID.randomUUID());
        vote.setUser(user);
        vote.setIdea(idea);

        return vote;
    }

    private Vote saveVote(Vote vote) throws Exception {
        String voteJson = objectMapper.writeValueAsString(vote);

        String newVoteResponse = mockMvc.perform(MockMvcRequestBuilders.post("/votes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(voteJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        Vote newVote = objectMapper.readValue(newVoteResponse, Vote.class);
        return newVote;
    }

    private User saveUser(User user) throws Exception {
        String userJson = objectMapper.writeValueAsString(user);

        String newUserResponse = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        User newUser = objectMapper.readValue(newUserResponse, User.class);
        return newUser;
    }

    private Idea saveIdea(Idea idea) throws Exception {
        String ideaJson = objectMapper.writeValueAsString(idea);

        String newIdeaResponse = mockMvc.perform(MockMvcRequestBuilders.post("/ideas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ideaJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        Idea newIdea = objectMapper.readValue(newIdeaResponse, Idea.class);
        return newIdea;
    }

    private Vote createAndSaveRandomVote(User user, Idea idea) throws Exception {
        Vote vote = createRandomVote(user, idea);
        return saveVote(vote);
    }
}
