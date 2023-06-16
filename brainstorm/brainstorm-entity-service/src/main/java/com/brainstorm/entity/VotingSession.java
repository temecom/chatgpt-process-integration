package com.brainstorm.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class VotingSession {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String description;

    @OneToMany
    private List<Idea> ideas;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
