package com.oopscope.brainstorm.repository;

import com.oopscope.brainstorm.model.Vote;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface VoteRepository extends ReactiveCrudRepository<Vote, UUID> {
}
