package com.brainstorm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Vote extends BaseEntity {
    @OneToOne
    private User user;

    @ManyToOne
    private Idea idea; 
}

