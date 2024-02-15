package com.lastterm.java.Repositories;

import com.lastterm.java.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, Integer> {
    User findByEmail(String email);
}
