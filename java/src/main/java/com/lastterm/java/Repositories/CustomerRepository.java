package com.lastterm.java.Repositories;

import com.lastterm.java.Models.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {
}
