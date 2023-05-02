package com.oopscope.brainstorm.controller;

import com.oopscope.brainstorm.model.Idea;
import com.oopscope.brainstorm.repository.IdeaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID; 

@RestController
@RequestMapping("/api/ideas")
public class IdeaController {
    @Autowired
    private IdeaRepository ideaRepository;

    @GetMapping("")
    public Flux<Idea> getAllIdeas() {
        return ideaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Idea> getIdeaById(@PathVariable UUID id) {
        return ideaRepository.findById(id);
        }
        @PostMapping("")
        public Mono<Idea> createIdea(@RequestBody Idea idea) {
            return ideaRepository.save(idea);
        }
        
        @PutMapping("/{id}")
        public Mono<Idea> updateIdea(@PathVariable UUID id, @RequestBody Idea idea) {
            return ideaRepository.findById(id)
                    .flatMap(existingIdea -> {
                        existingIdea.setTitle(idea.getTitle());
                        existingIdea.setDescription(idea.getDescription());
                        existingIdea.setVotes(idea.getVotes());
                        return ideaRepository.save(existingIdea);
                    });
        }
        
        @DeleteMapping("/{id}")
        public Mono<Void> deleteIdea(@PathVariable UUID id) {
            return ideaRepository.deleteById(id);
        }
    }        