package com.gate.gateway.user.repo;

import com.gate.gateway.user.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findFirstByName(String name);
}
