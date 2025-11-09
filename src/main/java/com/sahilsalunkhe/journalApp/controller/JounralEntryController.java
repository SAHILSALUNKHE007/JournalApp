package com.sahilsalunkhe.journalApp.controller;

import com.sahilsalunkhe.journalApp.entities.JournalEntry;
import com.sahilsalunkhe.journalApp.services.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class JounralEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    // ✅ Get all entries
    @GetMapping("/allEntry")
    public ResponseEntity<List<JournalEntry>> getAllEntries() {
        List<JournalEntry> entries = journalEntryService.getAllJournalEntry();
        if (entries.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(entries); // 200 OK
    }

    // ✅ Get entry by ID
    @GetMapping("/entry/{id}")
    public ResponseEntity<JournalEntry> getJournalById(@PathVariable String id) {
        Optional<JournalEntry> entry = journalEntryService.getJournalEntryById(id);
        return entry.map(ResponseEntity::ok) // 200 OK if found
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // 404 if not found
    }

    // ✅ Create single entry
    @PostMapping("/addEntry")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry) {
        try {
            JournalEntry createdEntry = journalEntryService.addJournalEntry(entry);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEntry); // 201 Created
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 Bad Request
        }
    }

    // ✅ Create multiple entries
    @PostMapping("/addEntries")
    public ResponseEntity<List<JournalEntry>> createAllEntry(@RequestBody List<JournalEntry> entries) {
        if (entries == null || entries.isEmpty()) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }
        List<JournalEntry> savedEntries = journalEntryService.addAllJournalEntries(entries);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEntries);
    }

    // ✅ Update entry by ID
    @PutMapping("/updateEntry/{id}")
    public ResponseEntity<JournalEntry> updateEntry(@PathVariable String id, @RequestBody JournalEntry entry) {
        JournalEntry updated = journalEntryService.updateJournalEntry(id, entry);
        if (updated != null) {
            return ResponseEntity.ok(updated); // 200 OK
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
    }

    // ✅ Delete entry by ID → 204 No Content
    @DeleteMapping("/deleteEntry/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable String id) {
        boolean deleted = journalEntryService.deleteJournalEntry(id);
        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
    }

    // ✅ Delete all entries → 204 No Content
    @DeleteMapping("/deleteAllEntries")
    public ResponseEntity<Void> deleteAllEntries() {
        journalEntryService.deleteAllJournalEntries();
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
