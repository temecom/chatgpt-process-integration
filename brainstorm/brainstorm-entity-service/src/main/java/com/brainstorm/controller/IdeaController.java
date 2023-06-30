package com.brainstorm.controller;

import com.brainstorm.entity.Idea;
import com.brainstorm.exception.NotFoundException;
import com.brainstorm.repository.IdeaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ideas")
public class IdeaController {
    private final IdeaRepository ideaRepository;

    @Autowired
    public IdeaController(IdeaRepository ideaRepository) {
        this.ideaRepository = ideaRepository;
    }

    @GetMapping
    public List<Idea> getAllIdeas() {
        return ideaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Idea getIdeaById(@PathVariable UUID id) throws NotFoundException {
        return ideaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Idea not found"));
    }

    @PostMapping
    public Idea createIdea(@RequestBody Idea idea) {
        return ideaRepository.save(idea);
    }

    @PutMapping("/{id}")
    public Idea updateIdea(@PathVariable UUID id, @RequestBody Idea idea) throws NotFoundException {
        if (!ideaRepository.existsById(id)) {
            throw new NotFoundException("Idea not found");
        }
        idea.setId(id);
        return ideaRepository.save(idea);
    }

    @DeleteMapping("/{id}")
    public void deleteIdea(@PathVariable UUID id) throws NotFoundException {
        if (!ideaRepository.existsById(id)) {
            throw new NotFoundException("Idea not found");
        }
        ideaRepository.deleteById(id);
    }
}

