package com.sahilsalunkhe.journalApp;

import com.sahilsalunkhe.journalApp.repositories.UserRepositories;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled
class JournalAppApplicationTests {

	@Autowired
	private  UserRepositories userRepositories;
	@Test
	void contextLoads() {
	}



}
