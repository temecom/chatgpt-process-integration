package com.brainstorm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Idea extends BaseEntity {
    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToMany(mappedBy = "idea")
    private List<Vote> votes;

    @ManyToOne
    @JoinColumn(name = "voting_session_id")
    private VotingSession votingSession;
}

