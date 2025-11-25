package com.sahilsalunkhe.journalApp.services;
import com.sahilsalunkhe.journalApp.repositories.UserRepositories;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
@Disabled
@SpringBootTest
@ActiveProfiles("dev")
public class UserServiceTest {


    @Autowired
    private UserRepositories userRepositories;
    @ParameterizedTest
    @ValueSource(strings=
    {
            "Sahil","Adarsh"
    })
    public void testFindByUserName(String name){

        assertNotNull(userRepositories.findByUsername(name));
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,2,3",
            "2,10,12"
    })
    public  void test(int a,int b,int expected){
        assertEquals(expected,a+b);
    }
}
