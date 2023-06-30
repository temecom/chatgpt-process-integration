package com.brainstorm.controller;

import com.brainstorm.entity.Vote;
import com.brainstorm.exception.NotFoundException;
import com.brainstorm.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/votes")
public class VoteController {
    private final VoteRepository voteRepository;

    @Autowired
    public VoteController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @GetMapping
    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Vote getVoteById(@PathVariable UUID id) throws NotFoundException {
        return voteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vote not found"));
    }

    @PostMapping
    public Vote createVote(@RequestBody Vote vote) {
        return voteRepository.save(vote);
    }

    @PutMapping("/{id}")
    public Vote updateVote(@PathVariable UUID id, @RequestBody Vote vote) throws NotFoundException {
        if (!voteRepository.existsById(id)) {
            throw new NotFoundException("Vote not found");
        }
        vote.setId(id);
        return voteRepository.save(vote);
    }

    @DeleteMapping("/{id}")
    public void deleteVote(@PathVariable UUID id) throws NotFoundException {
        if (!voteRepository.existsById(id)) {
            throw new NotFoundException("Vote not found");
        }
        voteRepository.deleteById(id);
    }
}

