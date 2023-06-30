package com.brainstorm.controller;

import com.brainstorm.entity.VotingSession;
import com.brainstorm.exception.NotFoundException;
import com.brainstorm.repository.VotingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/voting-sessions")
public class VotingSessionController {
    private final VotingSessionRepository votingSessionRepository;

    @Autowired
    public VotingSessionController(VotingSessionRepository votingSessionRepository) {
        this.votingSessionRepository = votingSessionRepository;
    }

    @GetMapping
    public List<VotingSession> getAllVotingSessions() {
        return votingSessionRepository.findAll();
    }

    @GetMapping("/{id}")
    public VotingSession getVotingSessionById(@PathVariable UUID id) throws NotFoundException {
        return votingSessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Voting session not found"));
    }

    @PostMapping
    public VotingSession createVotingSession(@RequestBody VotingSession votingSession) {
        return votingSessionRepository.save(votingSession);
    }

    @PutMapping("/{id}")
    public VotingSession updateVotingSession(@PathVariable UUID id, @RequestBody VotingSession votingSession) throws NotFoundException {
        if (!votingSessionRepository.existsById(id)) {
            throw new NotFoundException("Voting session not found");
        }
        votingSession.setId(id);
        return votingSessionRepository.save(votingSession);
    }

    @DeleteMapping("/{id}")
    public void deleteVotingSession(@PathVariable UUID id) throws NotFoundException {
        if (!votingSessionRepository.existsById(id)) {
            throw new NotFoundException("Voting session not found");
        }
        votingSessionRepository.deleteById(id);
    }
}

