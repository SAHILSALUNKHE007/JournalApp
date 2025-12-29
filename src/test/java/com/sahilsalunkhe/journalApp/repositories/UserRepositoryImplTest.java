package com.sahilsalunkhe.journalApp.repositories;

import com.sahilsalunkhe.journalApp.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
public class UserRepositoryImplTest {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Test
    public void getUserForSA(){
        List<User> user=userRepository.getUserForSA();
        assertNotNull(user);
        if(!user.isEmpty()){
            log.info("Username {}",user.get(0).getUsername());
        }
        else {
            log.error("User not found");
        }
    }
}
