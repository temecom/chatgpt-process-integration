package com.oopscope.brainstorm.repository;

import com.oopscope.brainstorm.model.Idea;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface IdeaRepository extends ReactiveCrudRepository<Idea, UUID> {
}
