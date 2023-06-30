package com.brainstorm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}

