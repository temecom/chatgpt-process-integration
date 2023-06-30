package com.brainstorm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class VotingSession extends BaseEntity {
    private String name;
    private String description;

    @OneToMany(mappedBy = "votingSession")
    private List<Idea> ideas;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}

