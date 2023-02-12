package com.example.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.blog.config.AppConstants;
import com.example.blog.entities.Role;
import com.example.blog.repositories.RoleRepo;

@SpringBootApplication
public class BloggingAppApplication implements CommandLineRunner{
	
	@Autowired
	private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BloggingAppApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			Role role1 = new Role();
			role1.setId(AppConstants.ADMIN_USER);
			role1.setName("ROLE_ADMIN");
			
			Role role2 = new Role();
			role2.setId(AppConstants.NORMAL_USER);
			role2.setName("ROLE_NORMAL");
			
			List<Role> roles = List.of(role1,role2);
			List<Role> savedRoles = this.roleRepo.saveAll(roles);
			
			savedRoles.forEach(role -> System.out.println(role.getName()));
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
