package com.brainstorm.repository;

import com.brainstorm.entity.VotingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VotingSessionRepository extends JpaRepository<VotingSession, UUID> {
}

