package com.sahilsalunkhe.journalApp.services;

import com.sahilsalunkhe.journalApp.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailServiceImp implements UserDetailsService {
    @Autowired
    private  UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user=userService.findUserBYUserName(username);



        if(user.isPresent()){
            User user1=user.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user1.getUsername())
                    .password(user1.getPassword())
                    .roles(user1.getRoles().toArray(new String[0]))
                    .build();
        }
        throw  new UsernameNotFoundException("User Not found "+username);
    }
}
