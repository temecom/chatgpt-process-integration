package com.oopscope.brainstorm.controller;
import java.util.UUID;
import com.oopscope.brainstorm.model.Vote;
import com.oopscope.brainstorm.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/votes")
public class VoteController {
    @Autowired
    private VoteRepository voteRepository;

    @GetMapping("")
    public Flux<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Vote> getVoteById(@PathVariable UUID id) {
        return voteRepository.findById(id);
    }

    @PostMapping("")
    public Mono<Vote> createVote(@RequestBody Vote vote) {
        return voteRepository.save(vote);
    }

    @PutMapping("/{id}")
    public Mono<Vote> updateVote(@PathVariable UUID id, @RequestBody Vote vote) {
        return voteRepository.findById(id)
                .flatMap(existingVote -> {
                    existingVote.setUser(vote.getUser());
                    existingVote.setTimestamp(vote.getTimestamp());
                    return voteRepository.save(existingVote);
                });
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteVote(@PathVariable UUID id) {
        return voteRepository.deleteById(id);
    }
}
