package com.sahilsalunkhe.journalApp.repositories;

import com.sahilsalunkhe.journalApp.entities.JournalEntry;
import com.sahilsalunkhe.journalApp.services.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {



}
