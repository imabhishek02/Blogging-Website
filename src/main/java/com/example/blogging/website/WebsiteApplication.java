package com.example.blogging.website;

import com.example.blogging.website.config.AppConstants;
import com.example.blogging.website.entity.Role;
import com.example.blogging.website.repository.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class WebsiteApplication {
	@Autowired
    private RoleRepo roleRepo;


	public static void main(String[] args) {
		SpringApplication.run(WebsiteApplication.class, args);

	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public void run(String... args){
		try {
			Role role = new Role();
			role.setId(AppConstants.ROLE_ADMIN);
			role.setName("ROLE_ADMIN");

			Role userRole = new Role();
			userRole.setId(AppConstants.ROLE_USER);
			userRole.setName("ROLE_USER");

			List<Role>listOfRoles = List.of(role,userRole);
            System.out.println(listOfRoles);
			roleRepo.saveAll(listOfRoles);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
