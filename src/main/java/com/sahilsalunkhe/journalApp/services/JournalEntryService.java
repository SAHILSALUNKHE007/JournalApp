package com.sahilsalunkhe.journalApp.services;

import com.sahilsalunkhe.journalApp.entities.JournalEntry;
import com.sahilsalunkhe.journalApp.entities.User;
import com.sahilsalunkhe.journalApp.repositories.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private  UserService userService;
    // Create single entry
    @Transactional
    public JournalEntry addJournalEntry(JournalEntry entry, String username){
        Optional<User> user=userService.findUserBYUserName(username);

        JournalEntry saved= journalEntryRepository.insert(entry);
        user.get().getJournalEntries().add(saved);
        userService.save(user.get());
        return saved;
    }

    // Create multiple entries
    public List<JournalEntry> addAllJournalEntries(List<JournalEntry> journalEntries, String username){
        Optional<User> user=userService.findUserBYUserName(username);
        if(user.isPresent()) {
            List<JournalEntry> entries= journalEntryRepository.insert(journalEntries);
            user.get().getJournalEntries().addAll(entries);
            userService.save(user.get());
            return  entries;

        }
        return  new ArrayList<>();
    }

    // Get all entries
    public List<JournalEntry> getAllJournalEntry(){
        return journalEntryRepository.findAll();
    }

    // Get entry by ID
    public Optional<JournalEntry> getJournalEntryById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    // Update entry by ID
    public JournalEntry updateJournalEntry(ObjectId id, String username, JournalEntry entry){
        Optional<User> user=userService.findUserBYUserName(username);
        if(user.isPresent()) {
            User user1=user.get();

            boolean ispresent=user1.getJournalEntries()
                    .stream()
                    .anyMatch(e -> e.getId().equals(id));

            if (!ispresent) {
                return  null;
            }
            JournalEntry existingEntry = journalEntryRepository.findById(id).orElse(null);
            if (existingEntry != null) {
                existingEntry.setTitle(entry.getTitle());
                existingEntry.setContent(entry.getContent());
                return journalEntryRepository.save(existingEntry);
            }
        }
        return null; // or throw exception if not found
    }

    // Delete entry by ID
    public boolean deleteJournalEntry(ObjectId id, String username){
        Optional<User> user=userService.findUserBYUserName(username);
        if(user.isPresent()) {

            List<JournalEntry> entries=user.get().getJournalEntries();
            boolean ispresent=entries
                    .stream()
                    .anyMatch(e -> e.getId().equals(id));

            if (!ispresent) {
                return  false;
            }
            else if (journalEntryRepository.existsById(id)) {
                entries.remove(id);
                journalEntryRepository.deleteById(id);
                userService.save(user.get());
                return true;
            }
        }
        return false;
    }

    // Delete all entries
    public void deleteAllJournalEntries(){
        journalEntryRepository.deleteAll();
    }
}
