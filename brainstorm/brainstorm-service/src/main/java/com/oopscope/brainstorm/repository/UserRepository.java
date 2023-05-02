package com.oopscope.brainstorm.repository;

import com.oopscope.brainstorm.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface UserRepository extends ReactiveCrudRepository<User, UUID> {
}
