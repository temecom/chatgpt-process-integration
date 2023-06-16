package com.brainstorm.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Idea {
    @Id
    @GeneratedValue
    private UUID id;

    private String title;
    private String description;

    @ManyToOne
    private User creator;

    @OneToMany
    private List<Vote> votes;
}
