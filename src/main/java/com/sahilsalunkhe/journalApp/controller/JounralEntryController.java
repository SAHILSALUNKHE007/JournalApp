package com.sahilsalunkhe.journalApp.controller;

import com.sahilsalunkhe.journalApp.entities.JournalEntry;
import com.sahilsalunkhe.journalApp.services.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class JounralEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    // Get all entries
    @GetMapping("/allEntry")
    public List<JournalEntry> getAllEntries(){
        return journalEntryService.getAllJournalEntry();
    }

    // Get entry by ID
    @GetMapping("/entry/{id}")
    public JournalEntry getJounralById(@PathVariable String id){
        return journalEntryService.getJournalEntryById(id);
    }

    // Create single entry
    @PostMapping("/addEntry")
    public JournalEntry createEntry(@RequestBody JournalEntry entry){
        return journalEntryService.addJournalEntry(entry);
    }

    // Create multiple entries
    @PostMapping("/addEntries")
    public List<JournalEntry> createAllEntry(@RequestBody List<JournalEntry> entries){
        return journalEntryService.addAllJournalEntries(entries);
    }

    // Update entry by ID
    @PutMapping("/updateEntry/{id}")
    public JournalEntry updateEntry(@PathVariable String id, @RequestBody JournalEntry entry){
        return journalEntryService.updateJournalEntry(id, entry);
    }

    // Delete entry by ID
    @DeleteMapping("/deleteEntry/{id}")
    public String deleteEntry(@PathVariable String id){
        boolean deleted = journalEntryService.deleteJournalEntry(id);
        return deleted ? "Deleted entry with id: " + id : "Entry not found with id: " + id;
    }

    // Delete all entries
    @DeleteMapping("/deleteAllEntries")
    public String deleteAllEntries(){
        journalEntryService.deleteAllJournalEntries();
        return "All entries deleted successfully!";
    }
}
