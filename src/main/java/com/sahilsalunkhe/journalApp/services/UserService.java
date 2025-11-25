package com.sahilsalunkhe.journalApp.services;

import com.sahilsalunkhe.journalApp.entities.JournalEntry;
import com.sahilsalunkhe.journalApp.entities.User;
import com.sahilsalunkhe.journalApp.repositories.UserRepositories;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepositories userRepositories;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //private  static  final Logger logger= LoggerFactory.getLogger(UserService.class);
    public User save_user(User user){
        try {
            if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            if (user.getRoles() == null || user.getRoles().isEmpty()) {
                user.setRoles(List.of("USER"));
            }
            return userRepositories.save(user);
        }catch (Exception e){
            log.warn("User Already Present ");
        }
        return  null;
    }
    public User saveAdminUser(User user) {
        try{if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(List.of("USER", "ADMIN"));
        }
        return userRepositories.save(user);
    }catch (Exception e){
            log.warn("Admin Already Present ");
        }
        return  null;
    }
    public  User save(User user){
        return  userRepositories.save(user);
    }

    public List<User> getAllUser(){
        return userRepositories.findAll();
    }

    public Optional<User> findUserBYUserName(String username){
        return Optional.ofNullable(userRepositories.findByUsername(username));
    }

    public void deleteUserById(ObjectId id){
         userRepositories.deleteById(id);
    }
    public User updateUser(String username, User updatedUser) {
        User existingUser = userRepositories.findByUsername(username);

        if (existingUser != null) {
            if (updatedUser.getUsername() != null)
                existingUser.setUsername(updatedUser.getUsername());
            if (updatedUser.getPassword() != null)
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            if (updatedUser.getJournalEntries() != null)
                existingUser.setJournalEntries(updatedUser.getJournalEntries());

            return userRepositories.save(existingUser);
        }
        return null;
    }

    public  List<ObjectId> getAllgetJournalEntriesId(String username){
        User user = userRepositories.findByUsername(username);
        return user.getJournalEntries()
                .stream()
                .map(JournalEntry::getId)
                .toList();
    }
}
