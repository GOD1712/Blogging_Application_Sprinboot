package com.example.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.blog.repositories.UserRepo;

@SpringBootTest
class BloggingAppApplicationTests {
	
	@Autowired
	private UserRepo userRepo;

	@Test
	void contextLoads() {
	}
	
	@Test
	public void repoTest() {
		System.out.println(this.userRepo.getClass().getName());
		System.out.println(this.userRepo.getClass().getPackageName());
	}

}
