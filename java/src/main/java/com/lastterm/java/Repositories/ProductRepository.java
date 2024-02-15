package com.lastterm.java.Repositories;//package com.lastterm.java.Repository;

import com.lastterm.java.Models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
