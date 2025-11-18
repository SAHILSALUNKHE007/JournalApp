package com.sahilsalunkhe.journalApp.controller;

import com.sahilsalunkhe.journalApp.entities.User;
import com.sahilsalunkhe.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping("/allUser")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> users=userService.getAllUser();
        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(users);
    }

    @PostMapping("/create-admin-user")
    public  ResponseEntity<?> addAdminUser(@RequestBody User user){
       User user1= userService.saveAdminUser(user);
       if(user1!=null)
           return new ResponseEntity<>(user1, HttpStatus.CREATED);

       return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);

    }
}
