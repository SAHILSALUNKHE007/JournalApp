package com.sahilsalunkhe.journalApp.services;

import com.sahilsalunkhe.journalApp.entities.JournalEntry;
import com.sahilsalunkhe.journalApp.repositories.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    // Create single entry
    public JournalEntry addJournalEntry(JournalEntry entry){
        return journalEntryRepository.insert(entry);
    }

    // Create multiple entries
    public List<JournalEntry> addAllJournalEntries(List<JournalEntry> journalEntries){
        return journalEntryRepository.insert(journalEntries);
    }

    // Get all entries
    public List<JournalEntry> getAllJournalEntry(){
        return journalEntryRepository.findAll();
    }

    // Get entry by ID
    public Optional<JournalEntry> getJournalEntryById(String id){
        return journalEntryRepository.findById(id);
    }

    // Update entry by ID
    public JournalEntry updateJournalEntry(String id, JournalEntry entry){
        JournalEntry existingEntry = journalEntryRepository.findById(id).orElse(null);
        if(existingEntry != null){
            existingEntry.setTitle(entry.getTitle());
            existingEntry.setContent(entry.getContent());
            return journalEntryRepository.save(existingEntry);
        }
        return null; // or throw exception if not found
    }

    // Delete entry by ID
    public boolean deleteJournalEntry(String id){
        if(journalEntryRepository.existsById(id)){
            journalEntryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Delete all entries
    public void deleteAllJournalEntries(){
        journalEntryRepository.deleteAll();
    }
}
