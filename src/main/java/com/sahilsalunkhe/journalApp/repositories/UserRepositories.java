package com.sahilsalunkhe.journalApp.repositories;

import com.sahilsalunkhe.journalApp.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositories extends MongoRepository<User,Object> {
    User findByUsername(String username);
}
