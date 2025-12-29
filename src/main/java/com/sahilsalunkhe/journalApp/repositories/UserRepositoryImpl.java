package com.sahilsalunkhe.journalApp.repositories;

import com.sahilsalunkhe.journalApp.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSA(){
        Query query=new Query();
        query.addCriteria(Criteria.where("email").exists(true));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        return mongoTemplate.find(query,User.class);

    }
}
