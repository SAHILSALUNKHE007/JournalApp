package com.sahilsalunkhe.journalApp.controller;

import com.sahilsalunkhe.journalApp.entities.JournalEntry;
import com.sahilsalunkhe.journalApp.entities.User;
import com.sahilsalunkhe.journalApp.services.JournalEntryService;
import com.sahilsalunkhe.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class JounralEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;
    // ✅ Get all entries
    @GetMapping("/allEntry")
    public ResponseEntity<List<JournalEntry>> getAllEntriesOfUser() {
        String username= SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        Optional<User> user=userService.findUserBYUserName(username);
        if(user.isPresent()) {
            List<JournalEntry> entries = user.get().getJournalEntries();
            if (entries.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204 No Content
            }

            return ResponseEntity.ok(entries);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);// 200 OK
    }

    // ✅ Get entry by ID
    @GetMapping("/entry/{id}")
    public ResponseEntity<JournalEntry> getJournalById(@PathVariable ObjectId id) {
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        List<ObjectId> ids=userService.getAllgetJournalEntriesId(username);
        boolean present=ids.stream().anyMatch(existingId->existingId.equals(id));
        if(present) {
            Optional<JournalEntry> entry = journalEntryService.getJournalEntryById(id);
            return entry.map(ResponseEntity::ok) // 200 OK if found
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);// 404 if not found
    }

    // ✅ Create single entry
    @PostMapping("/addEntry")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry) {
        try {
            String username= SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getName();
            JournalEntry createdEntry = journalEntryService.addJournalEntry(entry,username);
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
        String username= SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        List<JournalEntry> savedEntries = journalEntryService.addAllJournalEntries(entries,username);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEntries);
    }

    // ✅ Update entry by ID
    @PutMapping("/updateEntry/{id}")
    public ResponseEntity<JournalEntry> updateEntry(@PathVariable ObjectId id,  @RequestBody JournalEntry entry) {
        String username= SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        List<ObjectId> ids=userService.getAllgetJournalEntriesId(username);
        boolean exits =ids.stream().anyMatch(existingId->existingId.equals(id));
        if(exits) {
            JournalEntry updated = journalEntryService.updateJournalEntry(id, username, entry);
            if (updated != null) {
                return ResponseEntity.ok(updated); // 200 OK
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
    }

    // ✅ Delete entry by ID → 204 No Content
    @DeleteMapping("/deleteEntry/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable ObjectId id) {
        String username= SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        List<ObjectId> ids=userService.getAllgetJournalEntriesId(username);
        boolean exits =ids.stream().anyMatch(existingId->existingId.equals(id));
        if(exits) {
            boolean deleted = journalEntryService.deleteJournalEntry(id, username);
            if (deleted) {
                return ResponseEntity.noContent().build(); // 204 No Content
            }
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
