package com.oopscope.brainstorm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("ideas")
public class Idea {
    @Id
    private UUID id;
    private String title;
    private String description;
    private List<Vote> votes;
}
