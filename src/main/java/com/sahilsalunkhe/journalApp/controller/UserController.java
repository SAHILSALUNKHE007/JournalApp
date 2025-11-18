package com.sahilsalunkhe.journalApp.controller;

import com.sahilsalunkhe.journalApp.entities.User;
import com.sahilsalunkhe.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // ✅ 1️⃣ Get all users
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUser();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(users); // 200 OK
    }

    // ✅ 2️⃣ Get user by ID
    @GetMapping("/user")
    public ResponseEntity<User> getUserById() {
        String username= SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        Optional<User> user = userService.findUserBYUserName(username);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ 3️⃣ Create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.save_user(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // ✅ 4️⃣ Update user by username
    @PutMapping("/update")
    public ResponseEntity updateUser( @RequestBody User updatedUser) {
        String username= SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        User updated = userService.updateUser(username, updatedUser);
        if (updated != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 200 OK
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);// 404 Not Found
    }

    // ✅ 5️⃣ Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable ObjectId id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // 404 if not found
        }
    }
}
