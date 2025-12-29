package com.sahilsalunkhe.journalApp.Repository;

import com.sahilsalunkhe.journalApp.repositories.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserRepositoryImplTest {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Test
    public void getUserForSA(){
        assertNotNull(userRepository.getUserForSA());
    }
}
