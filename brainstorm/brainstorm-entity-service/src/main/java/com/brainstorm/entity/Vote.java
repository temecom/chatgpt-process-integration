package com.brainstorm.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Entity
public class Vote {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    private User user;
}
